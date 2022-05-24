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

	}
	// Happy path
	@Test
	public void asignadorManzanasGolosoGrupos3ManzanasTest() {
		radioCensal = new RadioCensal(6);
		
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(2));
		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(5));
		radioCensal.agregarManzanaContigua(new Manzana(3), new Manzana(4));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(5));
		
		AsignadorDeManzanasGoloso asignador = new AsignadorDeManzanasGoloso(censistas, radioCensal);
		
		asignador.asignarManzanasACensistas();
		
		ArrayList<Manzana> manzanasAsignadasJuan = censistas.get(0).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasTito = censistas.get(1).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasMonica = censistas.get(2).getManzanasAsignadas();
		
		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
		manzanasEsperadasJuan.add(new Manzana(0));
		manzanasEsperadasJuan.add(new Manzana(1));
		manzanasEsperadasJuan.add(new Manzana(2));
		
		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
		manzanasEsperadasTito.add(new Manzana(3));
		manzanasEsperadasTito.add(new Manzana(4));

		ArrayList<Manzana> manzanasEsperadasMonica = new ArrayList<Manzana>();
		manzanasEsperadasMonica.add(new Manzana(5));

		
		manzanasAsignadasJuan.sort((p,q) -> p.getNroManzana() - q.getNroManzana());
		manzanasAsignadasTito.sort((p,q) -> p.getNroManzana() - q.getNroManzana());



//		System.out.println(manzanasAsignadasTito.toString());
//		System.out.println(manzanasAsignadasJuan.toString());
//		System.out.println(manzanasAsignadasMonica.toString());
		
		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan));
		assertTrue(manzanasAsignadasTito.equals(manzanasEsperadasTito));
		assertTrue(manzanasAsignadasMonica.equals(manzanasEsperadasMonica));
	}
	
	@Test
	public void asignadorManzanasGolosoGrupos3Manzanas2Test() {
		radioCensal = new RadioCensal(9);
		
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
		
		AsignadorDeManzanasGoloso asignador = new AsignadorDeManzanasGoloso(censistas, radioCensal);
		
		asignador.asignarManzanasACensistas();
		
		ArrayList<Manzana> manzanasAsignadasJuan = censistas.get(0).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasTito = censistas.get(1).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasMonica = censistas.get(2).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasNahuel = censistas.get(3).getManzanasAsignadas();
		
		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
		manzanasEsperadasJuan.add(new Manzana(2));
		manzanasEsperadasJuan.add(new Manzana(3));
		manzanasEsperadasJuan.add(new Manzana(4));
		
		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
		manzanasEsperadasTito.add(new Manzana(1));
		manzanasEsperadasTito.add(new Manzana(5));
		manzanasEsperadasTito.add(new Manzana(8));
		
		ArrayList<Manzana> manzanasEsperadasMonica = new ArrayList<Manzana>();
		manzanasEsperadasMonica.add(new Manzana(6));
		manzanasEsperadasMonica.add(new Manzana(7));

		
		ArrayList<Manzana> manzanasEsperadasNahuel = new ArrayList<Manzana>();
		manzanasEsperadasNahuel.add(new Manzana(0));


		for(Censista censista : censistas) {
			if(censista.getManzanasAsignadas().size() == 0) {
				break;
			}
			ArrayList<Manzana> manzanasAsignadasCensista  = censista.getManzanasAsignadas();
			manzanasAsignadasCensista.sort((p,q) -> p.getNroManzana() - q.getNroManzana());	
		}
		
		// Imprime las manzanas asignadas de cada cencista que tenga alguna asignada.
//		int i = 0;
//		for(Censista censista : censistas) {
//			if(censista.getManzanasAsignadas().size() == 0) {
//				break;
//			}
//			ArrayList<Manzana> manzanasAsignadasCensista  = censista.getManzanasAsignadas();
//			manzanasAsignadasCensista.sort((p,q) -> p.getNroManzana() - q.getNroManzana());
//			System.out.println("censista nro: " + i + " " + manzanasAsignadasCensista.toString());
//			i++;
//			
//		}
		
		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan));
		assertTrue(manzanasAsignadasTito.equals(manzanasEsperadasTito));
		assertTrue(manzanasAsignadasMonica.equals(manzanasEsperadasMonica));
		assertTrue(manzanasAsignadasNahuel.equals(manzanasEsperadasNahuel));
	}
	
	// Casos borde
	@Test
	public void asignadorManzanasGolosoGruposDe3y2Test() {
		radioCensal = new RadioCensal(5);
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(2));
		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(4));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(3));

		AsignadorDeManzanasGoloso asignador = new AsignadorDeManzanasGoloso(censistas, radioCensal);
		
		asignador.asignarManzanasACensistas();
		
		ArrayList<Manzana> manzanasAsignadasJuan = censistas.get(0).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasTito = censistas.get(1).getManzanasAsignadas();
		
		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
		manzanasEsperadasJuan.add(new Manzana(0));
		manzanasEsperadasJuan.add(new Manzana(1));
		manzanasEsperadasJuan.add(new Manzana(2));
		
		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
		manzanasEsperadasTito.add(new Manzana(3));
		manzanasEsperadasTito.add(new Manzana(4));
		
		manzanasAsignadasJuan.sort((p,q) -> p.getNroManzana() - q.getNroManzana());
		manzanasAsignadasTito.sort((p,q) -> p.getNroManzana() - q.getNroManzana());
		manzanasEsperadasJuan.sort((p,q) -> p.getNroManzana() - q.getNroManzana());
		manzanasEsperadasTito.sort((p,q) -> p.getNroManzana() - q.getNroManzana());

		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan) && manzanasAsignadasTito.equals(manzanasEsperadasTito));
		
	}

}
