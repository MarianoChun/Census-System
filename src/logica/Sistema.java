
package logica;

import java.util.ArrayList;

public class Sistema {

	private AsignadorDeManzanasGoloso asignador;
	RadioCensal radioCensal;
	ArrayList<Censista> censistas;

	public Sistema(RadioCensal radioCensal, ArrayList<Censista> censistas) {
//		this.asignador = new AsignadorDeManzanasGoloso((ArrayList<Censista>)censistas.clone(), (RadioCensal)radioCensal.clone());
		this.radioCensal = (RadioCensal) radioCensal.clone();
		this.censistas = (ArrayList<Censista>) censistas.clone();
	}

	private void asignarCensistas() {
		asignador.asignarManzanasACensistas();
	}

	public ArrayList<Censista> obtenerCensistasAsignados() {
//		asignarCensistas();

//		return asignador.getCensistas();
		return new AsignadorDeManzanasGoloso(censistas, radioCensal).getCensistas();
	}

}
