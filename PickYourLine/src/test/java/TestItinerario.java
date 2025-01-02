import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ItinerarioTest {

    private Citta milano;
    private Citta roma;
    private Citta napoli;
    private Itinerario itinerario;
    private PickYourLine pickyourline;




    @BeforeEach

    void setUp() {
        milano = new Citta(1, "Milano", null);
        roma = new Citta(2, "Roma", null);
        napoli = new Citta(3, "Napoli", null);

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
    void testGetSeDisponibile_Positivo() {
        // Test caso positivo: Milano -> Roma
        PickYourLine.getInstance().setCittaPartenzaCorrente(milano);
        Itinerario result = itinerario.getSeDisponibile(roma);
        assertNotNull(result, "Il risultato non dovrebbe essere null per una direzione valida.");
    }

    @Test
    void testGetSeDisponibile_Negativo() {
        // Test caso negativo: Roma -> Milano (direzione inversa)
        PickYourLine.getInstance().setCittaPartenzaCorrente(roma);
        Itinerario result = itinerario.getSeDisponibile(milano);
        assertNull(result, "Il risultato dovrebbe essere null per la direzione inversa.");
    }

    @Test
    void testGetSeDisponibile_CittaNonNelPercorso() {
        // Test città non nel percorso
        Citta torino = new Citta(4, "Torino", null);
        Itinerario result = itinerario.getSeDisponibile(torino);
        assertNull(result, "Il risultato dovrebbe essere null per una città che non è nel percorso.");
    }

    @Test
    void testGetDestinazioniDisponibili_ContienePiuDestinazioni() {
        // Test partendo da Milano (deve ritornare Roma e Napoli)
        PickYourLine.getInstance().setCittaPartenzaCorrente(milano);
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili();

        assertEquals(2, destinazioni.size(), "Dovrebbero esserci 2 destinazioni disponibili.");
        assertTrue(destinazioni.containsValue(roma), "Roma dovrebbe essere disponibile.");
        assertTrue(destinazioni.containsValue(napoli), "Napoli dovrebbe essere disponibile.");
    }

    @Test
    void testGetDestinazioniDisponibili_ContieneUnaDestinazioni() {
        // Test partendo da Roma (deve ritornare solo Napoli)
        PickYourLine.getInstance().setCittaPartenzaCorrente(roma);
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili();

        assertEquals(1, destinazioni.size(), "Dovrebbe esserci 1 destinazione disponibile.");
        assertTrue(destinazioni.containsValue(napoli), "Napoli dovrebbe essere disponibile.");
        assertFalse(destinazioni.containsValue(roma), "Roma non dovrebbe essere inclusa come destinazione disponibile.");
    }

    @Test
    void testGetDestinazioniDisponibili_ContieneNessunaDestinazione() {
        // Test partendo da Napoli (nessuna destinazione disponibile)
        PickYourLine.getInstance().setCittaPartenzaCorrente(napoli);
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili();

        assertTrue(destinazioni.isEmpty(), "Non ci dovrebbero essere destinazioni disponibili.");
    }

    @Test
    void testGetDestinazioniDisponibili_CittaNonInPercorso() {
        // Test partendo da una città non nel percorso (Torino)
        Citta torino = new Citta(4, "Torino", null);
        PickYourLine.getInstance().setCittaPartenzaCorrente(torino);
        Map<Integer, Citta> destinazioni = itinerario.getDestinazioniDisponibili();

        assertTrue(destinazioni.isEmpty(), "Non ci dovrebbero essere destinazioni disponibili per una città non nel percorso.");
    }
}