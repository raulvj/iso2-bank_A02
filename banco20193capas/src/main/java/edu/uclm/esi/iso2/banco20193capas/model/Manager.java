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
@Autowired
private void loadDAO(final CuentaDAO cuentaDao1,
final MovimientoCuentaDAO movimientoDao1,
final ClienteDAO clienteDAO1, final MovimientoTarjetaCreditoDAO
movimientoTCDAO1, final TarjetaDebitoDAO tarjetaDebitoDAO1,
final TarjetaCreditoDAO tarjetaCreditoDAO1) {
Manager.cuentaDAO = cuentaDao1;
Manager.movimientoDAO = movimientoDao1;
Manager.clienteDAO = clienteDAO1;
Manager.movimientoTarjetaCreditoDAO = movimientoTCDAO1;
Manager.tarjetaDebitoDAO = tarjetaDebitoDAO1;
Manager.tarjetaCreditoDAO = tarjetaCreditoDAO1;
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
}
