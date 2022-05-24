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
	
	public int getNroManzana() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nroManzana;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Manzana other = (Manzana) obj;
		if (nroManzana != other.nroManzana)
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
}
