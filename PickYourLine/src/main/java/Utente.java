public abstract class Utente {
    public String codice;
    public String password;

    public Utente(String codice, String password) {
        this.codice = codice;
        this.password = password;
    }

    public String getCodice() {
        return codice;
    }

    public String getPassword() { return password; }
}
