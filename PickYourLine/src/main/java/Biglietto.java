public class Biglietto {
    private String codice;
    private Citta cittaPartenza;
    private Citta cittaDestinazione;

    public Biglietto(String codice, Citta cittaPartenza, Citta cittaDestinazione) {
        this.codice = codice;
        this.cittaPartenza = cittaPartenza;
        this.cittaDestinazione = cittaDestinazione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Citta getCittaPartenza() {
        return cittaPartenza;
    }

    public void setCittaPartenza(Citta cittaPartenza) {
        this.cittaPartenza = cittaPartenza;
    }

    public Citta getCittaDestinazione() {
        return cittaDestinazione;
    }

    public void setCittaDestinazione(Citta cittaDestinazione) {
        this.cittaDestinazione = cittaDestinazione;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "codice='" + codice + '\'' +
                ", cittaPartenza=" + cittaPartenza +
                ", cittaDestinazione=" + cittaDestinazione +
                '}';
    }
}
