package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logica.AsignadorDeManzanasFB;
import logica.Manzana;
import logica.RadioCensal;

public class asignadorFB {

	@Test
	public void test() {
		RadioCensal radioCensal = new RadioCensal(9);

		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(2));
		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(5));

		radioCensal.agregarManzanaContigua(new Manzana(3), new Manzana(4));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(5));
		radioCensal.agregarManzanaContigua(new Manzana(3), new Manzana(6));
		radioCensal.agregarManzanaContigua(new Manzana(5), new Manzana(8));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(7));

		radioCensal.agregarManzanaContigua(new Manzana(6), new Manzana(7));
		radioCensal.agregarManzanaContigua(new Manzana(7), new Manzana(8));

		AsignadorDeManzanasFB asignador = new AsignadorDeManzanasFB(null, radioCensal);

//		ArrayList<ArrayList<Manzana>> gruposAsignables = asignador.asignarManzanasACensistas();
//		System.out.print("La solucion por FB es: ");
//		for (ArrayList<Manzana> manzanas : gruposAsignables) {
//			System.out.print("[");
//			for (Manzana manzana : manzanas) {
//				System.out.print(manzana.getNroManzana());
//			}
//			System.out.print("], ");
//		}
//		System.out.print("\n");
	}

}
