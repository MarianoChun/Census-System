package logica;

public class Manzana {
	private int nroManzana;
	private double x; // son las coordenadas en caso de usar geolocalizacion
	private double y;
	
	public Manzana(double x, double y, int nroManzana) {
		verificarNroManzana(nroManzana);
		this.x = x;
		this.y = y;
		this.nroManzana = nroManzana;
	}
	
	public Manzana(int nroManzana) {
		verificarNroManzana(nroManzana);
		this.x = 0;
		this.y = 0;
		this.nroManzana = nroManzana;
	}
	
	public int getNumeroManzana() {
		return this.nroManzana;
	}
	
	private void verificarNroManzana(int i) {
		if(i < 0) {
			throw new IllegalArgumentException("El nro de manzana no puede ser negativo");
		}
	}

	@Override
	public String toString() {
		return "Manzana [nroManzana=" + nroManzana + ", x=" + x + ", y=" + y + "]";
	}
	
	
}
