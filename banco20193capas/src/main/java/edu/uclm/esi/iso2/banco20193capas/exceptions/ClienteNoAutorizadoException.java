package edu.uclm.esi.iso2.banco20193capas.exceptions;
/**
 * @author SODES
 */
public class ClienteNoAutorizadoException extends Exception {
/**
 * @param nif Nif cliente
 * @param id Id cliente
 */
public ClienteNoAutorizadoException(final String nif, final Long id) {
super("El cliente con NIF " + nif + " no está"
+ " autorizado para operar en la cuenta " + id);
}
}
