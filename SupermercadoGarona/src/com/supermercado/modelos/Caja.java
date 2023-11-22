package com.supermercado.modelos;

public class Caja {
	private int cantClientes;
	private float totalBeneficios;
	
	public Caja() {}

	public Caja(int cantClientes, float totalBeneficios) {
		this.setCantClientes(cantClientes);
		this.setTotalBeneficios(totalBeneficios);
	}

	// toString
	@Override
	public String toString() {
		return "cantClientes = " + this.cantClientes + ", totalBeneficios = " + this.totalBeneficios;
	}

	// Getters y Setters
	public int getCantClientes() {
		return cantClientes;
	}

	public void setCantClientes(int cantClientes) {
		this.cantClientes = cantClientes;
	}

	public float getTotalBeneficios() {
		return totalBeneficios;
	}

	public void setTotalBeneficios(float totalBeneficios) {
		this.totalBeneficios = totalBeneficios;
	}
}
