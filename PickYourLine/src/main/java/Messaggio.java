import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public abstract class Messaggio {
	
	private String codice;
    private String oggetto;
    private String contenuto;
    private LocalDateTime timeStamp;
    
	public Messaggio(String codice, String oggetto, String contenuto, LocalDateTime timeStamp) {
		this.codice = codice;
		this.oggetto = oggetto;
		this.contenuto = contenuto;
		this.timeStamp = timeStamp;
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
	
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [codice=" + codice + ", oggetto=" + oggetto + ", data="
				+ formattaData(timeStamp) + "]";
	}

	public static String generaCodice() {
        int l = 5;
        String valori = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder codice = new StringBuilder();

        for (int i = 0; i < l; i++) {
            int indiceValore = random.nextInt(valori.length());
            codice.append(valori.charAt(indiceValore));
        }

        return codice.toString();
    }
	
	private String formattaData(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formatter.format(date);
    }
	
	public abstract void visualizzaDettaglio();
    
}
