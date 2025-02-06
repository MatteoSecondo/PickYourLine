public class InTransito implements StatoAutomezzo {

	@Override
	public void inSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " è già in transito.");
	}

	@Override
	public void nonInSupervisione(Automezzo a) {
		System.out.println("L'automezzo " + a.getCodice() + " si ferma.");

		Itinerario itinerario = a.getItinerarioAssegnato();
		Citta ultimaCittaPercorso = itinerario.getPercorso().getLast();
		Fermata ultimaFermata = ultimaCittaPercorso.getElencoFermate().getLast();
		a.setPosizioneAttuale(ultimaFermata);
		a.svuotaAutomezzo();

		a.setStato(new NonInTransito());
	}
    
}