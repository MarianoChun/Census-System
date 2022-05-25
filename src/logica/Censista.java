package logica;

import java.util.ArrayList;

public class Censista {
	private ArrayList<Manzana> manzanasAsignadas;
	private String nombre;
	private String foto; // modificar tipo luego

	// TODO: Necesitamos cubrir la posibilidad de que no se asigne una manzana que
	// ya esta asignada al censista.

	public Censista(String nombre) {
		this.nombre = nombre;
		this.manzanasAsignadas = new ArrayList<Manzana>();
	}

	public void asignarManzana(Manzana manzana) {
		if (cantManzanasAsignadas() == 3) {
			throw new IllegalArgumentException("Solo se le puede asignar al censista un máximo de 3 manzanas");
		}
		if (!estaManzanaAsignada(manzana)) {
			manzanasAsignadas.add(manzana);
		}
	}

	public void asignarManzanas(ArrayList<Manzana> manzanas) {
		if (manzanas.size() > 3) {
			throw new IllegalArgumentException("La longitud del array ingresado debe ser menor o igual que 3");
		}
		if (cantManzanasAsignadas() + manzanas.size() > 3) {
			throw new IllegalArgumentException("Solo se le puede asignar al censista un máximo de 3 manzanas");
		}
		
		for (Manzana m : manzanas) {
			asignarManzana(m);
		}
	}
	
	private boolean estaManzanaAsignada(Manzana manzana) {
		for (Manzana m : manzanasAsignadas) {
			if (m.getNroManzana() == manzana.getNroManzana()) {
				return true;
			}
		}
		return false;
	}

	public int cantManzanasAsignadas() {
		return manzanasAsignadas.size();
	}

	public ArrayList<Manzana> getManzanasAsignadas() {
		return manzanasAsignadas;
	}
}
