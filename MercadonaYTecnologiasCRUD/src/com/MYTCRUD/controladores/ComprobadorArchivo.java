package com.MYTCRUD.controladores;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//comprueba que el archivo exista en la carpeta que le indico, y las que tenga dentro
public class ComprobadorArchivo {
	public static boolean comprobarArchivo(String _PATHNAME, String nombreArchivo) {
		Path directorioPath = Paths.get(_PATHNAME);

		// Buscar coincidencia en el nombre del PNG
		try {
			return Files.walk(directorioPath, FileVisitOption.FOLLOW_LINKS).filter(Files::isRegularFile)
					.anyMatch(path -> path.getFileName().toString().toLowerCase().equalsIgnoreCase(nombreArchivo));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}