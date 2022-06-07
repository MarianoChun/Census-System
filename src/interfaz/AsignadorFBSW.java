package interfaz;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import logica.Censista;
import logica.RadioCensal;
import logica.Sistema;

public class AsignadorFBSW extends SwingWorker<ArrayList<Censista>, Object> {
	private JProgressBar progressBar;
	private DefaultTableModel modeloTablaCensistas;
	private JFrame frmAsignadorDeCensistas;
	private RadioCensal radioCensal;
	private ArrayList<Censista> instanciaCensistas;
	private ArrayList<Censista> censistasAsignados;
	private ThreadTime threadTiempo;
	private JButton btnAsignarManzanasAG;
	private JButton btnAsignarManzanasFB;
	private long tiempoAlgoritmo;

	public AsignadorFBSW(ArrayList<Censista> instanciaCensistas, ArrayList<Censista> censistasAsignados,
			RadioCensal radioCensal, JProgressBar progressBar, DefaultTableModel modeloTablaCensistas,
			JFrame frmAsignadorDeCensistas, JButton btnAsignarManzanasAG, JButton btnAsignarManzanasFB) {
		this.instanciaCensistas = instanciaCensistas;
		this.radioCensal = radioCensal;
		this.modeloTablaCensistas = modeloTablaCensistas;
		this.progressBar = progressBar;
		this.frmAsignadorDeCensistas = frmAsignadorDeCensistas;
		this.threadTiempo = new ThreadTime();
		this.threadTiempo.start();
		this.btnAsignarManzanasAG = btnAsignarManzanasAG;
		this.btnAsignarManzanasFB = btnAsignarManzanasFB;
	}

	@Override
	public ArrayList<Censista> doInBackground() throws Exception {
		progressBar.setIndeterminate(true);
		deshabilitarBtnsAsignarManzanas();
		long tiempoInicial = threadTiempo.getTiempoActualMs();
		this.censistasAsignados = new Sistema(radioCensal, instanciaCensistas).obtenerCensistasAsignadosFB();
		long tiempoFinal = threadTiempo.getTiempoActualMs();
		this.tiempoAlgoritmo = (tiempoFinal - tiempoInicial);
		return this.censistasAsignados;
	}

	@Override
	public void done() {
		if (!this.isCancelled()) {
			progressBar.setIndeterminate(false);
			FuncionesAuxiliares.mostrarCensistasEnTabla(modeloTablaCensistas, censistasAsignados);
			progressBar.setBackground(new Color(204, 255, 51));
			FuncionesAuxiliares.popUpInfoTiempoDeEjecución(frmAsignadorDeCensistas, tiempoAlgoritmo);
			habilitarBtnsAsignarManzanas();
		} else if (this.isCancelled()) {
			progressBar.setIndeterminate(false);
			progressBar.setBackground(Color.RED);
			habilitarBtnsAsignarManzanas();
		}
	}
	

	private void deshabilitarBtnsAsignarManzanas() {
		btnAsignarManzanasAG.setEnabled(false);
		btnAsignarManzanasFB.setEnabled(false);
	}
	
	private void habilitarBtnsAsignarManzanas() {
		btnAsignarManzanasAG.setEnabled(true);
		btnAsignarManzanasFB.setEnabled(true);
	}

}
