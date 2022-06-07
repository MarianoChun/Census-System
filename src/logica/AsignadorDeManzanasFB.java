package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AsignadorDeManzanasFB {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;

	private ArrayList<ArrayList<Manzana>> gruposDeManzanasAsignables;
	private ArrayList<Manzana> manzanasAsignables;
	private static ArrayList<ArrayList<Manzana>> solucion;

	private ArrayList<ArrayList<Manzana>> recorridos;

	public AsignadorDeManzanasFB(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
		this.asignarManzanasACensistas();
	}

	private void asignarManzanasACensistas() {
		this.gruposDeManzanasAsignables = new ArrayList<ArrayList<Manzana>>();
		this.manzanasAsignables = new ArrayList<Manzana>();

		construirManzanasAsignables(0);
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
	// Genera todos los grupos de manzanas asignables posibles y devuelve el de
	// menor tamaño
	@SuppressWarnings("unchecked")
	private void construirManzanasAsignables(int nroManzana) {

		//caso base
		if (nroManzana == radioCensal.cantManzanas()) {
			if (hayManzanasAsignables() && elementosSonContiguos(manzanasAsignables)) {
				gruposDeManzanasAsignables.add((ArrayList<Manzana>) manzanasAsignables.clone());
			}
			return;
		}
		
		//caso recursivo
		asignarManzana(nroManzana);
		construirManzanasAsignables(nroManzana + 1);

		eliminarManzana(nroManzana);
		construirManzanasAsignables(nroManzana + 1);
	}

	private void eliminarManzana(int nroManzana) {
		manzanasAsignables.remove(radioCensal.getManzana(nroManzana));
	}

	private void asignarManzana(int nroManzana) {
		manzanasAsignables.add(radioCensal.getManzana(nroManzana));
	}

	private boolean hayManzanasAsignables() {
		return manzanasAsignables.size() >= 1 && manzanasAsignables.size() <= 3;
	}

	private ArrayList<ArrayList<Manzana>> construirSolucion(ArrayList<ArrayList<Manzana>> grupos) {

		Collections.sort(grupos, (p, q) -> q.size() - p.size());
		solucion = new ArrayList<ArrayList<Manzana>>();

		int indice = 0;
		int tamanoSolucion = 0;
		while (tamanoSolucion != radioCensal.cantManzanas()) {
			ArrayList<Manzana> grupoAsignable = grupos.get(indice);
			if (esGrupoValido(grupoAsignable)) {
				solucion.add(grupoAsignable);
			}

			indice++;
			tamanoSolucion = obtenerTamanoSolucion();
		}
		return solucion;
	}

	private int obtenerTamanoSolucion() {
		int tamano = 0;
		for (ArrayList<Manzana> grupoSolucion : solucion) {
			tamano += grupoSolucion.size();
		}
		return tamano;
	}

	private static boolean esGrupoValido(ArrayList<Manzana> grupo) {
		if (solucion.size() == 0)
			return true;

		return solucion.stream().allMatch(grupoSolucion -> gruposSonDisjuntos(grupoSolucion, grupo));
	}

	private static boolean gruposSonDisjuntos(ArrayList<Manzana> grupo, ArrayList<Manzana> otroGrupo) {
		ArrayList<Integer> nrosManzanaGrupo = extraerNumerosDeManzanas(grupo);
		ArrayList<Integer> nrosManzanaOtroGrupo = extraerNumerosDeManzanas(otroGrupo);

		return nrosManzanaGrupo.stream().allMatch(n -> !nrosManzanaOtroGrupo.contains(n))
				&& nrosManzanaOtroGrupo.stream().allMatch(n -> !nrosManzanaGrupo.contains(n));

	}

	private static ArrayList<Integer> extraerNumerosDeManzanas(ArrayList<Manzana> manzanas) {
		ArrayList<Integer> nrosManzana = new ArrayList<Integer>();

		for (Manzana manzana : manzanas)
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
		if (esManzanaContigua(nrosManzanas[0], nrosManzanas[1])
				&& esManzanaContigua(nrosManzanas[0], nrosManzanas[2])) {
			return true;
		}

		if (esManzanaContigua(nrosManzanas[1], nrosManzanas[0])
				&& esManzanaContigua(nrosManzanas[1], nrosManzanas[2])) {
			return true;
		}

		if (esManzanaContigua(nrosManzanas[2], nrosManzanas[0])
				&& esManzanaContigua(nrosManzanas[2], nrosManzanas[1])) {
			return true;
		}

		return false;

	}
	
	private boolean esManzanaContigua(int i, int j) {
		return radioCensal.sonVecinos(i, j);
	}

	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}
	
	public ArrayList<Censista> getCensistas() {
		return new ArrayList<Censista>(censistas);
	}

	public ArrayList<ArrayList<Manzana>> getRecorridos() {
		return recorridos;
	}

}
