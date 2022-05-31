package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.apache.poi.ss.formula.eval.NotImplementedException;

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
	
	public void asignarManzanasACensistas() {
		/* Construir los grupos de manzanas asignables, empezar desde manzana de mayor grado
		 * Asignar a censistas
		 */
	}
	
	private ArrayList<ArrayList<Manzana>> construirGrupoDeManzanasAsignables(int nroManzana){
		throw new NotImplementedException("No implementado");
		/* Si empezamos desde las manzanas de mayor grado, seguro se van a poder armar grupos de 3
		 * Recorrer de mayor a menor grado
		 * Caso base, grupo.size <= 3 
		 * 		grupos.add(grupoActual)
		 * grupoActual.minElem == 0
		 * 		return;
		 * ArrayList<ArrayList<Manzana>> grupos
		 * ArrayList<Manzana> grupoActual
		 * agregarManzana(nroManzana)
		 * construirGrupoDeManzanasAsignables(nroManzana-1)
		 * 
		 * quitarManzana(nroManzana)
		 * construirGrupoDeManzanasAsignables(nroManzana-1)
		 */
	}
	
	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}
}
