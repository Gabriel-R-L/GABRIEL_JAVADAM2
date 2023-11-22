package com.MYTCRUD.controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.MYTCRUD.modelos.User;

import java.sql.SQLException;

public class UserCRUD {
	public User usuario;
	String query;

	public UserCRUD(User usuario) {
		this.usuario = usuario;
		this.query = "";
	}

	public static boolean hayDatos() {
		String query = "";
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;

		try {
			query = String.format("SELECT COUNT(*) as usuarios_totales FROM usuarios;");
			Connection conexion = bd.conectar();

			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				if (resultado.next()) {
					int cantidadRegistros = resultado.getInt("usuarios_totales");
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

	public void datoBuscar(Scanner t, int opcion) {
		boolean entradaValida = false;

		if (hayDatos()) {
			do {
				try {
					if (opcion == 4)
						buscarDNI(false); // para que muestre los dados de baja
					else
						buscarDNI(true);
					System.out.print("Escribe el dni a buscar: ");
					String dni = t.next();
					usuario.setDni(dni);

					switch (opcion) {
					case 1:
						buscar(usuario, true);
						break;
					case 2:
						editar(usuario, t);
						break;
					case 3:
						eliminar(usuario, t);
						break;
					case 4:
						recuperar(usuario, true, t);
						break;
					default:
						break;
					}
					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida);
		} else {
			System.err.println("\nNo hay datos o no hay Base de Datos");
		}
	}

	public void datosAgregar(Scanner t) {
		boolean entradaValida = false;
		if (hayDatos()) {
			do {
				try {
					System.out.print("Escribe un nombre: ");
					String nombre = t.next();
					System.out.print("Escribe su primer apellido: ");
					String ap1 = t.next();
					System.out.print("Escribe su segundo apellido: ");
					String ap2 = t.next();
					System.out.print("Escribe un dni: ");
					String dni = t.next();

					usuario.setNombre(nombre);
					usuario.setApellido1(ap1);
					usuario.setApellido2(ap2);
					usuario.setDni(dni);
					agregar(usuario);

					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida);
		} else {
			System.err.println("\nNo hay datos o no hay Base de Datos");
		}
	}

	// Buscar
	public void buscar(User usuario, boolean condicion) {
		if (hayDatos()) {
			DataBase bd = new DataBase();
			ResultSet resultado = null;
			Statement sentencia = null;
			try {
				// Operador ternario para elegir entre una búsqueda global o concreta
				query = (!condicion) ? "SELECT * FROM usuarios WHERE alta = 1;"
						: String.format("SELECT * FROM usuarios WHERE dni= '%s' AND alta = 1;", usuario.getDni());

				Connection conexion = bd.conectar();
				if (conexion != null) {
					sentencia = conexion.createStatement();
					resultado = sentencia.executeQuery(query);

					System.out.println("ID - NOMBRE -   APELLIDO(S)   - DNI");

					while (resultado.next()) {
						int idusuario = resultado.getInt("idusuario");
						String nombre = resultado.getString("nombre");
						String apellido1 = resultado.getString("apellido1");
						String apellido2 = resultado.getString("apellido2");
						String dni = resultado.getString("dni");

						usuario.setIdusuario(idusuario);
						usuario.setNombre(nombre);
						usuario.setApellido1(apellido1);
						usuario.setApellido2(apellido2);
						usuario.setDni(dni);

						System.out.println(usuario.toString());
					}
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
		} else {
			System.err.println("\nNo hay datos o no hay Base de Datos");
		}
	}

	// Buscar dni para mostrar
	public void buscarDNI(boolean opcion) {
		DataBase bd = new DataBase();
		ResultSet resultado = null;
		Statement sentencia = null;
		try {
			// Operador ternario para elegir entre una búsqueda de dados de alta o baja
			query = (opcion) ? "SELECT dni FROM usuarios WHERE alta = 1;" : "SELECT dni FROM usuarios WHERE baja = 1;";

			Connection conexion = bd.conectar();
			if (conexion != null) {
				sentencia = conexion.createStatement();
				resultado = sentencia.executeQuery(query);

				System.out.println("DNIs disponibles:");

				while (resultado.next()) {
					System.out.println(resultado.getString("dni"));
				}

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
	}

	// Insertar
	public void agregar(User usuario) {
		DataBase bd = new DataBase();
		Connection conexion = bd.conectar();
		Statement sentencia = null;

		if (conexion != null) {
			query = String.format(
					"INSERT INTO usuarios (nombre, apellido1, apellido2, dni) VALUES ('%s', '%s', '%s', '%s')",
					usuario.getNombre(), usuario.getApellido1(), usuario.getApellido2(), usuario.getDni());
			try {
				sentencia = conexion.createStatement();
				sentencia.execute(query);
				System.err.println("\nSe ha insertado el nuevo usuario con éxito.");
			} catch (SQLException e) {
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

		} else {
			System.err.println("Ha habido un problema al conectar con la base de datos");
		}
	}

	// Editar
	public void editar(User usuario, Scanner t) {
		DataBase bd = new DataBase();
		Connection conexion = bd.conectar();
		Statement sentencia = null;

		int datoModificar = 0;
		String nuevoDato = "";

		if (conexion != null) {
			boolean entradaValida = false;

			do {
				try {
					System.out
							.print("\nEscribe el dato a modificar\n1) Nombre\n2) Apellido1\n3) Apellido2\n4) DNI\n> ");
					datoModificar = t.nextInt();

					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida || (datoModificar < 1 || datoModificar > 4));

			System.out.print("Escribe su nuevo valor: ");
			nuevoDato = t.next();

			switch (datoModificar) {
			case 1:
				query = String.format("UPDATE usuarios SET nombre = '%s' WHERE dni = '%s';", nuevoDato,
						usuario.getDni());
				break;
			case 2:
				query = String.format("UPDATE usuarios SET apellido1 = '%s' WHERE dni = '%s';", nuevoDato,
						usuario.getDni());
				break;
			case 3:
				query = String.format("UPDATE usuarios SET apellido2 = '%s' WHERE dni = '%s';", nuevoDato,
						usuario.getDni());
				break;
			case 4:
				query = String.format("UPDATE usuarios SET dni = '%s' WHERE dni = '%s';", nuevoDato, usuario.getDni());
				break;
			default:
				break;
			}

			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(query);
				System.err.println("\nEl registro se actualizó exitosamente.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (sentencia != null) {
					try {
						sentencia.close();
						bd.conexion.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("Ha habido un problema al conectar con la base de datos");
		}
	}

	// Eliminar
	public void eliminar(User usuario, Scanner t) {
		DataBase bd = new DataBase();
		Connection conexion = bd.conectar();
		Statement sentencia = null;
		if (conexion != null) {
			query = String.format("UPDATE usuarios SET alta = 0, baja = 1 WHERE dni = '%s';", usuario.getDni());
			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(query);
				System.err.println("\nEl registro se eliminó exitósamente.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (sentencia != null) {
					try {
						sentencia.close();
						bd.conexion.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("Ha habido un problema al conectar con la base de datos");
		}
	}

	// Recuperar usuario
	public void recuperar(User usuario, boolean condicion, Scanner t) {
		DataBase bd = new DataBase();
		Connection conexion = bd.conectar();
		Statement sentencia = null;

		if (conexion != null) {
			try {
				// Operador ternario para elegir entre una actualización global o concreta
				query = (!condicion) ? "UPDATE usuarios SET alta = 1, baja = 0 WHERE baja = 1;"
						: String.format("UPDATE usuarios SET alta = 1, baja = 0 WHERE dni = '%s';", usuario.getDni());

				sentencia = conexion.createStatement();
				sentencia.executeUpdate(query);
				System.err.println("El registro se recuperó exitosamente.");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (sentencia != null) {
					try {
						sentencia.close();
						bd.conexion.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("Ha habido un problema al conectar con la base de datos");
		}
	}
}