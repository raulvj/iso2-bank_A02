package edu.uclm.esi.iso2.banco20193capas;

import edu.uclm.esi.iso2.banco20193capas.model.MovimientoCuenta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMovimientoCuenta extends TestCase {

	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
	}

	@Test
	public void testSetId() {
		try {
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.setId((long) 12345);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testGetId() {
		try {
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.getId();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}

	@Test
	public void testSetCuenta() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.setCuenta(cuentaPepe);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testGetCuenta() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.getCuenta();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}

	@Test
	public void testSetImporte() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.setImporte(500);
			assertEquals("Fields didn't match", movimiento.getImporte(), 500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testGetImporte() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.getImporte();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}
	@Test
	public void testSetConcepto() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.setConcepto("Alquiler");
			assertEquals("Fields didn't match", movimiento.getImporte(), 500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testGetConcepto() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			Cuenta cuentaPepe = new Cuenta(1);
			MovimientoCuenta movimiento = new MovimientoCuenta(cuentaPepe, 500, "Alquiler");
			movimiento.getConcepto();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}
}
