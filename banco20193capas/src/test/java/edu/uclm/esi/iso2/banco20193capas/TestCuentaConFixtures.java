package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCuentaConFixtures extends TestCase {
	private Cuenta cuentaPepe, cuentaAna;
	private Cliente pepe, ana;
	private TarjetaDebito tdPepe, tdAna;
	private TarjetaCredito tcPepe, tcAna;

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
		this.ana = new Cliente("98765F", "Ana", "López");
		this.ana.insert();
		this.cuentaPepe = new Cuenta(1);
		this.cuentaAna = new Cuenta(2);
		try {
			this.cuentaPepe.addTitular(pepe);
			this.cuentaPepe.insert();
			this.cuentaPepe.ingresar(1000);
			this.cuentaAna.addTitular(ana);
			this.cuentaAna.insert();
			this.cuentaAna.ingresar(5000);
			this.tcPepe = this.cuentaPepe.emitirTarjetaCredito(pepe.getNif(), 2000);
			this.tcPepe.cambiarPin(this.tcPepe.getPin(), 1234);
			this.tcAna = this.cuentaAna.emitirTarjetaCredito(ana.getNif(), 10000);
			this.tcAna.cambiarPin(this.tcAna.getPin(), 1234);
			this.tdPepe = this.cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			this.tdPepe.cambiarPin(this.tdPepe.getPin(), 1234);
			this.tdAna = this.cuentaAna.emitirTarjetaDebito(ana.getNif());
			this.tdAna.cambiarPin(this.tdAna.getPin(), 1234);
		} catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}
	}
	
	@Test
	public void testImporteInvalido1_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}

	@Test
	public void testImporteInvalido2() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}

	// Warning: 3 oracles and 3 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException3_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido3_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException3_C() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException4_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(2L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido4_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(2L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido5_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(1L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException5_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(1L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido6_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException6_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(-1);
			saldoPepe=saldoPepe-(-1);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido7_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException7_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 3 oracles and 3 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException8_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido8_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException8_C() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(-1);
			saldoPepe=saldoPepe+(-1);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido9_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException9_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}

	@Test
	public void testImporteInvalido10() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(2L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}

	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException11_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(2L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido11_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(0);
			saldoPepe=saldoPepe-(0);
			this.cuentaPepe.transferir(2L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}

	@Test
	public void testImporteInvalido12() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}

	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException13_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido13_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException14_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido14_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(0);
			saldoPepe=saldoPepe+(0);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(2L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido15_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException15_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}

	@Test
	public void testCuentaInvalidaException16() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(50);
			saldoPepe=saldoPepe-(50);
			this.cuentaPepe.transferir(1L, 50, "Transferencia");
			saldoPepe = saldoPepe-(50)-Math.max(0.01*50, 1.5);
			saldoAna = saldoAna + 50;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}

	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException17_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException17_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException18_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException18_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(100);
			saldoPepe=saldoPepe+(100);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, 3000, "Transferencia");
			saldoPepe = saldoPepe-(3000)-Math.max(0.01*3000, 1.5);
			saldoAna = saldoAna + 3000;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 2 oracles and 2 test templates are applicable to this test case
	@Test
	public void testImporteInvalido19_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException19_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(1000);
			saldoPepe=saldoPepe-(1000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


	// Warning: 3 oracles and 3 test templates are applicable to this test case
	@Test
	public void testSaldoInsuficienteException20_A() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba SaldoInsuficienteException");
		}
		catch (SaldoInsuficienteException e) { }
		catch (Exception e) {
			fail("Se esperaba SaldoInsuficienteException, pero se lanzó " + e);
		}
	}
	@Test
	public void testImporteInvalido20_B() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba ImporteInvalidoException");
		}
		catch (ImporteInvalidoException e) { }
		catch (Exception e) {
			fail("Se esperaba ImporteInvalidoException, pero se lanzó " + e);
		}
	}
	@Test
	public void testCuentaInvalidaException20_C() {
		try {
			double saldoPepe=1000; double saldoAna=5000;
			this.cuentaPepe.ingresar(1000);
			saldoPepe=saldoPepe+(1000);
			this.cuentaPepe.retirar(100000);
			saldoPepe=saldoPepe-(100000);
			this.cuentaPepe.transferir(1L, -100, "Transferencia");
			saldoPepe = saldoPepe-(-100)-Math.max(0.01*-100, 1.5);
			saldoAna = saldoAna + -100;

			fail("Se esperaba CuentaInvalidaException");
		}
		catch (CuentaInvalidaException e) { }
		catch (Exception e) {
			fail("Se esperaba CuentaInvalidaException, pero se lanzó " + e);
		}
	}


}
