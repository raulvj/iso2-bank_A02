package edu.uclm.esi.iso2.banco20193capas.model;

import java.security.SecureRandom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TokenInvalidoException;

/**
* Representa una tarjeta bancaria, bien de débito o bien de crédito.
* Una {@code Tarjeta} está asociada a un {@code Cliente} y a
* una {@code Cuenta}.
*
*/
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tarjeta {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private Integer pin;
private Boolean activa;
protected Integer intentos;
@Transient
protected Compra compra;
@ManyToOne
protected Cliente titular;
@ManyToOne
protected Cuenta cuenta;
static final int ZERO = 0; //0.01 is a magic number!
static final double THREE = 3;
static final int TEN = 10;
static final double ZEROPTZERO = 0.0;
public Tarjeta() {
activa = true;
this.intentos = ZERO;
SecureRandom dado = new SecureRandom();
pin = ZERO;
for (int i = ZERO; i <= THREE; i++) {
pin = (int) (pin + dado.nextInt(TEN) * Math.pow(TEN, i));
}
}
protected void comprobar(final int pin1) throws TarjetaBloqueadaException,
PinInvalidoException {
if (!this.isActiva()) {
throw new TarjetaBloqueadaException();
}
if (this.pin != pin1) {
this.intentos++;
if (intentos == THREE) {
bloquear();
}
throw new PinInvalidoException();
}
}
/**
* Permite confirmar una compra que se ha iniciado por Internet. El método.
* {@link #comprarPorInternet(int, double)} devuelve un token que debe ser
* introducido en este método.
* @param token El token que introduce el usuario. Para que la
* compra se confirme,ha de coincidir con el token devuelto por
* {@link #comprarPorInternet(int, double)}
* @throws TokenInvalidoException Si el {@code token} introducido es
* distinto del recibido desde {@link #comprarPorInternet(int, double)}
* @throws ImporteInvalidoException Si el importe menor que o igual a 0
* @throws SaldoInsuficienteException Si el saldo de la cuenta asociada
* a la tarjeta (en el caso de {@link TarjetaDebito}) es menor
* que el importe, o si el crédito disponible en la tarjeta de crédito
* es menor que el importe
* @throws TarjetaBloqueadaException Si la tarjeta está bloqueada
* @throws PinInvalidoException Si el pin que se introdujo es inválido
*/
public void confirmarCompraPorInternet(final int token)
throws TokenInvalidoException, ImporteInvalidoException,
SaldoInsuficienteException, TarjetaBloqueadaException, PinInvalidoException {
if (token != this.compra.getToken()) {
this.compra = null;
throw new TokenInvalidoException();
}
this.comprar(this.pin, this.compra.getImporte());
}
protected abstract void bloquear();
public Long getId() {
return id;
}
public void setId(final Long id1) {
this.id = id1;
}
public Integer getPin() {
return pin;
}
public void setPin(final Integer pin1) {
this.pin = pin1;
}
public Cliente getTitular() {
return titular;
}
public void setTitular(final Cliente titular1) {
this.titular = titular1;
}
public Cuenta getCuenta() {
return cuenta;
}
public void setCuenta(final Cuenta cuenta1) {
this.cuenta = cuenta1;
}
/**
* @return true si la tarjeta está activa; false si está bloqueada.
*/
public Boolean isActiva() {
return activa;
}
public void setActiva(final Boolean activa1) {
this.activa = activa1;
}
public abstract void sacarDinero(int pin1, double importe) throws
ImporteInvalidoException, SaldoInsuficienteException,
TarjetaBloqueadaException, PinInvalidoException;
public abstract Integer comprarPorInternet(int pin1, double importe)
throws TarjetaBloqueadaException, PinInvalidoException,
SaldoInsuficienteException, ImporteInvalidoException;
public abstract void comprar(int pin1, double importe) throws
ImporteInvalidoException, SaldoInsuficienteException,
TarjetaBloqueadaException, PinInvalidoException;
/**
* Permite cambiar el pin de la tarjeta.
* @param pinViejo El pin actual
* @param pinNuevo El pin nuevo (el que desea establecer el usuario)
* @throws PinInvalidoException Si el {@code pinViejo} es incorrecto
*/
public abstract void cambiarPin(int pinViejo, int pinNuevo)
throws PinInvalidoException;
}
