package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import logica.Manzana;
import logica.RadioCensal;

public class RadioCensalTest {

	@Test
	public void agregarManzanaContiguaTest() {
		RadioCensal r = new RadioCensal(3);

		r.agregarManzanaContigua(new Manzana(0), new Manzana(1));

		assertTrue(r.existeManzanaContigua(0, 1));
	}

	@Test
	public void manzanaContiguaInexistenteTest() {
		RadioCensal r = new RadioCensal(4);
		r.agregarManzanaContigua(new Manzana(0), new Manzana(1));
		r.agregarManzanaContigua(new Manzana(1), new Manzana(2));

		assertFalse(r.existeManzanaContigua(0, 3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarManzanaNegativaTest() {
		RadioCensal r = new RadioCensal(3);

		r.agregarManzanaContigua(new Manzana(-1), new Manzana(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarManzanaExcedidaTest() {
		RadioCensal r = new RadioCensal(3);

		r.agregarManzanaContigua(new Manzana(2), new Manzana(10));
	}

	@Test
	public void manzanasVecinasTest() {
		RadioCensal r = new RadioCensal(5);

		r.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		r.agregarManzanaContigua(new Manzana(3), new Manzana(4));

		Set<Integer> esperado = new HashSet<Integer>();
		esperado.add(2);
		esperado.add(4);

		assertEquals(esperado, r.manzanasVecinas(3));
	}

	@Test
	public void gradoDeManzanaTest() {
		RadioCensal r = new RadioCensal(5);

		r.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		r.agregarManzanaContigua(new Manzana(3), new Manzana(4));

		assertEquals(2, r.gradoManzana(3));
	}

	@Test
	public void cloneTest() {
		RadioCensal r = new RadioCensal(5);

		r.agregarManzanaContigua(new Manzana(2), new Manzana(3));
		r.agregarManzanaContigua(new Manzana(3), new Manzana(4));

		RadioCensal rClone = r.clone();
		assertEquals(rClone.cantManzanas(), r.cantManzanas());
		assertTrue(rClone.existeManzanaContigua(2, 3));
		assertTrue(rClone.existeManzanaContigua(3, 4));

	}
}
