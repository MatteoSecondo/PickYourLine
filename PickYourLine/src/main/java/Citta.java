import java.util.ArrayList;
import java.util.List;

public class Citta {
    private int codice;
    private String nome;
    private List<Fermata> elencoFermate;

    public Citta(int codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.elencoFermate = new ArrayList<>();
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

    public  void loadFermate(List<Fermata> f) {
    	this.elencoFermate = f;
    }
    
    public Fermata getFermata(String nomeFermata) {
    	Fermata fermata = null;
    	
    	for (Fermata f : this.elencoFermate) {
    		fermata = f.getFermata(nomeFermata);
			
			if(fermata != null) break;
		}
		
		return fermata;
	}

    public void visualizzaElencoFermate() {
        elencoFermate.forEach(f -> System.out.println(f.getNome()));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citta citta = (Citta) o;
        return this.codice == citta.codice && this.nome.equals(citta.nome);
    }
    
    @Override
    public String toString() {
        return "Citta{" +
                "codice='" + codice + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }

}