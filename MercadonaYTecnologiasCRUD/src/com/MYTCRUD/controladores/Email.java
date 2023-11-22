package com.MYTCRUD.controladores;

import java.awt.Panel;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import com.MYTCRUD.vistas.Menu;

public class Email {
	private static String PATHNAME = "C:/tempJava_GABRIEL/";

	public Email() {
	}

	public void seleccionarProveedor(Scanner t) {
		boolean entradaValida = false;
		int tipoEnvioCorreo = 0;

		do {
			try {
				System.out
						.println("\n¿A qué proveedor de correos lo quieres enviar?\n1. Gmail\n2. Hotmail\n3. Hubspot\n"
								+ "4. Protonmail\n5. Icloudmail\n6. Zoho mail\n7. Outlook\n8. Mailbox\n9. Yahoo\n10. Bluehost\n11. Rackspace");
				System.out.print("> ");
				tipoEnvioCorreo = t.nextInt();
				entradaValida = true;
			} catch (Exception e) {
				t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
				System.out.println("Error en la escritura: " + e.getMessage());
			}
		} while (!entradaValida || (tipoEnvioCorreo < 1 || tipoEnvioCorreo > 11));
		enviarMail(tipoEnvioCorreo);
	}

	@SuppressWarnings("resource")
	public static void enviarMail(int tipoEnvioCorreo) {
		Scanner t = new Scanner(System.in);
		String correoEnvia = "mmiguellaangell888@gmail.com"; // thx to miguel por el correo :)
		String contrasena = "iolm rurv qnba lloj";
		String receptor = ""; // pedir al usuario a donde enviar
		String asunto = "";
		String mensaje = "";
		String fecha = ""; // ¿qué QR quiero buscar?
		boolean fe = false; // file exists

		// Mientras no encuentre el PNG que haga el bucle
		do {
			String nombrePNG = "";
			System.out.println("Archivos existentes:");
			BuscarArchivo.comprobarArchivo(PATHNAME, "png"); // muestra los archivos de la carpeta que le indico

			boolean entradaValida = false;

			do {
				try {
					System.err.println(
							"¡OJO! Cuando envíe el correo, se abrirá una ventana emergente confirmando el envío del mismo. Si no la ve, haga Alt + Tab para buscarla.");
					System.out.println(
							"\nEscribe la fecha del QR a enviar (Ejemplo: 2023-10-09). Si no hay ninguna, pulse [Enter] y un dos a continuación.");
					System.out.print("> ");
					fecha = t.nextLine();
					entradaValida = true;
				} catch (Exception e) {
					t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
					System.out.println("Error en la escritura: " + e.getMessage());
				}
			} while (!entradaValida);

			nombrePNG = fecha + ".png";
			fe = ComprobadorArchivo.comprobarArchivo(PATHNAME, nombrePNG); // comprueba que el archivo exista en
			// la carpeta que le indico, y las que tenga dentro

			if (!fe) {
				int o = 0;
				System.err.print("No se ha encontrado la fecha. Escriba otra fecha (1) o vuelva al menú (2)");
				System.out.print("\n> ");
				o = t.nextInt();

				switch (o) {
				case 1:
					fecha = t.nextLine();
					fe = ComprobadorArchivo.comprobarArchivo(PATHNAME, nombrePNG);
					break;
				case 2:
				default:
					Menu m = new Menu();
					m.menu();
					break;
				}
			}
		} while (!fe);

		boolean entradaValida = false;
		Properties propiedad = new Properties();

		do {
			try {
				asunto = "Codigo QR del día " + fecha;
				System.out.println("Escribe el email al que enviar el QR:");
				System.out.print("> ");
				receptor = t.next();

				System.out.println("Preparando Configuración");

				switch (tipoEnvioCorreo) {
				case 1:
					propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
					break;
				case 2:
					propiedad.setProperty("mail.smtp.host", "smtp.office365.com");
					break;
				case 3:
					propiedad.setProperty("mail.smtp.host", "smtp.hubspot.net");
					break;
				case 4:
					propiedad.setProperty("mail.smtp.host", "smtp.protonmail.ch");
					break;
				case 5:
					propiedad.setProperty("mail.smtp.host", "smtp.mail.me.com");
					break;
				case 6:
					propiedad.setProperty("mail.smtp.host", "smtp.zoho.eu");
					break;
				case 7:
					propiedad.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
					break;
				case 8:
					propiedad.setProperty("mail.smtp.host", "smtp.mailbox.org");
					break;
				case 9:
					propiedad.setProperty("mail.smtp.host", "smtp.mail.yahoo.com");
					break;
				case 10:
					propiedad.setProperty("mail.smtp.host", "smtp.oxcs.bluehost.com");
					break;
				case 11:
					propiedad.setProperty("mail.smtp.host", "secure.emailsrvr.com");
					break;
				}

				entradaValida = true;
			} catch (Exception e) {
				t.nextLine(); // Limpiar el búfer del escáner para evitar un bucle infinito
				System.out.println("Error en la escritura: " + e.getMessage());
			}
		} while (!entradaValida);

		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "465");
		propiedad.setProperty("mail.smtp.auth", "true");
		propiedad.setProperty("mail.smtp.user", correoEnvia);
		propiedad.setProperty("mail.smtp.password", contrasena);
		propiedad.setProperty("mail.transport.protocol", "smtp");
		propiedad.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session sesion = Session.getInstance(propiedad, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(correoEnvia, contrasena);
			}
		});

		System.out.println("Configuración OK");
		System.out.println("Sesión OK");

		MimeMessage mail = new MimeMessage(sesion);

		try {
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mail.setSubject(asunto);
			mail.setText(mensaje);

			System.out.println("Adjuntando...");
			Multipart multipart = new MimeMultipart();
			MimeBodyPart attachPart = new MimeBodyPart();
			String attachFile = PATHNAME + "/" + fecha + ".png";
			try {
				attachPart.attachFile(attachFile);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ERROR");
				t.close();
				return;
			}
			multipart.addBodyPart(attachPart);
			mail.setContent(multipart);
			System.out.println("Archivo adjunto preparado...");

			System.err.println("Enviando...");
			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
			transportar.close();

			JOptionPane.showMessageDialog(null, "¡QR enviado exitósamente!");

		} catch (AddressException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
