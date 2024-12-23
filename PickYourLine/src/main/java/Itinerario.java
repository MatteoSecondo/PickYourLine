import java.time.LocalTime;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;




public class Itinerario{

    private String codice;
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;

    private List<Citta> percorso;


    public Itinerario(String codice, LocalTime orarioPartenza, LocalTime orarioArrivo){
        this.codice = codice;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.percorso = new ArrayList<Citta>();
    }

    // Metodi getter e setter
    public String getCodice() {
        return codice;
    }

    public void setNome(String codice) {
        this.codice = codice;
    }

    public LocalTime getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(LocalTime orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public LocalTime getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(LocalTime orario_arrivo) {
        this.orarioArrivo = orario_arrivo;
    }




    public Itinerario getSeDisponibile(String cittaPartenzaCorrente, String cittaDestinazione){
        
        
        if (this.percorso.contains(cittaPartenzaCorrente) && this.percorso.contains(cittaDestinazione)) {

            int indicePartenza = this.percorso.indexOf(cittaPartenzaCorrente);
            int indiceArrivo = this.percorso.indexOf(cittaDestinazione);


            if (indicePartenza != -1 && indiceArrivo != -1 && indicePartenza < indiceArrivo) {
                return this;
            }
        }
        
        return null;
        
    }



    public Set<Citta> getDestinazioniDisponibili(String codiceCittaPartenza){

        Set<Citta> destinazioniDisponibili = new HashSet<>();

        int indicePartenza = percorso.indexOf(codiceCittaPartenza);

        if (indicePartenza != -1 && indicePartenza < percorso.size() - 1) {
            // Aggiungi tutte le città dopo l'indice trovato al set delle destinazioni disponibili
            for (int i = indicePartenza + 1; i < percorso.size(); i++) {
                destinazioniDisponibili.add(percorso.get(i));
            }
        }

        return destinazioniDisponibili;
    }




    public void visualizzaFermate(){
        
        System.out.println("Fermate per l'itinerario: " + codice);
        for (Citta citta : percorso) {
            
            System.out.println("Fermate in " + citta.getNome() + ":");
            
            for (Fermata fermata : citta.getElencoFermate()) {
                System.out.println(citta.getNome() + " - " +fermata.getNome());
            }
        }
        
    }



    public void loadPercorso(){
        this.percorso.add(new Citta(1, "Catania"));
        this.percorso.add(new Citta(9, "Belpasso"));
        this.percorso.add(new Citta(10, "Giarre"));
        this.percorso.add(new Citta(13, "San Giovanni la Punta"));
        
        
    }


    @Override
    public String toString() {
        return "Itinerario{" + "codice=" + codice + ", orarioPartenza=" + orarioPartenza + ", orarioArrivo=" + orarioArrivo + '}';
    }    

}
