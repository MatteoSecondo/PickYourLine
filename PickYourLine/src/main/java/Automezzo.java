import java.time.LocalTime;
import java.util.Map;

public class Automezzo {
    private String codice;
    private int posti;
    private LocalTime orarioUltimaTimbratura;
    private Fermata posizioneAttuale;
    private Map<String,Biglietto> elencoBiglietti;
    private Biglietto bigliettoCorrente;

    public Automezzo(String codice, int posti) {
        this.codice = codice;
        this.posti = posti;
        this.orarioUltimaTimbratura = null;
        this.posizioneAttuale = null;
        this.elencoBiglietti = null;
        this.bigliettoCorrente = null;
    }

    public void setPosizioneAttuale(Fermata posizioneAttuale) {
        this.posizioneAttuale = posizioneAttuale;
    }

    public void setOrarioUltimaTimbratura(LocalTime orarioUltimaTimbratura) {
        this.orarioUltimaTimbratura = orarioUltimaTimbratura;
    }

    public Biglietto getBigliettoCorrente() {
        return bigliettoCorrente;
    }

    public void setBigliettoCorrente(Biglietto bigliettoCorrente) {
        this.bigliettoCorrente = bigliettoCorrente;
    }

    public Map<String, Biglietto> getElencoBiglietti() {
        return elencoBiglietti;
    }

    public void setElencoBiglietti(Map<String, Biglietto> elencoBiglietti) {
        this.elencoBiglietti = elencoBiglietti;
    }

    public int getPostiDisponibili() {
        return posti - elencoBiglietti.size();
    }

    public Biglietto creaBiglietto(String codice,Citta cittaPartenza, Citta cittaDestinazione) {
        return new Biglietto(codice,cittaPartenza,cittaDestinazione);
    }

    public void inserisciBiglietto() {
        elencoBiglietti.put(bigliettoCorrente.getCodice(),bigliettoCorrente);
        setBigliettoCorrente(null);
    }

    @Override
    public String toString() {
        return "Automezzo{" +
                "codice='" + codice + '\'' +
                ", posti=" + posti +
                ", orarioUltimaTimbratura=" + orarioUltimaTimbratura +
                ", posizioneAttuale=" + posizioneAttuale +
                ", elencoBiglietti=" + elencoBiglietti +
                ", bigliettoCorrente=" + bigliettoCorrente +
                '}';
    }
}
