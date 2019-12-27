package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cliente {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
@Column(unique = true)
private String nif; //Was protected, now private. (more errors?)
private String nombre;
private String apellidos;
public Cliente() {
}
/**
* Crea un cliente.
* @param nif1 NIF del cliente
* @param nombre1 Nombre del cliente
* @param apellidos1 Apellidos del cliente
*/
public Cliente(final String nif1, final String nombre1,
final String apellidos1) {
this.nif = nif1; //si no le pones el 1 (nif1 hides a field)
this.nombre = nombre1;
this.apellidos = apellidos1;
}
public Long getId() {
return id;
}
public void setId(final Long id2) {
this.id = id2;
}
public String getNif() {
return nif;
}
public void setNif(final String nif2) {
this.nif = nif2;
}
public String getNombre() {
return nombre;
}
public void setNombre(final String nombre2) {
this.nombre = nombre2;
}
public String getApellidos() {
return apellidos;
}
public void setApellidos(final String apellidos2) {
this.apellidos = apellidos2;
}
/**
* Inserta un cliente en la base de datos.
*/
public void insert() {
Manager.getClienteDAO().save(this);
}
}
