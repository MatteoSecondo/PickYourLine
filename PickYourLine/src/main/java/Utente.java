import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class Utente {
    public String codice;
    public String password;

    public Utente(String codice, String password) {
        this.codice = codice;
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public String getCodice() {
        return codice;
    }

    public String getPassword() { return password; }
}
