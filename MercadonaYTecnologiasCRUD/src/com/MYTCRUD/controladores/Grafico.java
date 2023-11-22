package com.MYTCRUD.controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.MYTCRUD.vistas.Menu;

public class Grafico {

	public Grafico() {
	}

	public static void veriFicacionSeguridad(Scanner t) {
		int o = 0;
		boolean entradaValida = false;

		do {
			try {
				System.err.println(
						"Advertencia. Si desea hacer una gráfica de pocos datos, podría darse algún error inesperado.\n"
								+ "Para evitar esto, por favor, guarde datos de un mes entero.");
				System.out.println("Escriba (1) para proseguir o escriba (2) para volver al menú");
				System.out.print("\n> ");
				o = t.nextInt();

				switch (o) {
				case 1:
					entradaValida = true;
					break;
				case 2:
					new Menu().menu();
					break;
				}

				entradaValida = true;
			} catch (Exception e) {
				t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
				System.out.println("Error en la escritura: " + e.getMessage());
			}
		} while (!entradaValida || (o < 1 || o > 2));

		if (new Grafico().mostrarFechas())
			Grafico.generarGrafico();
	}

	@SuppressWarnings("resource")
	public static void generarGrafico() {
		Scanner t = new Scanner(System.in);
		XYSeries series = new XYSeries("Venta mensual");
		Grafico g = new Grafico();

		ArrayList<Double> resultadoMensual = new ArrayList<Double>();
		int mes = 0;
		int anyo = 0;
		int totalDias = 0;

		boolean entradaValida = false;

		do {
			try {
				System.out.println("Introduce mes: ");
				mes = t.nextInt();
				System.out.println("Introduce año: ");
				anyo = t.nextInt();
				entradaValida = true;
			} catch (Exception e) {
				t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
				System.out.println("Error en la escritura: " + e.getMessage());
			}
		} while (!entradaValida);

		totalDias = g.diasMes(mes, anyo); // Saber los días del mes

		boolean ed = g.buscarDatosMesAnyo(mes, anyo, resultadoMensual); // exists date (¿He introducido una fecha
																		// correcta?)
//		totalDias = 5; // para prueba

		// Mientras no encuentre la fecha que haga el bucle
		while (!ed) {
			int o = 0;
			entradaValida = false;

			do {
				try {
					System.err.print("No se ha encontrado la fecha. Escriba otra fecha (1) o vuelva al menú (2)");
					System.out.print("\n> ");
					o = t.nextInt();

					switch (o) {
					case 1:
						System.out.println("Introduce mes: ");
						mes = t.nextInt();
						System.out.println("Introduce año: ");
						anyo = t.nextInt();

						ed = g.buscarDatosMesAnyo(mes, anyo, resultadoMensual);
						break;
					case 2:
					default:
						Menu m = new Menu();
						m.menu();
						break;
					}

					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida);
		}

		for (int i = 1; i <= totalDias; i++) {
			series.add(i, resultadoMensual.get(i - 1));
		}

		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart("Recaudación " + months[mes - 1] + " " + anyo, // Título
				"Dia", // Etiqueta coord X
				"Cantidad", // Etiqueta coord Y
				dataset, // Datos
				PlotOrientation.VERTICAL, true, // Muestra la leyenda de los productos
				false, false);

		// Mostrar gráfica en pantalla
		ChartFrame frame = new ChartFrame("Gráfica lineal", chart); // titulo del frame
		frame.pack();
		frame.setVisible(true);

	}

	public int diasMes(int mes, int anyo) {
		int dias = 0;
		switch (mes) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			dias = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			dias = 30;
			break;

		case 2:
			if ((anyo % 4 == 0) && ((anyo % 100 != 0) || (anyo % 400 == 0))) {
				dias = 29;
			} else {
				dias = 28;
			}
			break;
		default:
			System.out.println("Mes incorrecto");
		}
		return dias;
	}

	// Buscar
	public boolean buscarDatosMesAnyo(int mes, int anyo, ArrayList<Double> resultadoMensual) {
		String query = "";
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = String.format(
					"SELECT totalDineroCajas FROM cajas_supermercado WHERE MONTH(fecha) = '%d' AND YEAR(fecha) = '%d';",
					mes, anyo);
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);
				double dineroRecaudado = 0;

				// Buscar el total recaudado para guardar en ArrayList
				while (resultado.next()) {
					dineroRecaudado = resultado.getDouble("totalDineroCajas");
					if (resultado != null) // Si hay resultado, lo guardo
						resultadoMensual.add(dineroRecaudado);
				}

				// Si hay algo en el arraylist, le digo que he encontrado algo
				return (!resultadoMensual.isEmpty()) ? true : false;

			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (sentencia != null) {
				try {
					sentencia.close();
					resultado.close();
					bd.conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false; // Por defecto
	}

	public boolean mostrarFechas() {
		String query = "";
		String mes = "";
		String anyo = "";

		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = "SELECT DISTINCT MONTH(fecha) as 'mes_fecha', YEAR(fecha) as 'anyo_fecha' FROM cajas_supermercado;";
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				System.out.println("MESES | AÑOS ~ Disponibles");
				while (resultado.next()) {
					mes = resultado.getString("mes_fecha");
					anyo = resultado.getString("anyo_fecha");
					System.out.println(mes + "\t" + anyo);
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
}
