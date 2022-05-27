package logica;

public class Manzana {
	private int nroManzana;
	Coordenada coordenadas; // son las coordenadas en caso de usar geolocalizacion

	public Manzana(Coordenada c, int nroManzana) {
		verificarNroManzana(nroManzana);
		this.coordenadas = c;
		this.nroManzana = nroManzana;
	}

	public Manzana(int nroManzana) {
		verificarNroManzana(nroManzana);
		this.coordenadas = new Coordenada(0.0, 0.0);
		this.nroManzana = nroManzana;
	}

	public int getNroManzana() {
		return this.nroManzana;
	}

	private void verificarNroManzana(int i) {
		if (i < 0) {
			throw new IllegalArgumentException("El nro de manzana no puede ser negativo");
		}
	}

	@Override
	public String toString() {
		return "Manzana [nroManzana=" + nroManzana + ", coordenadas=" + coordenadas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordenadas == null) ? 0 : coordenadas.hashCode());
		result = prime * result + nroManzana;
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
		if (coordenadas == null) {
			if (other.coordenadas != null)
				return false;
		} else if (!coordenadas.equals(other.coordenadas))
			return false;
		if (nroManzana != other.nroManzana)
			return false;
		return true;
	}

	

}
