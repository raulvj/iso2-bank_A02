package edu.uclm.esi.iso2.banco20193capas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
/**
 * @author SODES
 *
 */
@SpringBootApplication
public class Lanzadora {
/**
 * 
 */
static final double IMPORTE = 1000; //1000 is a magic number!
/**
 * Launch the program.
 * @param args Main definition
 * @throws Exception Generic exception
 */
private static void main(final String[] args) throws Exception {
SpringApplication.run(Lanzadora.class, args);
try {
Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
pepe.insert();
Cuenta cuenta = new Cuenta();
cuenta.addTitular(pepe);
cuenta.insert();
cuenta.ingresar(IMPORTE);
} catch (Exception e) {
e.printStackTrace();
}
}
}