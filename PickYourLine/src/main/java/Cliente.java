public class Cliente extends Utente{
    private String nome;
    private String cognome;

    public Cliente(String codice, String nome, String cognome) {
        super(codice);
        this.nome = nome;
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", codice='" + codice + '\'' +
                '}';

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;

    }
}
