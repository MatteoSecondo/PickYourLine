public class Fermata {
	
    private String nome;
    private Citta cittaDiAppartenenza;

    public Fermata(String nome, Citta c) {
        this.nome = nome;
        this.cittaDiAppartenenza = c;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Citta getCittaDiAppartenenza() {
		return cittaDiAppartenenza;
	}

	public void setCittaDiAppartenenza(Citta cittaDiAppartenenza) {
		this.cittaDiAppartenenza = cittaDiAppartenenza;
	}

	public Fermata getFermata(String nomeFermata) {
    	if(this.nome.equals(nomeFermata)) {
    		return this;
    	}
    	
    	return null;
    }

	@Override
	public String toString() {
		return "Fermata [nome=" + nome + ", cittaDiAppartenenza=" + cittaDiAppartenenza + "]";
	}
}