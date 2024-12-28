import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickYourLine {
	private static PickYourLine pickYourLine;
	private Map<String, Itinerario> elencoItinerari;
	private Map<Integer, Citta> elencoCitta;
	private Citta cittaPartenzaCorrente;
	
	private PickYourLine() {
		this.elencoItinerari = new HashMap<String, Itinerario>();
		this.elencoCitta = new HashMap<Integer, Citta>();
	}
	
	public static PickYourLine getInstance() {
		if(pickYourLine == null) {
			pickYourLine = new PickYourLine();
		}
		
		return pickYourLine;
	}
	
	public Map<String, Itinerario> getElencoItinerari() {
		return elencoItinerari;
	}

	public void setElencoItinerari(Map<String, Itinerario> elencoItinerari) {
		this.elencoItinerari = elencoItinerari;
	}

	public Map<Integer, Citta> getElencoCitta() {
		return elencoCitta;
	}

	public Citta getCittaPartenzaCorrente() {
		return cittaPartenzaCorrente;
	}

	public void setCittaPartenzaCorrente(Citta cittaPartenzaCorrente) {
		this.cittaPartenzaCorrente = cittaPartenzaCorrente;
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
		List<Fermata> f = new ArrayList<Fermata>();
		f.add(new Fermata("Catania Borgo"));
		f.add(new Fermata("Catania Nesima"));
		f.add(new Fermata("Catania Ospedale Cannizzaro"));
		this.elencoCitta.put(1, new Citta(1, "Catania", f));
		
		//this.elencoCitta.put(2, new Citta(2, "Acireale"));
		
		List<Fermata> f3 = new ArrayList<Fermata>();
		f3.add(new Fermata("Misterbianco Nord"));
		f3.add(new Fermata("Misterbianco Centro"));
		f3.add(new Fermata("Misterbianco Sud"));
		this.elencoCitta.put(3, new Citta(3, "Misterbianco", f3));
		
		List<Fermata> f4 = new ArrayList<Fermata>();
		f4.add(new Fermata("Paterno Stazione"));
		f4.add(new Fermata("Paterno Nord"));
		this.elencoCitta.put(4, new Citta(4, "Paterno", f4));
		
		List<Fermata> f5 = new ArrayList<Fermata>();
		f5.add(new Fermata("Caltagirone Stazione"));
		f5.add(new Fermata("Caltagirone Ospedale"));
		this.elencoCitta.put(5, new Citta(5, "Caltagirone", f5));
		
		List<Fermata> f6 = new ArrayList<Fermata>();
		f6.add(new Fermata("Adrano Sant'Agostino"));
		f6.add(new Fermata("Adrano Centro"));
		f6.add(new Fermata("Adrano Navicchia"));
		f6.add(new Fermata("Adrano Nord"));
		this.elencoCitta.put(6, new Citta(6, "Adrano", f6));
		
		/*this.elencoCitta.put(7, new Citta(7, "Mascalucia"));
		this.elencoCitta.put(8, new Citta(8, "Aci Catena"));
		this.elencoCitta.put(9, new Citta(9, "Belpasso"));
		this.elencoCitta.put(10, new Citta(10, "Giarre"));
		this.elencoCitta.put(11, new Citta(11, "Gravina di Catania"));*/
		
		List<Fermata> f12 = new ArrayList<Fermata>();
		f12.add(new Fermata("Biancavilla Stazione"));
		f12.add(new Fermata("Biancavilla Nord"));
		this.elencoCitta.put(12, new Citta(12, "Biancavilla", f12));
		
		/*this.elencoCitta.put(13, new Citta(13, "San Giovanni la Punta"));
		this.elencoCitta.put(14, new Citta(14, "Tremestieri Etneo"));*/
		
		List<Fermata> f15 = new ArrayList<Fermata>();
		f15.add(new Fermata("Bronte Stazione"));
		f15.add(new Fermata("Bronte Nord"));
		this.elencoCitta.put(15, new Citta(15, "Bronte", f15));
		
		/*this.elencoCitta.put(16, new Citta(16, "Aci Castello"));
		this.elencoCitta.put(17, new Citta(17, "Aci Sant'Antonio"));
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
		
		List<Fermata> f28 = new ArrayList<Fermata>();
		f28.add(new Fermata("Randazzo Stazione"));
		f28.add(new Fermata("Randazzo Nord"));
		this.elencoCitta.put(28, new Citta(28, "Randazzo", f28));
		
		/*this.elencoCitta.put(29, new Citta(29, "Zafferana Etnea"));
		this.elencoCitta.put(30, new Citta(30, "Fiumefreddo di Sicilia"));
		this.elencoCitta.put(31, new Citta(31, "Sant'Agata Li Battiati"));
		this.elencoCitta.put(32, new Citta(32, "Viagrande"));
		this.elencoCitta.put(33, new Citta(33, "Santa Venerina"));
		this.elencoCitta.put(34, new Citta(34, "San Pietro Clarenza"));
		this.elencoCitta.put(35, new Citta(35, "Valverde"));*/
		
		List<Fermata> f36 = new ArrayList<Fermata>();
		f36.add(new Fermata("Santa Maria di Licodia Stazione"));
		f36.add(new Fermata("Santa Maria di Licodia Nord"));
		this.elencoCitta.put(36, new Citta(36, "Santa Maria di Licodia", f36));
		
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
		
		List<Fermata> f49 = new ArrayList<Fermata>();
		f49.add(new Fermata("Maletto Stazione"));
		f49.add(new Fermata("Maletto Nord"));
		this.elencoCitta.put(49, new Citta(49, "Maletto", f49));
		
		/*this.elencoCitta.put(50, new Citta(50, "Maniace"));
		this.elencoCitta.put(51, new Citta(51, "Aci Bonaccorsi"));
		this.elencoCitta.put(52, new Citta(52, "San Michele di Ganzaria"));
		this.elencoCitta.put(53, new Citta(53, "Castiglione di Sicilia"));
		this.elencoCitta.put(54, new Citta(54, "Raddusa"));
		this.elencoCitta.put(55, new Citta(55, "Licodia Eubea"));
		this.elencoCitta.put(56, new Citta(56, "San Cono"));
		this.elencoCitta.put(57, new Citta(57, "Sant'Alfio"));
		this.elencoCitta.put(58, new Citta(58, "Milo"));*/
		
		List<Fermata> f59 = new ArrayList<Fermata>();
		f59.add(new Fermata("Valcorrente Stazione"));
		f59.add(new Fermata("Valcorrente Etnapolis"));
		this.elencoCitta.put(59, new Citta(59, "Valcorrente", f59));
		
		List<Fermata> f60 = new ArrayList<Fermata>();
		f60.add(new Fermata("Piano Tavola Via Nazionale"));
		this.elencoCitta.put(60, new Citta(60, "Piano Tavola", f60));
	}

	public void cercaItinerario() {
		elencoCitta.forEach((key, c) -> {System.out.println(c);});
	}
	
	public Map<Integer, Citta> inserisciCittaPartenza(int codiceCittaPartenza) throws Exception {
		Citta cittaPartenza = this.getElencoCitta().get(codiceCittaPartenza);
		
		if(cittaPartenza == null) {
			throw new Exception("Codice città non esistente.");
		}
		
		setCittaPartenzaCorrente(cittaPartenza);
		Map<Integer, Citta> elencoDestinazioniDisponibili = new HashMap<Integer, Citta>();
		
		this.elencoItinerari.forEach((key, i) -> {
			elencoDestinazioniDisponibili.putAll(i.getDestinazioniDisponibili());
		});
		
		return elencoDestinazioniDisponibili;
	}

	public Map<String, Itinerario> inserisciCittaDestinazione(int codiceCittaDestinazione, Map<Integer, Citta> elencoDestinazioniDisponibili) throws Exception{
		Citta cittaDestinazione = elencoDestinazioniDisponibili.get(codiceCittaDestinazione);
		
		if(this.cittaPartenzaCorrente.equals(this.getElencoCitta().get(codiceCittaDestinazione))) {
			throw new Exception("La città di partenza e quella di destinazione sono la stessa città.");
		} else if(cittaDestinazione == null) {
			throw new Exception("Codice città non non idoneo alla ricerca effettuata.");
		}
		
		Map<String, Itinerario> itinerariDaVisualizare = new HashMap<String, Itinerario>();
		
		for (Itinerario itinerario : this.elencoItinerari.values()) {
			Itinerario i = itinerario.getSeDisponibile(cittaDestinazione);
			
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
}
