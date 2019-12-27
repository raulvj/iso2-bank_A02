package edu.uclm.esi.iso2.banco20193capas.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.uclm.esi.iso2.banco20193capas.dao.ClienteDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.CuentaDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.MovimientoCuentaDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.MovimientoTarjetaCreditoDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.TarjetaCreditoDAO;
import edu.uclm.esi.iso2.banco20193capas.dao.TarjetaDebitoDAO;

/**
 * El Manager da acceso a las clases DAO asociadas a las clases de dominio.
 **/
@Component
public final class Manager { //Le he metido un final, por "FinalClass" error
private static CuentaDAO cuentaDAO;
private static MovimientoCuentaDAO movimientoDAO;
private static MovimientoTarjetaCreditoDAO movimientoTarjetaCreditoDAO;
private static ClienteDAO clienteDAO;
private static TarjetaDebitoDAO tarjetaDebitoDAO;
private static TarjetaCreditoDAO tarjetaCreditoDAO;
private Manager() { }
/**
 * @param cuentaDao1 CuentaDAO cuentaDao
 * @param movimientoDao1 MovimientoCuentaDAO movimientoDao
 * @param clienteDAO1 ClienteDAO clienteDAO
 * @param movimientoTCDAO1 MovimientoTarjetaCreditoDAO movimientoTCDAO
 * @param tarjetaDebitoDAO1 TarjetaDebitoDAO tarjetaDebitoDAO
 * @param tarjetaCreditoDAO1 TarjetaCreditoDAO tarjetaCreditoDAO
 */
@Autowired
private void loadDAO(final CuentaDAO cuentaDao1,
final MovimientoCuentaDAO movimientoDao1,
final ClienteDAO clienteDAO1, final MovimientoTarjetaCreditoDAO
movimientoTCDAO1, final TarjetaDebitoDAO tarjetaDebitoDAO1,
final TarjetaCreditoDAO tarjetaCreditoDAO1) {
Manager.setCuentaDAO(cuentaDao1);
Manager.setMovimientoDAO(movimientoDao1);
Manager.setClienteDAO(clienteDAO1);
Manager.setMovimientoTarjetaCreditoDAO(movimientoTCDAO1);
Manager.setTarjetaCreditoDAO(tarjetaCreditoDAO1);
Manager.setTarjetaDebitoDAO(tarjetaDebitoDAO1);
}
public static CuentaDAO getCuentaDAO() {
return cuentaDAO;
}
public static MovimientoCuentaDAO getMovimientoDAO() {
return movimientoDAO;
}
public static ClienteDAO getClienteDAO() {
return clienteDAO;
}
public static MovimientoTarjetaCreditoDAO getMovimientoTarjetaCreditoDAO() {
return movimientoTarjetaCreditoDAO;
}
public static TarjetaDebitoDAO getTarjetaDebitoDAO() {
return tarjetaDebitoDAO;
}
public static TarjetaCreditoDAO getTarjetaCreditoDAO() {
return tarjetaCreditoDAO;
}
/**
 * @param cuentaDAO1 parameter given
 */
public static synchronized void setCuentaDAO(
final CuentaDAO cuentaDAO1) {
Manager.cuentaDAO = cuentaDAO1;
}

/**
 * @param movimientoDAO1 parameter given
 */
public static synchronized void setMovimientoDAO(
final MovimientoCuentaDAO movimientoDAO1) {
Manager.movimientoDAO = movimientoDAO1;
}

/**
 * @param movimientoTarjetaCreditoDAO1 parameter given
 */
public static synchronized void setMovimientoTarjetaCreditoDAO(
final MovimientoTarjetaCreditoDAO movimientoTarjetaCreditoDAO1) {
Manager.movimientoTarjetaCreditoDAO = movimientoTarjetaCreditoDAO1;
}

/**
 * @param clienteDAO1 parameter given
 */
public static synchronized void setClienteDAO(
final ClienteDAO clienteDAO1) {
Manager.clienteDAO = clienteDAO1;
}

/**
 * @param tarjetaDebitoDAO1 parameter given
 */
public static synchronized void setTarjetaDebitoDAO(
final TarjetaDebitoDAO tarjetaDebitoDAO1) {
Manager.tarjetaDebitoDAO = tarjetaDebitoDAO1;
}

/**
 * @param tarjetaCreditoDAO1 parameter given
 */
public static synchronized void setTarjetaCreditoDAO(
final TarjetaCreditoDAO tarjetaCreditoDAO1) {
Manager.tarjetaCreditoDAO = tarjetaCreditoDAO1;
}
}
