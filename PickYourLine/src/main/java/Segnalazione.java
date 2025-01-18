import java.time.LocalDateTime;
import java.util.Date;

public class Segnalazione {
    private String codice;
    private String oggetto;
    private String contenuto;
    private LocalDateTime timestamp;

    public Segnalazione(String codice, String oggetto, String contenuto, Date now, Cliente cliente) {
        this.codice = codice;
        this.oggetto = oggetto;
        this.contenuto = contenuto;
        this.timestamp = LocalDateTime.now();
    }

    public String getCodice() {
        return codice;
    }
    public void setCodice(String codice) {
        this.codice = codice;
    }
    public String getOggetto() {
        return oggetto;
    }
    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }
    public String getContenuto() {
        return contenuto;
    }
    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public void visualizzaDettaglio(){
        System.out.println("\n--------- Segnalazione ---------");
        System.out.println("Codice: " + codice);
        System.out.println("Oggetto: " + oggetto);
        System.out.println("Contenuto: " + contenuto);
        System.out.println("Data: " + timestamp);
    }

}
