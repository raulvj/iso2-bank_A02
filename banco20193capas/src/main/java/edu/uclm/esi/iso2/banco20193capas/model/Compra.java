package edu.uclm.esi.iso2.banco20193capas.model;

public class Compra {
private double importe;
private int token;
public Compra(final double importe1, final int token1) {
this.importe = importe1;
this.token = token1;
}
public double getImporte() {
return importe;
}
public void setImporte(final double importe1) {
this.importe = importe1;
}
public int getToken() {
return token;
}
public void setToken(final int token1) {
this.token = token1;
}
}
