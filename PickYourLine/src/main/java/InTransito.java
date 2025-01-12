public class InTransito implements StatoAutomezzo {

	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già in transito.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " si ferma.");
		a.setStato(new NonInTransito());
	}
    
}