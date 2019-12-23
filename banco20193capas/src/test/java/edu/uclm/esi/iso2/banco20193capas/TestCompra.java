package edu.uclm.esi.iso2.banco20193capas;


import static org.junit.Assert.fail;

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
import edu.uclm.esi.iso2.banco20193capas.model.Compra;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCompra {
	private Compra compratest;
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		try {
			int token = 23;
			double importe =23.23;
			compratest = new Compra(importe, token);
		}
		catch(Exception e){
			fail("Unexpected Exception: " + e);
		}
	}
	@Test
	public void testSetImporte() {
		try {
			
			compratest.setImporte(21.23+2);
		}
		catch (Exception e){
			System.out.println("Excepción inesperada: " + e);
		}

	}

	@Test 
	public void testSetToken() {
		try {
			
			compratest.setToken(21+2);
		}
		catch (Exception e){
			System.out.println("Excepción inesperada: " + e);
		}
	}
}
