package logica;

import java.util.ArrayList;

public class Sistema {

	private AsignadorDeManzanasGoloso asignador;
	
	public Sistema(RadioCensal radioCensal, ArrayList<Censista> censistas) {
		this.asignador = new AsignadorDeManzanasGoloso(censistas, radioCensal);
	}

	private void asignarCensistas() {
		asignador.asignarManzanasACensistas();
	}
	
	public ArrayList<Censista> obtenerCensistasAsignados(){
		asignarCensistas();
		
		return asignador.getCensistas();
	}
}
