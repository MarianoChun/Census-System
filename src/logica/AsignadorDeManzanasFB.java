package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class AsignadorDeManzanasFB {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;
	
	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
	}
	
	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}
}
