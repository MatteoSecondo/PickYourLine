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
    }
}
