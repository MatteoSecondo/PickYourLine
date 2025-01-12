import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

	@Mock
	private Controllore controlloreMock;

	@Mock
	private Automezzo automezzoMock;

	@Mock
	private Itinerario itinerarioMock;

	@Mock
	private Citta cittaMock;

	@Mock
	private Fermata fermataMock;

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
	@DisplayName("Test per verificare successo di ConfermaInserimento")
	void testConfermaInserimentoCambiaStatoBigliettoCorrente() {


		Citta Catania = new Citta(1, "Catania");
		Catania.getElencoFermate().add(new Fermata("Fermata Catania", Catania));

		Citta Misterbianco = new Citta(3, "Misterbianco");
		Misterbianco.getElencoFermate().add(new Fermata("Fermata Misterbianco", Misterbianco));

		List<Citta> percorso = new ArrayList<>();
		percorso.add(Catania);
		percorso.add(Misterbianco);

		Itinerario itinerarioFake = new Itinerario("as", LocalTime.of(12, 12), LocalTime.of(12, 12), percorso);

		Automezzo automezzo = new Automezzo("as", 1, itinerarioFake, new HashMap<>());
		Controllore controllore = new Controllore("as");
		controllore.setAutomezzoSupervisionato(automezzo);

		Biglietto bigliettoFake = new Biglietto("12345", Catania, Misterbianco);
		automezzo.setBigliettoCorrente(bigliettoFake);


		controllore.confermaInserimento();

		assertTrue(automezzo.getElencoBiglietti().containsKey("12345"));
	}



	@Test
	@DisplayName("Test della funzione aggiornaPosizioneAutomezzo nel caso in cui la posizione non sia buona")
	public void testAggiornaPosizioneAutomezzoFermataNonTrovata() throws Exception {
		// Setup
		when(controlloreMock.getAutomezzoSupervisionato()).thenReturn(automezzoMock);
		when(automezzoMock.getItinerarioAssegnato()).thenReturn(itinerarioMock);
		when(itinerarioMock.getPercorso()).thenReturn(List.of(cittaMock));
		when(cittaMock.getFermata("inventata")).thenReturn(null);
		pickYourLine.setUtenteCorrente(controlloreMock);

		// Act & Assert
		Exception exception = assertThrows(Exception.class, () ->
						pickYourLine.aggiornaPosizioneAutomezzo("inventata"),
				"Dovrebbe lanciare un'eccezione quando la fermata non è trovata."
		);

		assertEquals("Nome fermata non consentito.", exception.getMessage());
	}



	@Test
	@DisplayName("Test della funzione aggiornaPosizioneAutomezzo nel caso in cui la posizione vada bene")
	public void testAggiornaPosizioneAutomezzoFermataTrovata() throws Exception {
		// Setup
		when(controlloreMock.getAutomezzoSupervisionato()).thenReturn(automezzoMock);
		when(automezzoMock.getItinerarioAssegnato()).thenReturn(itinerarioMock);
		when(itinerarioMock.getPercorso()).thenReturn(List.of(cittaMock));
		when(cittaMock.getFermata("valida")).thenReturn(fermataMock);
		pickYourLine.setUtenteCorrente(controlloreMock);

		assertDoesNotThrow(() -> pickYourLine.aggiornaPosizioneAutomezzo("valida"),
				"Non dovrebbe lanciare eccezioni quando la fermata è trovata.");
	}



}
