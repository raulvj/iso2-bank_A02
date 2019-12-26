package edu.uclm.esi.iso2.banco20193capas;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import antlr.collections.List;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
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
public class TestCuentaDLC extends TestCase {

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
	public void testAddTitularWrong() {

		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			Cliente pepito = new Cliente("12345Y", "Pepito", "Pérezito");
			pepe.insert();
			pepito.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.setCreada(true);
			cuentaPepe.addTitular(pepito);

			fail("Expecting CuentaYaCreadaException");
		} catch (CuentaYaCreadaException e) {
		} catch (Exception e) {
			fail("Expecting CuentaYaCreadaException, but " + e + " was thrown instead");
		}

	}

	@Test
	public void testEmitirTarjetaCreditoSinCliente() {

		try {
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.emitirTarjetaCredito("00001X", 1000);
			fail("Expecting ClienteNoEncontradoException");
		} catch (ClienteNoEncontradoException e) {

		} catch (Exception e) {
			fail("Expecting ClienteNoEncontradoException, but " + e + " was thrown instead");
		}

	}

	@Test
	public void testEmitirTarjetaCreditoSinTitular() {

		try {

			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.emitirTarjetaCredito("12345X", 1000);
			fail("Expecting ClienteNoAutorizadoException");
		} catch (ClienteNoAutorizadoException e) {

		} catch (Exception e) {
			fail("Expecting ClienteNoAutorizadoException, but " + e + " was thrown instead");
		}

	}

	@Test
	public void testEmitirTarjetaDebitoSinCliente() {

		try {
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.emitirTarjetaDebito("00001X");
			fail("Expecting ClienteNoEncontradoException");
		} catch (ClienteNoEncontradoException e) {

		} catch (Exception e) {
			fail("Expecting ClienteNoEncontradoException, but " + e + " was thrown instead");
		}

	}

	@Test
	public void testEmitirTarjetaDebitoSinTitular() {

		try {

			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.emitirTarjetaDebito("12345X");
			fail("Expecting ClienteNoAutorizadoException");
		} catch (ClienteNoAutorizadoException e) {

		} catch (Exception e) {
			fail("Expecting ClienteNoAutorizadoException, but " + e + " was thrown instead");
		}

	}

	@Test
	public void testGetTitulares() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.getTitulares();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}

	@Test
	public void testIsCreada() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.isCreada();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}

	}

	@Test
	public void testSetId() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.setId((long) 1);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testSetTitulares() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			
			ArrayList<Cliente> titulares = new ArrayList<Cliente>();
			titulares.add(pepe);
			
			cuentaPepe.setTitulares(titulares);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

}
