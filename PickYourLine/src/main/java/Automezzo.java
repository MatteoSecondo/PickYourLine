import java.time.LocalTime;
import java.util.HashMap;
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
        this.stato = new InTransito();
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

    public void cambiaStato() {

        this.stato.cambiaStato();
    }

    @Override
    public String toString() {
        return "Automezzo{" +
                "codice='" + codice + '\'' +
                ", posti=" + posti +
                ", orarioUltimaTimbratura=" + orarioUltimaTimbratura +
                ", stato=" + stato +
                '}';
    }

}
