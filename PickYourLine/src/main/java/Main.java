import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		pickYourLine.loadCitta();
		pickYourLine.loadItinerari();
		pickYourLine.loadControllori();
		pickYourLine.loadAutomezzi();
		pickYourLine.laodClienti();
		pickYourLine.loadAmministratori();
		pickYourLine.loadElencoSegnalazioni();

		System.out.println("Benvenuto!");
		Scanner sc = new Scanner(System.in);
		boolean ultimaCitta = false;

		while(true) {
			System.out.println("\nScegli tra le operazioni disponibili.");
			System.out.println("0- Esci\n"
					+ "1- Cerca itinerario\n"
					+ "2- Timbra biglietto\n"
					+ "3- Monitora automezzo\n"
					+ "6- Visualizza fermate\n"
					+ "9- Invio segnalazione\n"
					+ "10- Visualizza Segnalazioni\n");

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
					ultimaCitta = timbraBiglietto(sc, ultimaCitta);
					break;
				case 3:
					monitoraAutomezzo(sc);
				case 6:
					visualizzaFermate(sc);
					break;
				case 9:
					invioSegnalazione(sc);
					break;
				case 10:
					VisualizzaSegnalazioni(sc);
					break;
			}

		}
	}

	@SuppressWarnings("resource")
	public static void cercaItinerario(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		pickYourLine.visualizzaElencoCittaPartenza();
		
		Map<Integer, Citta> elencoDestinazioniDisponibili = null;
		int codiceCittaPartenza;

		do {
			System.out.println("\nInserisci il codice della città di partenza");
			sc = new Scanner(System.in);
			codiceCittaPartenza = sc.nextInt();

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
				itinerariDisponibili = pickYourLine.inserisciCittaDestinazione(codiceCittaPartenza, codiceCittaDestinazione, elencoDestinazioniDisponibili);
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
	}
	
	@SuppressWarnings({ "resource" })
	public static boolean timbraBiglietto(Scanner sc, boolean ultimaCitta) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		Controllore co = (Controllore) pickYourLine.getUtenteCorrente();
		
		int scelta;
	
		String nomeFermata;
		boolean successo = false;
		
		do {
			if(ultimaCitta) {
				System.out.println("Non è più possibile timbrare biglietti.");
				return true;
			}
			
			Citta cittaAttuale = co.getAutomezzoSupervisionato().getPosizioneAttuale().getCittaDiAppartenenza();
			
			System.out.println("\nFermate di " + cittaAttuale.getNome() + "(Citta Attuale):");
			cittaAttuale.getElencoFermate().forEach(f -> System.out.println(f.getNome()));
			
			List<Citta> percorso = co.getAutomezzoSupervisionato().getItinerarioAssegnato().getPercorso();
			
			int indexProssimaCitta = percorso.indexOf(cittaAttuale) + 1;
			Citta prossimaCitta = percorso.get(indexProssimaCitta);
			
			System.out.println("\nFermate di " + prossimaCitta.getNome() + "(Prossima Citta):");
			prossimaCitta.getElencoFermate().forEach(f -> System.out.println(f.getNome()));
			
			sc = new Scanner(System.in);
			System.out.println("\nInserisci il nome della fermata in cui ti trovi, altrimenti 0 per uscire");
			nomeFermata = sc.nextLine();
			
			if(nomeFermata.equals("0")) return false;
			
			try {
				ultimaCitta = pickYourLine.aggiornaPosizioneAutomezzo(nomeFermata);
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
					if(e.getMessage().equals("Non ci sono posti disponibili, operazione terminata.")) return false;
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
		
		pickYourLine.terminaInserimento();
		return ultimaCitta;
	}
	
	@SuppressWarnings("resource")
	public static void monitoraAutomezzo(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);
		
		System.out.println("Automezzi in transito:");
		Map<String, Automezzo> elencoAutomezziInTransito = pickYourLine.visualizzaAutomezziInTransito();
		
		String codiceAutomezzo;
		boolean successo = false;
		
		while(true) {
			do {
				System.out.println("\nInserisci il codice dell'automezzo da visualizzare in dettaglio, altrimenti 0 per uscire:");
				codiceAutomezzo = sc.nextLine();
				
				if(codiceAutomezzo.equals("0")) return;
				
				try {
					pickYourLine.visualizzaAutomezzo(codiceAutomezzo, elencoAutomezziInTransito);
					successo = true;
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
			}while(!successo);
		}	
	}

	@SuppressWarnings("resource")
	public static void visualizzaFermate(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);

		boolean successo = false;
		int codiceCitta;
		System.out.println("Elenco Città:");
		pickYourLine.visualizzaElencoCittaPartenza();

		while (true) {
			do {
				System.out.println("\nInserisci il codice della città di cui vuoi conoscere le fermate, altrimenti 0 per uscire");
				codiceCitta = sc.nextInt();

				if (codiceCitta == 0)
					return;
				try {
					pickYourLine.visualizzaFermate(codiceCitta);
					successo = true;
				}catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
			}while (!successo);
		}
	}

	@SuppressWarnings("resource")
	public static void invioSegnalazione(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		int scelta;
		String oggettoSegnalazione;
		String contenutoSegnalazione;

		do {
			Segnalazione segnalazione = null;
			do {
				System.out.println("Inserisci oggetto della segnalazione:");
				sc = new Scanner(System.in);
				oggettoSegnalazione = sc.nextLine();
				System.out.println("\nInserisci contenuto della segnalazione: ");
				sc = new Scanner(System.in);
				contenutoSegnalazione = sc.nextLine();

				try {
					segnalazione = pickYourLine.creaSegnalazione(oggettoSegnalazione,contenutoSegnalazione);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}

				System.out.println(segnalazione);

				System.out.println("\nInserisci 0 per annullare l'invio della segnalazione, altrimenti qualsiasi per confermare l'invio");

				if(sc.nextInt() != 0) {
					pickYourLine.invioSegnalazione(segnalazione);
				}
			}while (segnalazione == null);

			System.out.println("Inserisci 0 per terminare l'operazione, altrimenti qualsiasi per continuare");
			scelta = sc.nextInt();
		}while (scelta!=0);



	}


	public static void VisualizzaSegnalazioni(Scanner sc){
		PickYourLine pickYourLine = PickYourLine.getInstance();
		Amministratore a = (Amministratore) pickYourLine.getUtenteCorrente();

		String codiceSegnalazione;
		sc = new Scanner(System.in);

		pickYourLine.visualizzaElencoSegnalazioni();

		do {
			System.out.println("\n Inserisci il codice di una segnalazione per vedere il dettaglio(0 per uscire):");
			codiceSegnalazione = sc.nextLine().trim();


			if (pickYourLine.getElencoSegnalazioni().containsKey(codiceSegnalazione)) {
				try{
					pickYourLine.visualizzaDettaglioSegnalazione(codiceSegnalazione);
				}
				catch(Exception e){
					System.out.println("\n" + e.getMessage());
				}
			}
			else {
				System.out.println("\n Nessuna segnalazione trovata");
			}
		} while(!codiceSegnalazione.equals("0"));

	}

}
