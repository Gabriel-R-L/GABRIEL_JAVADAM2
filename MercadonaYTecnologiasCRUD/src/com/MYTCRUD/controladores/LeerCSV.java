package com.MYTCRUD.controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.csvreader.CsvReader;

public class LeerCSV {
	public static void leerCSV() {
		String query = "";
		String fichero = "C:/tempJava_GABRIEL/garona.csv";
		DataBase bd = new DataBase();
		Connection conexion = bd.conectar();
		Statement sentencia = null;

		if (conexion != null) {
			int rowExists = 0; // row exists (¿existe la fila con la fecha repetida?)

			try {
				try {
					boolean existe = new File(fichero).exists();
					if (!existe) {
						System.err.println("El archivo no existe");
						return;
					}

					CsvReader t1 = new CsvReader(fichero);
					sentencia = conexion.createStatement();

					try {
						t1.readHeaders();

						System.out.println("Lectura CSV:");
						while (t1.readRecord()) {
							String fecha = t1.get("fecha");
							String caja1 = t1.get("beneficioCaja1");
							String caja2 = t1.get("beneficioCaja2");
							String caja3 = t1.get("beneficioCaja3");
							String caja4 = t1.get("beneficioCaja4");
							String totalCajas = t1.get("totalDineroCajas");
							String numClientes = t1.get("clientesTotales");

//							DESCOMENTAR PARA VER EL SYSO CON TODOS LOS DATOS!!!!!!!!!!!!!!!!!!!!!							
//							System.out.println(String.format(
//									"Fecha: %s Caja 1: %s Caja 2: %s Caja 3: %s Caja 4: %s Dinero total: %s Clientes totales: %s\n",
//									fecha, caja1, caja2, caja3, caja4, totalCajas, numClientes));

							if (!new LeerCSV().existeFecha(fecha)) {
								// Leemos los datos del archivo que no existen
								System.out
										.println(String.format("Fecha: %s Caja 1: %s Caja 2: %s Caja 3: %s Caja 4: %s",
												fecha, caja1, caja2, caja3, caja4));

								query = String.format(
										"INSERT INTO cajas_supermercado (fecha, beneficioCaja1, beneficioCaja2, beneficioCaja3, beneficioCaja4, totalDineroCajas, clientesTotales) VALUES ('%s', %s, %s, %s, %s, %s, %s);",
										fecha, caja1, caja2, caja3, caja4, totalCajas, numClientes);

								sentencia.execute(query);
							} else {
								rowExists++;

								// Leemos los datos del archivo, que están insertados
								System.out.println(
										String.format("* Fecha: %s Caja 1: %s Caja 2: %s Caja 3: %s Caja 4: %s", fecha,
												caja1, caja2, caja3, caja4));
							}
						}
						if (rowExists != 0)
							System.err.println(
									"* Registros del CSV que no se insertaron en la base de datos porque ya existe un registro anterior.");
					} catch (IOException e) {
						System.out.println("Error en la lectura del CSV: " + e.getMessage());
					}
				} catch (FileNotFoundException e) {
					System.out.println("Error al encontrar el archivo: " + e.getMessage());
				}
			} catch (SQLException e) {
				System.out.println("Error en la conexión con la base de datos: " + e.getMessage());
			}

			if (sentencia != null) {
				try {
					sentencia.close();
					bd.conexion.close();
				} catch (SQLException e) {
					System.out.println("Error en la conexión con la base de datos: " + e.getMessage());
				}
			}
		} else {
			System.err.println("Ha habido un problema al conectar con la base de datos");
		}
	}

	private boolean existeFecha(String fechaGuardar) {
		String query = "";
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;
		try {
			query = "SELECT fecha FROM cajas_supermercado;";

			Connection conexion = bd.conectar();
			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				while (resultado.next()) {
					String fechaGuardada = resultado.getString("fecha");

					if (fechaGuardada.equalsIgnoreCase(fechaGuardar))
						return true;
				}
			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println("Error en la conexión con la base de datos: " + e.getMessage());
		} finally {
			if (sentencia != null) {
				try {
					sentencia.close();
					resultado.close();
					bd.conexion.close();
				} catch (SQLException e) {
					System.out.println("Error en la conexión con la base de datos: " + e.getMessage());
				}
			}
		}
		return false;
	}
}
