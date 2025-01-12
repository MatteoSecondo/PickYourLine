public class InManutenzione extends StatoAutomezzo {
	
	public InManutenzione() {
		this.setMalfunzionamento(Malfunzionamento.MalfunzionamentoRisolvibile);
	}
	
	@Override
	void action(Automezzo a) {
		
	}
	
}