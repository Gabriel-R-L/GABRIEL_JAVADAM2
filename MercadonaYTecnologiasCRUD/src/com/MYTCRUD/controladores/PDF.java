package com.MYTCRUD.controladores;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDType1Font;

import com.csvreader.CsvReader;

import org.pdfbox.pdmodel.font.PDFont;

public class PDF {
	public static void generarPDF() {
		ArrayList<String> alDatosGuardar = new ArrayList<String>();

		try {
			String fichero = "C:/tempJava_GABRIEL/garona.csv";
			CsvReader t1 = new CsvReader(fichero);

			try {
				t1.readHeaders();
				while (t1.readRecord()) {
					String fecha = t1.get("fecha");
					String caja1 = t1.get("beneficioCaja1");
					String caja2 = t1.get("beneficioCaja2");
					String caja3 = t1.get("beneficioCaja3");
					String caja4 = t1.get("beneficioCaja4");
					String totalCajas = t1.get("totalDineroCajas");
					String numClientes = t1.get("clientesTotales");

					alDatosGuardar.add(String.format("%s, %s, %s, %s, %s, %s, %s\n", fecha, caja1, caja2, caja3, caja4,
							totalCajas, numClientes));

					int pdfs = 600;
					PDDocument doc = null;

					try {
						doc = new PDDocument();

						PDFont font = PDType1Font.COURIER_BOLD_OBLIQUE;
						Color color = Color.blue;

						// 1 pagina -> 20 filas
						// n paginas -> longitudAL
						// n paginas = longitudAL/20
						int paginasGenerar = (int) Math.ceil((double) alDatosGuardar.size() / 20);
						int posFila = 0;

						for (int i = 0; i < paginasGenerar; i++) {
							pdfs = 600;
							PDPage page = new PDPage();
							doc.addPage(page);
							boolean esPrimeraPagina = (i == 0);

							PDPageContentStream contentStream = new PDPageContentStream(doc, page);
							try {
								if (esPrimeraPagina) {
									contentStream.beginText();
									contentStream.setFont(font, 16);
									contentStream.setNonStrokingColor(color);
									contentStream.moveTextPositionByAmount(160, 700);
									contentStream.drawString("------LISTADO DE RECAUDACIONES------");
									contentStream.endText();

									contentStream.beginText();
									contentStream.setFont(font, 12);
									contentStream.setNonStrokingColor(Color.BLACK);
									contentStream.moveTextPositionByAmount(100, 650);
									contentStream
											.drawString("FECHA - CAJA1 - CAJA2 - CAJA3 - CAJA4 - TOTAL - NUMCLIENTES");
									contentStream.endText();
								}

//								Cuántos elementos del arralist me faltan por colocar
								int elementosRestantes = alDatosGuardar.size() - posFila;

//								Para saber si hay menos o, como mucho, 20 filas a guardar en cada página
								int elementosPorPagina = Math.min(elementosRestantes, 20);

								for (int j = 0; j < elementosPorPagina; j++) {
									contentStream.beginText();
									contentStream.setFont(font, 12);
									contentStream.setNonStrokingColor(Color.blue);
									contentStream.moveTextPositionByAmount(100, pdfs);

									contentStream.drawString(alDatosGuardar.get(posFila));

									pdfs = pdfs - 25;
									contentStream.endText();

									posFila++;
								}
								contentStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						try {
							doc.save("C:/tempJava_GABRIEL/garona.pdf");
						} catch (COSVisitorException e) {
							e.printStackTrace();
						}
						doc.close();

						System.err.println("\nEl pdf se ha generado correctamente");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error al encontrar el archivo CSV: " + e.getMessage());
		}
	}
}
