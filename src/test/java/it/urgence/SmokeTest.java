package it.urgence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Test di verifica: JUnit funziona e le risorse dell'interfaccia sono presenti.
 */
class SmokeTest {

    @Test
    void laVistaPrincipaleEsiste() {
        assertNotNull(App.class.getResource("view/main.fxml"));
    }

    @Test
    void ilFoglioDiStileEsiste() {
        assertNotNull(App.class.getResource("css/style.css"));
    }
}
