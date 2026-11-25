package it.urgence.model;

import static org.junit.jupiter.api.Assertions.*;

import it.urgence.exception.DatiNonValidiException;
import it.urgence.exception.MedicoNonDisponibileException;
import it.urgence.exception.UrgenceFlowException;
import it.urgence.model.enums.CodiceTriage;
import it.urgence.model.enums.ModalitaArrivo;
import it.urgence.model.enums.Turno;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicoTest {

    private Medico medico;
    private Accesso accesso1, accesso2, accesso3, accesso4;

    @BeforeEach
    void prepara() throws DatiNonValidiException {
        medico = new Medico("Medicina d'urgenza", "MED-014", Turno.MATTINA,
                "BNCNDR75C10H223Z", "Andrea", "Bianchi", LocalDate.of(1975, 3, 10), "3400000001");
        accesso1 = creaAccesso("RSSMRA80A15H223K", "Mario");
        accesso2 = creaAccesso("VRDLGU85B20H223W", "Luigi");
        accesso3 = creaAccesso("BRNNNA90C30H223X", "Anna");
        accesso4 = creaAccesso("FRRPLA70D12H223Y", "Paola");
    }

    // Ogni accesso ha il suo paziente, con un codice fiscale diverso
    private Accesso creaAccesso(String codiceFiscale, String nome) throws DatiNonValidiException {
        Paziente p = new Paziente('M', "Via Emilia 1", codiceFiscale,
                nome, "Rossi", LocalDate.of(1980, 1, 15), "3400000000");
        return new Accesso(CodiceTriage.VERDE, p, LocalDateTime.now().minusMinutes(10),
                ModalitaArrivo.AUTONOMO, "Febbre");
    }

    @Test
    void unMedicoSenzaPazientiEDisponibile() {
        assertTrue(medico.isDisponibile());
        assertEquals(0, medico.getCarico());
    }

    @Test
    void unQuartoPazienteVieneRifiutato() throws UrgenceFlowException {
        medico.assegna(accesso1);
        medico.assegna(accesso2);
        medico.assegna(accesso3);

        assertFalse(medico.isDisponibile());
        assertThrows(MedicoNonDisponibileException.class, () -> medico.assegna(accesso4));
    }

    @Test
    void unAccessoRilasciatoLiberaUnPosto() throws UrgenceFlowException {
        medico.assegna(accesso1);
        medico.assegna(accesso2);
        medico.assegna(accesso3);

        medico.rilascia(accesso1);

        assertEquals(2, medico.getCarico());
        assertTrue(medico.isDisponibile());
    }
}