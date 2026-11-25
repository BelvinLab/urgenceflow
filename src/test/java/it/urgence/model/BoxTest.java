package it.urgence.model;

import static org.junit.jupiter.api.Assertions.*;

import it.urgence.exception.BoxNonDisponibileException;
import it.urgence.exception.DatiNonValidiException;
import it.urgence.exception.UrgenceFlowException;
import it.urgence.model.enums.CodiceTriage;
import it.urgence.model.enums.ModalitaArrivo;
import it.urgence.model.enums.StatoBox;
import it.urgence.model.enums.TipoBox;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoxTest {

    private Box box;
    private Accesso accesso;

    @BeforeEach
    void prepara() throws DatiNonValidiException {
        box = new Box("B1", TipoBox.VISITA);
        Paziente p = new Paziente('M', "Via Emilia 1", "RSSMRA80A15H223K",
                "Mario", "Rossi", LocalDate.of(1980, 1, 15), "3400000000");
        accesso = new Accesso(CodiceTriage.VERDE, p, LocalDateTime.now().minusMinutes(10),
        ModalitaArrivo.AUTONOMO, "Febbre");
    }

    @Test
    void unBoxNuovoELibero() {
        assertEquals(StatoBox.LIBERO, box.getStato());
    }

    @Test
    void occupareUnBoxLoRendeOccupato() throws UrgenceFlowException {
        box.occupa(accesso);

        assertEquals(StatoBox.OCCUPATO, box.getStato());
        assertEquals(accesso, box.getAccessoCorrente());
    }

    @Test
    void occupareUnBoxGiaOccupatoVieneRifiutato() throws UrgenceFlowException {
        box.occupa(accesso);

        assertThrows(BoxNonDisponibileException.class, () -> box.occupa(accesso));
    }

    @Test
    void liberareUnBoxLoMetteInPulizia() throws UrgenceFlowException {
        box.occupa(accesso);
        box.libera();

        assertEquals(StatoBox.IN_PULIZIA, box.getStato());
        assertNull(box.getAccessoCorrente());
    }

    @Test
    void segnaPulitoRimetteIlBoxLibero() throws UrgenceFlowException {
        box.occupa(accesso);
        box.libera();
        box.segnaPulito();

        assertEquals(StatoBox.LIBERO, box.getStato());
    }
}