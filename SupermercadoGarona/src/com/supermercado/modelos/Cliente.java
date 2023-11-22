package com.supermercado.modelos;

public class Cliente {
	private int cantProducts;
	private float costeProducts;
	private int numeroCaja;

	public Cliente() {}
	
	public Cliente(int cantProducts, float costeProducts, int numeroCaja) {
		this.setCantProducts(cantProducts);
		this.setCosteProducts(costeProducts);
		this.setNumeroCaja(numeroCaja);
	}

	// toString
	@Override
	public String toString() {
		return "Productos comprados = " + this.cantProducts + ", coste total = " + this.costeProducts
				+ ", caja donde paga = " + this.numeroCaja;
	}

	// Getters y Setters
	public int getCantProducts() {
		return cantProducts;
	}

	public void setCantProducts(int cantProducts) {
		this.cantProducts = cantProducts;
	}

	public float getCosteProducts() {
		return costeProducts;
	}

	public void setCosteProducts(float costeProducts) {
		this.costeProducts = costeProducts;
	}

	public int getNumeroCaja() {
		return numeroCaja;
	}

	public void setNumeroCaja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
	}
}
