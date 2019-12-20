package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
public class TestTarjetaCredito extends TestCase {

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
	public void testSacarDineroTC() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);

		TarjetaCredito tc = null;

		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();

			cuentaPepe.ingresar(1000);

			tc = cuentaPepe.emitirTarjetaCredito("12345X", 1000);

		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}

		try {

			tc.sacarDinero(tc.getPin(), 200);
			tc.liquidar();
			assertTrue(cuentaPepe.getSaldo() == 797); // 3 less than expected because there is a commission per
														// sacarDinero() of 3

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}

		try {

			tc.sacarDinero(tc.getPin(), -200);
			fail("Expecting ImporteInvalidoException");

		} catch (ImporteInvalidoException e) {

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}

		try {

			tc.sacarDinero(tc.getPin(), 8000);
			fail("Expecting SaldoInsuficienteException");

		} catch (SaldoInsuficienteException e) {

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}

	@Test
	public void testCambiarPinTC() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);

		TarjetaCredito tc = null;

		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();

			cuentaPepe.ingresar(1000);

			tc = cuentaPepe.emitirTarjetaCredito("12345X", 1000);

			tc.cambiarPin(0000, 1234);
			fail("Expecting PinInvalidoException");
		} catch (PinInvalidoException e) {

		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}

}
