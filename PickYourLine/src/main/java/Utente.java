public abstract class Utente {
    protected String codice;

    public Utente(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public abstract String toString();
}
