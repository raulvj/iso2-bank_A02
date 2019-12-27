package edu.uclm.esi.iso2.banco20193capas.exceptions;
/**
 * @author SODES
 */
public class CuentaInvalidaException extends Exception {
/**
 * @param numero Numero de cuenta
 */
public CuentaInvalidaException(final Long numero) {
super("La cuenta " + numero + " no existe");
}

}
