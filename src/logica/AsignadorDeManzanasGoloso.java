package logica;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class AsignadorDeManzanasGoloso {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private ArrayList<Manzana> manzanas;
	private HashMap<Integer, Manzana> manzanasMarcadas;

	public AsignadorDeManzanasGoloso(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanas = obtenerArrayManzanas(radioCensal);
		// Ordenado de menor a mayor de acuerdo al grado de la manzana (cant vecinos)
		Collections.sort(manzanas,
				(p, q) -> radioCensal.gradoManzana(q.getNroManzana()) - radioCensal.gradoManzana(p.getNroManzana()));
		this.manzanasMarcadas = new HashMap<Integer, Manzana>();
		this.asignarManzanasACensistas();

	}

	private ArrayList<Manzana> obtenerArrayManzanas(RadioCensal radioCensal) {
		Collection<Manzana> setManzanas = radioCensal.getManzanas().values();
		return new ArrayList<Manzana>(setManzanas);
	}

	public void asignarManzanasACensistas() {
		ArrayList<ArrayList<Manzana>> grupoManzanasAsignables = construirGrupoDeManzanasAsignables();
		int indice = 0;
		for (ArrayList<Manzana> grupoManzana : grupoManzanasAsignables) {
			if (censistas.size() == indice) {
				break;
			}

			censistas.get(indice).asignarManzanas(grupoManzana);
			indice++;
		}

	}

	private ArrayList<ArrayList<Manzana>> construirGrupoDeManzanasAsignables() {
		ArrayList<ArrayList<Manzana>> grupoDeManzanasAsignables = new ArrayList<ArrayList<Manzana>>();
		for (Manzana manzana : manzanas) {
			if (!estaManzanaMarcada(manzana.getNroManzana())) {
				ArrayList<Manzana> manzanasVecinasNoAsignadas = manzanasVecinasNoMarcadas(manzana);
				marcarManzanas(manzanasVecinasNoAsignadas);
				grupoDeManzanasAsignables.add(manzanasVecinasNoAsignadas);
			}
		}

		return grupoDeManzanasAsignables;
	}

	// Debe devolver como máximo un array list de 3 manzanas. 1 <= ArrayList.size
	// <=3;
	// La manzana actual debe incluirse en el arrayList
	private ArrayList<Manzana> manzanasVecinasNoMarcadas(Manzana manzana) {
		ArrayList<Manzana> manzanasVecinasNoMarcadas = new ArrayList<Manzana>();
		manzanasVecinasNoMarcadas.add(manzana);
		Set<Integer> manzanasVecinas = radioCensal.manzanasVecinas(manzana.getNroManzana());

		for (Integer nroManzanaVecina : manzanasVecinas) {
			if (!estaManzanaMarcada(nroManzanaVecina) && manzanasVecinasNoMarcadas.size() <= 2) {
				Manzana vecinaNoMarcada = radioCensal.getManzana(nroManzanaVecina);
				manzanasVecinasNoMarcadas.add(vecinaNoMarcada);
			}
		}

		return manzanasVecinasNoMarcadas;
	}

	private boolean estaManzanaMarcada(int nroManzana) {
		return manzanasMarcadas.containsKey(nroManzana);
	}

	private void marcarManzanas(ArrayList<Manzana> manzanasVecinasNoAsignadas) {
		for (Manzana manzana : manzanasVecinasNoAsignadas) {
			manzanasMarcadas.put(manzana.getNroManzana(), manzana);
		}
	}

	public ArrayList<Censista> getCensistas() {
		return new ArrayList<Censista>(censistas);
	}
}
