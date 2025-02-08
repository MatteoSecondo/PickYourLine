import java.time.LocalDateTime;

public class Segnalazione extends Messaggio{
	
    private Cliente cliente;

    public Segnalazione(String oggetto, String contenuto) {
    	super(generaCodice(), oggetto, contenuto, LocalDateTime.now());
        this.cliente = (Cliente) PickYourLine.getInstance().getUtenteCorrente();
    }

    public Segnalazione(String codice, String oggetto, String contenuto, LocalDateTime timestamp , Cliente cliente) {
        super(codice, oggetto, contenuto, timestamp);
        this.cliente = cliente;
    }

    public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
    public void visualizzaDettaglio() {
    	String s = super.toString();
    	
        System.out.println(s.substring(0, s.length() - 1) +
                ", \ncontenuto='" + this.getContenuto() + '\'' +
                ", \ncliente='" + cliente + '\'' +
                ']'
        );
    }


}
