package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.apache.poi.ss.formula.eval.NotImplementedException;

public class AsignadorDeManzanasFB {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;
	
	private ArrayList<ArrayList<Manzana>> grupos;
	private ArrayList<Manzana> grupoActual;
	
	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
		
		
		this.grupos = new ArrayList<ArrayList<Manzana>>();
		this.grupoActual = new ArrayList<Manzana>();
//		construirGrupoDeManzanasAsignables(0);
	}
	
	public void asignarManzanasACensistas() {
		/* Construir los grupos de manzanas asignables, empezar desde manzana de mayor grado
		 * Asignar a censistas
		 */
	}
	
	
//	private ArrayList<Integer> obtenerNrosManzanas(){
//		ArrayList<Integer> nrosManzanas = new ArrayList<Integer>();
//		for(Manzana manzana : manzanas) {
//			nrosManzanas.add(manzana.getNroManzana());
//		}
//		
//		return nrosManzanas;
//	}
	
	public ArrayList<ArrayList<Manzana>> getGrupos() {
		return grupos;
	}
	// El criterio para definir que una solucion es mejor que otra es si tiene menos grupos asignables.
	// La mejor solucion es la que tenga la menor cantidad de grupos asignables posibles.
	public void construirGrupoDeManzanasAsignables(int nroManzana){
//		grupoActual.stream().forEach(m -> System.out.print(m.getNroManzana()));
//		System.out.println();
		
		if(nroManzana == radioCensal.cantManzanas()) {
			return;
		}
		if(grupoActual.size() <= 3 && esCamino(grupoActual)) {
//			System.out.println("Pasa " + grupoActual);
			grupos.add(grupoActual);
		}
		
		grupoActual.add(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);
		
		grupoActual.remove(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);
		
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
	
	private boolean esCamino(ArrayList<Manzana> grupo) {
		if(grupo.size() == 0) {
			return false;
		}
		
		if(grupo.size() == 1) {
			return true;
		}
		if(grupo.size() == 2) {
			return sonVecinas(grupo.get(0), grupo.get(1));
		}
		
		return sonVecinas(grupo.get(0), grupo.get(1)) && sonVecinas(grupo.get(1), grupo.get(2));
	}


	private boolean sonVecinas(Manzana manzana, Manzana otraManzana) {
		return radioCensal.manzanasVecinas(manzana.getNroManzana()).contains(otraManzana.getNroManzana());
	}
	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}
}
