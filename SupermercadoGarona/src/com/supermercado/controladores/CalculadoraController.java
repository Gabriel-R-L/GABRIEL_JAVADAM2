package com.supermercado.controladores;

import java.time.LocalDate;

import com.supermercado.modelos.Caja;
import com.supermercado.modelos.Cliente;

public class CalculadoraController {

	public CalculadoraController() {
	}

	public String generarFecha() {
		return LocalDate.now().toString();
	}

	public void pauseMenu() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int generarCantClients() {
		return (int) ((Math.random() * (100 - 25 + 1)) + 25);
	}

	public int generarCantProducts() {
		return (int) ((Math.random() * (6 - 2 + 1)) + 2);
	}

	public float generarCosteProducts() {
		return (float) (Math.round((Math.random() * 10) * 100d) / 100d);
	}

	public int generarNumeroCaja() {
		return (int) ((Math.random() * 4) + 1);
	}

	public void almacenarDatosCaja(Cliente[] aClientes, Caja aCajas[]) {
		// Creo arrays en base a cuántas cajas tengo
		int clientesFilan[] = new int[aCajas.length];
		float dineroFilan[] = new float[aCajas.length];

		// for each
		for (Cliente cliente : aClientes) {
			int numeroCaja = cliente.getNumeroCaja();
			float gastoCliente = cliente.getCosteProducts();

//			Los números de caja comienzan desde 1, a si que resto 1 para obtener el índice de dicha caja
			clientesFilan[numeroCaja - 1]++;
			dineroFilan[numeroCaja - 1] += gastoCliente;
		}

		for (int i = 0; i < aCajas.length; i++) {
			float dineroRedondeado = (float) (Math.round(dineroFilan[i] * 100d) / 100d);
			aCajas[i] = new Caja(clientesFilan[i], dineroRedondeado);
		}

		pauseMenu();
	}

	public float dineroTotalCajas(Caja aCajas[]) {
		return (float) (Math.round((aCajas[0].getTotalBeneficios() + aCajas[1].getTotalBeneficios()
				+ aCajas[2].getTotalBeneficios() + aCajas[3].getTotalBeneficios()) * 100d) / 100d);
	}

	public void generarClientes(Cliente[] aClientes, int numeroClientePremiado) {
		for (int i = 0; i < aClientes.length; i++) {
			if (i == numeroClientePremiado)
				aClientes[i] = new Cliente(generarCantProducts(), 0, generarNumeroCaja());
			else
				aClientes[i] = new Cliente(generarCantProducts(), generarCosteProducts(), generarNumeroCaja());
		}
	}

	public int generarClientePremiado(Cliente[] aClientes) {
		return (int) ((Math.random() * aClientes.length - 1) + 1);
	}
}
