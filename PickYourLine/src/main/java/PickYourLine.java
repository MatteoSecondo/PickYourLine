import java.time.LocalTime;
import java.util.*;

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
			return new PickYourLine();
		}
		
		return pickYourLine;
	}
	
	public Map<String, Itinerario> getElencoItinerari() {
		return elencoItinerari;
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
		this.elencoItinerari.put("Catania-Adrano",
				new Itinerario("Catania-Adrano", LocalTime.of(12, 0), LocalTime.of(14, 0)));
		this.elencoItinerari.put("Adrano-Catania",
				new Itinerario("Adrano-Catania", LocalTime.of(12, 0), LocalTime.of(14, 0)));
		this.elencoItinerari.put("Catania-Caltagirone",
				new Itinerario("Catania-Caltagirone", LocalTime.of(12, 0), LocalTime.of(14, 0)));
		this.elencoItinerari.put("Caltagirone-Catania",
				new Itinerario("Caltagirone-Catania", LocalTime.of(12, 0), LocalTime.of(14, 0)));
	}
	
	public void loadCitta() {
		this.elencoCitta.put(1, new Citta(1, "Catania"));
		this.elencoCitta.put(2, new Citta(2, "Acireale"));
		this.elencoCitta.put(3, new Citta(3, "Misterbianco"));
		this.elencoCitta.put(4, new Citta(4, "Paterno"));
		this.elencoCitta.put(5, new Citta(5, "Caltagirone"));
		this.elencoCitta.put(6, new Citta(6, "Adrano"));
		this.elencoCitta.put(7, new Citta(7, "Mascalucia"));
		this.elencoCitta.put(8, new Citta(8, "Aci Catena"));
		this.elencoCitta.put(9, new Citta(9, "Belpasso"));
		this.elencoCitta.put(10, new Citta(10, "Giarre"));
		this.elencoCitta.put(11, new Citta(11, "Gravina di Catania"));
		this.elencoCitta.put(12, new Citta(12, "Biancavilla"));
		this.elencoCitta.put(13, new Citta(13, "San Giovanni la Punta"));
		this.elencoCitta.put(14, new Citta(14, "Tremestieri Etneo"));
		this.elencoCitta.put(15, new Citta(15, "Bronte"));
		this.elencoCitta.put(16, new Citta(16, "Aci Castello"));
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
		this.elencoCitta.put(27, new Citta(27, "Ramacca"));
		this.elencoCitta.put(28, new Citta(28, "Randazzo"));
		this.elencoCitta.put(29, new Citta(29, "Zafferana Etnea"));
		this.elencoCitta.put(30, new Citta(30, "Fiumefreddo di Sicilia"));
		this.elencoCitta.put(31, new Citta(31, "Sant'Agata Li Battiati"));
		this.elencoCitta.put(32, new Citta(32, "Viagrande"));
		this.elencoCitta.put(33, new Citta(33, "Santa Venerina"));
		this.elencoCitta.put(34, new Citta(34, "San Pietro Clarenza"));
		this.elencoCitta.put(35, new Citta(35, "Valverde"));
		this.elencoCitta.put(36, new Citta(36, "Santa Maria di Licodia"));
		this.elencoCitta.put(37, new Citta(37, "Nicolosi"));
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
		this.elencoCitta.put(48, new Citta(48, "Piedimonte Etneo"));
		this.elencoCitta.put(49, new Citta(49, "Maletto"));
		this.elencoCitta.put(50, new Citta(50, "Maniace"));
		this.elencoCitta.put(51, new Citta(51, "Aci Bonaccorsi"));
		this.elencoCitta.put(52, new Citta(52, "San Michele di Ganzaria"));
		this.elencoCitta.put(53, new Citta(53, "Castiglione di Sicilia"));
		this.elencoCitta.put(54, new Citta(54, "Raddusa"));
		this.elencoCitta.put(55, new Citta(55, "Licodia Eubea"));
		this.elencoCitta.put(56, new Citta(56, "San Cono"));
		this.elencoCitta.put(57, new Citta(57, "Sant'Alfio"));
		this.elencoCitta.put(58, new Citta(58, "Milo"));
	}

	public void cercaItinerario() {
		elencoCitta.forEach((key, c) -> {System.out.println(c);});
	}
	
	public void inserisciCittaPartenza() throws Exception {
		Citta cittaPartenza = null;
		
		do {
			System.out.println("Inserisci il codice della città di partenza");
			Scanner sc = new Scanner(System.in);
			int codiceCittaPartenza = sc.nextInt();
			cittaPartenza = this.elencoCitta.get(codiceCittaPartenza);
			
			if(cittaPartenza == null) throw new Exception("Codice città non esistente.");
		} while(cittaPartenza == null);
		
		setCittaPartenzaCorrente(cittaPartenza);
		
		this.elencoItinerari.forEach((key, i) -> {i.visualizzaDestinazioniDisponibili();});
	}

	public void inserisciCittaDestinazione() throws Exception {
		Citta cittaDestinazione = null;
		
		while(cittaDestinazione == null || this.cittaPartenzaCorrente.equals(cittaDestinazione)) {
			System.out.println("Inserisci il codice della città di destinazione");
			Scanner sc = new Scanner(System.in);
			int codiceCittaDestinazione = sc.nextInt();
			cittaDestinazione = this.elencoCitta.get(codiceCittaDestinazione);
			
			if(cittaDestinazione == null) {
				throw new Exception("Codice città non esistente.");
			} else if(this.cittaPartenzaCorrente.equals(cittaDestinazione)) {
				throw new Exception("La città di partenza e quella di destinazione sono la stessa città.");
			}
		}
		
		List<Itinerario> itinerariDaVisualizare = new ArrayList<Itinerario>();
		
		this.elencoItinerari.forEach((key, itinerario) -> {
			Itinerario i = itinerario.getSeDisponibile();
			
			if(i != null) {
				itinerariDaVisualizare.add(i);
			}
		});
		
		if(itinerariDaVisualizare.isEmpty()) {
			throw new Exception("Non ci sono itinerari che collegano direttamente le città di partenza e destinazione scelte.");
		}
		
		itinerariDaVisualizare.forEach(i -> System.out.println(i));
	}

	public void visualizzaItinerario() throws Exception {
		Itinerario itinerario = null;
		
		do {
			System.out.println("Inserisci il codice dell' itinerario");
			Scanner sc = new Scanner(System.in);
			String codiceItinerario = sc.nextLine();
			itinerario = this.elencoItinerari.get(codiceItinerario);
			
			if(itinerario == null) throw new Exception("Codice itinerario non esistente.");
		} while(itinerario == null);
		
		itinerario.visualizzaFermate();
	}
}
