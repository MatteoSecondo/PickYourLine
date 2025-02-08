import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Automezzo {

    private String codice;
    private int posti;
    private StatoAutomezzo stato;
    private LocalTime orarioUltimaTimbratura;
    private Biglietto bigliettoCorrente;
    private Map<String, Biglietto> elencoBiglietti;
    private Fermata posizioneAttuale;
    private Itinerario itinerarioAssegnato;

    public Automezzo(String codice, int posti) {
        this.elencoBiglietti = new HashMap<String, Biglietto>();
        this.codice = codice;
        this.posti = posti;
        this.stato = new NonInTransito();
    }
    
    public Automezzo(String codice, int posti, Itinerario i, Map<String, Biglietto> elencoBiglietti) {
        this.elencoBiglietti = elencoBiglietti;
        this.codice = codice;
        this.posti = posti;
        this.itinerarioAssegnato = i;
        this.posizioneAttuale = this.itinerarioAssegnato.getPercorso().getFirst().getElencoFermate().getFirst();
        this.orarioUltimaTimbratura = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.stato = new InTransito();
    }
    
    public StatoAutomezzo getStato() {
		return stato;
	}

	public void setStato(StatoAutomezzo stato) {
		this.stato = stato;
	}
	
	public void inSupervisione() {
		this.stato.inSupervisione(this);
	}
	
	public void nonInSupervisione() {
		this.stato.nonInSupervisione(this);
	}
	
	public void inManutenzione() {
		this.stato.inManutenzione(this);
	}
	
	public void inDismissione() {
		this.stato.inDismissione(this);
	}

	public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }


    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public LocalTime getOrarioUltimaTimbratura() {
        return orarioUltimaTimbratura;
    }

    public Map<String, Biglietto> getElencoBiglietti() {
        return elencoBiglietti;
    }

    public void setOrarioUltimaTimbratura(LocalTime orarioUltimaTimbratura) {
        this.orarioUltimaTimbratura = orarioUltimaTimbratura;
    }

    public Fermata getPosizioneAttuale() {
        return posizioneAttuale;
    }

    public void setPosizioneAttuale(Fermata posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
    }

    public Itinerario getItinerarioAssegnato() {
		return itinerarioAssegnato;
	}

    public void setItinerarioAssegnato(Itinerario itinerarioAssegnato) {
		this.itinerarioAssegnato = itinerarioAssegnato;
	}

	public void loadBiglietti(Map<String, Biglietto> elencoBiglietti){
        this.elencoBiglietti = elencoBiglietti;
    }

    public int getPostiDisponibili(){
        return posti - elencoBiglietti.size();
    }

    public Biglietto creaBiglietto(String codice, Citta cittaPartenza, Citta cittaDestinazione){
        Biglietto nuovoBiglietto = new Biglietto(codice, cittaPartenza, cittaDestinazione);
        this.bigliettoCorrente = nuovoBiglietto;
        return nuovoBiglietto;
    }

    public void inserisciBiglietto() {
        elencoBiglietti.put(bigliettoCorrente.getCodice(), bigliettoCorrente);
        setBigliettoCorrente(null);
    }

    public void setBigliettoCorrente(Biglietto biglietto) {
        this.bigliettoCorrente = biglietto;
    }
    
    public void aggiornaElencoBiglietti() {
    	int previousPostiDisponibili = getPostiDisponibili();
    	
    	List<Citta> percorso = this.itinerarioAssegnato.getPercorso();
    	
    	this.elencoBiglietti.values().removeIf(
    		    b -> (b.getCittaDestinazione().equals(this.posizioneAttuale.getCittaDiAppartenenza()))
    		    		&& this.posizioneAttuale.equals(b.getCittaDestinazione().getElencoFermate().getLast())
    	);
    	
    	this.elencoBiglietti.values().removeIf(
    		    b -> percorso.indexOf(b.getCittaDestinazione()) < percorso.indexOf(this.posizioneAttuale.getCittaDiAppartenenza())
    	);

    	System.out.println("Posti liberati:" + (getPostiDisponibili() - previousPostiDisponibili));
    }
    
    public boolean consentiTimbratura() {
    	Citta cittaAttuale = this.posizioneAttuale.getCittaDiAppartenenza();
    	Citta penultimaCitta = this.itinerarioAssegnato.getPercorso().get(this.itinerarioAssegnato.getPercorso().size() - 2);
    
    	if(cittaAttuale.equals(penultimaCitta)) {
    		return true;
    	}
    	return false;
    }
    
    public String getDettagliAutomezzo() {
    	String percorso = "";
    	
    	for (Citta c : this.itinerarioAssegnato.getPercorso()) {
			percorso = percorso.concat(" " + c.getNome() + " ->");
		}
    	
    	return "\nAutomezzo codice=" + codice + ", Posti=" + posti + ", PostiDisponibili=" + getPostiDisponibili()
    			+ ", Stato=" + stato
    			+ "\nOrarioUltimaTimbratura=" + orarioUltimaTimbratura
    			+ ", PosizioneAttuale= " + posizioneAttuale.getCittaDiAppartenenza().getNome() + ": " + posizioneAttuale.getNome()
				+ "\nItinerarioAssegnato=" + itinerarioAssegnato
    			+ "\nPercorsoItinerario=" + percorso;
    }

    public void svuotaAutomezzo() {
        this.elencoBiglietti.clear();
    }

	@Override
	public String toString() {
		return "Automezzo [codice=" + codice + ", posti=" + posti + ", stato=" + stato + ", itinerarioAssegnato="
				+ itinerarioAssegnato + "]";
	}
    
}
