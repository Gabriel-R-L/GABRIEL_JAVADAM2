package com.supermercado.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.supermercado.vistas.Supermercado;

// paso 1 en build path del proyecto y configure build path
// incluir el driver javacsv.jar
// ojo dentro de pestaña Libraries y en classpath no en modulepath

// paso 2 usar la nueva librería importando
//import com.csvreader.CsvReader; 
//import com.csvreader.CsvWriter;

public class CSV {

	private static String PATH_FICHERO = "C:/tempJava_GABRIEL/garona.csv";

	public void crearCSV(Supermercado supermercado) {
		boolean existe = new File(PATH_FICHERO).exists();

		try {
			CsvWriter t2 = new CsvWriter(new FileWriter(PATH_FICHERO, true), ',');

			if (!existe) {
				t2.write("fecha");
				t2.write("beneficioCaja1");
				t2.write("beneficioCaja2");
				t2.write("beneficioCaja3");
				t2.write("beneficioCaja4");
				t2.write("totalDineroCajas");
				t2.write("clientesTotales");
				t2.endRecord();
			}

			// Si es false => no hay fecha repetida
			if (!existeFecha(supermercado.getFecha())) {
				t2.write("" + supermercado.getFecha());
				t2.write("" + supermercado.getaCajas()[0].getTotalBeneficios());
				t2.write("" + supermercado.getaCajas()[1].getTotalBeneficios());
				t2.write("" + supermercado.getaCajas()[2].getTotalBeneficios());
				t2.write("" + supermercado.getaCajas()[3].getTotalBeneficios());
				t2.write("" + supermercado.getDineroTotalCajas());
				t2.write("" + supermercado.getTotalClientes());
				t2.endRecord();
				t2.close();

				System.err.println("\nCSV generado\n");
			} else {
				System.err.println("\nYa existe un registro con esa fecha. No se guardará en la Base de Datos\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Buscar si existe la fecha
	public boolean existeFecha(String fechaGuardar) {
		String fechaGuardada = "";

		try {
			boolean existe = new File(PATH_FICHERO).exists();
			if (!existe) {
				System.err.println("El archivo no existe");
				return false; // Si no existe el archivo, que de false
			}

			CsvReader t1 = new CsvReader(PATH_FICHERO);

			try {
				t1.readHeaders();

				while (t1.readRecord()) {
					fechaGuardada = t1.get("fecha");
					if (fechaGuardada.equalsIgnoreCase(fechaGuardar))
						return true; // Si ya existe la fecha, que de true
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return false; // Si no encuentra la fecha, que de false
	}
}
