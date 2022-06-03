package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AsignadorDeManzanasFB {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;

	private ArrayList<ArrayList<Manzana>> gruposAsignables;
	private ArrayList<Manzana> grupoAsignable;
	private static ArrayList<ArrayList<Manzana>> solucion;

	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
	}

//Asigna los grupos de manzanas a los censistas
	public ArrayList<ArrayList<Manzana>> asignarManzanasACensistas() {
		/*
		 * Construir los grupos de manzanas asignables Asignar a censistas
		 */
		this.gruposAsignables = new ArrayList<ArrayList<Manzana>>();
		this.grupoAsignable = new ArrayList<Manzana>();
		
		construirGrupoDeManzanasAsignables(0);
		armarSolucion(this.gruposAsignables);
		return AsignadorDeManzanasFB.solucion;
	}

	// El criterio para definir que una solucion es mejor que otra es si tiene menos
	// grupos asignables.
	// La mejor solucion es la que tenga la menor cantidad de grupos asignables
	// posibles.
//Genera todos los grupos de manzanas asignables posibles y devuelve el de menor tamaño
	@SuppressWarnings("unchecked")
	public void construirGrupoDeManzanasAsignables(int nroManzana) {

		if (nroManzana == radioCensal.cantManzanas()) {
			if ((grupoAsignable.size() >= 1 && grupoAsignable.size() <= 3) && elemsSonContiguos(grupoAsignable)) {
				gruposAsignables.add((ArrayList<Manzana>) grupoAsignable.clone());
			}	
			return;
		}

		grupoAsignable.add(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);

		grupoAsignable.remove(radioCensal.getManzana(nroManzana));
		construirGrupoDeManzanasAsignables(nroManzana + 1);
	}

	private ArrayList<ArrayList<Manzana>> armarSolucion(ArrayList<ArrayList<Manzana>> grupos) {
		
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
		
		return solucion.stream().allMatch(grupoSolucion -> sonDisjuntos(grupoSolucion, grupo));
	}
	private static boolean sonDisjuntos(ArrayList<Manzana> grupo, ArrayList<Manzana> otroGrupo) {
		ArrayList<Integer> nrosManzanaGrupo = extraerNrosManzana(grupo);
		ArrayList<Integer> nrosManzanaOtroGrupo = extraerNrosManzana(otroGrupo);
		
		return nrosManzanaGrupo.stream().allMatch(n -> !nrosManzanaOtroGrupo.contains(n)) &&
				nrosManzanaOtroGrupo.stream().allMatch(n -> !nrosManzanaGrupo.contains(n));
		
	}
	
	private static ArrayList<Integer> extraerNrosManzana(ArrayList<Manzana> manzanas){
		ArrayList<Integer> nrosManzana = new ArrayList<Integer>();
		
		for(Manzana manzana : manzanas)
			nrosManzana.add(manzana.getNroManzana());
		
		return nrosManzana;
	}
	private boolean elemsSonContiguos(ArrayList<Manzana> grupo) {
		if (grupo.size() == 0) {
			return false;
		}

		if (grupo.size() == 1) {
			return true;
		}
		if (grupo.size() == 2) {
			return radioCensal.sonVecinos(grupo.get(0).getNroManzana(), grupo.get(1).getNroManzana());
		}

		return sonContiguos(grupo);
	}

	private boolean sonContiguos(ArrayList<Manzana> grupo) {
		int[] nrosManzanas = grupo.stream().mapToInt(m -> m.getNroManzana()).toArray();
		
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

	private ArrayList<ArrayList<Manzana>> crearArrayListConCantFijaElementosVacios(int cantElementosVacios) {
		ArrayList<ArrayList<Manzana>> ret = new ArrayList<ArrayList<Manzana>>();
		for (int i = 0; i < cantElementosVacios; i++)
			ret.add(null);
		return ret;
	}

	private static ArrayList<ArrayList<Manzana>> clonarGrupos(ArrayList<ArrayList<Manzana>> grupos) {
		ArrayList<ArrayList<Manzana>> clon = new ArrayList<ArrayList<Manzana>>();
		grupos.stream().forEach(a -> clon.add(clonarGrupo(a)));

		return clon;
	}

	private static ArrayList<Manzana> clonarGrupo(ArrayList<Manzana> grupo) {
		ArrayList<Manzana> clon = new ArrayList<Manzana>();
		grupo.stream().forEach(m -> clon.add(m.clone()));

		return clon;
	}
}
