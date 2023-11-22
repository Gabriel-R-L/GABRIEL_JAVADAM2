package com.supermercado.controladores;

import java.io.File;

public class FicheroController {

	private static String RUTA_TEMP = "C:/tempJava_GABRIEL";

	public FicheroController() {
	}

	private boolean existeFichero() {
		try {
			File fileName = new File(RUTA_TEMP);
			boolean fileExists = fileName.isDirectory();
			return (fileExists) ? true : false;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
	}

	public static void crearFichero() {
		try {
			if (!new FicheroController().existeFichero())
				new File(RUTA_TEMP).mkdir();
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
