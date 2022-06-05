
package logica;

import java.util.ArrayList;

public class Sistema {
	RadioCensal radioCensal;
	ArrayList<Censista> censistas;

	public Sistema(RadioCensal radioCensal, ArrayList<Censista> censistas) {
//		this.asignador = new AsignadorDeManzanasGoloso((ArrayList<Censista>)censistas.clone(), (RadioCensal)radioCensal.clone());
		this.radioCensal = (RadioCensal) radioCensal.clone();
		this.censistas = (ArrayList<Censista>) censistas.clone();
	}

	public ArrayList<Censista> obtenerCensistasAsignadosGoloso() {
		return new AsignadorDeManzanasGoloso(censistas, radioCensal).getCensistas();
	}

	public ArrayList<Censista> obtenerCensistasAsignadosFB() {
		return new AsignadorDeManzanasFB(censistas, radioCensal).getCensistas();
	}

}
