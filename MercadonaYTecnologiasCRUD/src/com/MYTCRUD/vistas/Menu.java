package com.MYTCRUD.vistas;

import java.util.Scanner;

import com.MYTCRUD.controladores.ControladoraFichero;
import com.MYTCRUD.controladores.Email;
import com.MYTCRUD.controladores.Grafico;
import com.MYTCRUD.controladores.LeerCSV;
import com.MYTCRUD.controladores.PDF;
import com.MYTCRUD.controladores.QR;
import com.MYTCRUD.controladores.UserCRUD;
import com.MYTCRUD.controladores.XML;
import com.MYTCRUD.modelos.Supermercado;
import com.MYTCRUD.modelos.User;

public class Menu {
	private User usuario;

	public Menu() {
		this.usuario = new User();
	}

	public void Intro() {
		// Simular la carga
		try {
			// Miro si la carpeta tempJava existe, y la creo de forma automática si no
			ControladoraFichero.crearFichero();
			System.out.println("Cargando...");
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		menu();
	}

	public void pauseMenu() {
		try {
			Thread.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void menu() {
		Scanner t = new Scanner(System.in);
		UserCRUD execQuery = new UserCRUD(usuario);
		int opcion = 0;

		while (opcion != 15) {
			System.out.println("""
					\n
					╔════════════════════════════════════════════╗
					║ 1) Buscar usuario concreto                 ║
					║                                            ║
					║ 2) Listar a todos los usuarios             ║
					║                                            ║
					║ 3) Añadir un nuevo usuario                 ║
					║                                            ║
					║ 4) Editar un usuario                       ║
					║                                            ║
					║ 5) Eliminar un usuario                     ║
					║                                            ║
					║ 6) Recuperar un usuario eliminado          ║
					║                                            ║
					║ 7) Recuperar todos los usuarios eliminados ║
					║                                            ║
					║ 8) Leer CSV del supermercado               ║
					║                                            ║
					║ 9) Generar PDF del supermercado            ║
					║                                            ║
					║ 10) Generar QR de un día del supermercado  ║
					║                                            ║
					║ 11) Enviar QR por correo                   ║
					║                                            ║
					║ 12) Generar gráfico                        ║
					║                                            ║
					║ 13) Generar XML                            ║
					║                                            ║
					║ 14) Leer XML                               ║
					║                                            ║
					║ 15) Salir                                  ║
					╚════════════════════════════════════════════╝
					""");

			boolean entradaValida = false;

			do {
				try {
					System.out.print("\nEscribe una opción:\n> ");
					opcion = t.nextInt();
					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida || (opcion < 1 || opcion > 15));

			System.out.println();

			switch (opcion) {
			case 1:
				execQuery.datoBuscar(t, 1);
				pauseMenu();
				break;

			case 2:
				execQuery.buscar(usuario, false);
				pauseMenu();
				break;

			case 3:
				execQuery.datosAgregar(t);
				pauseMenu();
				break;

			case 4:
				execQuery.datoBuscar(t, 2);
				pauseMenu();
				break;

			case 5:
				execQuery.datoBuscar(t, 3);
				pauseMenu();
				break;

			case 6:
				execQuery.datoBuscar(t, 4);
				pauseMenu();
				break;

			case 7:
				execQuery.recuperar(usuario, false, t);
				pauseMenu();
				break;

			case 8:
				LeerCSV.leerCSV();
				pauseMenu();
				break;

			case 9:
				PDF.generarPDF();
				pauseMenu();
				break;

			case 10:
				QR qr = new QR();

				// Ver las fechas disponibles
				if (qr.mostrarFechas()) {
					Supermercado rs = new Supermercado();
					QR.escribirFecha(t, rs);
				}
				pauseMenu();
				break;

			case 11:
				new Email().seleccionarProveedor(t);
				pauseMenu();
				break;

			case 12:
				Grafico.veriFicacionSeguridad(t);
				pauseMenu();
				break;

			case 13:
				XML.crearXML();
				pauseMenu();
				break;

			case 14:
				XML.leerXML();
				pauseMenu();
				break;

			case 15:
				try {
					System.err.println("\nSaliendo...");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		System.out.println("Hasta luego!");
		t.close();
	}
}