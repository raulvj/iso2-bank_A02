package edu.uclm.esi.iso2.banco20193capas;




import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import edu.uclm.esi.iso2.banco20193capas.model.Manager;


import edu.uclm.esi.iso2.banco20193capas.model.Compra;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCompra extends TestCase{
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
			fail("Excepción inesperada: " + e);
		}

	}

	@Test 
	public void testSetToken() {
		try {
			
			compratest.setToken(21+2);
		}
		catch (Exception e){
			fail("Excepción inesperada: " + e);
		}
	}
}
