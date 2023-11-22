package com.MYTCRUD.controladores;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
	private final String URL = "jdbc:mysql://localhost:3307/";
	private final String BD = "BDMISCELANEADAM2?serverTimezone=UTC";
	private final String USER = "root";
	private final String PASSWORD = "Adivinala1.";

	public Connection conexion = null;

	@SuppressWarnings("finally")
	public Connection conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(URL + BD, USER, PASSWORD);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			try {
				if (conexion != null)
					return conexion;
				else
					return null;
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return null;
			}
		}
	}
}
