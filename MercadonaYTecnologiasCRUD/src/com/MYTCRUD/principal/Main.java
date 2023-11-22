package com.MYTCRUD.principal;

import com.MYTCRUD.vistas.Menu;
import com.MYTCRUD.vistas.SplashScreen;

public class Main {
	public static void main(String[] args) {
		// Ventana de carga
		new SplashScreen();

		Menu menu = new Menu();
		menu.Intro();
	}
}
