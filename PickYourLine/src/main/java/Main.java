import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
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
		pickYourLine.loadClienti();
		pickYourLine.loadAmministratori();
		pickYourLine.loadElencoSegnalazioni();
		pickYourLine.loadAvvisi();
		
		System.out.println("Benvenuto!");
		Scanner sc;
		boolean ultimaCitta = false;
		int scelta = 0;

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
					+ "10- Visualizza Segnalazioni\n"
					+ "11- Gestisci Controllori\n"
					+ "12- Inizio Corsa\n"
					+ "13- Fine Corsa\n"
					+ "14- Login Utente\n"
					+ "15- Logout Utente\n"
					+ "16- Registrazione Cliente\n"
					);
			

			sc = new Scanner(System.in);

			try {
				scelta = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}

			switch(scelta) {
				case 0:
					System.out.println("Arrivederci!");
					sc.close();
					System.exit(0);
				case 1:
					cercaItinerario(sc);
					break;
				case 2:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Controllore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					ultimaCitta = timbraBiglietto(sc, ultimaCitta);
					break;
				case 3:
					monitoraAutomezzo(sc);
					break;
				case 4:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Amministratore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					gestisciItinerari(sc);
					break;
				case 5:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Amministratore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					gestisciAutomezzi(sc);
					break;
				case 6:
					visualizzaFermate(sc);
					break;
				case 7:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Amministratore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					gestisciAvvisi(sc);
					break;
				case 8:
					visualizzaAvvisi(sc);
					break;
				case 9:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Cliente)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					invioSegnalazione(sc);
					break;
				case 10:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Amministratore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					visualizzaSegnalazioni(sc);
					break;
				case 11:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Amministratore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					gestisciControllori(sc);
          			break;
				case 12:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Controllore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					inizioCorsa(sc);
					break;
				case 13:
					if(pickYourLine.getUtenteCorrente() == null || !(pickYourLine.getUtenteCorrente() instanceof Controllore)) {
						System.out.println("Non hai il permesso per effettuare l'operazione.");
						break;
					}
					
					fineCorsa();
					break;
				case 14:
					loginUtente(sc);
					break;
				case 15:
					logoutUtente();
					break;
				case 16:
					registrazioneCliente(sc);
					break;
			}

		}
	}

	@SuppressWarnings("resource")
	public static void cercaItinerario(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		pickYourLine.visualizzaElencoCittaPartenza();
		
		Map<Integer, Citta> elencoDestinazioniDisponibili = null;
		int codiceCittaPartenza = 0;

		do {
			System.out.println("\nInserisci il codice della città di partenza");
			sc = new Scanner(System.in);

			try {
				codiceCittaPartenza = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}

			try {
				elencoDestinazioniDisponibili = pickYourLine.inserisciCittaPartenza(codiceCittaPartenza);
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}
		} while(elencoDestinazioniDisponibili == null);
		
		elencoDestinazioniDisponibili.forEach((key, c) -> System.out.println(c));

		Map<String, Itinerario> itinerariDisponibili = null;
		int codiceCittaDestinazione = 0;

		do {
			System.out.println("\nInserisci il codice della città di destinazione");
			sc = new Scanner(System.in);

			try {
				codiceCittaDestinazione = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}

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

		if (co.getAutomezzoSupervisionato() == null) {
			System.out.println("Non puoi timbrare biglietti finchè non supervisioni un automezzo");
			return false;
		}
		
		int scelta = 0;
	
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
			successo = false;
			Biglietto b = null;
			int codiceCittaPartenza = 0;
			int codiceCittaDestinazione = 0;

			do {
				System.out.println("\nInserisci il codice del biglietto");
				sc = new Scanner(System.in);
				String codiceBiglietto = sc.nextLine();

				do {
					sc = new Scanner(System.in);

					try {
						System.out.println("\nInserisci il codice della città di partenza");
						codiceCittaPartenza = sc.nextInt();
						System.out.println("\nInserisci il codice della città di destinazione");
						codiceCittaDestinazione = sc.nextInt();
						successo = true;
					} catch (InputMismatchException e) {
						System.out.println("Inserisci un valore numerico.");
						continue;
					}
				} while(!successo);

				try {
					b = pickYourLine.timbraBiglietto(codiceBiglietto, codiceCittaPartenza, codiceCittaDestinazione);
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
					if(e.getMessage().equals("Non ci sono posti disponibili, operazione terminata.")) return false;
				}
			} while(b == null);
			
			System.out.println(b);

			int conferma = 0;
			successo = false;

			do {
				sc = new Scanner(System.in);
				System.out.println("\nInserisci 0 per annullare l'inserimento, altrimenti qualsiasi per confermare");

				try {
					conferma = sc.nextInt();
					successo = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!successo);
			
			if(conferma != 0) {
				pickYourLine.confermaInserimento();
			}
			
			successo = false;

			do {
				sc = new Scanner(System.in);
				System.out.println("Inserisci 0 per terminare l'inserimento, altrimenti qualsiasi per continuare");

				try {
					scelta = sc.nextInt();
					successo = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!successo);

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
	
	@SuppressWarnings("resource")
	public static void gestisciItinerari(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		StringBuilder codice = new StringBuilder();
		int oraPartenza = 0, minutoPartenza = 0, oraArrivo = 0, minutoArrivo = 0;
		LocalTime[] orariPartenzaEArrivo = new LocalTime[2];
		Set<Citta> percorso = new HashSet<Citta>();
		boolean successo = false;
		int operazioneScelta = 0;

		do {
			System.out.println("Inserisci 1 per visualizzare, 2 per inserire, 3 per modificare, 4 per eliminare, qualsiasi per uscire:");
			sc = new Scanner(System.in);

			try {
				operazioneScelta = sc.nextInt();
				successo = true;
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}
		} while(!successo);
		
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
		
		boolean success = true;
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
			success = false;
			
			do {
				if(operazioneScelta == 2) {
					System.out.println("Inserisci l'ora di partenza");
				} else {
					System.out.println("Inserisci l'ora di partenza, 0 per non modificare");
				}

				sc = new Scanner(System.in);

				try {
					oraPartenza = sc.nextInt();
					success = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!success);
			
			if(operazioneScelta == 2 || (operazioneScelta == 3 && oraPartenza != 0)) {
				success = false;

				do {
					System.out.println("Inserisci il minuto di partenza");
					sc = new Scanner(System.in);

					try {
						minutoPartenza = sc.nextInt();
						success = true;
					} catch (InputMismatchException e) {
						System.out.println("Inserisci un valore numerico.");
						continue;
					}
				} while(!success);

				orariPartenzaEArrivo[0] = LocalTime.of(oraPartenza, minutoPartenza);
			} else {
				orariPartenzaEArrivo[0] = i.getOrarioPartenza();
			}
			
			success = false;

			do {
				if(operazioneScelta == 2) {
					System.out.println("Inserisci l'ora di arrivo");
				} else {
					System.out.println("Inserisci l'ora di arrivo, 0 per non modificare");
				}

				sc = new Scanner(System.in);

				try {
					oraArrivo = sc.nextInt();
					success = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!success);
			
			if(operazioneScelta == 2 || (operazioneScelta == 3 && oraArrivo != 0)) {
				success = false;

				do {
					System.out.println("Inserisci il minuto di arrivo");
					sc = new Scanner(System.in);

					try {
						minutoArrivo = sc.nextInt();
						success = true;
					} catch (InputMismatchException e) {
						System.out.println("Inserisci un valore numerico.");
						continue;
					}
				} while(!success);

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
			codiceCitta = -1;

			do {
				System.out.println("\nInserisci il codice della città da aggiungere al percorso dell'itinerario, 0 per terminare l'operazione");
				sc = new Scanner(System.in);

				try {
					codiceCitta = sc.nextInt();
					success = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!success);
			
			if(codiceCitta == 0) {
				if((operazioneScelta == 2 && percorso.size() < 2) || (operazioneScelta == 3 && !percorso.isEmpty() && percorso.size() < 2)) {
					System.out.println("\nIl percorso deve essere composto da almeno due città.");
					continue;
				} else if(percorso.size() >= 2 || (operazioneScelta == 3 && percorso.isEmpty())) {
					break;
				}	
			}
			
			if(codiceCitta != -1) {
				c = pickYourLine.getElencoCitta().get(codiceCitta);

				if(c == null) {
					System.out.println("\nCodice città non esistente.");
				}

				if(!percorso.add(c)) {
					System.out.println("\nCittà già presente nel percorso.");
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public static void gestisciAutomezzi(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		StringBuilder codice = new StringBuilder(), codiceItinerario = new StringBuilder();
		AtomicInteger posti = new AtomicInteger(), codiceStato = new AtomicInteger();
		
		boolean successo = false;
		int operazioneScelta = 0;

		do {
			System.out.println("Inserisci 1 per visualizzare, 2 per inserire, 3 per modificare, 4 per eliminare, qualsiasi per uscire:");
			sc = new Scanner(System.in);

			try {
				operazioneScelta = sc.nextInt();
				successo = true;
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}
		} while(!successo);
		
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
			
			success = true;

			if(operazioneScelta == 3) {
				Map<String, Automezzo> automezziModificabili = new HashMap<String, Automezzo>();
				
				pickYourLine.getElencoAutomezzi().forEach((k, a) -> {
					if(a.getStato() instanceof NonInTransito || a.getStato() instanceof InManutenzione) {
						automezziModificabili.put(a.getCodice(), a);
					}
				});
				
				automezzo = automezziModificabili.get(codice.toString());
				
				if(automezzo == null) {
					System.out.println("\nAutomezzo non modificabile perchè in transito o dismesso.");
					codice.setLength(0);
					success = false;
				}
			}
		} while(!success);
		
		success = false;

		if(operazioneScelta == 3) {
			do {
				System.out.println("Inserisci 1 per cambiare lo stato in NonInTransito, 2 in Manutenzione, 3 in Dismesso, 0 per non cambiarlo");
				sc = new Scanner(System.in);

				try {
					codiceStato.set(sc.nextInt());
					success = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!success);

			sc = new Scanner(System.in);

			if((!(automezzo.getStato() instanceof NonInTransito) && codiceStato.get() != 1) || ((automezzo.getStato() instanceof NonInTransito)) && (codiceStato.get() == 2 || codiceStato.get() == 3)) {
				return;
			}
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
		
		success = false;

		if(operazioneScelta == 2) {
			do {
				System.out.println("\nInserisci il numero di posti");
				sc = new Scanner(System.in);

				try {
					posti.set(sc.nextInt());
					success = true;
				} catch (InputMismatchException e) {
					System.out.println("Inserisci un valore numerico.");
					continue;
				}
			} while(!success);
		}
	}

	@SuppressWarnings("resource")
	public static void visualizzaFermate(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		sc = new Scanner(System.in);

		boolean successo = false;
		int codiceCitta = -1;
		System.out.println("Elenco Città:");
		pickYourLine.visualizzaElencoCittaPartenza();

		while (true) {
			do {
				codiceCitta = -1;
				do {
					System.out.println("\nInserisci il codice della città di cui vuoi conoscere le fermate, altrimenti 0 per uscire");
					sc = new Scanner(System.in);

					try {
						codiceCitta = sc.nextInt();
						successo = true;
					} catch (InputMismatchException e) {
						System.out.println("Inserisci un valore numerico.");
						continue;
					}
				} while(!successo);

				if (codiceCitta == 0) {
					return;
				} else if(codiceCitta == -1) {
					continue;
				}

				successo = false;

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
		sc = new Scanner(System.in);

		String oggettoSegnalazione;
		String contenutoSegnalazione;

		while(true) {
			Segnalazione segnalazione = null;
			do {
				System.out.println("Inserisci oggetto della segnalazione:");
				oggettoSegnalazione = sc.nextLine();
				System.out.println("\nInserisci contenuto della segnalazione: ");
				contenutoSegnalazione = sc.nextLine();

				try {
					segnalazione = pickYourLine.creaSegnalazione(oggettoSegnalazione,contenutoSegnalazione);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}

				segnalazione.visualizzaDettaglio();

				System.out.println("\nInserisci 0 per annullare l'invio della segnalazione, altrimenti qualsiasi per confermare l'invio");

				if(!sc.nextLine().equals("0")) {
					pickYourLine.invioSegnalazione(segnalazione);
				}
			}while (segnalazione == null);

			System.out.println("Inserisci 0 per terminare l'operazione, altrimenti qualsiasi per continuare");

			if(sc.nextLine().equals("0")) {
				return;
			}
		}
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
		int operazioneScelta = 0;
		boolean successo = false;

		do {
			System.out.println("Inserisci 1 per inserire, 2 per modificare, 3 per eliminare, qualsiasi per uscire:");
			sc = new Scanner(System.in);

			try {
				operazioneScelta = sc.nextInt();
				successo = true;
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}
		} while(!successo);
		
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

	@SuppressWarnings("resource")
	public static void inizioCorsa(Scanner sc){
		PickYourLine pickYourLine = PickYourLine.getInstance();
		Map<String, Automezzo> automezziDisponibili = new HashMap<>();
		String codiceSupervisione;
		boolean codiceValido = false;

		sc = new Scanner(System.in);

		try {
			automezziDisponibili = pickYourLine.visualizzaElencoAutomezziNonInTransito();
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
			return;
		}
		do {
			System.out.println("Inserisci il codice dell'automezzo che vuoi supervisionare(0 per uscire): ");
			codiceSupervisione = sc.nextLine();

			if (codiceSupervisione.equals("0"))
				break;

			try {
				pickYourLine.supervisionaAutomezzo(codiceSupervisione, automezziDisponibili);
				codiceValido = true;
				System.out.println("Inizio servizio...\n");
				System.out.println("Automezzo supervisionato correttamente.");
			} catch (Exception e) {
				System.out.println("\n" + e.getMessage());
			}

		} while (!codiceValido);

    }

	public static void fineCorsa() {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		try{
			pickYourLine.fineCorsa();
		}catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}
	}

	@SuppressWarnings("resource")
	public static void gestisciControllori(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		String codice;
		int operazioneScelta = 0;
		boolean successo = false;

		do {
			System.out.println("Inserisci 1 per visualizzare, 2 per inserire, 3 per eliminare, qualsiasi per uscire:");
			sc = new Scanner(System.in);

			try {
				operazioneScelta = sc.nextInt();
				successo = true;
			} catch (InputMismatchException e) {
				System.out.println("Inserisci un valore numerico.");
				continue;
			}
		} while(!successo);

		sc = new Scanner(System.in);

		switch (operazioneScelta) {
			case 1:
				pickYourLine.visualizzaElencoControllori();
				break;
			case 2:
				System.out.println("Inserisci il codice del controllore:");
				codice = sc.nextLine();
				try {
					pickYourLine.inserisciControllore(codice);
					System.out.println("Controllore inserito correttamente.");
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				break;
			case 3:
				System.out.println("Inserisci il codice del controllore da eliminare:");
				codice = sc.nextLine();

				try {
					pickYourLine.eliminaControllore(codice);
					System.out.println("Controllore eliminato correttamente.");
				} catch (Exception e) {
					System.out.println("\n" + e.getMessage());
				}
				break;
		}
	}

	@SuppressWarnings("resource")
	public static void loginUtente(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		String codice;
		String password;

		if (pickYourLine.verificaAutenticazione()) {
			System.out.println("Hai già effettuato l'accesso. Effettua il logout prima di tentare il login.");
			return;
		}

		System.out.println("Inserisci codice utente: ");
		sc = new Scanner(System.in);
		codice = sc.nextLine();
		System.out.println("Inserisci password: ");
		sc = new Scanner(System.in);
		password = sc.nextLine();

		if (codice.isEmpty() || password.isEmpty()) {
			System.out.println("Codice utente o password non possono essere vuoti.");
			return;
		}

		try{
			pickYourLine.login(codice,password);
		}catch (Exception e){
			System.out.println("\n" + e.getMessage());
		}
	}

	public static void logoutUtente() {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		try {
			pickYourLine.logout();
			System.out.println("Hai effettuato il logout correttamente.");
		} catch (Exception e) {
			System.out.println("\n" + e.getMessage());
		}
	}

	public static void registrazioneCliente(Scanner sc) {
		PickYourLine pickYourLine = PickYourLine.getInstance();

		if (pickYourLine.verificaAutenticazione()) {
			System.out.println("Non puoi effettuare la registrazione, bisogna effettuare logout");
			return;
		}

		sc.nextLine();

		System.out.println("Inserisci il codice utente (min:2  max:6):");
		String codice = sc.nextLine();
		System.out.println("Inserisci la password (min:8  max:16  almeno una maiuscola, una minuscola, un numero e un carattere speciale):");
		String password = sc.nextLine();
		System.out.println("Inserisci il nome:");
		String nome = sc.nextLine();
		System.out.println("Inserisci il cognome:");
		String cognome = sc.nextLine();

		try{
			pickYourLine.registrazioneCliente(codice, password, nome, cognome);
			System.out.println("Registrazione avvenuta con successo.");
		}
		catch (Exception e){
			System.out.println("\n" + e.getMessage());
		}



	}
}
