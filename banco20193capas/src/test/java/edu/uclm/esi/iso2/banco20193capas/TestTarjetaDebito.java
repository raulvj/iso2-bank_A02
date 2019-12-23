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
	public void testTarjetaDebitoSacarDinero_Pin_ImporteInvalido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			
			tdPepe.sacarDinero(tdPepe.getPin(), -1);
			fail("Expecting ImporteInvalidoException, but none thrown");
		} catch (ImporteInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_Pin_ImporteValido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(tdPepe.getPin(), 1);
		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_Pin_ImporteSuperior() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(tdPepe.getPin(), tdPepe.getCuenta().getSaldo() +1);
			fail("Expecting SaldoInsuficienteException, but none thrown");
		} catch (SaldoInsuficienteException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteInvalido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, -1);
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteValido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, 1);
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testTarjetaDebitoSacarDinero_NoPin_ImporteSuperior() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito("12345X");
			tdPepe.sacarDinero(0000, tdPepe.getCuenta().getSaldo() +1);
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Unexpected Exception: " + e);
		}
	}
	@Test
	public void testCambiarPin_Invalido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			tdPepe.cambiarPin(9876, 1234);
			fail("Esperaba PinInvalidoException");
		} catch (PinInvalidoException e) {
		} catch (Exception e) {
			fail("Esperaba PinInvalidoException" + e);
		}
	}
	
	@Test
	public void testCambiarPin_Valido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);

			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			tdPepe.cambiarPin(tdPepe.getPin(), 0000);
		}catch (Exception e) {
			fail("No se esperaba ninguna excepcion. Recibida: " + e);
		}
	}
	
	@Test
	public void testConfirmarCompraPorInternetConTD_Valido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
		
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);
			cuentaPepe.retirar(200);;
			assertTrue(cuentaPepe.getSaldo()==800);
			
			TarjetaDebito td = cuentaPepe.emitirTarjetaDebito("12345X");
			int token = td.comprarPorInternet(td.getPin(), 300);
			td.confirmarCompraPorInternet(token);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	
	@Test
	public void testConfirmarCompraPorInternetConTD_Invalido() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
		
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			
			cuentaPepe.ingresar(1000);
			cuentaPepe.retirar(200);;
			assertTrue(cuentaPepe.getSaldo()==800);
			
			TarjetaDebito td = cuentaPepe.emitirTarjetaDebito("12345X");
			int token = td.comprarPorInternet(td.getPin(), 300);
			td.confirmarCompraPorInternet(0000);
			fail("Esperaba TokenInvalidoException");
		} catch(TokenInvalidoException e) {
		} catch (Exception e) {
			fail("Esperaba TokenInvalidoException. Recibida: " + e.getMessage());
		}
	}
	
	@Test
	public void testTarjetaDebitoBloqueada() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();
			
			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
		
			TarjetaDebito tdPepe = cuentaPepe.emitirTarjetaDebito(pepe.getNif());

			for (int i = 0; i<3; i++) {
				try {
					tdPepe.sacarDinero(tdPepe.getPin()+1, 100);
					fail("Esperaba PinInvalidoException");
				} catch (PinInvalidoException e) {
				} catch (Exception e) {
					fail("Esperaba PinInvalidoException y obtuvo: " + e);
				}
			}
			assertTrue(tdPepe.isActiva() == false);
			tdPepe.sacarDinero(tdPepe.getPin(), 100);
			fail("Esperaba TarjetaBloqueadaException");
		} catch (TarjetaBloqueadaException e) {
		} catch (Exception e) {
			fail("Unexpected Exception " + e);
		}
	}
}
