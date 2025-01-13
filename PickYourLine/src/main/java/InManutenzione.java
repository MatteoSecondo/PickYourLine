public class InManutenzione implements StatoAutomezzo {

	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è ancora in manutenzione.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " ha superato la manutenzione.");
		a.setStato(new NonInTransito());
	}
    
}