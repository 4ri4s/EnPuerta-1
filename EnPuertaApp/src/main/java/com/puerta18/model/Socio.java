package com.puerta18.model;

import java.sql.Date;

public class Socio {

	int	id;
	String nombre;
	String apellido;
	String email;
	String documento;
	Date nacimiento; 
	String celular; 
	String genero ; 
	String celular_tutor;
	String telefono; 
	String tutor; 
	String foto_personal;
	String facebook_url; 
	Boolean presente;
	
	public Socio(int id_socio, String nombre, String apellido, String email, String documento, Date nacimiento,
			String celular, String genero, String celular_tutor, String telefono, String tutor, String foto_personal,
			String facebook_url, Boolean presente) {
		super();
		this.id = id_socio;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.documento = documento;
		this.nacimiento = nacimiento;
		this.celular = celular;
		this.genero = genero;
		this.celular_tutor = celular_tutor;
		this.telefono = telefono;
		this.tutor = tutor;
		this.foto_personal = foto_personal;
		this.facebook_url = facebook_url;
		this.presente = presente;
	}

	public Socio(int id, String nombre, String apellido, String mail, String dni) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.documento = dni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getCelular_tutor() {
		return celular_tutor;
	}

	public void setCelular_tutor(String celular_tutor) {
		this.celular_tutor = celular_tutor;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	public String getFoto_personal() {
		return foto_personal;
	}

	public void setFoto_personal(String foto_personal) {
		this.foto_personal = foto_personal;
	}

	public String getFacebook_url() {
		return facebook_url;
	}

	public void setFacebook_url(String facebook_url) {
		this.facebook_url = facebook_url;
	}

	public Boolean getPresente() {
		return presente;
	}

	public void setPresente(Boolean presente) {
		this.presente = presente;
	}
	
	
}
