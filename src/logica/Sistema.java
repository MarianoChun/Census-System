
package logica;

import java.util.ArrayList;

public class Sistema {

	private AsignadorDeManzanasGoloso asignador;
	
	public Sistema(RadioCensal radioCensal, ArrayList<Censista> censistas) {
		this.asignador = new AsignadorDeManzanasGoloso((ArrayList<Censista>)censistas.clone(), (RadioCensal)radioCensal.clone());
	}

	private void asignarCensistas() {
		asignador.asignarManzanasACensistas();
	}
	
	public ArrayList<Censista> obtenerCensistasAsignados(){
		asignarCensistas();
		
		return asignador.getCensistas();
	}
	
	
}
