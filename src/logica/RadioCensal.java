package logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RadioCensal {
	private boolean[][] A;
	private HashMap<Integer, Manzana> manzanas;
	private ArrayList<ManzanaContigua> manzanasContiguas; // aristas

	public RadioCensal(int manzanas) {
		this.A = new boolean[manzanas][manzanas];
		this.manzanasContiguas = new ArrayList<ManzanaContigua>();
		this.manzanas = new HashMap<Integer, Manzana>();
		generarManzanas();
	}

	public void generarManzanas() {
		int nroManzana = 0;
		while (nroManzana < cantManzanas()) {
			manzanas.put(nroManzana, new Manzana(nroManzana));
			nroManzana++;
		}
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

	public boolean sonVecinos(int i, int j) {
		verificarManzana(i);
		verificarManzana(j);

		return manzanasVecinas(i).contains(j);
	}

	public int gradoManzana(int i) {
		return manzanasVecinas(i).size();
	}

	private void verificarManzana(int i) {
		if (i < 0 || i >= A.length) {
			throw new IllegalArgumentException("Ingrese un nro de manzana valida entre 0 y cantManzanas - 1");
		}
	}

	private void verificarDistintos(int i, int j) {
		if (i == j) {
			throw new IllegalArgumentException("No puede ingresar manzanas con el mismo nro de manzana");
		}
	}

	public int cantManzanas() {
		return A.length;
	}

	public boolean existeManzanaContigua(int i, int j) {
		return A[i][j];
	}

	public ArrayList<ManzanaContigua> getManzanasContiguas() {
		return manzanasContiguas;
	}

	public HashMap<Integer, Manzana> getManzanas() {
		return (HashMap<Integer, Manzana>) manzanas.clone();
	}

	public Manzana getManzana(int indice) {
		return manzanas.get(indice);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(A);
		result = prime * result + ((manzanas == null) ? 0 : manzanas.hashCode());
		result = prime * result + ((manzanasContiguas == null) ? 0 : manzanasContiguas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RadioCensal other = (RadioCensal) obj;
		if (!Arrays.deepEquals(A, other.A))
			return false;
		if (manzanas == null) {
			if (other.manzanas != null)
				return false;
		} else if (!manzanas.equals(other.manzanas))
			return false;
		if (manzanasContiguas == null) {
			if (other.manzanasContiguas != null)
				return false;
		} else if (!manzanasContiguas.equals(other.manzanasContiguas))
			return false;
		return true;
	}

	public RadioCensal clone() {
		RadioCensal ret = new RadioCensal(this.cantManzanas());
		Map<Integer, Manzana> manzanas = this.getManzanas();

		for (Manzana manzana : manzanas.values()) {
			ret.agregarManzana(manzana);
			for (Integer vecino : this.manzanasVecinas(manzana.getNroManzana()))
				ret.agregarManzanaContigua(manzana, this.getManzana(vecino));
		}

		return ret;
	}
}
