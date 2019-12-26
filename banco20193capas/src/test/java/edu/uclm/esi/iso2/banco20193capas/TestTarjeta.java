package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;

import edu.uclm.esi.iso2.banco20193capas.model.Cliente;

import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarjeta extends TestCase {

	private Cliente pepe;
	private Cuenta cuentaPepe;
	private TarjetaCredito tc;

	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();

		try {

			pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			cuentaPepe = new Cuenta(1);

			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();

			cuentaPepe.ingresar(1000);

			tc = cuentaPepe.emitirTarjetaCredito("12345X", 1000);
			
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}

	}

	
	
	@Test
	public void testGetTitular() {

		try {

			tc.getTitular();

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testSetId() {

		try {

			tc.setId((long)2332);

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	@Test
	public void testSetPin() {

		try {

			tc.setPin(1234);

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	@Test
	public void testGetCuenta() {

		try {

			tc.getCuenta();

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	@Test
	public void testSetActiva() {

		try {

			tc.setActiva(true);

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}

}
