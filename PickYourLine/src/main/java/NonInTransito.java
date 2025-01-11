public class NonInTransito extends StatoAutomezzo {
	
	public NonInTransito() {
		this.setMalfunzionamento(Malfunzionamento.NessunMalfunzionamento);
	}
	
	@Override
	void cambiaStato() {
		super.cambiaStato();
	}
	
}