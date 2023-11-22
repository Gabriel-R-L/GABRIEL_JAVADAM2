package com.supermercado.vistas;

import com.supermercado.controladores.CalculadoraController;
import com.supermercado.modelos.Caja;
import com.supermercado.modelos.Cliente;

public class Supermercado extends Thread {
	CalculadoraController calcSupermercado = new CalculadoraController(); // me permitirá calcular los datos
																				// necesarios
	// Variables que luego se guardarán en el CSV
	private Caja aCajas[];
	private Cliente[] aClientes;
	private String fecha;
	private int totalClientes;
	private float dineroTotalCajas;

	public Supermercado() {
		setaCajas(new Caja[4]); // cuantas cajas hay en el supermercado
		setaClientes(new Cliente[calcSupermercado.generarCantClients()]);
	}

	@Override
	public void run() {
		fecha = calcSupermercado.generarFecha();
		totalClientes = aClientes.length;

		int numeroClientePremiado = calcSupermercado.generarClientePremiado(aClientes); // cliente ganador para no pagar

		System.out.println("****************************************");
		System.out.println("* GARONA SUPERMERCADO ABRE SUS PUERTAS *");
		System.out.println("*   Son las 9:00 a.m. -  " + getFecha() + "    *");
		System.out.println("****************************************");

		calcSupermercado.pauseMenu();

		System.out.println("\nEl numero de clientes el dia de hoy es de " + getTotalClientes());
		System.out.println("El numero de cajas abiertas son " + getaCajas().length + "\n");
		System.out.println("El cliente " + (numeroClientePremiado + 1) + " no pagará nada por su compra\n");

		calcSupermercado.pauseMenu();

		calcSupermercado.generarClientes(aClientes, numeroClientePremiado);

		calcSupermercado.almacenarDatosCaja(aClientes, aCajas);

//		DESCOMENTAR PARA VER LOS CLIENTES GENERADOS!!!!!!!!!!!!!!!!!
//		for (int i = 0; i < aClientes.length; i++) {
//			if (i == numeroClientePremiado)
//				System.err.println("GANADOR: " + aClientes[i].toString());
//			else
//				System.out.println(aClientes[i].toString());
//		}
//		System.out.println();

		for (int i = 0; i < aCajas.length; i++) {
			System.out.println("Caja " + (i + 1) + " ventas: $" + aCajas[i].getTotalBeneficios());
		}

		dineroTotalCajas = calcSupermercado.dineroTotalCajas(aCajas);
		System.out.println(
				"\n-- El dinero total recaudado por las cajas el dia de hoy es de $" + dineroTotalCajas + " --\n");

		for (int i = 0; i < aCajas.length; i++) {
			System.out.println("Clientes atendidos en la caja " + (i + 1) + ": " + aCajas[i].getCantClientes());
		}
		System.out.println("\nSe han cerrado todas las cajas");

	}

	// Getters y Setters
	public CalculadoraController getCalcSupermercado() {
		return calcSupermercado;
	}

	public void setCalcSupermercado(CalculadoraController calcSupermercado) {
		this.calcSupermercado = calcSupermercado;
	}

	public Caja[] getaCajas() {
		return aCajas;
	}

	public void setaCajas(Caja[] aCajas) {
		this.aCajas = aCajas;
	}

	public Cliente[] getaClientes() {
		return aClientes;
	}

	public void setaClientes(Cliente[] aClientes) {
		this.aClientes = aClientes;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(int totalClientes) {
		this.totalClientes = totalClientes;
	}

	public float getDineroTotalCajas() {
		return dineroTotalCajas;
	}

	public void setDineroTotalCajas(float dineroTotalCajas) {
		this.dineroTotalCajas = dineroTotalCajas;
	}
}
