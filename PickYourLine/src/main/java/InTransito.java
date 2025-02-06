public class InTransito implements StatoAutomezzo {

	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già in transito.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		a.setStato(new NonInTransito());
		System.out.println("L'automezzo " + a.getCodice() + " si ferma.");
	}

	@Override
	public void inManutenzione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è ancora in transito.");
	}

	@Override
	public void inDismissione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è ancora in transito.");
	}
    
}