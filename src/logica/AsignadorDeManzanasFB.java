package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.util.SystemOutLogger;

public class AsignadorDeManzanasFB {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;
	
	private ArrayList<ArrayList<Manzana>> grupos;
	private ArrayList<ArrayList<Manzana>> gruposActual;
	private ArrayList<Manzana> grupoActual;
	
	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
		
		
		
//		construirGrupoDeManzanasAsignables(0);
	}
//Asigna los grupos de manzanas a los censistas
	public ArrayList<ArrayList<Manzana>> asignarManzanasACensistas() {
		/* Construir los grupos de manzanas asignables
		 * Asignar a censistas
		 */
		this.grupos = crearArrayListConCantFijaElementosVacios(radioCensal.cantManzanas());
		System.out.println(grupos.size());
		this.gruposActual = new ArrayList<ArrayList<Manzana>>();
		this.grupoActual = new ArrayList<Manzana>();
		construirGrupoDeManzanasAsignables(0);
		return this.grupos;
	}

	
	// El criterio para definir que una solucion es mejor que otra es si tiene menos grupos asignables.
	// La mejor solucion es la que tenga la menor cantidad de grupos asignables posibles.
//Genera todos los grupos de manzanas asignables posibles y devuelve el de menor tamaño
	@SuppressWarnings("unchecked")
	public void construirGrupoDeManzanasAsignables(int nroManzana){
//		grupoActual.stream().forEach(m -> System.out.print(m.getNroManzana()));
//		System.out.println();
		
		if((grupoActual.size() <= 3 && grupoActual.size() >= 1) && esCamino(grupoActual)) {
			System.out.println("Pasa " + grupoActual);
			gruposActual.add(grupoActual);
//			System.out.println("grupos " + grupos);
		}
		if(gruposActual.size() < grupos.size() && gruposActual.size() > 0) {
			grupos =  clonarGrupos(gruposActual);
			System.out.println("grupos actual: "+ gruposActual);
			System.out.println("grupos: "+grupos);
		}
		if(nroManzana == radioCensal.cantManzanas()-1) {
			return;
		}
		grupoActual.add(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);
		
		grupoActual.remove(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);
	}
	
	public ArrayList<ArrayList<Manzana>> getGrupos() {
		return grupos;
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
	
	private ArrayList<ArrayList<Manzana>> crearArrayListConCantFijaElementosVacios(int cantElementosVacios) {
		ArrayList<ArrayList<Manzana>> ret = new ArrayList<ArrayList<Manzana>>();
		for(int i = 0;i<cantElementosVacios;i++)
			ret.add(null);
		return ret;
	}
	
	private static ArrayList<ArrayList<Manzana>> clonarGrupos(ArrayList<ArrayList<Manzana>> grupos){
		ArrayList<ArrayList<Manzana>> clon = new ArrayList<ArrayList<Manzana>>();
		grupos.stream().forEach(a -> clon.add(clonarGrupo(a)));
		
		return clon;
	}
	
	private static ArrayList<Manzana> clonarGrupo(ArrayList<Manzana> grupo){
		ArrayList<Manzana> clon = new ArrayList<Manzana>();
		grupo.stream().forEach(m -> clon.add(m.clone()));

		return clon;
	}
	
//	private ArrayList<Integer> obtenerNrosManzanas(){
//		ArrayList<Integer> nrosManzanas = new ArrayList<Integer>();
//		for(Manzana manzana : manzanas) {
//			nrosManzanas.add(manzana.getNroManzana());
//		}
//		
//		return nrosManzanas;
//	}
}
