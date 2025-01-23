import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Itinerario{

    private String codice;
    private LocalTime orarioPartenza;
    private LocalTime orarioArrivo;
    private List<Citta> percorso;

    public Itinerario(String codice, LocalTime orarioPartenza, LocalTime orarioArrivo, List<Citta> p){
        this.codice = codice;
        this.orarioPartenza = orarioPartenza;
        this.orarioArrivo = orarioArrivo;
        this.percorso = new ArrayList<Citta>();
        loadPercorso(p);
    }

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

    public List<Citta> getPercorso() {
		return percorso;
	}

	public void setPercorso(List<Citta> percorso) {
		this.percorso = percorso;
	}

	public Itinerario getSeDisponibile(Citta cittaPartenza, Citta cittaDestinazione){
    	
        if (this.percorso.contains(cittaPartenza) && this.percorso.contains(cittaDestinazione)) {

            int indicePartenza = this.percorso.indexOf(cittaPartenza);
            int indiceArrivo = this.percorso.indexOf(cittaDestinazione);

            if (indicePartenza < indiceArrivo) {
                return this;
            }
        }
        
        return null;
    }

    public Map<Integer, Citta> getDestinazioniDisponibili(Citta cittaPartenza){

    	Map<Integer, Citta> destinazioniDisponibili = new HashMap<Integer, Citta>();

        int indicePartenza = this.percorso.indexOf(cittaPartenza);

        if (indicePartenza != -1 && indicePartenza < this.percorso.size()) {
            // Aggiungi tutte le città dopo l'indice trovato alla mappa delle destinazioni disponibili
            for (int i = indicePartenza + 1; i < this.percorso.size(); i++) {
                destinazioniDisponibili.put(this.percorso.get(i).getCodice(), this.percorso.get(i));
            }
        }

        return destinazioniDisponibili;
    }

    public void visualizzaFermate(){
        
        System.out.println("\nFermate per l'itinerario: " + this.codice);
        
        for (Citta citta : this.percorso) {
            for (Fermata fermata : citta.getElencoFermate()) {
                System.out.println(citta.getNome() + " - " + fermata.getNome());
            }
        }
    }

    public void loadPercorso(List<Citta> p){
        this.percorso = p;
    }

    @Override
    public String toString() {
        return "Itinerario{" + "codice=" + codice + ", orarioPartenza=" + orarioPartenza + ", orarioArrivo=" + orarioArrivo + '}';
    }    

}
