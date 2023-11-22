package com.MYTCRUD.controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

import com.MYTCRUD.modelos.Supermercado;
import com.MYTCRUD.vistas.Menu;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QR {
	/**
	 * procedimiento para general el código QR con los datos del usuario
	 * 
	 * @param r14
	 * @throws SQLException
	 */

	private static String PATHNAME = "C:/tempJava_GABRIEL/";

	public QR() {
	}

	public static void escribirFecha(Scanner t, Supermercado rs) {
		boolean entradaValida = false;
		String fecha = "";

		if (hayDatos()) {
			do {
				try {
					System.out.println("\nEscribe la fecha para buscar (Ejemplo: 2023-10-09)");
					System.out.print("> ");
					fecha = t.next();

					if (!entradaValida) {
						try {
							LocalDate fechaFormat = LocalDate.parse(fecha);
							rs.setFecha(fechaFormat);
							generarQR(rs);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida);
		} else {
			System.err.println("\nNo hay datos para generar un QR");
		}
	}

	@SuppressWarnings({ "unused", "resource" })
	public static void generarQR(Supermercado s) throws SQLException {
		Scanner t = new Scanner(System.in);
		String query = "";
		String fecha = "";
		double totalDineroCajas = 0;
		int clientesTotales = 0;

		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		boolean dateExists = false; // date exists

		try {
			query = String.format("SELECT * FROM cajas_supermercado WHERE fecha= '%s';", s.getFecha());
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				while (resultado.next()) {
					fecha = resultado.getString("fecha");
					totalDineroCajas = resultado.getDouble("totalDineroCajas");
					clientesTotales = resultado.getInt("clientesTotales");

					if (new QR().existeFecha(fecha)) {
						String textToQr = "Fecha: " + fecha + "\nTotal recaudado: " + totalDineroCajas
								+ "\nClientes totales: " + clientesTotales;
						dateExists = true;
						try {
							writeQR(textToQr, fecha);
							System.err.println("\nCódigo QR generado con éxito\n");
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

				if (!dateExists) {
					System.err.println("\nFormato incorrecto o no hay ningún registro con esa fecha\n");

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (sentencia != null) {
						try {
							sentencia.close();
							bd.conexion.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}

					Menu m = new Menu();
					m.menu();
				}
			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean mostrarFechas() {
		String query = "";
		String fecha = "";

		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = "SELECT fecha FROM cajas_supermercado;";
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				System.out.println("Fechas disponibles:");
				while (resultado.next()) {
					fecha = resultado.getString("fecha");

					System.out.println(fecha);
				}
				return true;
			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (sentencia != null) {
			try {
				sentencia.close();
				bd.conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean existeFecha(String fechaComprobar) {
		String query = "";
		String fecha = "";
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = String.format("SELECT fecha FROM cajas_supermercado where fecha='%s';", fechaComprobar);
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				while (resultado.next()) {
					fecha = resultado.getString("fecha");

					if (fecha.equalsIgnoreCase(fechaComprobar)) {
						if (sentencia != null) {
							try {
								sentencia.close();
								bd.conexion.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						return true;
					}
				}
			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (sentencia != null) {
			try {
				sentencia.close();
				bd.conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean hayDatos() {
		String query = "";
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = String.format("SELECT COUNT(*) as cantidad_registros FROM cajas_supermercado;");
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				if (resultado.next()) {
					int cantidadRegistros = resultado.getInt("cantidad_registros");
					if (cantidadRegistros >= 1) {
						return true;
					}
				}
			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (sentencia != null) {
			try {
				sentencia.close();
				bd.conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Procedimiento para escribir el QR con los datos y tamaño
	 * 
	 * @param text
	 * @param pathname
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void writeQR(String text, String fecha) throws WriterException, IOException {
		int width = 600;
		int height = 400;
		String imageFormat = "png"; // "jpeg" "gif" "tiff"
		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		FileOutputStream outputStream = new FileOutputStream(new File(PATHNAME + fecha + ".png"));
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStream);
	}
}
