package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CargadorCensistas {
	private Map<Integer, Censista> censistas;
	private String pathExcel;

	public CargadorCensistas(String pathExcel) {
		this.pathExcel = pathExcel;
		this.censistas = new HashMap<Integer, Censista>();
		cargarCensistasDesdeExcel();
	}

	public void cargarCensistasDesdeExcel() {
		try {
			Iterator<Row> itr = obtenerIteradorExcel();
			List<XSSFPictureData> fotosCensistas = obtenerImagenesCensistas();
			Censista censista;
			String nombreCensista;
			int i = 0;

			while (itr.hasNext()) {

				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();

					nombreCensista = row.getCell(0).getStringCellValue().toLowerCase();
					censista = new Censista(nombreCensista);

					if (!esNombreVacio(nombreCensista) && cell.getColumnIndex() == 0) {
						censistas.put(i, censista);
						censista.setFoto(fotosCensistas.get(i).getData());
						i++;
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Censista> getCensistas() {
		return censistas;
	}

	public ArrayList<Censista> getCensistasArray() {
		return new ArrayList<Censista>(censistas.values());
	}

	private boolean esNombreVacio(String nombre) {
		return nombre.equals("");
	}

	private List<XSSFPictureData> obtenerImagenesCensistas() throws IOException {
		FileInputStream archivo;
		try {
			archivo = new FileInputStream(this.getClass().getResource(pathExcel).getPath());
		} catch (NullPointerException e) {
			archivo = new FileInputStream(pathExcel);
		}
		// Creamos una instancia Workbook que hace referencia al archivo .xlsx
		XSSFWorkbook workbook = new XSSFWorkbook(archivo);
		List<XSSFPictureData> fotos = workbook.getAllPictures();
		workbook.close();

		return fotos;
	}

	private Iterator<Row> obtenerIteradorExcel() throws FileNotFoundException, IOException {
		FileInputStream archivo;
		try {
			archivo = new FileInputStream(this.getClass().getResource(pathExcel).getPath());
		} catch (NullPointerException e) {
			archivo = new FileInputStream(pathExcel);
		}
		// Creamos una instancia Workbook que hace referencia al archivo .xlsx
		XSSFWorkbook workbook = new XSSFWorkbook(archivo);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Iterator<Row> itr = sheet.iterator();
		itr.next();
		workbook.close();

		return itr;
	}
}
