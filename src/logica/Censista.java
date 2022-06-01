package logica;

import java.io.File;
import java.util.ArrayList;

public class Censista implements Cloneable{
	private ArrayList<Manzana> manzanasAsignadas;
	private String nombre;
	private byte[] foto; 
 
	public Censista(String nombre) {
		this.nombre = nombre;
		this.manzanasAsignadas = new ArrayList<Manzana>();
	}

	public void asignarManzana(Manzana manzana) {
		verificarCantManzanasAsignadas();
		if (!estaManzanaAsignada(manzana)) {
			manzanasAsignadas.add(manzana);
		}
	}

	private void verificarCantManzanasAsignadas() {
		if (cantManzanasAsignadas() == 3) {
			throw new IllegalArgumentException("Solo se le puede asignar al censista un máximo de 3 manzanas");
		}
	}

	public void asignarManzanas(ArrayList<Manzana> manzanas) {
		verificarSizeManzanasAAsignar(manzanas);
		verificarExcesoDeManzanasAAsignar(manzanas);
		
		for (Manzana m : manzanas) {
			asignarManzana(m);
		}
	}

	private void verificarSizeManzanasAAsignar(ArrayList<Manzana> manzanas) {
		if (manzanas.size() > 3) {
			throw new IllegalArgumentException("La longitud del array ingresado debe ser menor o igual que 3");
		}
	}

	private void verificarExcesoDeManzanasAAsignar(ArrayList<Manzana> manzanas) {
		if (cantManzanasAsignadas() + manzanas.size() > 3) {
			throw new IllegalArgumentException("Asignar dicha cantidad de manzanas excede la capacidad de manzanas del censista");
		}
	}

	public Censista clone() {
		Censista clon = new Censista(this.nombre);
		clon.setFoto(this.foto);
		clon.asignarManzanas(this.manzanasAsignadas);

		return clon;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	public byte[] getFoto() {
		return foto;
	}
	
	public String getNombre() {
		return nombre;
	}

	private boolean estaManzanaAsignada(Manzana manzana) {
		return manzanasAsignadas.stream().anyMatch(m -> m.getNroManzana() == manzana.getNroManzana());
	}

	public int cantManzanasAsignadas() {
		return manzanasAsignadas.size();
	}

	public ArrayList<Manzana> getManzanasAsignadas() {
		return manzanasAsignadas;
	}
}
