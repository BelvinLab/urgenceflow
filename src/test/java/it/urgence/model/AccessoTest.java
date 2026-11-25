package it.urgence.model;

import static org.junit.jupiter.api.Assertions.*;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.exception.StatoAccessoNonValidoException;
import it.urgence.exception.UrgenceFlowException;
import it.urgence.model.enums.CodiceTriage;
import it.urgence.model.enums.Esito;
import it.urgence.model.enums.ModalitaArrivo;
import it.urgence.model.enums.StatoAccesso;
import it.urgence.model.enums.TipoBox;
import it.urgence.model.enums.Turno;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.PriorityQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccessoTest {

    // Un'ora fissa NEL PASSATO: il costruttore rifiuta un arrivo futuro
    private static final LocalDateTime ORE_10 = LocalDateTime.of(2026, 11, 20, 10, 0);

    private Paziente paziente;
    private Box box;
    private Medico medico;

    @BeforeEach
    void prepara() throws DatiNonValidiException {
        paziente = new Paziente('M', "Via Emilia 1", "RSSMRA80A15H223K",
                "Mario", "Rossi", LocalDate.of(1980, 1, 15), "3400000000");
        box = new Box("B1", TipoBox.VISITA);
        medico = new Medico("Medicina d'urgenza", "MED-014", Turno.MATTINA,
                "BNCNDR75C10H223Z", "Andrea", "Bianchi", LocalDate.of(1975, 3, 10), "3400000001");
    }

    // Crea un accesso con il codice dato, arrivato "minuti" dopo le 10:00
    private Accesso creaAccesso(CodiceTriage codice, int minuti) throws DatiNonValidiException {
        return new Accesso(codice, paziente, ORE_10.plusMinutes(minuti),
                ModalitaArrivo.AUTONOMO, "Febbre");
    }

    // ---------- 1. Creazione ----------

    @Test
    void unAccessoNuovoEInAttesa() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);

        assertEquals(StatoAccesso.IN_ATTESA, a.getStato());
        assertEquals(CodiceTriage.VERDE, a.getCodice());
        assertTrue(a.getStoricoTriage().isEmpty());
    }

    @Test
    void unSintomoVuotoVieneRifiutato() {
        assertThrows(DatiNonValidiException.class, () ->
                new Accesso(CodiceTriage.VERDE, paziente, ORE_10, ModalitaArrivo.AUTONOMO, "  "));
    }

    @Test
    void unArrivoNelFuturoVieneRifiutato() {
        LocalDateTime domani = LocalDateTime.now().plusDays(1);
        assertThrows(DatiNonValidiException.class, () ->
                new Accesso(CodiceTriage.VERDE, paziente, domani, ModalitaArrivo.AUTONOMO, "Febbre"));
    }

    // ---------- 2. Ordine della coda (compareTo) ----------

    @Test
    void unRossoPassaPrimaDiUnVerdeAncheSeArrivatoDopo() throws DatiNonValidiException {
        Accesso verde = creaAccesso(CodiceTriage.VERDE, 0);
        Accesso rosso = creaAccesso(CodiceTriage.ROSSO, 30);

        assertTrue(rosso.compareTo(verde) < 0);
        assertTrue(verde.compareTo(rosso) > 0);
    }

    @Test
    void aParitaDiCodicePassaChiEArrivatoPrima() throws DatiNonValidiException {
        Accesso primo = creaAccesso(CodiceTriage.AZZURRO, 0);
        Accesso secondo = creaAccesso(CodiceTriage.AZZURRO, 10);

        assertTrue(primo.compareTo(secondo) < 0);
    }

    @Test
    void laPriorityQueueRestituisceIPazientiNellOrdineGiusto() throws DatiNonValidiException {
        Accesso bianco    = creaAccesso(CodiceTriage.BIANCO, 0);
        Accesso verde     = creaAccesso(CodiceTriage.VERDE, 5);
        Accesso arancione = creaAccesso(CodiceTriage.ARANCIONE, 10);
        Accesso rosso     = creaAccesso(CodiceTriage.ROSSO, 15);

        PriorityQueue<Accesso> coda = new PriorityQueue<>();
        coda.add(bianco);
        coda.add(verde);
        coda.add(arancione);
        coda.add(rosso);

        assertEquals(rosso, coda.poll());
        assertEquals(arancione, coda.poll());
        assertEquals(verde, coda.poll());
        assertEquals(bianco, coda.poll());
    }

    // ---------- 3. Rivalutazione del triage ----------

    @Test
    void rivalutareCambiaIlCodiceERegistraLoStorico() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);

        a.rivaluta(CodiceTriage.ARANCIONE);

        assertEquals(CodiceTriage.ARANCIONE, a.getCodice());
        assertEquals(1, a.getStoricoTriage().size());
        VariazioneTriage v = a.getStoricoTriage().get(0);
        assertEquals(CodiceTriage.VERDE, v.da());
        assertEquals(CodiceTriage.ARANCIONE, v.a());
    }

    @Test
    void nonSiPuoRivalutareUnAccessoConcluso() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);
        a.prendiInCarico(box, medico);
        a.dimetti(Esito.DOMICILIO);

        assertThrows(StatoAccessoNonValidoException.class,
                () -> a.rivaluta(CodiceTriage.ROSSO));
    }

    // ---------- 4. Ciclo di vita: presa in carico e dimissione ----------

    @Test
    void prendereInCaricoAssegnaBoxEMedico() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.AZZURRO, 0);

        a.prendiInCarico(box, medico);

        assertEquals(StatoAccesso.IN_CARICO, a.getStato());
        assertEquals(box, a.getBox());
        assertEquals(medico, a.getMedico());
    }

    @Test
    void prendereInCaricoDueVolteVieneRifiutato() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.AZZURRO, 0);
        a.prendiInCarico(box, medico);

        assertThrows(StatoAccessoNonValidoException.class, () -> a.prendiInCarico(box, medico));
    }

    @Test
    void dimettereUnPazienteAncoraInAttesaVieneRifiutato() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);

        assertThrows(StatoAccessoNonValidoException.class, () -> a.dimetti(Esito.DOMICILIO));
    }

    @Test
    void dimettereConcludeLAccessoERegistraLEsito() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.AZZURRO, 0);
        a.prendiInCarico(box, medico);

        a.dimetti(Esito.RICOVERO);

        assertEquals(StatoAccesso.CONCLUSO, a.getStato());
        assertEquals(Esito.RICOVERO, a.getEsito());
    }

    // ---------- 5. Tempo di attesa e allerte ----------

    @Test
    void ilTempoDiAttesaVieneCalcolatoInMinuti() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);

        assertEquals(25, a.getAttesaMinuti(ORE_10.plusMinutes(25)));
    }

    @Test
    void unArancioneDopo20MinutiEOltreLAttesaMassima() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.ARANCIONE, 0);   // massimo 15 min

        assertTrue(a.isOltreAttesaMassima(ORE_10.plusMinutes(20)));
    }

    @Test
    void unArancioneEsattamenteA15MinutiNonEAncoraInRitardo() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.ARANCIONE, 0);

        assertFalse(a.isOltreAttesaMassima(ORE_10.plusMinutes(15)));
    }

    @Test
    void unVerdeDopo20MinutiNonEInRitardo() throws DatiNonValidiException {
        Accesso a = creaAccesso(CodiceTriage.VERDE, 0);       // massimo 120 min

        assertFalse(a.isOltreAttesaMassima(ORE_10.plusMinutes(20)));
    }

    @Test
    void unPazienteGiaInCaricoNonEInRitardo() throws UrgenceFlowException {
        Accesso a = creaAccesso(CodiceTriage.ARANCIONE, 0);
        a.prendiInCarico(box, medico);

        assertFalse(a.isOltreAttesaMassima(ORE_10.plusMinutes(60)));
    }
}