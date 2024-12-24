import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		pickYourLine.loadCitta();
		pickYourLine.loadItinerari();

		System.out.println("Benvenuto!");
		Scanner sc = new Scanner(System.in);

		while(true) {
			System.out.println("\nScegli tra le operazioni disponibili.");
			System.out.println("0- Esci\n"
					+ "1- Cerca itinerario");

			int scelta = sc.nextInt();

			switch(scelta) {
			case 0:
				System.out.println("Arrivederci!");
				sc.close();
				System.exit(0);
			case 1:
				cercaItinerario(sc);
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
			System.out.println("\nInserisci il codice dell' itinerario, oppure 0 per tornare al menu principale");
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

}
