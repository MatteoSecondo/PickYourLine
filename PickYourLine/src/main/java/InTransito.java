public class InTransito extends StatoAutomezzo {
	
	public InTransito() {
		this.setMalfunzionamento(Malfunzionamento.NessunMalfunzionamento);
	}
	
	@Override
	void cambiaStato() {
		super.cambiaStato();
	}
	
}