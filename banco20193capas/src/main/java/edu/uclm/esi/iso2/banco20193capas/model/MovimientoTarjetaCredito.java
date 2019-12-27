package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Representa un movimiento asociado a una tarjeta de crédito.
 *
 */
@Entity
public class MovimientoTarjetaCredito {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@ManyToOne
private TarjetaCredito tarjeta;
private double importe;
private String concepto;
private boolean liquidado;
public MovimientoTarjetaCredito() {
}
public MovimientoTarjetaCredito(final TarjetaCredito tarjeta1,
final double importe1, final String concepto1) {
this.importe = importe1;
this.concepto = concepto1;
this.tarjeta = tarjeta1;
}
public Long getId() {
return id;
}
public void setId(final Long id1) {
this.id = id1;
}
public TarjetaCredito getTarjeta() {
return tarjeta;
}
public void setTarjeta(final TarjetaCredito tarjeta1) {
this.tarjeta = tarjeta1;
}
public double getImporte() {
return importe;
}
public void setImporte(final double importe1) {
this.importe = importe1;
}
public String getConcepto() {
return concepto;
}
public void setConcepto(final String concepto1) {
this.concepto = concepto1;
}
public boolean isLiquidado() {
return liquidado;
}
public void setLiquidado(final boolean liquidado1) {
this.liquidado = liquidado1;
}
}
