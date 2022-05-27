package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RadioCensal {
	private boolean[][] A;
	private HashMap<Integer, Manzana> manzanas;
	private ArrayList<ManzanaContigua> manzanasContiguas; // aristas

	public RadioCensal(int manzanas) {
		this.A = new boolean[manzanas][manzanas];
		this.manzanasContiguas = new ArrayList<ManzanaContigua>();
		this.manzanas = new HashMap<Integer, Manzana>();
	}

	public void agregarManzana(Manzana manzana) {
		verificarManzana(manzana.getNroManzana());
		manzanas.put(manzana.getNroManzana(), manzana);
	}
	public void agregarManzanaContigua(Manzana primerManzana, Manzana segundaManzana) {
		int i = primerManzana.getNroManzana();
		int j = segundaManzana.getNroManzana();
		verificarManzana(i);
		verificarManzana(j);
		verificarDistintos(i, j);
		if (!existeManzanaContigua(i, j)) {
			A[i][j] = A[j][i] = true;
			manzanasContiguas.add(new ManzanaContigua(primerManzana, segundaManzana));
		}
	}

	public Set<Integer> manzanasVecinas(int i) {
		verificarManzana(i);

		Set<Integer> ret = new HashSet<Integer>();
		for (int j = 0; j < this.cantManzanas(); ++j)
			if (i != j) {
				if (this.existeManzanaContigua(i, j))
					ret.add(j);
			}

		return ret;
	}

	public int gradoManzana(int i) {
		return manzanasVecinas(i).size();
	}

	private void verificarManzana(int i) {
		if (i < 0 || i > cantManzanas()) {
			throw new IllegalArgumentException("Ingrese un nro de manzana valida entre 0 y cantManzanas - 1");
		}
	}

	private void verificarDistintos(int i, int j) {
		if (i == j) {
			throw new IllegalArgumentException("No puede ingresar manzanas con el mismo nro de manzana");
		}
	}

	public int cantManzanas() {
		return manzanas.size();
	}

	public boolean existeManzanaContigua(int i, int j) {
		return A[i][j];
	}

	public ArrayList<ManzanaContigua> getManzanasContiguas() {
		return manzanasContiguas;
	}

	public HashMap<Integer, Manzana> getManzanas() {
		return manzanas;
	}

	public Manzana getManzana(int indice) {
		return manzanas.get(indice);
	}
}
