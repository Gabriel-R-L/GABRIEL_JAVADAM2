package com.supermercado.principal;

import com.supermercado.controladores.FicheroController;
import com.supermercado.vistas.SplashScreen;
import com.supermercado.controladores.CSV;
import com.supermercado.vistas.Supermercado;

public class Main extends Thread {
	public static void main(String[] args) {
		// Ventana de carga
		new SplashScreen();

		// Buscamos y creamos (si no existe) el directorio temporal
		FicheroController.crearFichero();

		// Inicializamos el objeto supermercado
		Supermercado supermercado = new Supermercado();
		supermercado.start();

		// Le hago un sleep para que le de tiempo al hilo a ejecutarse y no salga error
		// al insertar los datos en el csv
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Inicializamos el CSV para guardar los datos
		CSV csv = new CSV();
		csv.crearCSV(supermercado);
	}
}
