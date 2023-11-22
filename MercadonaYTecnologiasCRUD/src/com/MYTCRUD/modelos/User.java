package com.MYTCRUD.modelos;

public class User {
	private int idusuario;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private int alta;
	private int baja;

	public User() {}

	public User(int idusuario, String nombre, String apellido1, String apellido2, String dni, int alta, int baja) {
		this.idusuario = idusuario;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.alta = alta;
		this.baja = baja;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public int getAlta() {
		return alta;
	}

	public void setAlta(int alta) {
		this.alta = alta;
	}

	public int getBaja() {
		return baja;
	}

	public void setBaja(int baja) {
		this.baja = baja;
	}

	public String toString() {
		return "" + idusuario + " " + nombre + " " + apellido1 + " " + apellido2 + " " + dni;
	}
}