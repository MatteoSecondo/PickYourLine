import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		pickYourLine.loadCitta();
		pickYourLine.loadItinerari();
		pickYourLine.loadControllori();
		pickYourLine.loadAutomezzi();

		System.out.println("Benvenuto!");
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("\nScegli tra le operazioni disponibili.");
			System.out.println("0- Esci\n"
					+ "1- Cerca itinerario\n"
					+ "2- Timbra biglietto");

			int scelta = sc.nextInt();

			switch(scelta) {
				case 0:
					System.out.println("Arrivederci!");
					sc.close();
					System.exit(0);
				case 1:
					cercaItinerario(sc);
					break;
				case 2:
					timbraBiglietto(sc);
					break;
				}
		}
	}

	@SuppressWarnings("resource")
	public static void cercaItinerario(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		pickYourLine.cercaItinerario();
		
		Map<Integer, Citta> elencoDestinazioniDisponibili = null;

		do {
			System.out.println("\nInserisci il codice della città di partenza");
			sc = new Scanner(System.in);
			int codiceCittaPartenza = sc.nextInt();

			try {
				elencoDestinazioniDisponibili = pickYourLine.inserisciCittaPartenza(codiceCittaPartenza);
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		} while(elencoDestinazioniDisponibili == null);
		
		elencoDestinazioniDisponibili.forEach((key, c) -> System.out.println(c));

		Map<String, Itinerario> itinerariDisponibili = null;

		do {
			System.out.println("\nInserisci il codice della città di destinazione");
			sc = new Scanner(System.in);
			int codiceCittaDestinazione = sc.nextInt();

			try {
				itinerariDisponibili = pickYourLine.inserisciCittaDestinazione(codiceCittaDestinazione, elencoDestinazioniDisponibili);
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		} while(itinerariDisponibili == null);		

		itinerariDisponibili.forEach((key, i) -> System.out.println(i));

		String codiceItinerario;

		while(true) {
			System.out.println("\nInserisci il codice dell' itinerario per visualizzare i dettagli, oppure 0 per tornare al menu principale");
			sc = new Scanner(System.in);
			codiceItinerario = sc.nextLine();

			if(codiceItinerario.equals("0")) break;

			try {
				pickYourLine.visualizzaItinerario(codiceItinerario, itinerariDisponibili);
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		}
		
		pickYourLine.setCittaPartenzaCorrente(null);
	}
	
	public static void timbraBiglietto(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		int scelta;
		
		//TODO: cambio metodo di input della fermata tramite menu
		String nomeFermata;
		boolean successo = false;
		
		do {
			sc = new Scanner(System.in);
			System.out.println("Inserisci il nome della fermata in cui ti trovi, altrimenti 0 per uscire");
			nomeFermata = sc.nextLine();
			
			try {
				pickYourLine.terminaInserimento(nomeFermata);
				successo = true;
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		} while(!successo);
		
		do {
			Biglietto b = null;
			
			do {
				System.out.println("\nInserisci il codice del biglietto");
				sc = new Scanner(System.in);
				String codiceBiglietto = sc.nextLine();
				System.out.println("\nInserisci il codice della città di partenza");
				int codiceCittaPartenza = sc.nextInt();
				System.out.println("\nInserisci il codice della città di destinazione");
				int codiceCittaDestinazione = sc.nextInt();
	
				try {
					b = pickYourLine.timbraBiglietto(codiceBiglietto, codiceCittaPartenza, codiceCittaDestinazione);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
			} while(b == null);
			
			System.out.println(b);
			System.out.println("\nInserisci 0 per annullare l'inserimento, altrimenti qualsiasi per confermare");
			
			if(sc.nextInt() != 0) {
				pickYourLine.confermaInserimento();
			}
			
			System.out.println("Inserisci 0 per terminare l'inserimento, altrimenti qualsiasi per continuare");
			scelta = sc.nextInt();
		} while(scelta != 0);
		
		Controllore co = (Controllore) pickYourLine.getUtenteCorrente();
		co.aggiornaOrarioUltimaTimbratura();
	}

}
