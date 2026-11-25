package it.urgence.model;

import static org.junit.jupiter.api.Assertions.*;

import it.urgence.exception.DatiNonValidiException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PazienteTest {

    // Méthode d'aide : crée un patient valide, pour ne pas répéter 7 paramètres dans chaque test
  private Paziente creaPaziente(String codiceFiscale) throws DatiNonValidiException {
    return new Paziente('M', "Via Emilia 1",
            codiceFiscale, "Mario", "Rossi", LocalDate.of(1980, 1, 15), "3400000000");
}
    @Test
    void unPazienteValidoVieneCreato() throws DatiNonValidiException {
        // PREPARA + ESEGUI
        Paziente p = creaPaziente("RSSMRA80A15H223K");

        // VERIFICA
        assertEquals("Mario", p.getNome());
        assertEquals("Rossi", p.getCognome());
        assertEquals("RSSMRA80A15H223K", p.getCodiceFiscale());
    }

    @Test
    void ilCodiceFiscaleVieneMessoInMaiuscolo() throws DatiNonValidiException {
        Paziente p = creaPaziente("rssmra80a15h223k");

        assertEquals("RSSMRA80A15H223K", p.getCodiceFiscale());
    }

    @Test
    void unCodiceFiscaleTroppoCortoVieneRifiutato() {
        assertThrows(DatiNonValidiException.class, () -> creaPaziente("RSSMRA80"));
    }
}