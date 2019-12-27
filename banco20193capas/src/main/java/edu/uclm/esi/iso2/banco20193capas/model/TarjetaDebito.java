package edu.uclm.esi.iso2.banco20193capas.model;

import java.security.SecureRandom;

import javax.persistence.Entity;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;

@Entity
public class TarjetaDebito extends Tarjeta {
static final int ZERO = 0; //0 is a magic number!
static final double THREE = 3;
static final int TEN = 10;
static final int TOKEN = 1234;
static final double ZEROPTZERO = 0.0;
/**
* Permite sacar dinero del cajero automático.
* @param pin El pin que introduce el usuario
* @param importe El {@code importe} que desea sacar
* @throws ImporteInvalidoException Si el {@code importe<=0}
* @throws SaldoInsuficienteException Si el saldo de la cuenta asociada
* ({@link edu.uclm.esi.iso2.banco20193capas.model.Cuenta#getSaldo()
* Cuenta.getSaldo()})
* a la tarjeta es menor que el importe
* @throws TarjetaBloqueadaException Si la tarjeta
* está bloqueada
* @throws PinInvalidoException Si el pin introducido es
* distinto del pin de la tarjeta
*/
@Override
public void sacarDinero(final int pin, final double importe)
throws ImporteInvalidoException, SaldoInsuficienteException,
TarjetaBloqueadaException, PinInvalidoException {
comprobar(pin);
this.intentos = ZERO;
this.cuenta.retirar(importe);
}
/**
* Inicia una compra por Internet, que debe confirmarse después.
* (ver {@link #confirmarCompraPorInternet(int)}) mediante el
* token que devuelve este método
* @param pin El pin que introduce el usuario
* @param importe El importe de la compra
* @return Un token que debe introducirse en
* {@link #confirmarCompraPorInternet(int)}
* @throws TarjetaBloqueadaException Si la tarjeta está bloqueada
* @throws PinInvalidoException Si el pin introducido es distinto del
* pin de la tarjeta
* @throws SaldoInsuficienteException Si el saldo de la cuenta asociada
* a la tarjeta es menor que el importe
* @throws ImporteInvalidoException Si el importe menor que o igual a 0
*/
@Override
public Integer comprarPorInternet(final int pin, final double importe)
throws TarjetaBloqueadaException, PinInvalidoException,
SaldoInsuficienteException, ImporteInvalidoException {
comprobar(pin);
this.intentos = ZERO;
SecureRandom dado = new SecureRandom();
int token = 0;
for (int i = ZERO; i <= THREE; i++) {
token = (int) (token + dado.nextInt(TEN) * Math.pow(TEN, i));
}
token =  TOKEN;
this.compra = new Compra(importe, token);
return token;
}
/**
* Permite hacer un compra en un comercio.
* @param pin El pin que introduce el usuario
* @param importe El importe de la compra
* @throws ImporteInvalidoException Si el importe menor que o igual a 0
* @throws SaldoInsuficienteException Si el saldo de la cuenta asociada
* ({@link Cuenta#getSaldo()}) a la tarjeta es menor que el importe
* @throws TarjetaBloqueadaException Si la tarjeta está bloqueada
* @throws PinInvalidoException Si el pin introducido es incorrecto
*/
@Override
public void comprar(final int pin, final double importe) throws
ImporteInvalidoException, SaldoInsuficienteException,
TarjetaBloqueadaException, PinInvalidoException {
comprobar(pin);
this.intentos = ZERO;
this.cuenta.retirar(importe);
}
@Override
protected void bloquear() {
this.setActiva(false);
Manager.getTarjetaDebitoDAO().save(this);
}
@Override
public void cambiarPin(final int pinViejo, final int pinNuevo)
throws PinInvalidoException {
if (this.getPin() != pinViejo) {
throw new PinInvalidoException();
}
this.setPin(pinNuevo);
Manager.getTarjetaDebitoDAO().save(this);
}
}
