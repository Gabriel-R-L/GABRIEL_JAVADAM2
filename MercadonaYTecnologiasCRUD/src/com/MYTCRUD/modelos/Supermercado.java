package com.MYTCRUD.modelos;

import java.time.LocalDate;

public class Supermercado {
	private LocalDate fecha;
	private String totalDineroCajas;
	private String clientesTotales;

	public Supermercado() {}

	public Supermercado(LocalDate fecha, String totalDineroCajas, String clientesTotales) {
		super();
		this.fecha = fecha;
		this.totalDineroCajas = totalDineroCajas;
		this.clientesTotales = clientesTotales;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getTotalDineroCajas() {
		return totalDineroCajas;
	}

	public void setTotalDineroCajas(String totalDineroCajas) {
		this.totalDineroCajas = totalDineroCajas;
	}

	public String getClientesTotales() {
		return clientesTotales;
	}

	public void setClientesTotales(String clientesTotales) {
		this.clientesTotales = clientesTotales;
	}

}
