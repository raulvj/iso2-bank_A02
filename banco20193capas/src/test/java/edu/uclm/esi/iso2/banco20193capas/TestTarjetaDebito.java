package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TokenInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarjetaDebito extends TestCase {
	private Cuenta cuentaPepe;
	private Cliente pepe;
	private TarjetaDebito tdPepe;
	
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		this.pepe = new Cliente("12345X", "Pepe", "Pérez");
		this.pepe.insert();
		this.cuentaPepe = new Cuenta(1);
		
		try {
			this.cuentaPepe.addTitular(pepe);
			this.cuentaPepe.insert();
			this.cuentaPepe.ingresar(1000);
			//this.tdPepe = this.cuentaPepe.emitirTarjetaDebito("12345X");
		}catch (Exception e) {
			fail("Unexpected exception in setUp(): " + e);
		}
	}

	@Test
	public void testTarjetaDebitoSacarDinero_Pin_ImporteInvalido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(tdPepe.getPin(), -1);
			
			fail("Expecting ImporteInvalidoException, but none thrown");
		} catch (ImporteInvalidoException e) {
		} catch (Exception e) {
			fail("Expected ImporteInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_Pin_ImporteValido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(tdPepe.getPin(), 1);
			
		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_Pin_ImporteSuperior() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(tdPepe.getPin(), tdPepe.getCuenta().getSaldo() +1);
			
			fail("Expecting SaldoInsuficienteException, but none thrown");
		} catch (SaldoInsuficienteException e) {
		} catch (Exception e) {
			fail("Expected SaldoInsuficienteException but received: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteInvalido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, -1);
			
			fail("Expecting PinInvalidoException, but none thrown");
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Expected PinInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteValido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, 1);
			
			fail("Expecting PinInvalidoException, but none thrown");
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Expected PinInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteSuperior() {
		try {

			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, tdPepe.getCuenta().getSaldo() +1);
			
			fail("Expecting PinInvalidoException, but none thrown");
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Expected PinInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testCambiarPin_Invalido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			tdPepe.cambiarPin(9876, 1234);
			
			fail("Expecting PinInvalidoException, but none thrown");
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Expected PinInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testCambiarPin_Valido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			tdPepe.cambiarPin(tdPepe.getPin(), 0000);
			
		}catch (Exception e) {
			fail("Unexpected exception: " + e);
		}
	}
	
	@Test
	public void testConfirmarCompraPorInternetConTD_Valido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			int token = tdPepe.comprarPorInternet(tdPepe.getPin(), 300);
			tdPepe.confirmarCompraPorInternet(token);
			
		} catch (Exception e) {
			fail("Unexpected exception: " + e);
		}
	}
	
	@Test
	public void testConfirmarCompraPorInternetConTD_Invalido() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			int token = tdPepe.comprarPorInternet(tdPepe.getPin(), 300);
			tdPepe.confirmarCompraPorInternet(0000);
			
			fail("Expecting TokenInvalidoException, but none thrown.");
		} catch(TokenInvalidoException e) {
		} catch (Exception e) {
			fail("Expected TokenInvalidoException but received: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoBloqueada() {
		try {
			
			tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());

			/* introduce wrong PIN three times */
			for (int i = 0; i<3; i++) {
				try {
					tdPepe.sacarDinero(tdPepe.getPin()+1, 100);
					fail("Expecting PinInvalidoException, but none thrown.");
				} catch (PinInvalidoException e) {
				} catch (Exception e) {
					fail("Expected PinInvalidoException but received: " + e);
				}
			}
			assertTrue(tdPepe.isActiva() == false);
			tdPepe.sacarDinero(tdPepe.getPin(), 100);
			
			fail("Expecting TarjetaBloqueadaException, but none thrown.");
		} catch (TarjetaBloqueadaException e) {
		} catch (Exception e) {
			fail("Expected TarjetaBloqueadaException but received: " + e);
		}
	}
}
