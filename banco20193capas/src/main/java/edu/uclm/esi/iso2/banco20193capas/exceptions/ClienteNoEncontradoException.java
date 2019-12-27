package edu.uclm.esi.iso2.banco20193capas.exceptions;
/**
 * @author SODES
 */
public class ClienteNoEncontradoException extends Exception {
/**
 * @param nif Nif cliente
 */
public ClienteNoEncontradoException(final String nif) {
super("No se encuentra el cliente con NIF " + nif);
}
}
