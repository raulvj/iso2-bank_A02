package edu.uclm.esi.iso2.banco20193capas.exceptions;
/**
 * @author SODES
 */
public class ImporteInvalidoException extends Exception {
/**
 * @param importe Importe realizado
 */
public ImporteInvalidoException(final double importe) {
super("El importe " + importe + " no es"
+ " válido para esta operación");
}
}
