package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RadioCensal {
	private boolean[][] A;
	private ArrayList<Manzana> manzanas;
	private ArrayList<ManzanaContigua> manzanasContiguas; //aristas
	
	public RadioCensal(int manzanas) {
		this.A = new boolean[manzanas][manzanas];
		this.manzanasContiguas = new ArrayList<ManzanaContigua>();
		for(int i = 0; i < manzanas; i++) {
			this.manzanas.add(new Manzana(i));
		}
	}
	
	public void agregarManzanaContigua(Manzana primerManzana, Manzana segundaManzana) {
		int i = primerManzana.getNumeroManzana();
		int j = segundaManzana.getNumeroManzana();
	
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
	
	private void verificarManzana(int i) {
		if(i < 0 || i > cantManzanas()) {
			throw new IllegalArgumentException("Ingrese un nro de manzana valida entre 0 y cantManzanas - 1");
		}
	}

	private int cantManzanas() {
		return manzanas.size();
	}

	private boolean existeManzanaContigua(int i, int j) {
		return A[i][j];
	}
	
	public ArrayList<ManzanaContigua> getManzanasContiguas() {
		return manzanasContiguas;
	}
	
	public ArrayList<Manzana> getManzanas() {
		return manzanas;
	}
	
	public Manzana getManzana(int indice) {
		return manzanas.get(indice);
	}
}
