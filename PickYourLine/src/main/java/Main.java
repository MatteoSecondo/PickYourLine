import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
		pickYourLine.loadAvvisi();
		
		System.out.println("Benvenuto!");
		Scanner sc = new Scanner(System.in);
		boolean ultimaCitta = false;

		while(true) {
			System.out.println("\nScegli tra le operazioni disponibili.");
			System.out.println("0- Esci\n"
					+ "1- Cerca itinerario\n"
					+ "2- Timbra biglietto\n"
					+ "3- Monitora automezzo\n"
					+ "4- Gestisci itinerari\n"
					+ "5- Gestisci automezzi\n"
					+ "6- Visualizza fermate\n"
					+ "7- Gestisci avvisi\n"
					+ "8- Visualizza avvisi\n"
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
					pickYourLine.setUtenteCorrente(pickYourLine.getElencoControllori().get("f5b3"));
					pickYourLine.getElencoControllori().get("f5b3").setAutomezzoSupervisionato(pickYourLine.getElencoAutomezzi().get("H23"));
					ultimaCitta = timbraBiglietto(sc, ultimaCitta);
					break;
				case 3:
					monitoraAutomezzo(sc);
				case 4:
					pickYourLine.setUtenteCorrente(pickYourLine.getElencoAmministratori().get("a7b7"));
					gestisciItinerari(sc);
					break;
				case 5:
					pickYourLine.setUtenteCorrente(pickYourLine.getElencoAmministratori().get("a7b7"));
					gestisciAutomezzi(sc);
					break;
				case 6:
					visualizzaFermate(sc);
					break;
				case 7:
					pickYourLine.setUtenteCorrente(pickYourLine.getElencoAmministratori().get("a7b7"));
					gestisciAvvisi(sc);
					break;
				case 8:
					visualizzaAvvisi(sc);
					break;
				case 9:
					pickYourLine.setUtenteCorrente(pickYourLine.getElencoClienti().get("c74i"));
					invioSegnalazione(sc);
					break;
				case 10:
					visualizzaSegnalazioni(sc);
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
			} while(!successo);
		}	
	}
	
	public static void gestisciItinerari(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		StringBuilder codice = new StringBuilder();
		int oraPartenza = 0, minutoPartenza = 0, oraArrivo = 0, minutoArrivo = 0;
		LocalTime[] orariPartenzaEArrivo = new LocalTime[2];
		Set<Citta> percorso = new HashSet<Citta>();
		
		System.out.println("Inserisci 1 per visualizzare, 2 per inserire, 3 per modificare, 4 per eliminare, qualsiasi per uscire:");
		int operazioneScelta = sc.nextInt();
		
		switch(operazioneScelta) {
			case 1:
				pickYourLine.visualizzaElencoItinerari();
				break;
			case 2:
				inserisciInputItinerario(sc, operazioneScelta, codice, oraPartenza, minutoPartenza,
						oraArrivo, minutoArrivo, orariPartenzaEArrivo, percorso);
				
				try {
					pickYourLine.inserisciItinerario(codice.toString(), orariPartenzaEArrivo[0], orariPartenzaEArrivo[1], new ArrayList<Citta>(percorso));
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
			case 3:
				inserisciInputItinerario(sc, operazioneScelta, codice, oraPartenza, minutoPartenza,
						oraArrivo, minutoArrivo, orariPartenzaEArrivo, percorso);
				
				try {
					pickYourLine.modificaItinerario(codice.toString(), orariPartenzaEArrivo[0], orariPartenzaEArrivo[1], new ArrayList<Citta>(percorso));
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
			case 4:
				sc = new Scanner(System.in);
				System.out.println("\nInserisci il codice dell'itinerario");
				codice.append(sc.nextLine());
				
				try {
					pickYourLine.eliminaItinerario(codice.toString());
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
		}
	}
	
	@SuppressWarnings("resource")
	private static void inserisciInputItinerario(Scanner sc, int operazioneScelta,
			StringBuilder codice, int oraPartenza, int minutoPartenza, int oraArrivo, int minutoArrivo,
			LocalTime[] orariPartenzaEArrivo, Set<Citta> percorso) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);
		
		boolean success;
		Itinerario i;
		
		do {
			success = true;
			
			System.out.println("\nInserisci il codice dell'itinerario");
			codice.append(sc.nextLine());
			
			i = pickYourLine.getElencoItinerari().get(codice.toString());
			
			if(operazioneScelta == 2 && i != null) {
				System.out.println("\nCodice itinerario già presente");
				success = false;
				codice.setLength(0);
			} else if(operazioneScelta == 3 && i == null){
				System.out.println("\nCodice itinerario non esistente.");
				success = false;
				codice.setLength(0);
			}
		} while(!success);
		
		do {
			success = true;
			
			if(operazioneScelta == 2) {
				System.out.println("Inserisci l'ora di partenza");
			} else {
				System.out.println("Inserisci l'ora di partenza, 0 per non modificare");
			}
			
			oraPartenza = sc.nextInt();
			
			if(operazioneScelta == 2 || (operazioneScelta == 3 && oraPartenza != 0)) {
				System.out.println("Inserisci il minuto di partenza");
				minutoPartenza = sc.nextInt();
				orariPartenzaEArrivo[0] = LocalTime.of(oraPartenza, minutoPartenza);
			} else {
				orariPartenzaEArrivo[0] = i.getOrarioPartenza();
			}
			
			if(operazioneScelta == 2) {
				System.out.println("Inserisci l'ora di arrivo");
			} else {
				System.out.println("Inserisci l'ora di arrivo, 0 per non modificare");
			}
			
			oraArrivo = sc.nextInt();
			
			if(operazioneScelta == 2 || (operazioneScelta == 3 && oraArrivo != 0)) {
				System.out.println("Inserisci il minuto di arrivo");
				minutoArrivo = sc.nextInt();
				orariPartenzaEArrivo[1] = LocalTime.of(oraArrivo, minutoArrivo);
			} else {
				orariPartenzaEArrivo[1] = i.getOrarioArrivo();
			}
			
			
			if(!orariPartenzaEArrivo[1].isAfter(orariPartenzaEArrivo[0])) {
				System.out.println("\nL'orario di arrivo deve essere successivo a quello di partenza.");
				success = false;
			}
			
		} while(!success);
		
		int codiceCitta;
		Citta c;
		
		pickYourLine.visualizzaElencoCittaPartenza();
		
		while(true) {
			System.out.println("\nInserisci il codice della città da aggiungere al percorso dell'itinerario, 0 per terminare l'operazione");
			codiceCitta = sc.nextInt();
			
			if(codiceCitta == 0) {
				if((operazioneScelta == 2 && percorso.size() < 2) || (operazioneScelta == 3 && !percorso.isEmpty() && percorso.size() < 2)) {
					System.out.println("\nIl percorso deve essere composto da almeno due città.");
					continue;
				} else if(percorso.size() >= 2 || (operazioneScelta == 3 && percorso.isEmpty())) {
					break;
				}	
			}
			
			c = pickYourLine.getElencoCitta().get(codiceCitta);
			
			if(c == null) {
				System.out.println("\nCodice città non esistente.");
			}
			
			if(!percorso.add(c)) {
				System.out.println("\nCittà già presente nel percorso.");
			}
		}
	}
	
	public static void gestisciAutomezzi(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		StringBuilder codice = new StringBuilder(), codiceItinerario = new StringBuilder();
		AtomicInteger posti = new AtomicInteger(), codiceStato = new AtomicInteger();
		
		System.out.println("Inserisci 1 per visualizzare, 2 per inserire, 3 per modificare, 4 per eliminare, qualsiasi per uscire:");
		int operazioneScelta = sc.nextInt();
		
		switch(operazioneScelta) {
			case 1:
				pickYourLine.visualizzaElencoAutomezzi();
				break;
			case 2:
				inserisciInputAutomezzzo(sc, operazioneScelta, codice, posti, codiceItinerario, codiceStato);
				
				try {
					pickYourLine.inserisciAutomezzo(codice.toString(), posti.get(), codiceItinerario.toString());
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
			case 3:
				inserisciInputAutomezzzo(sc, operazioneScelta, codice, posti, codiceItinerario, codiceStato);
				
				try {
					pickYourLine.modificaAutomezzo(codice.toString(), codiceStato.get(), codiceItinerario.toString());
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
			case 4:
				sc = new Scanner(System.in);
				System.out.println("\nInserisci il codice dell'automezzo");
				codice.append(sc.nextLine());
				
				try {
					pickYourLine.eliminaAutomezzo(codice.toString());
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
		}
	}
	
	@SuppressWarnings("resource")
	private static void inserisciInputAutomezzzo(Scanner sc, int operazioneScelta,
			StringBuilder codice, AtomicInteger posti, StringBuilder codiceItinerario, AtomicInteger codiceStato) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);
		
		boolean success;
		Automezzo automezzo = null;
		
		do {
			System.out.println("\nInserisci il codice dell'automezzo");
			codice.append(sc.nextLine());
			
			success = !pickYourLine.getElencoAutomezzi().containsKey(codice.toString());
			
			if(operazioneScelta == 2) {
				if(!success) {
					System.out.println("\nCodice automezzo già esistente.");
					codice.setLength(0);
				}
			}
			
			if(operazioneScelta == 3) {
				Map<String, Automezzo> automezziModificabili = new HashMap<String, Automezzo>();
				
				pickYourLine.getElencoAutomezzi().forEach((k, a) -> {
					if(a.getStato() instanceof NonInTransito || a.getStato() instanceof InManutenzione) {
						automezziModificabili.put(a.getCodice(), a);
					}
				});
				
				automezzo = automezziModificabili.get(codice.toString());
				
				if(automezzo == null) {
					System.out.println("\nCodice automezzo non esistente o non modificabile perchè in transito.");
					codice.setLength(0);
				}
			}
		} while(automezzo == null);
		
		System.out.println("Inserisci 1 per cambiare lo stato in NonInTransito, 2 in Manutenzione, 3 in Dismesso, 0 per non cambiarlo");
		codiceStato.set(sc.nextInt());
		
		sc = new Scanner(System.in);
		
		if((!(automezzo.getStato() instanceof NonInTransito) && codiceStato.get() != 1) || ((automezzo.getStato() instanceof NonInTransito)) && (codiceStato.get() == 2 || codiceStato.get() == 3)) {
			return;
		}
		
		pickYourLine.visualizzaElencoItinerari();
		
		do {
			success = true;
			
			if(operazioneScelta == 2) {
				System.out.println("\nInserisci il codice dell'itinerario");
			} else {
				System.out.println("\nInserisci il codice dell'itinerario, 0 per non modificare");
			}
			
			codiceItinerario.append(sc.nextLine());
			
			if(!codiceItinerario.toString().equals("0")) {
				success = pickYourLine.getElencoItinerari().containsKey(codiceItinerario.toString());
				
				if(operazioneScelta == 2 && !success) {
					System.out.println("\nCodice itinerario non esistente.");
					codiceItinerario.setLength(0);
				}
			}
		} while(!success);
		
		if(operazioneScelta == 2) {
			System.out.println("\nInserisci il numero di posti");
			posti.set(sc.nextInt());
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

	@SuppressWarnings("resource")
	public static void visualizzaSegnalazioni(Scanner sc){
		PickYourLine pickYourLine = PickYourLine.getInstance();

		String codiceSegnalazione;
		sc = new Scanner(System.in);

		try {
			pickYourLine.visualizzaElencoSegnalazioni();
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
			return;
		}

		while (true){
			System.out.println("\nInserisci il codice di una segnalazione per vedere il dettaglio(0 per uscire):");
			codiceSegnalazione = sc.nextLine().trim();

			if (codiceSegnalazione.equals("0"))
				break;

			if (pickYourLine.getElencoSegnalazioni().containsKey(codiceSegnalazione)) {
				try{
					pickYourLine.visualizzaDettaglioSegnalazione(codiceSegnalazione);
				}
				catch(Exception e){
					System.out.println("\n" + e.getMessage());
				}
			}
			else {
				System.out.println("\nSegnalazione non trovata");
			}
		}

	}
	
	@SuppressWarnings("resource")
	public static void gestisciAvvisi(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		String codice, oggetto, contenuto;
		
		System.out.println("Inserisci 1 per inserire, 2 per modificare, 3 per eliminare, qualsiasi per uscire:");
		int operazioneScelta = sc.nextInt();
		
		sc = new Scanner(System.in);
		
		switch(operazioneScelta) {
			case 1: 
				System.out.println("Inserisci l'oggetto dell'avviso");
				oggetto = sc.nextLine();
				System.out.println("Inserisci il contenuto dell'avviso");
				contenuto = sc.nextLine();
				
				pickYourLine.inserisciAvviso(oggetto, contenuto);
				break;
			case 2: 
				do {
					System.out.println("Inserisci il codice dell'avviso");
					codice = sc.nextLine();
					
					if(!pickYourLine.getElencoAvvisi().containsKey(codice)) {
						System.out.println("Codice avviso non esistente.");
					}
				} while(!pickYourLine.getElencoAvvisi().containsKey(codice));
			
				System.out.println("Inserisci l'oggetto dell'avviso, 0 per non modificare");
				oggetto = sc.nextLine();
				System.out.println("Inserisci il contenuto dell'avviso, 0 per non modificare");
				contenuto = sc.nextLine();
				
				try {
					pickYourLine.modificaAvviso(codice, oggetto, contenuto);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
			
				break;
			case 3: 
				System.out.println("Inserisci il codice dell'avviso");
				codice = sc.nextLine();
				
				try {
					pickYourLine.eliminaAvviso(codice);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				
				break;
		}
	}
	
	@SuppressWarnings("resource")
	public static void visualizzaAvvisi(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);
		String codice;

		while (true) {
			try {
				pickYourLine.visualizzaElencoAvvisi();
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}

			System.out.println("\nInserisci il codice dell'avviso da visualizzare in dettaglio, altrimenti 0 per uscire");
			codice = sc.nextLine();

			if (codice.equals("0"))
				break;

			try {
				pickYourLine.visualizzaDettaglioAvviso(codice);
				System.out.println("");
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		}
	}

}
