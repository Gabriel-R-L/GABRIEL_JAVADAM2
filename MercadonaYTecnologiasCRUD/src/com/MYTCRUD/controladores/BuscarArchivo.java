package com.MYTCRUD.controladores;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//muestra los archivos de la carpeta que le indico
public class BuscarArchivo {
	public static void comprobarArchivo(String _PATHNAME, String extension) {
		Path directorioPath = Paths.get(_PATHNAME);

		// Buscar nombres PNG
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directorioPath, ("*" + extension))) {
			for (Path path : stream) {					  // v quitar C:/tempJava_GABRIEL/
				System.out.println(path.toString().substring(20, path.toString().length() - 4)); // Busco SOLO la fecha (Ej: 2023-10-17)
																			  // ^ quitar el .png 														
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}