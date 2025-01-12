public class Dismesso extends StatoAutomezzo {

	public Dismesso() {
		this.setMalfunzionamento(Malfunzionamento.MalfunzionamentoNonRisolvibile);
	}
	
	@Override
	void action(Automezzo a) {
		
	}
    
}