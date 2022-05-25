package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import logica.Censista;
import logica.Manzana;

public class CensistaTest {

	@Test
	public void asignarManzanaUnicaTest() {
		Censista censista = new Censista("Pedro");

		censista.asignarManzana(new Manzana(0));

		assertEquals(1, censista.cantManzanasAsignadas());
	}

	@Test
	public void asignarCollectionManzanasTest() {
		Censista censista = new Censista("Pedro");

		ArrayList<Manzana> manzanas = new ArrayList<Manzana>();
		manzanas.add(new Manzana(0));
		manzanas.add(new Manzana(1));

		censista.asignarManzana(manzanas);

		assertEquals(2, censista.cantManzanasAsignadas());
	}

	@Test(expected = IllegalArgumentException.class)
	public void asignarManzanaACensistaCon3ManzanasAsignadasTest() {
		Censista censista = new Censista("Pedro");

		censista.asignarManzana(new Manzana(0));
		censista.asignarManzana(new Manzana(1));
		censista.asignarManzana(new Manzana(2));

		censista.asignarManzana(new Manzana(4));

	}

	@Test(expected = IllegalArgumentException.class)
	public void asignarManzanasQueExcedanCantMaxTest() {
		Censista censista = new Censista("Pedro");

		censista.asignarManzana(new Manzana(0));
		censista.asignarManzana(new Manzana(1));
		ArrayList<Manzana> manzanas = new ArrayList<Manzana>();
		manzanas.add(new Manzana(2));
		manzanas.add(new Manzana(3));

		censista.asignarManzana(manzanas);
	}

	@Test(expected = IllegalArgumentException.class)
	public void asignarListaDeMasDe3ManzanasTest() {
		Censista censista = new Censista("Pedro");

		ArrayList<Manzana> manzanas = new ArrayList<Manzana>();
		manzanas.add(new Manzana(0));
		manzanas.add(new Manzana(1));
		manzanas.add(new Manzana(2));
		manzanas.add(new Manzana(3));
		manzanas.add(new Manzana(4));

		censista.asignarManzana(manzanas);

	}
}
