public class Amministratore extends Utente{

    public Amministratore(String codice,String password) {
    	super(codice,password);
    }

	@Override
	public String toString() {
		return "Amministratore [codice=" + codice + "]";
	}
	
}
