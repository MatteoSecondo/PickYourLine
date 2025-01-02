import java.time.LocalTime;

public class Controllore extends Utente {
    private Automezzo automezzoSupervisionato;

    public Controllore(String codice) {
        super(codice);
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

    public void aggiornaPosizione(Fermata fermata) {
        automezzoSupervisionato.setPosizioneAttuale(fermata);
        System.out.println("Aggiornata la posizione dell'automezzo a: " + fermata);
    }

    public void aggiornaOrarioUltimaTimbratura() {
        automezzoSupervisionato.setOrarioUltimaTimbratura(LocalTime.now());
        System.out.println("Aggiornato l'orario di ultima timbratura a: " + LocalTime.now());
    }
}
