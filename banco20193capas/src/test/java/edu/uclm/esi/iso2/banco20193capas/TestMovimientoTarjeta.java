package edu.uclm.esi.iso2.banco20193capas;


import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoTarjetaCredito;

import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMovimientoTarjeta extends TestCase {
	
	private MovimientoTarjetaCredito movtarjeta;
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		
		try {
			movtarjeta = new MovimientoTarjetaCredito();
		}
		catch(Exception e){
			fail("Unexpected Exception: " + e);
		}
	}
	
	@Test
	public void testSetId() {
		try {
			
			movtarjeta.setId((long) 34567);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}
	
	@Test
	public void testSetTarjeta() {
		try {
			
			TarjetaCredito tarjeta= new TarjetaCredito();
			movtarjeta.setTarjeta(tarjeta);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

	@Test
	public void testGetTarjeta() {
		try {
			
			movtarjeta.getTarjeta();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}
	@Test
	public void testSetImporte() {
		try {
			
			double importe = 1323.3;
			movtarjeta.setImporte(importe);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}
	@Test
	public void testSetConcepto() {
		try {
		
			movtarjeta.setConcepto("Loteria de Navidad ");
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}
	@Test
	public void testGetConcepto() {
		try {
			
			movtarjeta.getConcepto();
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
	}

}
