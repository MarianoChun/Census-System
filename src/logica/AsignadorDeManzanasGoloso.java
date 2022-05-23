package logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AsignadorDeManzanasGoloso {
	private RadioCensal radioCensal;
	private ArrayList<Censista> censistas;
	private HashMap<Integer, Manzana> manzanasMarcadas;
	
	public AsignadorDeManzanasGoloso(ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.radioCensal = radioCensal;
		this.manzanasMarcadas = new HashMap<Integer, Manzana>();
	}
	
	
	public void asignarManzanasACensistas() {
		ArrayList<ArrayList<Manzana>> grupoManzanasAsignables = construirGrupoDeManzanasAsignables();
		int indice = 0;
		for(ArrayList<Manzana> grupoManzana : grupoManzanasAsignables) {
			if(censistas.size() == indice) {
				break;
			}
			censistas.get(indice).asignarManzana(grupoManzana);
			indice++;
		}
		
	}
	
	private ArrayList<ArrayList<Manzana>> construirGrupoDeManzanasAsignables() {
		ArrayList<ArrayList<Manzana>> grupoDeManzanasAsignables = new ArrayList<ArrayList<Manzana>>();
		for(Manzana manzana : radioCensal.getManzanas()) {
			if(!estaManzanaMarcada(manzana.getNumeroManzana())) {
				ArrayList<Manzana> manzanasVecinasNoAsignadas = manzanasVecinasNoMarcadas(manzana.getNumeroManzana());
				marcarManzanas(manzanasVecinasNoAsignadas);	
				grupoDeManzanasAsignables.add(manzanasVecinasNoAsignadas);
			}
		}
		
		return grupoDeManzanasAsignables;
	}
	
	private void marcarManzanas(ArrayList<Manzana> manzanasVecinasNoAsignadas) {
		for(Manzana manzana : manzanasVecinasNoAsignadas) {
			manzanasMarcadas.put(manzana.getNumeroManzana(), manzana);
		}
		
	}

	// Debe devolver como máximo un array list de 3 manzanas. 0 < ArrayList.size <= 3;
	// La manzana actual debe incluirse en el arrayList
	private ArrayList<Manzana> manzanasVecinasNoMarcadas(int nroManzana) {
		
		ArrayList<Manzana> manzanasVecinasNoMarcadas = new ArrayList<Manzana>();
		manzanasVecinasNoMarcadas.add(new Manzana(nroManzana));
		Set<Integer> manzanasVecinas = radioCensal.manzanasVecinas(nroManzana);
		
		for(Integer nroManzanaVecina : manzanasVecinas) {
			if(!estaManzanaMarcada(nroManzanaVecina)) {
				Manzana vecinaNoMarcada = radioCensal.getManzana(nroManzanaVecina);
				manzanasVecinasNoMarcadas.add(vecinaNoMarcada);
			}
		}
		
		return manzanasVecinasNoMarcadas;
	}

	private boolean estaManzanaMarcada(int nroManzana) {
		return manzanasMarcadas.containsKey(nroManzana);
	}
}
