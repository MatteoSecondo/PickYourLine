import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AutomezzoTest {
	
	@Mock
	Automezzo automezzoMock;
	
	@Mock
	Fermata fermataMock;
	
	@Mock
	Itinerario itinerarioMock;
	
	@Mock
	List<Citta> percorsoMock;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		PickYourLine.getInstance().loadCitta();
		PickYourLine.getInstance().loadItinerari();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAggiornaElencoBiglietti() {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		Itinerario i = pickYourLine.getElencoItinerari().get("Catania-Randazzo");
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("1", new Biglietto("1", pickYourLine.getElencoCitta().get(1), pickYourLine.getElencoCitta().get(3)));
		elencoBiglietti.put("2", new Biglietto("2", pickYourLine.getElencoCitta().get(1), pickYourLine.getElencoCitta().get(49)));
		
		Automezzo a = new Automezzo("PROVA", 20, i, elencoBiglietti);
		
		a.setPosizioneAttuale(pickYourLine.getElencoCitta().get(28).getElencoFermate().getFirst());
		a.aggiornaElencoBiglietti();
		assertTrue(a.getPostiDisponibili() == a.getPosti());
	}
	
	@Test
	void testConsentiTimbratura() {
		PickYourLine pickYourLine = PickYourLine.getInstance();
		Itinerario i = pickYourLine.getElencoItinerari().get("Catania-Randazzo");
		Map<String, Biglietto> elencoBiglietti = new HashMap<String, Biglietto>();
		elencoBiglietti.put("1", new Biglietto("1", pickYourLine.getElencoCitta().get(1), pickYourLine.getElencoCitta().get(3)));
		elencoBiglietti.put("2", new Biglietto("2", pickYourLine.getElencoCitta().get(1), pickYourLine.getElencoCitta().get(49)));
		
		Automezzo a = new Automezzo("PROVA", 20, i, elencoBiglietti);
		a.setPosizioneAttuale(pickYourLine.getElencoCitta().get(49).getElencoFermate().getLast());
		
		assertTrue(a.consentiTimbratura());	
	}

}
