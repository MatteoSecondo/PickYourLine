import static org.junit.jupiter.api.Assertions.*;
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
	
	@Mock
	private List<Citta> percorsoMock;

	@BeforeAll
	void setupAll(){
		this.pickYourLine = PickYourLine.getInstance();
		this.pickYourLine.loadCitta();
		this.pickYourLine.loadElencoSegnalazioni();
	}

	@BeforeEach
	void setup() throws Exception {
		this.pickYourLine.loadItinerari();
		MockitoAnnotations.openMocks(this);
		this.pickYourLine.loadControllori();
		this.pickYourLine.loadAutomezzi();
		this.pickYourLine.loadAvvisi();
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

	@Test
	@DisplayName("Test timbratura del biglietto con successo")
	void testTimbraBigliettoPostiConSuccesso() throws Exception{
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("aeiou",new Biglietto("H1357",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(4))); // Catania -> Paterno
		elencoBiglietti.put("bcdef",new Biglietto("H1358",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(6)));
		elencoBiglietti.put("bcdeg",new Biglietto("H1358",pickYourLine.getElencoCitta().get(3),
				pickYourLine.getElencoCitta().get(15)));

		Automezzo automezzo = new Automezzo("H123",10,pickYourLine.getElencoItinerari().get("Catania-Randazzo")
				,elencoBiglietti);

		Controllore controllore = new Controllore("C2343");
		controllore.setAutomezzoSupervisionato(automezzo);
		pickYourLine.setUtenteCorrente(controllore);

		Biglietto bigliettoVerifica = pickYourLine.timbraBiglietto("albero",1,3);
		assertNotNull(bigliettoVerifica);
	}

	@Test
	@DisplayName("Test timbra biglietto: posti non disponibili")
	void testTimbraBigliettoPostiNonDisponibili() throws Exception {
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("aeiou",new Biglietto("H1357",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(4))); // Catania -> Paterno
		elencoBiglietti.put("bcdef",new Biglietto("H1358",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(6)));
		elencoBiglietti.put("bcdeg",new Biglietto("H1358",pickYourLine.getElencoCitta().get(3),
				pickYourLine.getElencoCitta().get(15)));

		Automezzo automezzo = new Automezzo("H123",3,pickYourLine.getElencoItinerari().get("Catania-Randazzo")
				,elencoBiglietti);

		Controllore controllore = new Controllore("C2343");
		controllore.setAutomezzoSupervisionato(automezzo);
		pickYourLine.setUtenteCorrente(controllore);

		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.timbraBiglietto("albero",1,3));
		assertEquals("Non ci sono posti disponibili, operazione terminata.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test timbra biglietto: citta di partenza diversa rispetto a quella attuale")
	void testTimbraBigliettoCittaPartenzaDiversaDaCittaAttuale() throws Exception {
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("aeiou",new Biglietto("H1357",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(4))); // Catania -> Paterno
		elencoBiglietti.put("bcdef",new Biglietto("H1358",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(6)));
		elencoBiglietti.put("bcdeg",new Biglietto("H1358",pickYourLine.getElencoCitta().get(3),
				pickYourLine.getElencoCitta().get(15)));
		Automezzo automezzo = new Automezzo("H123",10,pickYourLine.getElencoItinerari().get("Catania-Randazzo")
				,elencoBiglietti);

		automezzo.setPosizioneAttuale(pickYourLine.getElencoCitta().get(6).getFermata("Adrano Nord"));

		Controllore controllore = new Controllore("C2343");
		controllore.setAutomezzoSupervisionato(automezzo);
		pickYourLine.setUtenteCorrente(controllore);


		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.timbraBiglietto("asjaf",1,6));
		assertEquals("Non è possibile partire da una città diversa da quella attuale.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test timbra biglietto: citta non appartiene all'itinerario ")
	void testTimbraBigliettoCittaCittaNonItinerario() throws Exception {
		List<Citta> elencoCitta = new ArrayList<>();
		elencoCitta.add(new Citta(99,"Genova"));
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("aeiou",new Biglietto("H1357",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(4))); // Catania -> Paterno
		elencoBiglietti.put("bcdef",new Biglietto("H1358",pickYourLine.getElencoCitta().get(1),
				pickYourLine.getElencoCitta().get(6)));
		elencoBiglietti.put("bcdeg",new Biglietto("H1358",pickYourLine.getElencoCitta().get(3),
				pickYourLine.getElencoCitta().get(15)));
		Automezzo automezzo = new Automezzo("H123",10,pickYourLine.getElencoItinerari().get("Catania-Randazzo"),
				elencoBiglietti);

		automezzo.setPosizioneAttuale(pickYourLine.getElencoCitta().get(1).getFermata("Catania Borgo"));

		Controllore controllore = new Controllore("C2343");
		controllore.setAutomezzoSupervisionato(automezzo);
		pickYourLine.setUtenteCorrente(controllore);


		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.timbraBiglietto("asjaf",1,5));
		assertEquals("Città di destinazione non presente nel percorso.", exception.getMessage());
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
		when(fermataMock.getCittaDiAppartenenza()).thenReturn(new Citta(98, "vbsd"));
		when(percorsoMock.getLast()).thenReturn(new Citta(99, "vbsdui"));
		pickYourLine.setUtenteCorrente(controlloreMock);

		assertDoesNotThrow(() -> pickYourLine.aggiornaPosizioneAutomezzo("valida"),
				"Non dovrebbe lanciare eccezioni quando la fermata è trovata e le citta sono diverse.");
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui vada a buon fine")
	public void testInserisciItinerario_Successo() throws Exception {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.inserisciItinerario("prova", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso);

		assertTrue(pickYourLine.getElencoItinerari().containsKey("prova"));
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui il codice sia già esistente")
	public void testInserisciItinerario_CodiceItinerarioEsistente() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.inserisciItinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Codice itinerario già esistente."
		);
		
		assertEquals("Codice itinerario già esistente.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui gli orari non siano validi")
	public void testInserisciItinerario_OrariNonValidi() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.inserisciItinerario("prova", LocalTime.of(12, 0), LocalTime.of(11, 0), percorso),
			"L'orario di arrivo deve essere successivo a quello di partenza."
		);
		
		assertEquals("L'orario di arrivo deve essere successivo a quello di partenza.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui il percorso ha meno di due città")
	public void testInserisciItinerario_PercorsoTroppoCorto() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.inserisciItinerario("prova", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Il percorso deve essere composto da almeno due città."
		);
		
		assertEquals("Il percorso deve essere composto da almeno due città.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui ci siano città inserite piu volte nel percorso")
	public void testInserisciItinerario_CittaDuplicateNelPercorso() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.inserisciItinerario("prova", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Il percorso non può contenere città duplicate."
		);
		
		assertEquals("Il percorso non può contenere città duplicate.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione modificaItinerario nel caso in cui vada a buon fine")
	public void testModificaItinerario_Successo() throws Exception {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(3));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Itinerario i = pickYourLine.getElencoItinerari().get("Caltagirone-Catania");
		LocalTime oldOrarioPartenza = i.getOrarioPartenza();
		LocalTime oldOrarioArrivo = i.getOrarioArrivo();
		List<Citta> oldPercorso = i.getPercorso();
		
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.modificaItinerario("Caltagirone-Catania", LocalTime.of(12, 30), LocalTime.of(13, 0), percorso);
		
		assertFalse(oldOrarioPartenza.equals(LocalTime.of(12, 30)));
		assertFalse(oldOrarioArrivo.equals(LocalTime.of(13, 0)));
		assertNotEquals(oldPercorso, percorso);
		
		oldOrarioPartenza = i.getOrarioPartenza();
		oldOrarioArrivo = i.getOrarioArrivo();
		oldPercorso = i.getPercorso();
		
		pickYourLine.modificaItinerario("Caltagirone-Catania", null, LocalTime.of(14, 30), new ArrayList<Citta>());

		assertTrue(oldOrarioPartenza.equals(LocalTime.of(12, 30)));
		assertFalse(oldOrarioArrivo.equals(LocalTime.of(14, 30)));
		assertEquals(oldPercorso, percorso);
	}
	
	@Test
	@DisplayName("Test della funzione modificaItinerario nel caso in cui il codice sia non esistente")
	public void testModificaItinerario_CodiceItinerarioNonEsistente() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.modificaItinerario("prova", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Codice itinerario non esistente."
		);
		
		assertEquals("Codice itinerario non esistente.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui gli orari non siano validi")
	public void testModificaItinerario_OrariNonValidi() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(3));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.modificaItinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(11, 0), percorso),
			"L'orario di arrivo deve essere successivo a quello di partenza."
		);
		
		assertEquals("L'orario di arrivo deve essere successivo a quello di partenza.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui il percorso ha meno di due città")
	public void testModificaItinerario_PercorsoTroppoCorto() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.modificaItinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Il percorso deve essere composto da almeno due città."
		);
		
		assertEquals("Il percorso deve essere composto da almeno due città.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione inserisciItinerario nel caso in cui ci siano città inserite piu volte nel percorso")
	public void testModificaItinerario_CittaDuplicateNelPercorso() {
		List<Citta> percorso = new ArrayList<Citta>();
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(1));
		percorso.add(pickYourLine.getElencoCitta().get(5));
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.modificaItinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(13, 0), percorso),
			"Il percorso non può contenere città duplicate."
		);
		
		assertEquals("Il percorso non può contenere città duplicate.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test della funzione eliminaItinerario")
	public void testEliminaItinerario_ItinerarioNonEsistente() {
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.eliminaItinerario("prova99"),
			"Codice itinerario non esistente."
		);
		
		assertEquals("Codice itinerario non esistente.", exception.getMessage());
	}

	@Test
	@DisplayName("Test di inserisciAutomezzo nel caso che vada a buon fine")
	public void testInserimentoAutomezzoValido() throws Exception {
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.inserisciAutomezzo("automezzo70", 50, "Catania-Randazzo");
		assertTrue(pickYourLine.getElencoAutomezzi().containsKey("automezzo70"));
	}


	@Test
	@DisplayName("Test fallimento inserimento automezzo con codice già esistente")
	public void testInserimentoAutomezzoCodiceEsistente() throws Exception {
		// Assumi che 'automezzo1' sia già inserito nel sistema
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.inserisciAutomezzo("automezzo1", 50, "Catania-Randazzo");
		Exception exception = assertThrows(Exception.class, () -> {
			pickYourLine.inserisciAutomezzo("automezzo1", 60, "Catania-Randazzo");
		});
		assertTrue(exception.getMessage().contains("Codice automezzo già esistente"));
	}

	@Test
	@DisplayName("Test fallimento inserimento automezzo con itinerario non esistente")
	public void testInserimentoAutomezzoItinerarioNonEsistente() {
		Exception exception = assertThrows(Exception.class, () -> {
			pickYourLine.inserisciAutomezzo("automezzo20", 50, "ItinerarioInesistente");
		});
		
		assertTrue(exception.getMessage().contains("Codice itinerario non esistente"));
	}

	@Test
	@DisplayName("Test successo modifica itinerario automezzo")
	public void testModificaAutomezzo_Successo() throws Exception {
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.inserisciAutomezzo("automezzo22", 50, "Catania-Randazzo");
		pickYourLine.modificaAutomezzo("automezzo22", 0,"Randazzo-Catania");
		Automezzo automezzo = pickYourLine.getElencoAutomezzi().get("automezzo2");
		assertNotNull(automezzo.getItinerarioAssegnato());
		assertEquals("Randazzo-Catania", automezzo.getItinerarioAssegnato().getCodice());
	}
	
	@Test
	@DisplayName("Test successo modifica stato automezzo")
	public void testModificaAutomezzo_Stato_Successo() throws Exception {
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		pickYourLine.inserisciAutomezzo("automezzo2", 50, "Catania-Randazzo");
		Automezzo automezzo = pickYourLine.getElencoAutomezzi().get("automezzo2");
		
		pickYourLine.modificaAutomezzo("automezzo2", 2,"Randazzo-Catania");
		
		assertTrue(automezzo.getStato() instanceof InManutenzione);
		assertEquals("Catania-Randazzo", automezzo.getItinerarioAssegnato().getCodice());
		
		pickYourLine.modificaAutomezzo("automezzo2", 1,"Randazzo-Catania");
		
		assertTrue(automezzo.getStato() instanceof NonInTransito);
		assertEquals("Randazzo-Catania", automezzo.getItinerarioAssegnato().getCodice());
		
		pickYourLine.modificaAutomezzo("automezzo2", 3,"Caltagirone-Catania");
		
		assertTrue(automezzo.getStato() instanceof Dismesso);
		assertEquals("Randazzo-Catania", automezzo.getItinerarioAssegnato().getCodice());
	}

	@Test
	@DisplayName("Test fallimento modifica automezzo non esistente")
	public void testModificaAutomezzoNonEsistente() {
		Exception exception = assertThrows(Exception.class, () -> {
			pickYourLine.modificaAutomezzo("automezzoInesistente", 0,"Catania-Randazzo");
		});
		
		assertTrue(exception.getMessage().contains("Automezzo non esistente o in transito."));
	}
	
	@Test
	@DisplayName("Test modifica automezzo nel caso in cui non ci sono automezzi da modificare")
	public void testModificaAutomezzo_AutomezziNonModificabili() {
		pickYourLine.getElencoAutomezzi().forEach((k, a) -> a.inSupervisione());
		
		Exception exception = assertThrows(Exception.class, () -> {
			pickYourLine.modificaAutomezzo("automezzoInesistente", 0,"Catania-Randazzo");
		});
		
		assertTrue(exception.getMessage().contains("Nessun automezzo modificabile."));
	}

	@Test
	@DisplayName("Test eliminazione automezzo esistente")
	public void testEliminazioneAutomezzoEsistente() throws Exception {
		pickYourLine.setUtenteCorrente(new Amministratore("001"));
		
		pickYourLine.inserisciAutomezzo("automezzo3", 50, "Catania-Randazzo");
		assertTrue(pickYourLine.getElencoAutomezzi().containsKey("automezzo3"));
		pickYourLine.eliminaAutomezzo("automezzo3");
		assertFalse(pickYourLine.getElencoAutomezzi().containsKey("automezzo3"));
	}

	@Test
	@DisplayName("Test eliminazione automezzo non esistente")
	public void testEliminazioneAutomezzoNonEsistente() {
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.eliminaAutomezzo("prova99"),
			"Automezzo non esistente o in transito."
		);
	
		assertEquals("Automezzo non esistente o in transito.", exception.getMessage());
	}

	@Test
	@DisplayName("Test per verificare visualizzaDettaglioSegnalazione in caso in cui il codice sia valido")
	void testVisualizzaDettaglioSegnalazioneConCodiceValido() throws Exception {
		String codiceValido = "s001";
		assertDoesNotThrow(() -> pickYourLine.visualizzaDettaglioSegnalazione(codiceValido));
	}

	@Test
	@DisplayName("Test per verificare visualizzaDettaglioSegnalazione in caso in cui il codice non sia valido")
	void testVisualizzaDettaglioSegnalazioneConCodiceNonValido() {
		String codiceNonValido = "s004";
		Exception exception = assertThrows(Exception.class, () -> {
			pickYourLine.visualizzaDettaglioSegnalazione(codiceNonValido);
		});

		assertEquals("Codice segnalazione non valido.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test visualizza fermate data una citta nel caso in cui il codice città non è valido")
	public void testVisualizzaElencoFermateCittaNonValida() throws Exception{
		int codiceCitta = 999;
		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.visualizzaFermate(codiceCitta));
		assertEquals("Codice città non non valido", exception.getMessage());
	}

	@Test
	@DisplayName("Test creazione segnalazione")
	public void testCreaSegnalazioneCorretto() {
		pickYourLine.setUtenteCorrente(pickYourLine.getElencoClienti().get("c74i"));
		
		Segnalazione sExpected = new Segnalazione("Critica Servizio","Servizio automezzi in ritardo");
		sExpected.setCliente(pickYourLine.getElencoClienti().get("c74i"));
		
		String oggettoActual = "Critica Servizio";
		String contenutoActual = "Servizio automezzi in ritardo";

		Segnalazione sActual = pickYourLine.creaSegnalazione(oggettoActual,contenutoActual);
		assertNotNull(sActual);
		assertEquals(sExpected.getOggetto(), sActual.getOggetto());
		assertEquals(sExpected.getContenuto(), sActual.getContenuto());
	}

	@Test
	@DisplayName("Test invio segnalazione con successo")
	public void testInvioSegnalazione() {
		Map<String,Segnalazione> elencoSegnalazioneActual = pickYourLine.getElencoSegnalazioni();

		Cliente cliente = new Cliente("12345","Cristian","Torrisi");
		pickYourLine.setUtenteCorrente(cliente);

		String oggetto = "Critica automezzi e traffico";
		String contenuto = "Insulti offensivi rivolti al cliente";

		Segnalazione segnalazione = new Segnalazione(oggetto,contenuto);
		pickYourLine.invioSegnalazione(segnalazione);

		assertTrue(elencoSegnalazioneActual.containsKey(segnalazione.getCodice()),
				"Elenco segnalazioni non presenta la nuova segnalazione inviate");
	}
	
	@Test
	@DisplayName("Test invio avviso con successo")
	public void testInserisciAvviso_Successo() {
		int oldSize = pickYourLine.getElencoAvvisi().size();
		pickYourLine.inserisciAvviso("test", "test");
		assertTrue(pickYourLine.getElencoAvvisi().size() == oldSize + 1);
	}
	
	@Test
	@DisplayName("Test modifica avviso con successo")
	public void testModificaAvviso_Successo() throws Exception {
		Avviso av = pickYourLine.getElencoAvvisi().get("a001");
		String oldContent = av.getContenuto();
		pickYourLine.modificaAvviso("a001", "oggetto", "0");
		
		assertEquals("oggetto", av.getOggetto());
		assertNotEquals(oldContent, "0");
	}
	
	@Test
	@DisplayName("Test modifica avviso nel caso in cui l'avviso non esista")
	public void testModificaAvviso_AvvisoNonEsistente() {
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.modificaAvviso("test", "modifica", "modifica"),
			"Codice avviso non esistente."
		);

		assertEquals("Codice avviso non esistente.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test elimina avviso con successo")
	public void testEliminaAvviso_Successo() throws Exception {
		int oldSize = pickYourLine.getElencoAvvisi().size();
		pickYourLine.eliminaAvviso("a001");
		assertTrue(pickYourLine.getElencoAvvisi().size() == oldSize - 1);
	}
	
	@Test
	@DisplayName("Test elimina avviso nel caso in cui l'avviso non esista")
	public void testEliminaAvviso_AvvisoNonEsistente(){
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.eliminaAvviso("test"),
			"Codice avviso non esistente."
		);

		assertEquals("Codice avviso non esistente.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test visualizza avvisi nel caso in cui l'elenco sia vuoto")
	public void testVisualizzaElencoAvvisi_ElencoVuoto() {
		pickYourLine.getElencoAvvisi().clear();
		
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.visualizzaElencoAvvisi(),
			"Non sono presenti avvisi nel sistema."
		);

		assertEquals("Non sono presenti avvisi nel sistema.", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test visualizza dettaglio avviso nel caso in cui l'avviso non esista")
	public void testVisualizzaDettaglioAvviso_AvvisoNonEsistente(){
		Exception exception = assertThrows(Exception.class, () ->
			pickYourLine.visualizzaDettaglioAvviso("test"),
			"Codice avviso non esistente."
		);

		assertEquals("Codice avviso non esistente.", exception.getMessage());
	}

	@Test
	@DisplayName("Test visualizza automezzi non in transito, nel caso in cui ho un elenco vuoto")
	public void testVisualizzaElencoAutomezziNonInTransitoElencoVuoto() {
		Map<String, Automezzo> elencoAutomezzi = pickYourLine.getElencoAutomezzi();
		pickYourLine.setUtenteCorrente(pickYourLine.getElencoControllori().get("f5b3"));

		elencoAutomezzi.forEach((k,a) ->
				a.setItinerarioAssegnato(pickYourLine.getElencoItinerari().get("Catania-Adrano Rapido")));

		elencoAutomezzi.forEach((k,a) -> a.inSupervisione());

		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.visualizzaElencoAutomezziNonInTransito());
		assertEquals("Nessun automezzo disponibile per la supervisione.", exception.getMessage());

	}

	@Test
	@DisplayName("Test supervisione automezzo con successo")
	public void testSupervisionaAutomezzoConSuccesso()  {
		Controllore controllore = pickYourLine.getElencoControllori().get("f5b3");
		String codiceAutomezzo = "C98";
		Map<String, Automezzo> automezziDisponibili = pickYourLine.getElencoAutomezzi();
		pickYourLine.setUtenteCorrente(controllore);

		automezziDisponibili.forEach((k,a) -> {
			if (a.getCodice().equals(codiceAutomezzo)) {
				a.nonInSupervisione();
				a.setItinerarioAssegnato(pickYourLine.getElencoItinerari().get("Catania-Paterno"));
			}
		});

		assertDoesNotThrow(() -> pickYourLine.supervisionaAutomezzo(codiceAutomezzo,automezziDisponibili));
		assertEquals(pickYourLine.getElencoAutomezzi().get("C98"),controllore.getAutomezzoSupervisionato());
		assertInstanceOf(InTransito.class,controllore.getAutomezzoSupervisionato().getStato());
	}

	@Test
	@DisplayName("Test supervisiona automezzo nel caso di codice automezzo non valido")
	public void testSupervisionaAutomezzoCodiceNonValido() {
		Controllore controllore = pickYourLine.getElencoControllori().get("f5b3");
		String automezzoNonEsistente = "H99";
		Map<String, Automezzo> automezziDisponibili = pickYourLine.getElencoAutomezzi();
		pickYourLine.setUtenteCorrente(controllore);

		automezziDisponibili.forEach((k,a) ->
				a.setItinerarioAssegnato(pickYourLine.getElencoItinerari().get("Catania-Adrano Rapido")));

		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.supervisionaAutomezzo(automezzoNonEsistente,automezziDisponibili));
		assertEquals("Codice automezzo non valido", exception.getMessage());
	}

	@Test
	@DisplayName("Test supervisiona automezzo nel caso di itinerario non assegnato all'automezzo")
	public void testSupervisionaAutomezzoItinerarioNonAssegnato() {
		Controllore controllore = pickYourLine.getElencoControllori().get("f5b3");
		String automezzoSenzaItinerario = "C98";
		Map<String, Automezzo> automezziDisponibili = pickYourLine.getElencoAutomezzi();
		pickYourLine.setUtenteCorrente(controllore);

		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.supervisionaAutomezzo(automezzoSenzaItinerario,automezziDisponibili));
		assertEquals("Assegna un itinerario prima di supervisionare questo automezzo", exception.getMessage());
	}

	@Test
	@DisplayName("Test fine corsa in caso di successo")
	public void testFineCorsaConSuccesso() {
		Controllore controllore = pickYourLine.getElencoControllori().get("f5b3");
		pickYourLine.setUtenteCorrente(controllore);
		Automezzo automezzoSupervisionato = pickYourLine.getElencoAutomezzi().get("C98");
		automezzoSupervisionato.setItinerarioAssegnato(pickYourLine.getElencoItinerari().get("Catania-Adrano Rapido"));
		automezzoSupervisionato.inSupervisione();
		controllore.setAutomezzoSupervisionato(automezzoSupervisionato);

		assertDoesNotThrow(() -> pickYourLine.fineCorsa());
		assertEquals(null,controllore.getAutomezzoSupervisionato());
		assertInstanceOf(NonInTransito.class,automezzoSupervisionato.getStato());
	}
	@Test
	@DisplayName("Test fine corsa nel caso controllore non supervisiona automezzo")
	public void testFineCorsaControlloreNonSupervisionaAutomezzo() {
		Controllore controllore = pickYourLine.getElencoControllori().get("f5b3");
		pickYourLine.setUtenteCorrente(controllore);
		controllore.setAutomezzoSupervisionato(null);

		Exception exception = assertThrows(Exception.class, () ->
				pickYourLine.fineCorsa());
		assertEquals("Non stai supervisionando nessun automezzo.", exception.getMessage());
	}


}
