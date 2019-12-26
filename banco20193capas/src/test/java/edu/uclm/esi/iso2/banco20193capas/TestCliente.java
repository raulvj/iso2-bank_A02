package edu.uclm.esi.iso2.banco20193capas;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TokenInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCliente extends TestCase{
	
		
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
			Cliente pepe = new Cliente();
			pepe.setId((long) 12345);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		}
			
		@Test
		public void testGetId() {
			try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.getId();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
			
		}
		@Test
		public void testSetNif() {
			try {
			Cliente pepe = new Cliente();
			pepe.setNif("12345X");
			assertEquals("Fields didn't match", pepe.getNif(), "12345X");
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		}
			
		@Test
		public void testGetNif() {
			try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.getNif();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
			
		}
		@Test
		public void testSetNombre() {
			try {
			Cliente pepe = new Cliente();
			pepe.setNombre("Pepe");
			assertEquals("Fields didn't match", pepe.getNif(), "Pepe");
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		}
			
		@Test
		public void testGetNombre() {
			try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.getNombre();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
			
		}
		@Test
		public void testSetApellidos() {
			try {
			Cliente pepe = new Cliente();
			pepe.setApellidos("Pérez");
			assertEquals("Fields didn't match", pepe.getNif(), "Pérez");
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		}
			
		@Test
		public void testGetApellidos() {
			try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.getApellidos();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
			
		}
		
}
