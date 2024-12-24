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
    	
        /*Fermata f1 = new Fermata("Stazione Randazzo FCE");
        Fermata f2 = new Fermata("Stazione Maletto FCE");
        Fermata f3 = new Fermata("Stazione Bronte FCE");
        Fermata f4 = new Fermata("Adrano Nord");
        Fermata f5 = new Fermata("Adrano Navicchia");
        Fermata f6 = new Fermata("Adrano Centro");
        Fermata f7 = new Fermata("Adrano Sant'Agostino");
        Fermata f8 = new Fermata("Adrano Cappuccini");
        Fermata f9 = new Fermata("Biancavilla Colombo");
        Fermata f10 = new Fermata("Biancavilla Ospedale");
        Fermata f11 = new Fermata("Biancavilla Pozzillo");
        Fermata f12 = new Fermata("Biancavilla Poggio Rosso");
        Fermata f13 = new Fermata("S.M.Licodia Sud");
        Fermata f14 = new Fermata("S.M.Licodia Via Alcide De Gasperi");
        Fermata f15 = new Fermata("S.M.Licodia Centro");
        Fermata f16 = new Fermata("Paternò Parco del Sole");
        Fermata f17 = new Fermata("Paternò Stazione FCE");
        Fermata f18 = new Fermata("Piano Tavola Stazione FCE");
        Fermata f19 = new Fermata("Etnapolis");
        Fermata f20 = new Fermata("Misterbianco Stazione FCE");
        Fermata f21 = new Fermata("Catania Ospedale Cannizzaro");
        Fermata f22 = new Fermata("Catania Nesima");
        Fermata f23 = new Fermata("Catania Borgo");
        this.elencoFermate.add(f1);
        this.elencoFermate.add(f2);
        this.elencoFermate.add(f3);*/
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citta citta = (Citta) o;
        return this.codice == citta.codice && this.nome.equals(citta.nome);
    }

}