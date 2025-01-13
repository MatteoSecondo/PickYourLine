public class Dismesso implements StatoAutomezzo {

	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è dismesso e non effettuerà più nessuna corsa.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è dismesso e non effettuerà più nessuna corsa.");
	}
    
}