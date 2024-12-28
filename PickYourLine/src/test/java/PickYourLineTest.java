import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@TestInstance(Lifecycle.PER_CLASS)
class PickYourLineTest {
	
	private PickYourLine pickYourLine;
	private Map<Integer, Citta> elencoDestinazioniDisponibili;
	
	@Mock
    private Itinerario itinerarioMock1;
	
	@Mock
    private Itinerario itinerarioMock2;

	@BeforeAll
	void setupBeforeAll(){
		this.pickYourLine = PickYourLine.getInstance();
		this.pickYourLine.loadCitta();
	}

	@BeforeEach
	void setup() throws Exception {
		this.pickYourLine.loadItinerari();
		MockitoAnnotations.openMocks(this); // Inizializza i mock

		this.pickYourLine.setCittaPartenzaCorrente(this.pickYourLine.getElencoCitta().get(1));
	}

	@AfterEach
	void tearDown() throws Exception {
		this.pickYourLine.setCittaPartenzaCorrente(null);
		this.elencoDestinazioniDisponibili.clear();
	}
	@Test
	@DisplayName("Test inserimento città di partenza")
	void testInserisciCittaDiPartenza() throws Exception {
		Map<Integer,Citta> destinazioniDisponibili = pickYourLine.inserisciCittaPartenza(1);

		Map<Integer,Citta> destinazioniAttese = new HashMap<Integer,Citta>();
		destinazioniAttese.put(49,pickYourLine.getElencoCitta().get(49));
		destinazioniAttese.put(3,pickYourLine.getElencoCitta().get(3));
		destinazioniAttese.put(4,pickYourLine.getElencoCitta().get(4));
		destinazioniAttese.put(36,pickYourLine.getElencoCitta().get(36));
		destinazioniAttese.put(5,pickYourLine.getElencoCitta().get(5));
		destinazioniAttese.put(6,pickYourLine.getElencoCitta().get(6));
		destinazioniAttese.put(59,pickYourLine.getElencoCitta().get(59));
		destinazioniAttese.put(12,pickYourLine.getElencoCitta().get(12));
		destinazioniAttese.put(28,pickYourLine.getElencoCitta().get(28));
		destinazioniAttese.put(60,pickYourLine.getElencoCitta().get(60));
		destinazioniAttese.put(15,pickYourLine.getElencoCitta().get(15));

		assertEquals(destinazioniAttese, destinazioniDisponibili,"Elenco delle città destinazione previste: ");

	}
	@Test
	void testInserisciCittaDestinazione_Successo() throws Exception {
		pickYourLine.setElencoItinerari(new HashMap<String,Itinerario>());
		this.pickYourLine.getElencoItinerari().put("IT01", itinerarioMock1);
		this.pickYourLine.getElencoItinerari().put("IT02", itinerarioMock2);
		this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		this.elencoDestinazioniDisponibili.put(5, this.pickYourLine.getElencoCitta().get(5));
		
		when(itinerarioMock1.getSeDisponibile(this.pickYourLine.getElencoCitta().get(5))).thenReturn(itinerarioMock1);
		when(itinerarioMock1.getCodice()).thenReturn("IT01");
		
		when(itinerarioMock2.getSeDisponibile(this.pickYourLine.getElencoCitta().get(5))).thenReturn(null);
		when(itinerarioMock2.getCodice()).thenReturn("IT02");
		
		Map<String, Itinerario> result = this.pickYourLine.inserisciCittaDestinazione(5, this.elencoDestinazioniDisponibili);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("IT01"));
	}
	
	@Test
    void testInserisciCittaDestinazione_StessaCitta() {
		this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		
        Exception exception = assertThrows(Exception.class, () -> {
        	this.pickYourLine.inserisciCittaDestinazione(1, this.elencoDestinazioniDisponibili);
        });
        assertEquals("La città di partenza e quella di destinazione sono la stessa città.", exception.getMessage());
    }

    @Test
    void testInserisciCittaDestinazione_CodiceNonValido() {
    	this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
    	
        Exception exception = assertThrows(Exception.class, () -> {
            this.pickYourLine.inserisciCittaDestinazione(3, this.elencoDestinazioniDisponibili);
        });
        assertEquals("Codice città non non idoneo alla ricerca effettuata.", exception.getMessage());
    }
	@Test
	@DisplayName("Test inserimento città di partenza inesistente")
	void testInserisciCittaDiPartenzaInesistente() {
		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.inserisciCittaPartenza(999));
		assertEquals("Codice città non esistente.", exception.getMessage());
	}

	@Test
	@DisplayName("Test visualizzazione itinerario valido")
	void testVisualizzaItinerarioValido() throws Exception {
		Map<String, Itinerario> itinerario = pickYourLine.getElencoItinerari();
		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.visualizzaItinerario("Catania-Salerno",itinerario));
		assertEquals("Codice itinerario non idoneo alla ricerca effettuata.", exception.getMessage());
	}


}
