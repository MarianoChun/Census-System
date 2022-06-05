package interfaz;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import logica.Censista;
import logica.Manzana;
import logica.RadioCensal;
import logica.Sistema;

public class AsignadorFBSW extends SwingWorker<ArrayList<Censista>, Object> {
	private JProgressBar progressBar;
	JTable tablaCensistas;
	private RadioCensal radioCensal;
	private DefaultTableModel modeloTablaCensistas;
	private ArrayList<Censista> instanciaCensistas;
	private ArrayList<Censista> censistasAsignados;

	public AsignadorFBSW(ArrayList<Censista> instanciaCensistas, ArrayList<Censista> censistasAsignados, RadioCensal radioCensal, JProgressBar progressBar, 
			JTable tablaCensistas, DefaultTableModel modeloTablaCensistas) {
		this.instanciaCensistas = instanciaCensistas;
		this.progressBar = progressBar;
		this.radioCensal = radioCensal;
		this.tablaCensistas = tablaCensistas;
		this.modeloTablaCensistas = modeloTablaCensistas;
	}

	@Override
	public ArrayList<Censista> doInBackground() throws Exception {
		progressBar.setIndeterminate(true);
		this.censistasAsignados = new Sistema(radioCensal, instanciaCensistas).obtenerCensistasAsignadosFB();
		return this.censistasAsignados;
	}
	
	@Override
	public void done() {
		try {
			if(this.isCancelled() == false) {
				progressBar.setIndeterminate(false);
				mostrarCensistasEnTabla(censistasAsignados);
			}
		}
//		catch(InterruptedException ex){
//			progressBar.setBackground(Color.RED);
//		}
//		catch(ExecutionException ex) {
//			progressBar.setBackground(Color.RED);
//		}
		finally {
			
		}
		
		
	}
	
	private void mostrarCensistasEnTabla(ArrayList<Censista> censistas) {
		ArrayList<Manzana> manzanasAsignadas;
		removerRegistrosTabla(modeloTablaCensistas);

		for (Censista censista : censistas) {
			manzanasAsignadas = censista.getManzanasAsignadas();
			ImageIcon fotoCensista = new ImageIcon(setTamanoFotoCensista(censista, 40, 40));
			JLabel fotoAColocar = setFotoEnLabel(fotoCensista);

			modeloTablaCensistas.addRow(new Object[] { censista.getNombre(), fotoAColocar,
					obtenerValoresStringManzanas(manzanasAsignadas) });
		}
	}
	
	private void removerRegistrosTabla(DefaultTableModel modeloTabla) {
		int cantRegistros = modeloTabla.getRowCount();
		if (cantRegistros > 1) {
			modeloTabla.getDataVector().removeAllElements();
			modeloTabla.fireTableDataChanged();
		}
	}
	
	private JLabel setFotoEnLabel(ImageIcon fotoCensista) {
		JLabel fotoAColocar = new JLabel();
		fotoAColocar.setIcon(fotoCensista);
		return fotoAColocar;
	}

	private Image setTamanoFotoCensista(Censista censista, int ancho, int alto) {
		return new ImageIcon(censista.getFoto()).getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
	}
	
	protected String obtenerValoresStringManzanas(ArrayList<Manzana> manzanasAsignadas) {
		StringBuffer ret = new StringBuffer();
		ret.append("[ ");
		for (Manzana manzana : manzanasAsignadas) {
			ret.append(manzana.getNroManzana()).append(" ");
		}
		ret.append(" ]");

		return ret.toString();
	}
}
