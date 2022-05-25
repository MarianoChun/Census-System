package logica;

public class ManzanaContigua {
	private Manzana primerManzana;
	private Manzana segundaManzana;

	public ManzanaContigua(Manzana primerManzana, Manzana segundaManzana) {
		this.primerManzana = primerManzana;
		this.segundaManzana = segundaManzana;
	}

	public Manzana getPrimerManzana() {
		return this.primerManzana;
	}

	public Manzana getSegundaManzana() {
		return this.segundaManzana;
	}
}
