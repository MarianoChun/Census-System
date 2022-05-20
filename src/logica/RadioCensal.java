package logica;

import java.util.ArrayList;

public class RadioCensal {
	private boolean[][] A;
	private ArrayList<ManzanaContigua> manzanasContiguas; //aristas
	
	public RadioCensal(int manzanas) {
		this.A = new boolean[manzanas][manzanas];
		this.manzanasContiguas = new ArrayList<ManzanaContigua>();
	}
	
	public void agregarManzanaContigua(Manzana primerManzana, Manzana segundaManzana) {
		int i = primerManzana.getNumeroManzana();
		int j = segundaManzana.getNumeroManzana();
	
		if (!existeManzanaContigua(i, j)) {
			A[i][j] = A[j][i] = true;
			manzanasContiguas.add(new ManzanaContigua(primerManzana, segundaManzana));
		}
	}

	private boolean existeManzanaContigua(int i, int j) {
		return A[i][j];
	}
}
