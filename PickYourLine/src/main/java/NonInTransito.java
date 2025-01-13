public class NonInTransito implements StatoAutomezzo {
	
	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è partito.");
		a.setStato(new InTransito());
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già fermo.");
	}
	
}