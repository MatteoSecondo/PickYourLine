public class NonInTransito implements StatoAutomezzo {
	
	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è partito.");
		Itinerario itinerario = a.getItinerarioAssegnato();
		Citta primaCittaPercorso = itinerario.getPercorso().getFirst();
		Fermata primaFermata = primaCittaPercorso.getElencoFermate().getFirst();
		a.setPosizioneAttuale(primaFermata);
		a.setStato(new InTransito());
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già fermo.");
	}
	
}