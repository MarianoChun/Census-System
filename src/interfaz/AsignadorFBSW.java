package interfaz;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import logica.Censista;
import logica.Manzana;
import logica.RadioCensal;
import logica.Sistema;
import logica.ThreadTime;


public class AsignadorFBSW extends SwingWorker<ArrayList<Censista>, Object> {
	private JProgressBar progressBar;
	private JTable tablaCensistas;
	private DefaultTableModel modeloTablaCensistas;
	private JFrame frmAsignadorDeCensistas;
	private RadioCensal radioCensal;
	private ArrayList<Censista> instanciaCensistas;
	private ArrayList<Censista> censistasAsignados;
	private ThreadTime threadTiempo;

	public AsignadorFBSW(ArrayList<Censista> instanciaCensistas, 
						ArrayList<Censista> censistasAsignados, 
						RadioCensal radioCensal, 
						JProgressBar progressBar, 
						JTable tablaCensistas, 
						DefaultTableModel modeloTablaCensistas, 
						JFrame frmAsignadorDeCensistas) {
		this.instanciaCensistas = instanciaCensistas;
		this.radioCensal = radioCensal;
		this.tablaCensistas = tablaCensistas;
		this.modeloTablaCensistas = modeloTablaCensistas;
		this.progressBar = progressBar;
		this.frmAsignadorDeCensistas = frmAsignadorDeCensistas;
		threadTiempo = new ThreadTime();
		threadTiempo.start();
	}

	@Override
	public ArrayList<Censista> doInBackground() throws Exception {
		progressBar.setIndeterminate(true);
		
		long tiempoInicial = threadTiempo.getTiempoActualMs();
		this.censistasAsignados = new Sistema(radioCensal, instanciaCensistas).obtenerCensistasAsignadosFB();
		long tiempoFinal = threadTiempo.getTiempoActualMs();
		long tiempoAlgoritmo = (tiempoFinal - tiempoInicial);
		FuncionesAuxiliares.popUpInfoTiempoDeEjecución(frmAsignadorDeCensistas, tiempoAlgoritmo);
		return this.censistasAsignados;
	}
	
	@Override
	public void done() {
		try {
			if(this.isCancelled() == false) {
				progressBar.setIndeterminate(false);
				FuncionesAuxiliares.mostrarCensistasEnTabla(modeloTablaCensistas, censistasAsignados);
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
	
//	private void mostrarCensistasEnTabla(ArrayList<Censista> censistas) {
//		ArrayList<Manzana> manzanasAsignadas;
//		removerRegistrosTabla(modeloTablaCensistas);
//
//		for (Censista censista : censistas) {
//			manzanasAsignadas = censista.getManzanasAsignadas();
//			ImageIcon fotoCensista = new ImageIcon(setTamanoFotoCensista(censista, 40, 40));
//			JLabel fotoAColocar = setFotoEnLabel(fotoCensista);
//
//			modeloTablaCensistas.addRow(new Object[] { censista.getNombre(), fotoAColocar,
//					obtenerValoresStringManzanas(manzanasAsignadas) });
//		}
//	}
//	
//	private void removerRegistrosTabla(DefaultTableModel modeloTabla) {
//		int cantRegistros = modeloTabla.getRowCount();
//		if (cantRegistros > 1) {
//			modeloTabla.getDataVector().removeAllElements();
//			modeloTabla.fireTableDataChanged();
//		}
//	}
//	
//	private JLabel setFotoEnLabel(ImageIcon fotoCensista) {
//		JLabel fotoAColocar = new JLabel();
//		fotoAColocar.setIcon(fotoCensista);
//		return fotoAColocar;
//	}
//
//	private Image setTamanoFotoCensista(Censista censista, int ancho, int alto) {
//		return new ImageIcon(censista.getFoto()).getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
//	}
//	
//	protected String obtenerValoresStringManzanas(ArrayList<Manzana> manzanasAsignadas) {
//		StringBuffer ret = new StringBuffer();
//		ret.append("[ ");
//		for (Manzana manzana : manzanasAsignadas) {
//			ret.append(manzana.getNroManzana()).append(" ");
//		}
//		ret.append(" ]");
//
//		return ret.toString();
//	}
}
