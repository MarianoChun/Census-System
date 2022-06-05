package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.SwingWorker;

public class AsignadorDeManzanasFB{
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;

	private ArrayList<ArrayList<Manzana>> gruposDeManzanasAsignables;
	private ArrayList<Manzana> manzanasAsignables;
	private static ArrayList<ArrayList<Manzana>> solucion;
	
	private ArrayList<ArrayList<Manzana>> recorridosActual;
	private ArrayList<ArrayList<Manzana>> recorridos;

	
	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
		asignarManzanasACensistas();
	}

//Asigna los grupos de manzanas a los censistas
	private void asignarManzanasACensistas() {
		/*
		 * Construir los grupos de manzanas asignables Asignar a censistas
		 */
		this.gruposDeManzanasAsignables = new ArrayList<ArrayList<Manzana>>();
		this.manzanasAsignables = new ArrayList<Manzana>();
		
		
		
		construirManzanasAsignables(0);
		
//		Intento de otro algoritmo de FB
		Collections.sort(gruposDeManzanasAsignables, (p, q) -> q.size() - p.size());
		this.recorridosActual= new ArrayList<ArrayList<Manzana>>();
		this.recorridos = construirSolucion(this.gruposDeManzanasAsignables);
		construirGrupoDeRecorridos(0);
//		
		
		construirSolucion(this.gruposDeManzanasAsignables);
		
		int indice = 0;
		for (ArrayList<Manzana> grupoManzana : solucion) {
			if (censistas.size() == indice) {
				break;
			}

			censistas.get(indice).asignarManzanas(grupoManzana);
			indice++;
		}
	}

	// El criterio para definir que una solucion es mejor que otra es si tiene menos
	// grupos asignables.
	// La mejor solucion es la que tenga la menor cantidad de grupos asignables
	// posibles.
//Genera todos los grupos de manzanas asignables posibles y devuelve el de menor tamaño
	@SuppressWarnings("unchecked")
	public void construirManzanasAsignables(int nroManzana) {

		if (nroManzana == radioCensal.cantManzanas()) {
			if ((manzanasAsignables.size() >= 1 && manzanasAsignables.size() <= 3) && elementosSonContiguos(manzanasAsignables)) {
				gruposDeManzanasAsignables.add((ArrayList<Manzana>) manzanasAsignables.clone());
			}	
			return;
		}

		manzanasAsignables.add(radioCensal.getManzana(nroManzana));
		construirManzanasAsignables(nroManzana + 1);

		manzanasAsignables.remove(radioCensal.getManzana(nroManzana));
		construirManzanasAsignables(nroManzana + 1);
	}

/*	Genera todos los grupos de recorridos posibles y solo almacena el que es mejor a la solución trivial
 * y tiene tamaño almenos 1 y hasta cantManzanas / 3
 */
	public void construirGrupoDeRecorridos(int indiceRecorridos) {
		
		if(indiceRecorridos == gruposDeManzanasAsignables.size()-1)
			return;
		if(recorridosActual.size() < recorridos.size() && recorridosActual.size() >= 1 && recorridosActual.size() <= (int) radioCensal.cantManzanas()/3) {
			recorridos = (ArrayList<ArrayList<Manzana>>) recorridosActual.clone();
		}
		
		recorridosActual.add(gruposDeManzanasAsignables.get(indiceRecorridos));
		construirGrupoDeRecorridos(indiceRecorridos+1);
		
		recorridosActual.remove(gruposDeManzanasAsignables.get(indiceRecorridos));
		construirGrupoDeRecorridos(indiceRecorridos+1);
	}
	
	private ArrayList<ArrayList<Manzana>> construirSolucion(ArrayList<ArrayList<Manzana>> grupos) {
		
		Collections.sort(grupos, (p, q) -> q.size() - p.size());
		solucion = new ArrayList<ArrayList<Manzana>>();
		
		int indice = 0;
		int tamanoSolucion = 0;
		while(tamanoSolucion != radioCensal.cantManzanas()) {
			ArrayList<Manzana> grupoAsignable = grupos.get(indice);		
			if(esGrupoValido(grupoAsignable)) {
				solucion.add(grupoAsignable);
			}
				
			indice++;
			tamanoSolucion = obtenerTamanoSolucion();
		}
		return solucion;
	}

	private int obtenerTamanoSolucion() {
		int tamano = 0;
		for(ArrayList<Manzana> grupoSolucion : solucion) {
			tamano += grupoSolucion.size();
		}
		return tamano;
	}

	
	private static boolean esGrupoValido(ArrayList<Manzana> grupo) {
		if(solucion.size() == 0)
			return true;
		
		return solucion.stream().allMatch(grupoSolucion -> gruposSonDisjuntos(grupoSolucion, grupo));
	}
	private static boolean gruposSonDisjuntos(ArrayList<Manzana> grupo, ArrayList<Manzana> otroGrupo) {
		ArrayList<Integer> nrosManzanaGrupo = extraerNumerosDeManzanas(grupo);
		ArrayList<Integer> nrosManzanaOtroGrupo = extraerNumerosDeManzanas(otroGrupo);
		
		return nrosManzanaGrupo.stream().allMatch(n -> !nrosManzanaOtroGrupo.contains(n)) &&
				nrosManzanaOtroGrupo.stream().allMatch(n -> !nrosManzanaGrupo.contains(n));
		
	}
	
	private static ArrayList<Integer> extraerNumerosDeManzanas(ArrayList<Manzana> manzanas){
		ArrayList<Integer> nrosManzana = new ArrayList<Integer>();
		
		for(Manzana manzana : manzanas)
			nrosManzana.add(manzana.getNroManzana());
		
		return nrosManzana;
	}
	private boolean elementosSonContiguos(ArrayList<Manzana> grupo) {
		if (grupo.size() == 0) {
			return false;
		}

		if (grupo.size() == 1) {
			return true;
		}
		if (grupo.size() == 2) {
			return radioCensal.sonVecinos(grupo.get(0).getNroManzana(), grupo.get(1).getNroManzana());
		}

		return tresManzanasSonContiguas(grupo);
	}

	private boolean tresManzanasSonContiguas(ArrayList<Manzana> grupoDeTresManzanas) {
		int[] nrosManzanas = grupoDeTresManzanas.stream().mapToInt(m -> m.getNroManzana()).toArray();
		
		// Si algun vertice es vecino de los otros dos, son contiguos
		if(radioCensal.sonVecinos(nrosManzanas[0], nrosManzanas[1]) && radioCensal.sonVecinos(nrosManzanas[0], nrosManzanas[2])) {
			return true;
		}
		
		if(radioCensal.sonVecinos(nrosManzanas[1], nrosManzanas[0]) && radioCensal.sonVecinos(nrosManzanas[1], nrosManzanas[2])) {
			return true;
		}
		
		if(radioCensal.sonVecinos(nrosManzanas[2], nrosManzanas[0]) && radioCensal.sonVecinos(nrosManzanas[2], nrosManzanas[1])) {
			return true;
		}
		
		return false;
		
	}
	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}

//	private ArrayList<ArrayList<Manzana>> crearArrayListConCantFijaElementosVacios(int cantElementosVacios) {
//		ArrayList<ArrayList<Manzana>> ret = new ArrayList<ArrayList<Manzana>>();
//		for (int i = 0; i < cantElementosVacios; i++)
//			ret.add(null);
//		return ret;
//	}
//
//	private static ArrayList<ArrayList<Manzana>> clonarGrupos(ArrayList<ArrayList<Manzana>> grupos) {
//		ArrayList<ArrayList<Manzana>> clon = new ArrayList<ArrayList<Manzana>>();
//		grupos.stream().forEach(a -> clon.add(clonarGrupo(a)));
//
//		return clon;
//	}

	private static ArrayList<Manzana> clonarGrupo(ArrayList<Manzana> grupo) {
		ArrayList<Manzana> clon = new ArrayList<Manzana>();
		grupo.stream().forEach(m -> clon.add(m.clone()));

		return clon;
	}
	
	public ArrayList<Censista> getCensistas() {
		return new ArrayList<Censista>(censistas);
	}
	
	public ArrayList<ArrayList<Manzana>> getRecorridos(){
		return recorridos;
	}
}
