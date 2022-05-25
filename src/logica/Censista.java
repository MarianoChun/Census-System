package logica;

import java.io.File;
import java.util.ArrayList;

public class Censista {
	private ArrayList<Manzana> manzanasAsignadas;
	private String nombre;
	private byte[] foto; //modificar tipo luego
	
	// TODO: Necesitamos cubrir la posibilidad de que no se asigne una manzana que ya asignada al censista.
	
	public Censista(String nombre) {
		this.nombre = nombre;
		this.manzanasAsignadas = new ArrayList<Manzana>();
	}
	
	public void asignarManzana(Manzana manzana) {
		if(cantManzanasAsignadas() == 3) {
			throw new IllegalArgumentException("Solo se le puede asignar al censista un máximo de 3 manzanas");
		}
		manzanasAsignadas.add(manzana);
	}
	
	public void asignarManzana(ArrayList<Manzana> manzanas) {
		if(manzanas.size() > 3) {
			throw new IllegalArgumentException("La longitud del array ingresado debe ser menor o igual que 3");
		}
		if(cantManzanasAsignadas() + manzanas.size() > 3) {
			throw new IllegalArgumentException("Solo se le puede asignar al censista un máximo de 3 manzanas");
		}
		
		manzanasAsignadas.addAll(manzanas);
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
	public int cantManzanasAsignadas() {
		return manzanasAsignadas.size();
	}
	
	public ArrayList<Manzana> getManzanasAsignadas(){
		return manzanasAsignadas;
	}
}
