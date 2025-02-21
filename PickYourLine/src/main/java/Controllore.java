import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Controllore extends Utente {
    private Automezzo automezzoSupervisionato;

    public Controllore(String codice,String password) {
        super(codice,password);
    }

    public Automezzo getAutomezzoSupervisionato() {
        return automezzoSupervisionato;
    }

    public void setAutomezzoSupervisionato(Automezzo automezzoSupervisionato) {
        this.automezzoSupervisionato = automezzoSupervisionato;
    }

    @Override
    public String toString() {
        return "Controllore{" +
                "codice'" + codice + '\'' +
                '}';
    }

    public int verificaDisponibilitaPosti() {
        return automezzoSupervisionato.getPostiDisponibili();
    }

    public Biglietto creaBiglietto(String codice, Citta cittaPartenza, Citta cittaDestinazione) {
        return automezzoSupervisionato.creaBiglietto(codice,cittaPartenza,cittaDestinazione);
    }

    public void confermaInserimento() {
        automezzoSupervisionato.inserisciBiglietto();
        System.out.println("Biglietto inserito nell'elenco biglietti");
    }

    public boolean aggiornaPosizione(Fermata fermata) throws Exception {
    	
    	List<Citta> percorso = automezzoSupervisionato.getItinerarioAssegnato().getPercorso();
    	Citta cittaAttuale = automezzoSupervisionato.getPosizioneAttuale().getCittaDiAppartenenza();
    	Citta prossimaCitta = fermata.getCittaDiAppartenenza();
    	
    	if(percorso.indexOf(cittaAttuale) > percorso.indexOf(prossimaCitta)) {
    		throw new Exception("Non è possibile tornare alle citta precedenti.");
    	}
    	
    	if(cittaAttuale.equals(prossimaCitta)) {
    		Fermata fermataAttuale = automezzoSupervisionato.getPosizioneAttuale();
    		
    		if(fermataAttuale.equals(fermata)) {
    			throw new Exception("Hai già timbrato biglietti per questa fermata.");
    		}
    		
    		if(cittaAttuale.getElencoFermate().indexOf(fermataAttuale) > cittaAttuale.getElencoFermate().indexOf(fermata)) {
    			throw new Exception("Non è possibile tornare alle fermate precedenti.");
    		}
    	}
    	
        automezzoSupervisionato.setPosizioneAttuale(fermata);
        automezzoSupervisionato.aggiornaElencoBiglietti();
        
        System.out.println("Aggiornata la posizione dell'automezzo a: " + fermata);
        
        return automezzoSupervisionato.consentiTimbratura();
    }

    public void aggiornaOrarioUltimaTimbratura() {
        automezzoSupervisionato.setOrarioUltimaTimbratura(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        System.out.println("Aggiornato l'orario di ultima timbratura a: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
