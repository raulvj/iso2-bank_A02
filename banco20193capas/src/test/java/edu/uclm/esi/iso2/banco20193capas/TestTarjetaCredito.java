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
	public void testSacarDinero1() {

			try {
				
				tc.sacarDinero(tc.getPin(), -1);
				fail("Expecting ImporteInvalidoException, but no exception thrown");
				
			} catch (ImporteInvalidoException e) {
			} catch (Exception e) {
				fail("Unexpected Exception: " + e);
			}
	}
	
	@Test
	public void testSacarDinero2() {

		try {

			tc.sacarDinero(tc.getPin(), 1);

		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testSacarDinero3() {
		try {
			
			tc.sacarDinero(tc.getPin(), tc.getCreditoDisponible()+1);
			fail("Expecting ImporteInvalidoException, but no exception thrown");
			
		} catch (SaldoInsuficienteException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testSacarDinero4() {

			try {
				tc.sacarDinero(0000, -1);
				fail("Expecting PinInvalidoException, but no exception thrown");
				
			} catch (PinInvalidoException e) {
			} catch (Exception e) {
				fail("Unexpected Exception: " + e);
			}

	}
	
	@Test
	public void testSacarDinero5() {

		try {
			tc.sacarDinero(0000, 1);
			fail("Expecting PinInvalidoException, but no exception thrown");
			
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}

	}
	
	@Test
	public void testSacarDinero6() {

		try {
			tc.sacarDinero(0000, tc.getCreditoDisponible()+1);
			fail("Expecting PinInvalidoException, but no exception thrown");
			
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}

	}

	@Test
	public void testCambiarPin1() {

		try {
			tc.cambiarPin(tc.getPin(), 0000);
		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}
	
	@Test
	public void testCambiarPin2() {

		try {
			tc.cambiarPin(0001, 0000);
			fail("Expecting PinInvalidoException");
		} catch (PinInvalidoException e) {

		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}

}
