public class InManutenzione extends StatoAutomezzo {
	
	public InManutenzione() {
		this.setMalfunzionamento(Malfunzionamento.MalfunzionamentoRisolvibile);
	}
	
	@Override
	void cambiaStato() {
		super.cambiaStato();
	}
	
}