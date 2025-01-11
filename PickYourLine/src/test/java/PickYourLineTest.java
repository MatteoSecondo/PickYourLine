import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	void setupAll(){
		this.pickYourLine = PickYourLine.getInstance();
		this.pickYourLine.loadCitta();
	}

	@BeforeEach
	void setup() throws Exception {
		this.pickYourLine.loadItinerari();
		MockitoAnnotations.openMocks(this);
	}

	@AfterAll
	void tearDownAll() throws Exception {
	}
	
	@Test
	@DisplayName("Test inserimento città di partenza")
	void testInserisciCittaDiPartenza() throws Exception {
		Map<String, Itinerario> elencoItinerari = new HashMap<String, Itinerario>();
		
		List<Citta> p = new ArrayList<Citta>();
		p.add(pickYourLine.getElencoCitta().get(1)); //Catania
		p.add(pickYourLine.getElencoCitta().get(3)); //Misterbianco
		p.add(pickYourLine.getElencoCitta().get(4)); //Paterno
		p.add(pickYourLine.getElencoCitta().get(36)); //Santa Maria di Licodia
		p.add(pickYourLine.getElencoCitta().get(12)); //Biancavilla
		p.add(pickYourLine.getElencoCitta().get(6)); //Adrano
		p.add(pickYourLine.getElencoCitta().get(15)); //Bronte
		p.add(pickYourLine.getElencoCitta().get(49)); //Maletto
		p.add(pickYourLine.getElencoCitta().get(28)); //Randazzo
		
		elencoItinerari.put("Catania-Randazzo",
				new Itinerario("Catania-Randazzo", LocalTime.of(9, 25), LocalTime.of(11, 35), p));
		
		this.pickYourLine.setElencoItinerari(elencoItinerari);
		
		Map<Integer,Citta> destinazioniDisponibili = pickYourLine.inserisciCittaPartenza(1);

		Map<Integer,Citta> destinazioniAttese = new HashMap<Integer,Citta>();
		destinazioniAttese.put(49,pickYourLine.getElencoCitta().get(49));
		destinazioniAttese.put(3,pickYourLine.getElencoCitta().get(3));
		destinazioniAttese.put(4,pickYourLine.getElencoCitta().get(4));
		destinazioniAttese.put(36,pickYourLine.getElencoCitta().get(36));
		destinazioniAttese.put(6,pickYourLine.getElencoCitta().get(6));
		destinazioniAttese.put(12,pickYourLine.getElencoCitta().get(12));
		destinazioniAttese.put(28,pickYourLine.getElencoCitta().get(28));
		destinazioniAttese.put(15,pickYourLine.getElencoCitta().get(15));

		assertEquals(destinazioniAttese, destinazioniDisponibili,"Elenco delle città di destinazione non corretto");
	}
	
	@Test
	@DisplayName("Test inserimento città di destinazione corretto funzionamento")
	void testInserisciCittaDestinazione_Successo() throws Exception {
		this.pickYourLine.setElencoItinerari(new HashMap<String, Itinerario>());
		
		this.pickYourLine.getElencoItinerari().put("IT01", itinerarioMock1);
		this.pickYourLine.getElencoItinerari().put("IT02", itinerarioMock2);
		
		this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		this.elencoDestinazioniDisponibili.put(5, this.pickYourLine.getElencoCitta().get(5));
		
		when(itinerarioMock1.getSeDisponibile(this.pickYourLine.getElencoCitta().get(1) ,this.pickYourLine.getElencoCitta().get(5))).thenReturn(itinerarioMock1);
		when(itinerarioMock1.getCodice()).thenReturn("IT01");
		
		when(itinerarioMock2.getSeDisponibile(this.pickYourLine.getElencoCitta().get(1), this.pickYourLine.getElencoCitta().get(5))).thenReturn(null);
		when(itinerarioMock2.getCodice()).thenReturn("IT02");
		
		Map<String, Itinerario> result = this.pickYourLine.inserisciCittaDestinazione(1, 5, this.elencoDestinazioniDisponibili);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("IT01"));
        
        this.elencoDestinazioniDisponibili.clear();
	}
	
	@Test
	@DisplayName("Test inserimento città di destinazione, citta partenza e destinazione uguali")
    void testInserisciCittaDestinazione_StessaCitta() {
		this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		
        Exception exception = assertThrows(Exception.class, () -> {
        	this.pickYourLine.inserisciCittaDestinazione(1, 1, this.elencoDestinazioniDisponibili);
        });
        assertEquals("La città di partenza e quella di destinazione sono la stessa città.", exception.getMessage());
    }

    @Test
    @DisplayName("Test inserimento città di destinazione, codice città non valido")
    void testInserisciCittaDestinazione_CodiceNonValido() {
    	this.elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
    	
        Exception exception = assertThrows(Exception.class, () -> {
            this.pickYourLine.inserisciCittaDestinazione(1, 3, this.elencoDestinazioniDisponibili);
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
	@DisplayName("Test visualizzazione itinerario, codice itinerario non idoneo")
	void testVisualizzaItinerarioValido() throws Exception {
		Map<String, Itinerario> itinerario = pickYourLine.getElencoItinerari();
		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.visualizzaItinerario("Catania-Salerno", itinerario));
		assertEquals("Codice itinerario non idoneo alla ricerca effettuata.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test visualizzazione automezzi in transito")
	void testVisualizzaAutomezziInTransito() {
		Automezzo a = new Automezzo("H23", 25, this.pickYourLine.getElencoItinerari().get("Randazzo-Catania"), null);
		this.pickYourLine.getElencoAutomezzi().put("H23", a);
		
		Controllore co = new Controllore("f5b3");
		co.setAutomezzoSupervisionato(a);
		this.pickYourLine.getElencoControllori().put("f5b3", co);
		
		this.pickYourLine.getElencoControllori().put("n7j5", new Controllore("n7j5"));
		this.pickYourLine.getElencoControllori().put("h1ig", new Controllore("h1ig"));
		this.pickYourLine.getElencoControllori().put("ba56", new Controllore("ba56"));
		this.pickYourLine.getElencoControllori().put("zy31", new Controllore("zy31"));
		
		Map<String, Automezzo> elencoAutomezziInTransitoAtteso = new HashMap<String, Automezzo>();
		
		elencoAutomezziInTransitoAtteso.put(a.getCodice(), a);
		
		Map<String, Automezzo> elencoAutomezziInTransito = pickYourLine.visualizzaAutomezziInTransito();
		
		assertEquals(elencoAutomezziInTransitoAtteso, elencoAutomezziInTransito, "Elenco automezzi in transito non corretto");
	}
	
	@Test
	@DisplayName("Test visualizzazione automezzo, codice non valido")
	void testVisualizzaAutomezzo() {
		Map<String, Automezzo> elencoAutomezziInTransito = new HashMap<String, Automezzo>();
		Automezzo a = new Automezzo("H23", 25, this.pickYourLine.getElencoItinerari().get("Randazzo-Catania"), null);
		elencoAutomezziInTransito.put(a.getCodice(), a);
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.visualizzaAutomezzo("H24", elencoAutomezziInTransito));
		assertEquals("Codice automezzo non valido.", exception.getMessage());
	}


}
