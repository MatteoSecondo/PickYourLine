import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Segnalazione {
    private String codice;
    private String oggetto;
    private String contenuto;
    private LocalDateTime timeStamp;
    private Cliente cliente;

    public Segnalazione(String oggetto, String contenuto) {
        this.codice = generaCodice();
        this.oggetto = oggetto;
        this.contenuto = contenuto;
        this.timeStamp = LocalDateTime.now();
        this.cliente = (Cliente) PickYourLine.getInstance().getUtenteCorrente();
    }

    public Segnalazione(String codice, String oggetto, String contenuto, LocalDateTime timestamp , Cliente cliente) {
        this.codice = codice;
        this.oggetto = oggetto;
        this.contenuto = contenuto;
        this.timeStamp = timestamp;
        this.cliente = cliente;
    }

    private String generaCodice() {
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

    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
    public String toString() {
        return "Segnalazione{" +
                "codice='" + codice + '\'' +
                ", oggetto='" + oggetto + '\'' +
                ", data='" + formattaData(timeStamp) + '\'' +
                '}';
    }

    public void visualizzaDettaglio() {
        System.out.println("Segnalazione{" +
                "codice='" + codice + '\'' +
                ", oggetto='" + oggetto + '\'' +
                ", data='" + formattaData(timeStamp) + '\'' +
                ", \ncontenuto='" + contenuto + '\'' +
                ", \ncliente='" + cliente + '\'' +
                '}');
    }


}
