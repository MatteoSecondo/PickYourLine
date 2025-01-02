public class Fermata {
    public String nome;

    public Fermata(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Fermata getFermata(String nomeFermata) {
    	if(this.nome.equals(nomeFermata)) {
    		return this;
    	}
    	
    	return null;
    }

    @Override
    public String toString() {
        return "Fermata{" +
                "nome='" + nome + '\'' +
                '}';
    }
}