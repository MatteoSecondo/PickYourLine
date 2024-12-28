import java.util.ArrayList;
import java.util.List;

public class Citta {
    private int codice;
    private String nome;
    private List<Fermata> elencoFermate;

    public Citta(int codice, String nome, List<Fermata> f) {
        this.codice = codice;
        this.nome = nome;
        this.elencoFermate = new ArrayList<>();
        loadFermate(f);
    }

    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Fermata> getElencoFermate() {
        return elencoFermate;
    }

    @Override
    public String toString() {
        return "Citta{" +
                "codice='" + codice + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

    public  void loadFermate(List<Fermata> f) {
    	this.elencoFermate = f;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citta citta = (Citta) o;
        return this.codice == citta.codice && this.nome.equals(citta.nome);
    }

}