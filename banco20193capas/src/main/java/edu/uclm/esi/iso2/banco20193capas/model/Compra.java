package edu.uclm.esi.iso2.banco20193capas.model;

public class Compra {
private double importe;
private int token;
/**
 * @param importe1 Importe de la compra
 * @param token1 Token de la compra
 */
public Compra(final double importe1, final int token1) {
this.importe = importe1;
this.token = token1;
}
public double getImporte() {
return importe;
}
/**
 * @param importe1 Importe de la compra
 */
public void setImporte(final double importe1) {
this.importe = importe1;
}
public int getToken() {
return token;
}
/**
 * @param token1 Token de la compra
 */
public void setToken(final int token1) {
this.token = token1;
}
}
