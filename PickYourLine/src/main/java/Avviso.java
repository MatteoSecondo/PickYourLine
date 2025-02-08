import java.time.LocalDateTime;

public class Avviso extends Messaggio {
	
	private Amministratore amministratore;

	public Avviso(String oggetto, String contenuto) {
		super(generaCodice(), oggetto, contenuto, LocalDateTime.now());
		this.amministratore = (Amministratore) PickYourLine.getInstance().getUtenteCorrente();
	}
	
	public Avviso(String codice, String oggetto, String contenuto, LocalDateTime timestamp, Amministratore am) {
		super(codice, oggetto, contenuto, timestamp);
		this.amministratore = am;
	}

	public Amministratore getAmministratore() {
		return amministratore;
	}

	public void setAmministratore(Amministratore amministratore) {
		this.amministratore = amministratore;
	}
	
	@Override
	public void visualizzaDettaglio() {
		String s = super.toString();
		
        System.out.println(s.substring(0, s.length() - 1) +
                ", \ncontenuto='" + this.getContenuto() + '\'' +
                ", \namministratore='" + amministratore + '\'' +
                ']'
        );
    }

}
