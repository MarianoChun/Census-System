package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import logica.AsignadorDeManzanasGoloso;
import logica.Censista;
import logica.Manzana;
import logica.RadioCensal;

public class AsignadorDeManzanasGolosoTest {
	ArrayList<Censista> censistas;
	RadioCensal radioCensal;
	@Before
	public void setup() {
		censistas = new ArrayList<Censista>();
		censistas.add(new Censista("Juan"));
		censistas.add(new Censista("Tito"));
		censistas.add(new Censista("Monica"));
		censistas.add(new Censista("Nahuel"));
		censistas.add(new Censista("Julieta"));
		censistas.add(new Censista("Paula"));
		
		radioCensal = new RadioCensal(5);
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(2));
		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(4));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(3));
	}
	
	@Test
	public void asignadorManzanasGolosoTest() {
		// Descomentar cuando se solucione el problema del algoritmo goloso
		
//		AsignadorDeManzanasGoloso asignador = new AsignadorDeManzanasGoloso(censistas, radioCensal);
//		
//		asignador.asignarManzanasACensistas();
//		
//		ArrayList<Manzana> manzanasAsignadasJuan = censistas.get(0).getManzanasAsignadas();
//		ArrayList<Manzana> manzanasAsignadasTito = censistas.get(1).getManzanasAsignadas();
//		
//		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
//		manzanasEsperadasJuan.add(new Manzana(0));
//		manzanasEsperadasJuan.add(new Manzana(1));
//		manzanasEsperadasJuan.add(new Manzana(2));
//		
//		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
//		manzanasEsperadasTito.add(new Manzana(3));
//		manzanasEsperadasTito.add(new Manzana(4));
//		
//		System.out.println(manzanasAsignadasJuan.toString());
//		System.out.println(manzanasAsignadasTito.toString());
//		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan) && manzanasAsignadasTito.equals(manzanasEsperadasTito));
//		
	}

}
