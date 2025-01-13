import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class ItinerarioTest {

    private Citta milano;
    private Citta roma;
    private Citta napoli;
    private Itinerario itinerario;

    @BeforeAll
    void setUpAll() {
        milano = new Citta(1, "Milano");
        roma = new Citta(2, "Roma");
        napoli = new Citta(3, "Napoli");

        List<Citta> percorso = new ArrayList<>();
        percorso.add(milano);
        percorso.add(roma);
        percorso.add(napoli);

        itinerario = new Itinerario("Milano-Napoli",
                LocalTime.of(10, 0),
                LocalTime.of(14, 0),
                percorso);
    }

    @Test
    @DisplayName("Test funzionamento corretto di disponibilità")
    void testGetSeDisponibile_Positivo() {
        // Test caso positivo: Milano -> Roma
        Itinerario result = itinerario.getSeDisponibile(milano, roma);
        assertNotNull(result, "Il risultato non dovrebbe essere null per una direzione valida.");
    }

    @Test
    @DisplayName("Test funzionamento corretto di disponibilità, direzione inversa")
    void testGetSeDisponibile_Negativo() {
        // Test caso negativo: Roma -> Milano (direzione inversa)
        Itinerario result = itinerario.getSeDisponibile(roma, milano);
        assertNull(result, "Il risultato dovrebbe essere null per la direzione inversa.");
    }

    @Test
    @DisplayName("Test eccezione di città non appartenente al percorso")
    void testGetSeDisponibile_CittaNonNelPercorso() {
        // Test città non nel percorso
        Citta torino = new Citta(4, "Torino");
        Itinerario result = itinerario.getSeDisponibile(milano, torino);
        assertNull(result, "Il risultato dovrebbe essere null per una città che non è nel percorso.");
    }

    @Test
    @DisplayName("Test funzionamento corretto di disponibilità, più destinazioni")
    void testGetDestinazioniDisponibili_ContienePiuDestinazioni() {
        // Test partendo da Milano (deve ritornare Roma e Napoli)
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili(milano);

        assertEquals(2, destinazioni.size(), "Dovrebbero esserci 2 destinazioni disponibili.");
        assertTrue(destinazioni.containsValue(roma), "Roma dovrebbe essere disponibile.");
        assertTrue(destinazioni.containsValue(napoli), "Napoli dovrebbe essere disponibile.");
    }

    @Test
    @DisplayName("Test funzionamento corretto di disponibilità, una destinazione")
    void testGetDestinazioniDisponibili_ContieneUnaDestinazioni() {
        // Test partendo da Roma (deve ritornare solo Napoli)
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili(roma);

        assertEquals(1, destinazioni.size(), "Dovrebbe esserci 1 destinazione disponibile.");
        assertTrue(destinazioni.containsValue(napoli), "Napoli dovrebbe essere disponibile.");
        assertFalse(destinazioni.containsValue(roma), "Roma non dovrebbe essere inclusa come destinazione disponibile.");
    }

    @Test
    @DisplayName("Test funzionamento corretto di disponibilità, nessuna destinazione")
    void testGetDestinazioniDisponibili_ContieneNessunaDestinazione() {
        // Test partendo da Napoli (nessuna destinazione disponibile)
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili(napoli);

        assertTrue(destinazioni.isEmpty(), "Non ci dovrebbero essere destinazioni disponibili.");
    }

    @Test
    @DisplayName("Test eccezione di città non appartenente al percorso")
    void testGetDestinazioniDisponibili_CittaNonInPercorso() {
        // Test partendo da una città non nel percorso (Torino)
        Citta torino = new Citta(4, "Torino");
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili(torino);

        assertTrue(destinazioni.isEmpty(), "Non ci dovrebbero essere destinazioni disponibili per una città non nel percorso.");
    }
}