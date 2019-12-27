package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Representa un movimiento en una cuenta bancaria.
 *
 */
@Entity
public class MovimientoCuenta {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@ManyToOne
private Cuenta cuenta;
private double importe;
private String concepto;
public MovimientoCuenta() {
}
/**
 * @param cuenta1 Cuenta
 * @param importe1 Importe
 * @param concepto1 Concepto
 */
public MovimientoCuenta(final Cuenta cuenta1,
final double importe1, final String concepto1) {
this.importe = importe1;
this.concepto = concepto1;
this.cuenta = cuenta1;
}
public Long getId() {
return id;
}
/**
 * @param id1 Id
 */
public void setId(final Long id1) {
this.id = id1;
}
public Cuenta getCuenta() {
return cuenta;
}
/**
 * @param cuenta1 Cuenta
 */
public void setCuenta(final Cuenta cuenta1) {
this.cuenta = cuenta1;
}
public double getImporte() {
return importe;
}
/**
 * @param importe1 Importe
 */
public void setImporte(final double importe1) {
this.importe = importe1;
}
public String getConcepto() {
return concepto;
}
/**
 * @param concepto1 Concepto
 */
public void setConcepto(final String concepto1) {
this.concepto = concepto1;
}
}
