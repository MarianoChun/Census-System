package logica;

import java.util.ArrayList;

public class Sistema {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;

	public Sistema(RadioCensal radioCensal, ArrayList<Censista> censistas) {
		this.radioCensal = radioCensal;
		this.censistas = censistas;
	}

}
