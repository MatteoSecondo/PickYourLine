import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		pickYourLine.loadCitta();
		pickYourLine.loadItinerari();
		
		System.out.println("Benvenuto!");
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.println("Scegli tra le operazioni disponibili.");
			System.out.println("0- Esci\n"
								+ "1- Cerca itinerario");
			
			int scelta = sc.nextInt();
			
			switch(scelta) {
				case 0:
					System.out.println("Arrivederci!");
					sc.close();
					System.exit(0);
				case 1:
					cercaItinerario();
					break;
			}
		}
	}
	
	public static void cercaItinerario() {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		
		pickYourLine.cercaItinerario();
		
		try {
			pickYourLine.inserisciCittaPartenza();
			pickYourLine.inserisciCittaDestinazione();
			pickYourLine.visualizzaItinerario();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
