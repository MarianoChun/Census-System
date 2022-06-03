package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CargadorManzanas {
	RadioCensal radio;
	String pathExcel;

	public CargadorManzanas(String pathExcel) {
		this.pathExcel = pathExcel;
		this.radio = new RadioCensal(cantManzanasExcel());
		cargarManzanasDesdeExcel();
	}

	public void cargarManzanasYVecinosDesdeExcel() {
		cargarManzanasDesdeExcel();
		cargarVecinosManzanasDesdeExcel();
	}

	private void cargarManzanasDesdeExcel() {
		try {
			Iterator<Row> itr = obtenerIteradorExcel();

			Manzana manzanaActual;
			String coordenadaActual;
			DataFormatter formateador = new DataFormatter();
			int nroManzanaActual;

			while (itr.hasNext()) {

				Row row = itr.next();
				if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					nroManzanaActual = (int) row.getCell(0).getNumericCellValue();
					coordenadaActual = formateador.formatCellValue(row.getCell(1));

					manzanaActual = new Manzana(nroManzanaActual, obtenerCoordeanda(coordenadaActual));
					radio.agregarManzana(manzanaActual);
				} else {
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cargarVecinosManzanasDesdeExcel() {
		try {
			Iterator<Row> itr = obtenerIteradorExcel();

			int nroManzanaActual;
			DataFormatter formateador = new DataFormatter();
			ArrayList<Integer> vecinosManzana;

			while (itr.hasNext()) {
				Row row = itr.next();

				if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					vecinosManzana = obtenerVecinosManzana(formateador.formatCellValue(row.getCell(2)));
					nroManzanaActual = (int) row.getCell(0).getNumericCellValue();
					for (Integer vecinoActual : vecinosManzana) {
						radio.agregarManzanaContigua(radio.getManzana(nroManzanaActual),radio.getManzana(vecinoActual));
					}
				} else {
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int cantManzanasExcel() {
		int cantManzanas = 0;
		try {
			Iterator<Row> iterador = obtenerIteradorExcel();

			while (iterador.hasNext()) {
				Row row = iterador.next();

				if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					cantManzanas++;
				} else {
					break;
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cantManzanas;
	}

	private XSSFWorkbook obtenerWorkbookExcel() throws FileNotFoundException, IOException {
		FileInputStream archivo;
		try {
			archivo = new FileInputStream(this.getClass().getResource(pathExcel).getPath());
		} catch (NullPointerException e) {
			archivo = new FileInputStream(pathExcel);
		}
		// Creamos una instancia Workbook que hace referencia al archivo .xlsx
		XSSFWorkbook workbook = new XSSFWorkbook(archivo);
		return workbook;
	}

	private Coordenada obtenerCoordeanda(String coordenada) {
		String[] coordeandas = coordenada.split(", ");
		return new Coordenada(Double.parseDouble(coordeandas[0]), Double.parseDouble(coordeandas[1]));
	}

	private ArrayList<Integer> obtenerVecinosManzana(String vecinos) {
		String[] vecinosStringManzana = vecinos.split(",");
		ArrayList<Integer> vecinosManzanas = new ArrayList<Integer>();

		for (String vecinoActual : vecinosStringManzana) {
			vecinosManzanas.add(Integer.parseInt(vecinoActual));
		}

		return vecinosManzanas;
	}

	private Iterator<Row> obtenerIteradorExcel() throws FileNotFoundException, IOException {
		XSSFWorkbook workbook = obtenerWorkbookExcel();
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> itr = sheet.iterator();
		itr.next();
		workbook.close();

		return itr;
	}

	public RadioCensal getRadioCensal() {
		return radio;
	}

}
