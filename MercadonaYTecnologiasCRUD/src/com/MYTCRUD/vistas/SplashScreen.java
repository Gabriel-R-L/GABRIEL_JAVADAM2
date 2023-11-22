package com.MYTCRUD.vistas;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

@SuppressWarnings("serial")
public class SplashScreen extends JWindow {
	private static int TIMER_PAUSE = 3000;
	private static String PATH_IMAGE = "../resources/image/logo.png";
	private static String PATH_GIF = "../resources/image/loading.gif";
	private URL urlFile;
	

	public SplashScreen() {
		
		Container container = getContentPane();
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.decode("#ccdffc"));
		container.add(panel, BorderLayout.CENTER);

		// Cargar la imagen
		urlFile = getClass().getResource(PATH_IMAGE);
		ImageIcon icon = new ImageIcon(urlFile);
		ImageIcon resizedIcon1 = new ImageIcon(icon.getImage().getScaledInstance(300, 300, Image.SCALE_FAST));
		JLabel label1 = new JLabel(resizedIcon1);
		panel.add(label1, BorderLayout.NORTH);

		// Panel para el texto "Cargando..." y el GIF
		JPanel loadingPanel = new JPanel(new GridLayout(1, 2));
		loadingPanel.setBackground(Color.decode("#ccdffc"));

		// Cargar el GIF
		urlFile = getClass().getResource(PATH_GIF);
		ImageIcon gifIcon = new ImageIcon(urlFile);
		// Redimensionar el GIF
		Image gifImage = gifIcon.getImage().getScaledInstance(20, 20, Image.SCALE_FAST);
		ImageIcon resizedGifIcon = new ImageIcon(gifImage);
		// Utilizar un JLabel con ImageIcon animado
		JLabel label3 = new JLabel(resizedGifIcon);
		label3.setFont(new Font("Verdana", Font.BOLD, 14));
		label3.setHorizontalAlignment(JLabel.CENTER); // Centrar el GIF
		loadingPanel.add(label3);

		panel.add(loadingPanel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setSize(350, 360);
		setVisible(true);

		// Iniciar el temporizador después de mostrar la ventana
		try {
			for (int i = 0; i < TIMER_PAUSE / 400; i++) {
				// Actualizar el texto con puntos suspensivos cada 3 sumas de iterador
				label3.setText("Cargando" + ".".repeat(i % 4));
				Thread.sleep(900);
			}
			dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
