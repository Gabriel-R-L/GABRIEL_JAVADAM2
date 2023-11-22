package com.MYTCRUD.controladores;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XML {

	public XML() {
	}

	public static void crearXML() {
		String query = "";
		String id = "", fecha = "";
		double beneficioCaja1 = 0, beneficioCaja2 = 0, beneficioCaja3 = 0, beneficioCaja4 = 0, totalDineroCajas = 0;
		int clientesTotales = 0;

		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		String archivoXML = "C:/tempJava_GABRIEL/garona.xml";
		FileWriter fw = null;
		PrintWriter pw = null;

		try {
			fw = new FileWriter(archivoXML);
		} catch (IOException e) {
			e.printStackTrace();
		}

		pw = new PrintWriter(fw);

		pw.println("<?xml version=\"1.0\"?>");
		pw.println("<recaudaciones>");

		try {
			query = String.format("SELECT * FROM cajas_supermercado;");
			Connection conexion = bd.conectar();

			if (conexion != null) {
				if (hayDatos()) {
					sentencia = conexion.createStatement();
					resultado = sentencia.executeQuery(query);

					while (resultado.next()) {
						id = resultado.getString("id");
						fecha = resultado.getString("fecha");
						beneficioCaja1 = resultado.getDouble("beneficioCaja1");
						beneficioCaja2 = resultado.getDouble("beneficioCaja2");
						beneficioCaja3 = resultado.getDouble("beneficioCaja3");
						beneficioCaja4 = resultado.getDouble("beneficioCaja4");
						totalDineroCajas = resultado.getDouble("totalDineroCajas");
						clientesTotales = resultado.getInt("clientesTotales");

						pw.println("<recaudacion id=\"" + id + "\" fecha=\"" + fecha + "\">");
						pw.println("<caja1>" + beneficioCaja1 + "</caja1>");
						pw.println("<caja2>" + beneficioCaja2 + "</caja2>");
						pw.println("<caja3>" + beneficioCaja3 + "</caja3>");
						pw.println("<caja4>" + beneficioCaja4 + "</caja4>");
						pw.println("<total_dinero>" + totalDineroCajas + "</total_dinero>");
						pw.println("<clientes_totales>" + clientesTotales + "</clientes_totales>");
						pw.println("</recaudacion>");

						System.out.println(id + " " + fecha + " " + beneficioCaja1 + " " + beneficioCaja2);
					}

					pw.println("</recaudaciones>");
					System.err.println("\nXML generado con éxito.\n");
				} else {
					System.err.println("\nNo hay ningún registro para buscar");
					return;
				}

			} else {
				System.err.println("Ha habido un problema al conectar con la base de datos");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		if (sentencia != null) {
			try {
				try {
					pw.close();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				sentencia.close();
				bd.conexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void leerXML() {
		try {
			// Se crea un SAXBuilder para poder pasrsear el archivo
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File("C:/tempJava_GABRIEL/garona.xml");

			// Se crea el documento a través del archivo
			Document document = (Document) builder.build(xmlFile);

			// Se obtiene la raíz recaudaciones
			Element rootNode = document.getRootElement();

			// Se obtiene la lista de los hijos de la raiz
			List<Element> list = rootNode.getChildren("recaudacion");

			System.out.println("""
					FECHA\t\tCAJA 1  CAJA 2  CAJA 3  CAJA 4   TOTAL   CLIENTES
					                                               RECAUDADO  TOTALES
					===================================================================================
					""");

			for (int i = 0; i < list.size(); i++) {
				// Se obtiene el elemento de la tabla recaudacion
				Element tabla = (Element) list.get(i);

				// Se obtiene la lista de hijos del tag tabla
				String fecha = tabla.getAttributeValue("fecha");
				String caja1 = tabla.getChildText("caja1");
				String caja2 = tabla.getChildText("caja2");
				String caja3 = tabla.getChildText("caja3");
				String caja4 = tabla.getChildText("caja4");
				String totalDineroCajas = tabla.getChildText("total_dinero");
				String clientesTotales = tabla.getChildText("clientes_totales");

				System.out.println(String.format("""
						%s\t%s\t%s\t%s\t%s\t %s\t    %s
						""", fecha, caja1, caja2, caja3, caja4, totalDineroCajas, clientesTotales));
			}
		} catch (IOException io) {
			System.err.println(io.getMessage());
		} catch (JDOMException jdonex) {
			System.out.println(jdonex.getMessage());
		}
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
}
