public class InManutenzione implements StatoAutomezzo {

	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è ancora in manutenzione.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		a.setStato(new NonInTransito());
		
		System.out.println("L'automezzo " + a.getCodice() + " ha superato la manutenzione.");
		PickYourLine.getInstance().inserisciAvviso("Automezzo" + a.getCodice() + " disponibile", "L'automezzo" + a.getCodice() + " ha terminato la manutenzione con successo ed è di nuovo disponibile per il servizio");
	}

	@Override
	public void inManutenzione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già in manutenzione.");
	}

	@Override
	public void inDismissione(Automezzo a) {
		a.setStato(new Dismesso());
		
		System.out.println("L'automezzo" + a.getCodice() + " non ha superato la manutenzione, quindi non sarà più disponibile per il servizio");
		PickYourLine.getInstance().inserisciAvviso("Automezzo" + a.getCodice() + " in dismissione", "L'automezzo" + a.getCodice() + " non ha superato la manutenzione, quindi non sarà più disponibile per il servizio");
	}
    
}