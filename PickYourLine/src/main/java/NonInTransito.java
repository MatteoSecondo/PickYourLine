public class NonInTransito implements StatoAutomezzo {
	
	@Override
	public void inSupervisione(Automezzo a) {
		a.setStato(new InTransito());
		System.out.println("L'automezzo " + a.getCodice() + " è partito.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già fermo.");
	}

	@Override
	public void inManutenzione(Automezzo a) {
		a.setStato(new InManutenzione());
		
		System.out.println("L'automezzo " + a.getCodice() + " è ora in manutenzione.");
		PickYourLine.getInstance().inserisciAvviso("Automezzo" + a.getCodice() + " in manutenzione", "L'automezzo" + a.getCodice() + " è adesso in manutenzione e non sarà disponibile sino a nuove comunicazioni");
	}

	@Override
	public void inDismissione(Automezzo a) {
		a.setStato(new Dismesso());
		
		System.out.println("L'automezzo " + a.getCodice() + " è ora dismesso.");
		PickYourLine.getInstance().inserisciAvviso("Automezzo" + a.getCodice() + " in dismissione", "L'automezzo" + a.getCodice() + " non è più in grado di fornire un servizio adeguato, quindi non sarà più disponibile per il servizio");
	}
	
}