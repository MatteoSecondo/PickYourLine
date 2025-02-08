public class Amministratore extends Utente{

    public Amministratore(String codice) {
    	super(codice);
    }

	@Override
	public String toString() {
		return "Amministratore [codice=" + codice + "]";
	}
	
}
