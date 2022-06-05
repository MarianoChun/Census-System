package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import logica.AsignadorDeManzanasFB;
import logica.Censista;
import logica.Manzana;
import logica.RadioCensal;
import logica.Sistema;

public class asignadorFB {
	ArrayList<Censista> censistas;

	@Before
	public void setUp() {
		censistas = new ArrayList<Censista>();
		censistas.add(new Censista("Juan"));
		censistas.add(new Censista("Tito"));
		censistas.add(new Censista("Monica"));
		censistas.add(new Censista("Nahuel"));
		censistas.add(new Censista("Julieta"));
		censistas.add(new Censista("Paula"));
	}

	@Test
	public void algoritmoFBGruposDe3Test() {
		RadioCensal radioCensal = new RadioCensal(9);

		// 1er grupo
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(2));
		// ------

		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(5));

		// 2do grupo
		radioCensal.agregarManzanaContigua(new Manzana(3), new Manzana(4));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(5));
		// ------
		radioCensal.agregarManzanaContigua(new Manzana(3), new Manzana(6));
		radioCensal.agregarManzanaContigua(new Manzana(5), new Manzana(8));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(7));

		// 3er grupo
		radioCensal.agregarManzanaContigua(new Manzana(6), new Manzana(7));
		radioCensal.agregarManzanaContigua(new Manzana(7), new Manzana(8));
		// ------

		System.out.println(new AsignadorDeManzanasFB(censistas, radioCensal).getRecorridos());

		ArrayList<Censista> censistasAsignados = new Sistema(radioCensal, censistas).obtenerCensistasAsignadosFB();

		ArrayList<Manzana> manzanasAsignadasJuan = censistasAsignados.get(0).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasTito = censistasAsignados.get(1).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasMonica = censistasAsignados.get(2).getManzanasAsignadas();

		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
		manzanasEsperadasJuan.add(new Manzana(0));
		manzanasEsperadasJuan.add(new Manzana(1));
		manzanasEsperadasJuan.add(new Manzana(2));

		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
		manzanasEsperadasTito.add(new Manzana(3));
		manzanasEsperadasTito.add(new Manzana(4));
		manzanasEsperadasTito.add(new Manzana(5));

		ArrayList<Manzana> manzanasEsperadasMonica = new ArrayList<Manzana>();
		manzanasEsperadasMonica.add(new Manzana(6));
		manzanasEsperadasMonica.add(new Manzana(7));
		manzanasEsperadasMonica.add(new Manzana(8));

		ordenarManzanasAsignadasDeMenorAMayor();

		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan));
		assertTrue(manzanasAsignadasTito.equals(manzanasEsperadasTito));
		assertTrue(manzanasAsignadasMonica.equals(manzanasEsperadasMonica));

	}

	@Test
	public void algoritmoFBGruposDe3TiposTest() {
		RadioCensal radioCensal = new RadioCensal(9);

		radioCensal.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		radioCensal.agregarManzanaContigua(new Manzana(1), new Manzana(2));

		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		radioCensal.agregarManzanaContigua(new Manzana(2), new Manzana(4));

		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(6));
		radioCensal.agregarManzanaContigua(new Manzana(4), new Manzana(5));

		radioCensal.agregarManzanaContigua(new Manzana(6), new Manzana(7));
		radioCensal.agregarManzanaContigua(new Manzana(7), new Manzana(8));

		ArrayList<Censista> censistasAsignados = new Sistema(radioCensal, censistas).obtenerCensistasAsignadosFB();

		ArrayList<Manzana> manzanasAsignadasJuan = censistasAsignados.get(0).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasTito = censistasAsignados.get(1).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasMonica = censistasAsignados.get(2).getManzanasAsignadas();
		ArrayList<Manzana> manzanasAsignadasNahuel = censistasAsignados.get(3).getManzanasAsignadas();

		ArrayList<Manzana> manzanasEsperadasJuan = new ArrayList<Manzana>();
		manzanasEsperadasJuan.add(new Manzana(0));
		manzanasEsperadasJuan.add(new Manzana(1));
		manzanasEsperadasJuan.add(new Manzana(2));

		ArrayList<Manzana> manzanasEsperadasTito = new ArrayList<Manzana>();
		manzanasEsperadasTito.add(new Manzana(4));
		manzanasEsperadasTito.add(new Manzana(5));
		manzanasEsperadasTito.add(new Manzana(6));

		ArrayList<Manzana> manzanasEsperadasMonica = new ArrayList<Manzana>();
		manzanasEsperadasMonica.add(new Manzana(7));
		manzanasEsperadasMonica.add(new Manzana(8));

		ArrayList<Manzana> manzanasEsperadasNahuel = new ArrayList<Manzana>();
		manzanasEsperadasNahuel.add(new Manzana(3));

		ordenarManzanasAsignadasDeMenorAMayor();

		assertTrue(manzanasAsignadasJuan.equals(manzanasEsperadasJuan));
		assertTrue(manzanasAsignadasTito.equals(manzanasEsperadasTito));
		assertTrue(manzanasAsignadasMonica.equals(manzanasEsperadasMonica));
		assertTrue(manzanasAsignadasNahuel.equals(manzanasEsperadasNahuel));
	}

	private void ordenarManzanasAsignadasDeMenorAMayor() {
		for (Censista censista : censistas) {
			if (censista.getManzanasAsignadas().size() == 0) {
				break;
			}
			ArrayList<Manzana> manzanasAsignadasCensista = censista.getManzanasAsignadas();
			manzanasAsignadasCensista.sort((p, q) -> p.getNroManzana() - q.getNroManzana());
		}
	}

}
