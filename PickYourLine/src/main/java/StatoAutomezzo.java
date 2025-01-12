public abstract class StatoAutomezzo {
	private Malfunzionamento malfunzionamento;
	
    public Malfunzionamento getMalfunzionamento() {
		return malfunzionamento;
	}

	public void setMalfunzionamento(Malfunzionamento malfunzionamento) {
		this.malfunzionamento = malfunzionamento;
	}

	@Override
	public String toString() {
		return "StatoAutomezzo [stato=" + getClass().getSimpleName() + " malfunzionamento=" + malfunzionamento + "]";
	}

	void cambiaStato() {};
}






