import at.favre.lib.crypto.bcrypt.BCrypt;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PickYourLine {
	private static PickYourLine pickYourLine;
	private Utente utenteCorrente;
	private Map<Integer, Citta> elencoCitta;
	private Map<String, Itinerario> elencoItinerari;
	private Map<String, Automezzo> elencoAutomezzi;
	private Map<String,Segnalazione> elencoSegnalazioni;
	private Map<String, Avviso> elencoAvvisi;
	private Map<String, Utente> elencoUtenti;

	private PickYourLine() {
		this.elencoItinerari = new HashMap<String, Itinerario>();
		this.elencoCitta = new HashMap<Integer, Citta>();
		this.elencoUtenti = new HashMap<String, Utente>();
		this.elencoAutomezzi = new HashMap<String, Automezzo>();
		this.elencoSegnalazioni = new HashMap<String, Segnalazione>();
		this.elencoAvvisi = new HashMap<String, Avviso>();
	}
	
	public static PickYourLine getInstance() {
		if(pickYourLine == null) {
			pickYourLine = new PickYourLine();
		}
		
		return pickYourLine;
	}
	
	public Utente getUtenteCorrente() {
		return utenteCorrente;
	}

	public void setUtenteCorrente(Utente utenteCorrente) {
		this.utenteCorrente = utenteCorrente;
	}
	
	public Map<Integer, Citta> getElencoCitta() {
		return elencoCitta;
	}
	
	public Map<String, Itinerario> getElencoItinerari() {
		return elencoItinerari;
	}

	public void setElencoItinerari(Map<String, Itinerario> elencoItinerari) {
		this.elencoItinerari = elencoItinerari;
	}

	public Map<String, Automezzo> getElencoAutomezzi() {
		return elencoAutomezzi;
	}

	public Map<String, Avviso> getElencoAvvisi() {
		return elencoAvvisi;
	}

	public Map<String,Segnalazione> getElencoSegnalazioni() {
		return elencoSegnalazioni;
	}

	public Map<String, Utente> getElencoUtenti() {
		return elencoUtenti;
	}

	public void loadItinerari() {
		List<Citta> p = new ArrayList<Citta>();
		p.add(elencoCitta.get(1)); //Catania
		p.add(elencoCitta.get(3)); //Misterbianco
		p.add(elencoCitta.get(4)); //Paterno
		p.add(elencoCitta.get(36)); //Santa Maria di Licodia
		p.add(elencoCitta.get(12)); //Biancavilla
		p.add(elencoCitta.get(6)); //Adrano
		p.add(elencoCitta.get(15)); //Bronte
		p.add(elencoCitta.get(49)); //Maletto
		p.add(elencoCitta.get(28)); //Randazzo
		this.elencoItinerari.put("Catania-Randazzo",
				new Itinerario("Catania-Randazzo", LocalTime.of(9, 25), LocalTime.of(11, 35), p));
		this.elencoItinerari.put("Randazzo-Catania",
				new Itinerario("Randazzo-Catania", LocalTime.of(10, 10), LocalTime.of(12, 15), p.reversed()));
		
		List<Citta> p1 = new ArrayList<Citta>();
		p1.add(elencoCitta.get(1));
		//p1.add(elencoCitta.get(19));
		p1.add(elencoCitta.get(5));
		this.elencoItinerari.put("Catania-Caltagirone",
				new Itinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(14, 0), p1));
		this.elencoItinerari.put("Caltagirone-Catania",
				new Itinerario("Caltagirone-Catania", LocalTime.of(12, 0), LocalTime.of(14, 0), p1.reversed()));
		
		List<Citta> p2 = new ArrayList<Citta>();
		p2.add(elencoCitta.get(1)); //Catania
		p2.add(elencoCitta.get(3)); //Misterbianco
		p2.add(elencoCitta.get(60)); //Piano Tavola
		p2.add(elencoCitta.get(59)); //Valcorrente
		p2.add(elencoCitta.get(4)); //Paterno
		this.elencoItinerari.put("Catania-Paterno",
				new Itinerario("Catania-Paterno", LocalTime.of(13, 25), LocalTime.of(14, 30), p2));
		this.elencoItinerari.put("Paterno-Catania",
				new Itinerario("Paterno-Catania", LocalTime.of(18, 25), LocalTime.of(19, 15), p2.reversed()));
		
		List<Citta> p3 = new ArrayList<Citta>();
		p3.add(elencoCitta.get(1)); //Catania
		p3.add(elencoCitta.get(36)); //Santa Maria di Licodia
		p3.add(elencoCitta.get(12)); //Biancavilla
		p3.add(elencoCitta.get(6)); //Adrano
		this.elencoItinerari.put("Catania-Adrano Rapido",
				new Itinerario("Catania-Adrano Rapido", LocalTime.of(13, 25), LocalTime.of(14, 40), p3));
		this.elencoItinerari.put("Adrano-Catania Rapido",
				new Itinerario("Adrano-Catania Rapido", LocalTime.of(17, 50), LocalTime.of(18, 50), p3.reversed()));
	}
	
	public void loadCitta() {
		Citta c1 = new Citta(1, "Catania");
		List<Fermata> f = new ArrayList<Fermata>();
		f.add(new Fermata("Catania Borgo", c1));
		f.add(new Fermata("Catania Nesima", c1));
		f.add(new Fermata("Catania Ospedale Cannizzaro", c1));
		c1.loadFermate(f);
		this.elencoCitta.put(1, c1);
		
		//this.elencoCitta.put(2, new Citta(2, "Acireale"));
		
		Citta c3 = new Citta(3, "Misterbianco");
		List<Fermata> f3 = new ArrayList<Fermata>();
		f3.add(new Fermata("Misterbianco Nord", c3));
		f3.add(new Fermata("Misterbianco Centro", c3));
		f3.add(new Fermata("Misterbianco Sud", c3));
		c3.loadFermate(f3);
		this.elencoCitta.put(3, c3);
		
		Citta c4 = new Citta(4, "Paterno");
		List<Fermata> f4 = new ArrayList<Fermata>();
		f4.add(new Fermata("Paterno Stazione", c4));
		f4.add(new Fermata("Paterno Nord", c4));
		c4.loadFermate(f4);
		this.elencoCitta.put(4, c4);
		
		Citta c5 = new Citta(5, "Caltagirone");
		List<Fermata> f5 = new ArrayList<Fermata>();
		f5.add(new Fermata("Caltagirone Stazione", c5));
		f5.add(new Fermata("Caltagirone Ospedale",c5));
		c5.loadFermate(f5);
		this.elencoCitta.put(5, c5);
		
		Citta c6 = new Citta(6, "Adrano");
		List<Fermata> f6 = new ArrayList<Fermata>();
		f6.add(new Fermata("Adrano Sant'Agostino", c6));
		f6.add(new Fermata("Adrano Centro", c6));
		f6.add(new Fermata("Adrano Navicchia", c6));
		f6.add(new Fermata("Adrano Nord", c6));
		c6.loadFermate(f6);
		this.elencoCitta.put(6, c6);
		
		/*this.elencoCitta.put(7, new Citta(7, "Mascalucia"));
		this.elencoCitta.put(8, new Citta(8, "Aci Catena"));
		this.elencoCitta.put(9, new Citta(9, "Belpasso"));
		this.elencoCitta.put(10, new Citta(10, "Giarre"));
		this.elencoCitta.put(11, new Citta(11, "Gravina di Catania"));*/
		
		Citta c12 = new Citta(12, "Biancavilla");
		List<Fermata> f12 = new ArrayList<Fermata>();
		f12.add(new Fermata("Biancavilla Stazione", c12));
		f12.add(new Fermata("Biancavilla Nord", c12));
		c12.loadFermate(f12);
		this.elencoCitta.put(12, c12);
		
		/*this.elencoCitta.put(13, new Citta(13, "San Giovanni la Punta"));
		this.elencoCitta.put(14, new Citta(14, "Tremestieri Etneo"));*/
		
		Citta c15 = new Citta(15, "Bronte");
		List<Fermata> f15 = new ArrayList<Fermata>();
		f15.add(new Fermata("Bronte Stazione", c15));
		f15.add(new Fermata("Bronte Nord", c15));
		c15.loadFermate(f15);
		this.elencoCitta.put(15, c15);
		
		//this.elencoCitta.put(16, new Citta(16, "Aci Castello", new ArrayList<>()));
		/*this.elencoCitta.put(17, new Citta(17, "Aci Sant'Antonio"));
		this.elencoCitta.put(18, new Citta(18, "Scordia"));
		this.elencoCitta.put(19, new Citta(19, "Palagonia"));
		this.elencoCitta.put(20, new Citta(20, "Riposto"));
		this.elencoCitta.put(21, new Citta(21, "Pedara"));
		this.elencoCitta.put(22, new Citta(22, "Mascali"));
		this.elencoCitta.put(23, new Citta(23, "Grammichele"));
		this.elencoCitta.put(24, new Citta(24, "Motta Sant'Anastasia"));
		this.elencoCitta.put(25, new Citta(25, "San Gregorio di Catania"));
		this.elencoCitta.put(26, new Citta(26, "Trecastagni"));
		this.elencoCitta.put(27, new Citta(27, "Ramacca"));*/
		
		Citta c28 = new Citta(28, "Randazzo");
		List<Fermata> f28 = new ArrayList<Fermata>();
		f28.add(new Fermata("Randazzo Stazione", c28));
		f28.add(new Fermata("Randazzo Nord", c28));
		c28.loadFermate(f28);
		this.elencoCitta.put(28, c28);
		
		/*this.elencoCitta.put(29, new Citta(29, "Zafferana Etnea"));
		this.elencoCitta.put(30, new Citta(30, "Fiumefreddo di Sicilia"));
		this.elencoCitta.put(31, new Citta(31, "Sant'Agata Li Battiati"));
		this.elencoCitta.put(32, new Citta(32, "Viagrande"));
		this.elencoCitta.put(33, new Citta(33, "Santa Venerina"));
		this.elencoCitta.put(34, new Citta(34, "San Pietro Clarenza"));
		this.elencoCitta.put(35, new Citta(35, "Valverde"));*/
		
		Citta c36 = new Citta(36, "Santa Maria di Licodia");
		List<Fermata> f36 = new ArrayList<Fermata>();
		f36.add(new Fermata("Santa Maria di Licodia Stazione", c36));
		f36.add(new Fermata("Santa Maria di Licodia Nord", c36));
		c36.loadFermate(f36);
		this.elencoCitta.put(36, c36);
		
		/*this.elencoCitta.put(37, new Citta(37, "Nicolosi"));
		this.elencoCitta.put(38, new Citta(38, "Militello in Val di Catania"));
		this.elencoCitta.put(39, new Citta(39, "Vizzini"));
		this.elencoCitta.put(40, new Citta(40, "Linguaglossa"));
		this.elencoCitta.put(41, new Citta(41, "Calatabiano"));
		this.elencoCitta.put(42, new Citta(42, "Mineo"));
		this.elencoCitta.put(43, new Citta(43, "Camporotondo Etneo"));
		this.elencoCitta.put(44, new Citta(44, "Mirabella Imbaccari"));
		this.elencoCitta.put(45, new Citta(45, "Castel di Iudica"));
		this.elencoCitta.put(46, new Citta(46, "Mazzarrone"));
		this.elencoCitta.put(47, new Citta(47, "Ragalna"));
		this.elencoCitta.put(48, new Citta(48, "Piedimonte Etneo"));*/
		
		Citta c49 = new Citta(49, "Maletto");
		List<Fermata> f49 = new ArrayList<Fermata>();
		f49.add(new Fermata("Maletto Stazione", c49));
		f49.add(new Fermata("Maletto Nord", c49));
		c49.loadFermate(f49);
		this.elencoCitta.put(49, c49);
		
		/*this.elencoCitta.put(50, new Citta(50, "Maniace"));
		this.elencoCitta.put(51, new Citta(51, "Aci Bonaccorsi"));
		this.elencoCitta.put(52, new Citta(52, "San Michele di Ganzaria"));
		this.elencoCitta.put(53, new Citta(53, "Castiglione di Sicilia"));
		this.elencoCitta.put(54, new Citta(54, "Raddusa"));
		this.elencoCitta.put(55, new Citta(55, "Licodia Eubea"));
		this.elencoCitta.put(56, new Citta(56, "San Cono"));
		this.elencoCitta.put(57, new Citta(57, "Sant'Alfio"));
		this.elencoCitta.put(58, new Citta(58, "Milo"));*/
		
		Citta c59 = new Citta(59, "Valcorrente");
		List<Fermata> f59 = new ArrayList<Fermata>();
		f59.add(new Fermata("Valcorrente Stazione", c59));
		f59.add(new Fermata("Valcorrente Etnapolis", c59));
		c59.loadFermate(f59);
		this.elencoCitta.put(59, c59);
		
		Citta c60 = new Citta(60, "Piano Tavola");
		List<Fermata> f60 = new ArrayList<Fermata>();
		f60.add(new Fermata("Piano Tavola Via Nazionale", c60));
		c60.loadFermate(f60);
		this.elencoCitta.put(60, c60);
	}

	public void loadControllori() {
		String password = "$2a$12$mM.vND0a6ncereEv6uzo0O3dPtLjRen8IIbi85EsJ1ZY0M8lrKHAy"; // bello
		this.elencoUtenti.put("f5b3", new Controllore("f5b3", password));
		this.elencoUtenti.put("n7j5", new Controllore("n7j5", password));
		this.elencoUtenti.put("h1ig", new Controllore("h1ig", password));
		this.elencoUtenti.put("ba56", new Controllore("ba56", password));
		this.elencoUtenti.put("zy31", new Controllore("zy31", password));
	}

	public void loadAmministratori() {
		String password = "$2a$12$rbKK0Fduv2qtmzkt1TJkQeM.mcyPHrdU3WAvvRiY3w/gEGPLkRF0q"; // ciao
		this.elencoUtenti.put("a7b7", new Amministratore("a7b7",password));
		this.elencoUtenti.put("a34j", new Amministratore("a34j",password));
		this.elencoUtenti.put("a35j", new Amministratore("a35j",password));
		this.elencoUtenti.put("a36j", new Amministratore("a36j",password));
		this.elencoUtenti.put("a37j", new Amministratore("a37j",password));
	}

	public void loadClienti(){
		String password = "$2a$12$5JuKw/dujA2gj98AuxSkoeURrP3n6E2IOOIKXin8z8HbioXJNrGa2"; // franco
		this.elencoUtenti.put("c74i", new Cliente("c74i", password ,"Franco", "Tredita"));
		this.elencoUtenti.put("c2312", new Cliente("c2312", password, "Franco", "Bianchi"));
		this.elencoUtenti.put("c34rf", new Cliente("c34rf", password, "Giorgio", "Bianchi"));
		this.elencoUtenti.put("21dd", new Cliente("21dd", password, "Franco", "Rossi"));
		this.elencoUtenti.put("asda", new Cliente("asda", password, "Franco", "Marroni"));
	}

	public void loadAutomezzi() {
		Map<String, Biglietto> b1 = new HashMap<String, Biglietto>();
		b1.put("g7rbc8", new Biglietto("g7rbc8", elencoCitta.get(1), elencoCitta.get(3)));
		b1.put("ypc8jf", new Biglietto("ypc8jf", elencoCitta.get(1), elencoCitta.get(4)));
		
		Automezzo a = new Automezzo("H23", 25, this.getElencoItinerari().get("Catania-Randazzo"), b1);
		Controllore co = (Controllore) pickYourLine.getElencoUtenti().get("f5b3");
		co.setAutomezzoSupervisionato(a);
		this.elencoAutomezzi.put("H23", a);
		
		this.elencoAutomezzi.put("B51", new Automezzo("B51", 20));
		this.elencoAutomezzi.put("Z22", new Automezzo("Z22", 25));
		this.elencoAutomezzi.put("Y77", new Automezzo("Y77", 30));
		this.elencoAutomezzi.put("C98", new Automezzo("C98", 25));
	}

	public void loadElencoSegnalazioni(){
		String password = "$2a$12$KKC5pJp/JoonpfWMV56wGOv1YhOBRqQRoEJ6GD/4BRLE2ZdlyNpza";
		this.elencoSegnalazioni.put("s001", new Segnalazione("s001","Critica Personale", "Insulto verbale", LocalDateTime.of(2025, 1, 10, 10, 50), new Cliente("0001",password, "Tizio", "Bello")));
		this.elencoSegnalazioni.put("s002", new Segnalazione("s002","Critica Servizio", "Servizio in ritardo nella mattina", LocalDateTime.of(2025, 1, 10, 11, 00), new Cliente("0001",password, "Tizio", "Bello")));
		this.elencoSegnalazioni.put("s003", new Segnalazione("s003","Critica Automezzo", "Sedili senza cintura di sicurezza", LocalDateTime.of(2025, 1, 12, 16, 30), new Cliente("0001",password, "Tizio", "Bello")));
	}
	
	public void loadAvvisi() {
		this.elencoAvvisi.put("a001", new Avviso("a001","Possibili ritardi", "Possibili ritardi nella giornata odierna", LocalDateTime.of(2025, 1, 10, 10, 50), (Amministratore) this.getElencoUtenti().get("a7b7")));
		this.elencoAvvisi.put("a002", new Avviso("a002","Incidente", "Grave incidente blocca alcune strade", LocalDateTime.of(2025, 1, 25, 9, 10), (Amministratore) this.getElencoUtenti().get("a34j")));
	}

	public void visualizzaElencoCittaPartenza() {
		elencoCitta.forEach((key, c) -> {System.out.println(c
				.getCodice() + " - " + c.getNome());});
	}
	
	public Map<Integer, Citta> inserisciCittaPartenza(int codiceCittaPartenza) throws Exception {
		Citta cittaPartenza = this.getElencoCitta().get(codiceCittaPartenza);
		
		if(cittaPartenza == null) {
			throw new Exception("Codice città non esistente.");
		}
		
		Map<Integer, Citta> elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		
		this.elencoItinerari.forEach((key, i) -> {
			elencoDestinazioniDisponibili.putAll(i.getDestinazioniDisponibili(cittaPartenza));
		});
		
		return elencoDestinazioniDisponibili;
	}

	public Map<String, Itinerario> inserisciCittaDestinazione(int codiceCittaPartenza, int codiceCittaDestinazione, Map<Integer, Citta> elencoDestinazioniDisponibili) throws Exception{
		Citta cittaDestinazione = elencoDestinazioniDisponibili.get(codiceCittaDestinazione);
		
		if(cittaDestinazione == null) {
			throw new Exception("Codice città non non idoneo alla ricerca effettuata.");
		}
		
		Map<String, Itinerario> itinerariDaVisualizare = new HashMap<String, Itinerario>();
		
		for (Itinerario itinerario : this.elencoItinerari.values()) {
			Itinerario i = itinerario.getSeDisponibile(this.getElencoCitta().get(codiceCittaPartenza), cittaDestinazione);
			
			if(i != null) {
				itinerariDaVisualizare.put(i.getCodice(), i);
			}
		}
		
		return itinerariDaVisualizare;
	}

	public void visualizzaItinerario(String codiceItinerario, Map<String, Itinerario> itinerariDisponibili) throws Exception {
		Itinerario i = itinerariDisponibili.get(codiceItinerario);
		
		if(i == null) {
			throw new Exception("Codice itinerario non idoneo alla ricerca effettuata.");
		}
		
		i.visualizzaFermate();
	}
	
	public Biglietto timbraBiglietto(String codice, int codiceCittaPartenza, int codiceCittaDestinazione) throws Exception {
		Controllore co = (Controllore) this.utenteCorrente;
		
		int postiDisponibili = co.verificaDisponibilitaPosti();
		
		if(postiDisponibili <= 0) {
			throw new Exception("Non ci sono posti disponibili, operazione terminata.");
		}
		
		Citta cittaPartenza = this.elencoCitta.get(codiceCittaPartenza);
		
		if(cittaPartenza == null) {
			throw new Exception("La città di partenza non è presente nell'elenco.");
		}
		
		Citta cittaAttuale = co.getAutomezzoSupervisionato().getPosizioneAttuale().getCittaDiAppartenenza();
		
		if(!cittaPartenza.equals(cittaAttuale)) {
			throw new Exception("Non è possibile partire da una città diversa da quella attuale.");
		}
		
		Citta cittaDestinazione = this.elencoCitta.get(codiceCittaDestinazione);
		
		if(cittaDestinazione == null) {
			throw new Exception("La città di destinazione non è presente nell'elenco.");
		}
		
		if(cittaPartenza.equals(cittaDestinazione)) {
			throw new Exception("Città di partenza e destinazione non possono essere uguali.");
		}
		
		Itinerario i = co.getAutomezzoSupervisionato().getItinerarioAssegnato().getSeDisponibile(this.getElencoCitta().get(codiceCittaPartenza), cittaDestinazione);
		
		if(i == null) {
			throw new Exception("Città di destinazione non presente nel percorso.");
		}
		
		return co.creaBiglietto(codice, cittaPartenza, cittaDestinazione);
	}
	
	public void confermaInserimento() {
		Controllore co = (Controllore) this.utenteCorrente;
		co.confermaInserimento();
	}
	
	public boolean aggiornaPosizioneAutomezzo(String nomeFermata) throws Exception {
		Controllore co = (Controllore) this.utenteCorrente;
		
		List<Citta> percorsoItinerarioAssegnato = co.getAutomezzoSupervisionato().getItinerarioAssegnato().getPercorso();
		Fermata f = null;
		
		for (Citta c : percorsoItinerarioAssegnato) {
			f = c.getFermata(nomeFermata);
			if(f != null) break;
		}
		
		if(f == null || f.getCittaDiAppartenenza().equals(percorsoItinerarioAssegnato.getLast())) {
			throw new Exception("Nome fermata non consentito.");
		}
		
		return co.aggiornaPosizione(f);
	}
	
	public void terminaInserimento() {
		Controllore co = (Controllore) this.utenteCorrente;
		co.aggiornaOrarioUltimaTimbratura();
	}
	
	public Map<String, Automezzo> visualizzaAutomezziInTransito(){
		Map<String, Automezzo> elencoAutomezziInTransito = new HashMap<String, Automezzo>();
		
		for (Automezzo a : this.elencoAutomezzi.values()) {
			if(a.getStato() instanceof InTransito) {
				elencoAutomezziInTransito.put(a.getCodice(), a);
				System.out.println(a);
			}
		}
		
		return elencoAutomezziInTransito;
	}
	
	public void visualizzaAutomezzo(String codiceAutomezzo, Map<String, Automezzo> elencoAutomezziInTransito) throws Exception {
		Automezzo a = elencoAutomezziInTransito.get(codiceAutomezzo);
		
		if(a == null) {
			throw new Exception("Codice automezzo non valido.");
		}
		
		System.out.println(a.getDettagliAutomezzo());
	}

	public void visualizzaFermate(int codiceCitta) throws Exception{
		Citta c = elencoCitta.get(codiceCitta);

		if (c == null) {
			throw new Exception("Codice città non non valido");
		}

		c.visualizzaElencoFermate();
	}

	public Segnalazione creaSegnalazione(String oggettoSegnalazione,String contenutoSegnalazione) {
		Segnalazione s = new Segnalazione(oggettoSegnalazione,contenutoSegnalazione);
		return s;
	}

	public void invioSegnalazione(Segnalazione segnalazione) {
		this.elencoSegnalazioni.put(segnalazione.getCodice(), segnalazione);
	}

	public void visualizzaElencoSegnalazioni() throws Exception {
		System.out.println("-------------- Elenco segnalazioni --------------");
		
		if(elencoSegnalazioni.isEmpty()) {
			throw new Exception("Non sono presenti segnalazioni.");
		}
		
		for (Map.Entry<String, Segnalazione> entry : elencoSegnalazioni.entrySet()) {
			Segnalazione s = entry.getValue();
			System.out.println(s);
		}
	}

	public void visualizzaDettaglioSegnalazione(String codiceSegnalazione) throws Exception {
		Segnalazione s = elencoSegnalazioni.get(codiceSegnalazione);

		if(s == null) {
			throw new Exception("Codice segnalazione non valido.");
		}

		s.visualizzaDettaglio();
	}
	
	public void visualizzaElencoItinerari() {
		this.elencoItinerari.forEach((k, i) -> System.out.println(i));
	}
	
	public void inserisciItinerario(String codice, LocalTime orarioPartenza, LocalTime orarioArrivo, List<Citta> percorso) throws Exception {
		Itinerario i = this.elencoItinerari.get(codice);
		
		if(i != null) {
			throw new Exception("Codice itinerario già esistente.");
		}
		
		if(!orarioArrivo.isAfter(orarioPartenza)) {
			throw new Exception("L'orario di arrivo deve essere successivo a quello di partenza.");
		}
		
		if(percorso.size() < 2) {
			throw new Exception("Il percorso deve essere composto da almeno due città.");
		}
		
		if(!controlloCittaDuplicate(percorso)) {
			throw new Exception("Il percorso non può contenere città duplicate.");
		}
		
		this.elencoItinerari.put(codice, new Itinerario(codice, orarioPartenza, orarioArrivo, percorso));
		inserisciAvviso("Nuovo itinerario disponibile", "L'itinerario " + i + "è stato aggiunto al servizio");
	}
	
	public void modificaItinerario(String codice, LocalTime orarioPartenza, LocalTime orarioArrivo, List<Citta> percorso) throws Exception {
		Itinerario i = this.elencoItinerari.get(codice);
		
		if(i == null) {
			throw new Exception("Codice itinerario non esistente.");
		}
		
		if(orarioPartenza != null) {
			i.setOrarioPartenza(orarioPartenza);
		}
		
		if(orarioArrivo != null) {
			
			if(!orarioArrivo.isAfter(i.getOrarioPartenza())) {
				throw new Exception("L'orario di arrivo deve essere successivo a quello di partenza.");
			}
			
			i.setOrarioArrivo(orarioArrivo);
		}
		
		if(!percorso.isEmpty()) {
			
			if(percorso.size() < 2) {
				throw new Exception("Il percorso deve essere composto da almeno due città.");
			}
			
			if(!controlloCittaDuplicate(percorso)) {
				throw new Exception("Il percorso non può contenere città duplicate.");
			}
			
			i.setPercorso(percorso);
		}
		
		inserisciAvviso("Modifica itinerario", "L'itinerario " + i.getCodice() + " ha subito delle variazioni, da adesso l'itinerario sarà " + i);
	}
	
	private boolean controlloCittaDuplicate(List<Citta> percorso) {
		Set<Citta> set = new HashSet<Citta>();
		
		for (Citta c : percorso) {
			if(!set.add(c)) {
				return false;
			}
		}
		
		return true;
	}
	
	public void eliminaItinerario(String codice) throws Exception {
		Itinerario i = this.elencoItinerari.get(codice);
		
		if(i == null) {
			throw new Exception("Codice itinerario non esistente.");
		}
		
		this.elencoItinerari.remove(codice);
		inserisciAvviso("Rimozione itinerario dal servizio", "L'itinerario " + i + " non sarà più disponibile");
	}
	
	public void visualizzaElencoAutomezzi() {
		this.elencoAutomezzi.forEach((k, a) -> System.out.println(a));
	}
	
	public void inserisciAutomezzo(String codice, int posti, String codiceItinerario) throws Exception {
		
		if(this.elencoAutomezzi.containsKey(codice)) {
			throw new Exception("Codice automezzo già esistente.");
		}
		
		Automezzo a = new Automezzo(codice, posti);
		Itinerario i = pickYourLine.elencoItinerari.get(codiceItinerario);
		
		if(i == null) {
			throw new Exception("Codice itinerario non esistente.");
		}
		
		a.setItinerarioAssegnato(i);
		this.elencoAutomezzi.put(codice, a);
		inserisciAvviso("Nuovo automezzo",  "L'automezzo " + a + "è stato aggiunto al servizio, da adesso percorrerà l'itinerario " + i.getCodice());
	}
	
	public void modificaAutomezzo(String codice, int codiceStato, String codiceItinerario) throws Exception {
		Map<String, Automezzo> automezziNonInTransito = new HashMap<String, Automezzo>();
		
		this.elencoAutomezzi.forEach((k, a) -> {
			if(a.getStato() instanceof NonInTransito || a.getStato() instanceof InManutenzione) {
				automezziNonInTransito.put(a.getCodice(), a);
			}
		});
		
		if(automezziNonInTransito.isEmpty()) {
			throw new Exception("Nessun automezzo modificabile.");
		}
		
		Automezzo a = automezziNonInTransito.get(codice);
		
		if(a == null) {
			throw new Exception("Automezzo non modificabile perchè in transito o dismesso.");
		}
		
		switch(codiceStato) {
			case 1:
				a.nonInSupervisione();
				break;
			case 2:
				a.inManutenzione();
				break;
			case 3:
				a.inDismissione();
				break;
		}
		
		if(a.getStato() instanceof NonInTransito && codiceItinerario != null && !codiceItinerario.equals("0")) {
			Itinerario i = pickYourLine.elencoItinerari.get(codiceItinerario);
			a.setItinerarioAssegnato(i);
			inserisciAvviso("Cambio itinerario per l'automezzo " + a.getCodice(), "L'automezzo " + a.getCodice() + " ha subito un cambio di itinerario, da adesso percorrerà l'itinerario " + i);
		}
	}
	
	public void eliminaAutomezzo(String codice) throws Exception {
		Map<String, Automezzo> automezziNonInTransito = new HashMap<String, Automezzo>();
		
		this.elencoAutomezzi.forEach((k, a) -> {
			if(a.getStato() instanceof NonInTransito) {
				automezziNonInTransito.put(a.getCodice(), a);
			}
		});
		
		Automezzo a = automezziNonInTransito.get(codice);
		
		if(a == null) {
			throw new Exception("Automezzo non esistente o in transito.");
		}
		
		this.elencoAutomezzi.remove(codice);
		inserisciAvviso("Rimozione automezzo", "L'automezzo " + a + "non sarà più disponibile");
	}
	
	public void inserisciAvviso(String oggetto, String contenuto) {
		Avviso av = new Avviso(oggetto, contenuto);
		this.elencoAvvisi.put(av.getCodice(), av);
	}
	
	public void modificaAvviso(String codice, String oggetto, String contenuto) throws Exception {
		Avviso av = this.elencoAvvisi.get(codice);
		
		if(av == null) {
			throw new Exception("Codice avviso non esistente.");
		}
		
		if(!oggetto.equals("0")) {
			av.setOggetto(oggetto);
		}
		
		if(!contenuto.equals("0")) {
			av.setContenuto(contenuto);
		}
	}
	
	public void eliminaAvviso(String codice) throws Exception {
		if(!this.elencoAvvisi.containsKey(codice)) {
			throw new Exception("Codice avviso non esistente.");
		}
		
		this.elencoAvvisi.remove(codice);
	}
	
	public void visualizzaElencoAvvisi() throws Exception {
		if(this.elencoAvvisi.isEmpty()) {
			throw new Exception("Non sono presenti avvisi nel sistema.");
		}
		
		this.elencoAvvisi.forEach((k, av) -> System.out.println(av));
	}
	
	public void visualizzaDettaglioAvviso(String codice) throws Exception {
		Avviso av = this.elencoAvvisi.get(codice);
		
		if(av == null) {
			throw new Exception("Codice avviso non esistente.");
		}
		
		av.visualizzaDettaglio();
	}
	
	private boolean verificaSupervisione() throws Exception {
		Utente u = pickYourLine.getUtenteCorrente();
	
		if(!(u instanceof Controllore)) {
			return true;
		}
		
		Controllore controllore = (Controllore) u;
		
		Automezzo automezzoSupervisionato = controllore.getAutomezzoSupervisionato();
		
		if (automezzoSupervisionato != null) {
			return false;
		}
		
		return true;
	}

	public Map<String, Automezzo> visualizzaElencoAutomezziNonInTransito() throws Exception {
		Map<String, Automezzo> automezziDisponibili = new HashMap<String, Automezzo>();

		if (!verificaSupervisione()) {
			throw new Exception("Stai già supervisionando un automezzo");
		}

		this.elencoAutomezzi.forEach((k,a) -> {
			if (a.getStato() instanceof NonInTransito && a.getItinerarioAssegnato() != null){
				automezziDisponibili.put(a.getCodice(),a);
			}
		});

		if (automezziDisponibili.isEmpty()) {
			throw new Exception("Nessun automezzo disponibile per la supervisione.");
		}

		System.out.println("Automezzi disponibili:");
		automezziDisponibili.forEach((codice, automezzo) -> System.out.println("- " + codice + " posti: " + automezzo.getPosti()));

		return automezziDisponibili;
	}

	public void supervisionaAutomezzo(String codiceAutomezzo,Map<String, Automezzo> automezziDisponibili) throws Exception {
		Controllore controllore = (Controllore) pickYourLine.getUtenteCorrente();
		Automezzo automezzo = automezziDisponibili.get(codiceAutomezzo);

		if (automezzo == null) {
			throw new Exception("Codice automezzo non valido");
		}

		controllore.setAutomezzoSupervisionato(automezzo);
		automezzo.inSupervisione();
	}

	public void fineCorsa() throws Exception {
		Controllore controllore = (Controllore) pickYourLine.getUtenteCorrente();
		Automezzo automezzoSupervisionato = controllore.getAutomezzoSupervisionato();
		
		if (automezzoSupervisionato == null) {
			throw new Exception("Non stai supervisionando nessun automezzo.");
		}

		automezzoSupervisionato.nonInSupervisione();
		controllore.setAutomezzoSupervisionato(null);
	}

	public void visualizzaElencoControllori() {
		this.elencoUtenti.forEach((k, u) -> {
			if(u instanceof Controllore) {
				System.out.println(u);
			}
		});
	}

	public void inserisciControllore(String codice) throws Exception {
		if(this.elencoUtenti.containsKey(codice)){
			throw new Exception("Controllore già esistente.");
		}

		Controllore c = new Controllore(codice,"Bellissimo");
        this.elencoUtenti.put(codice, c);
	}

	public void eliminaControllore(String codice) throws Exception {
		Utente u = this.elencoUtenti.get(codice);

		if (u == null || !(u instanceof Controllore)){
			throw new Exception("Controllore non esistente.");
		}

		Controllore co = (Controllore) u;

		if (co.getAutomezzoSupervisionato() != null){
			throw new Exception("Il controllore sta supervisionando un automezzo.");
		}

		this.elencoUtenti.remove(codice);
	}

	public boolean verificaAutenticazione() {
		return getUtenteCorrente() != null;
	}

	public void login(String codice, String password) throws Exception {
		
		if(verificaAutenticazione()) {
			throw new Exception("Hai già effettuato l'accesso. Effettua il logout prima di tentare il login.");
		}
		
		Utente utente = this.elencoUtenti.get(codice);
		
		if (utente == null) {
			throw new Exception("Codice utente non esistente");
		}

		String passwordUtente = utente.getPassword();
		BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), passwordUtente);

		if (!result.verified) {
			throw new Exception("Password inserita non corretta");
		}
		
		setUtenteCorrente(utente);
		System.out.println("L'Utente: " + utente.getCodice() + " ha eseguito correttamente l'accesso");
	}

	public void logout() throws Exception {
		
		if(!verificaAutenticazione()) {
			throw new Exception("Non hai effettuato l'accesso. Effettua il login prima di tentare il logout.");
		}
		
		if(!verificaSupervisione()) {
			throw new Exception("Non puoi effettuare il logout se stai supervisionando un automezzo.");
		}
		
		setUtenteCorrente(null);
	}

	private boolean verificaCredenziali(String codice, String password){

		if (codice.length() < 2 || codice.length() > 6) {
			return false;
		}

		if (password.length() < 8 || password.length() > 16) {
			return false;
		}

		boolean hasUpperCase = password.matches(".*[A-Z].*");

		boolean hasLowerCase = password.matches(".*[a-z].*");

		boolean hasSpecialChar = password.matches(".*[!?£$@#*%].*");

		boolean hasNumber = password.matches(".*[0-9].*");

		return hasLowerCase && hasUpperCase && hasSpecialChar && hasNumber;
	}

	public void registrazioneCliente(String codice, String password, String nome, String cognome) throws Exception {

		boolean esistente = elencoUtenti.containsKey(codice);
		boolean autenticato = verificaAutenticazione();

		if (autenticato){
			throw new Exception("Non puoi effettuare la registrazione, bisogna effettuare logout");
		}

		if (esistente) {
			throw new Exception("Il cliente è gia registrato");
		}

		boolean bool = verificaCredenziali(codice, password);
		if (!bool) {
			throw new Exception("Codice o Password inseriti non validi");
		}

		String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		Utente u = new Cliente(codice, passwordHash, nome, cognome);
		elencoUtenti.put(codice, u);
		setUtenteCorrente(u);
	}
}
