package interfaz;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import logica.Censista;
import logica.RadioCensal;
import logica.Sistema;
import logica.ThreadTime;

public class AsignadorFBSW extends SwingWorker<ArrayList<Censista>, Object> {
	private JProgressBar progressBar;
	private DefaultTableModel modeloTablaCensistas;
	private JFrame frmAsignadorDeCensistas;
	private RadioCensal radioCensal;
	private ArrayList<Censista> instanciaCensistas;
	private ArrayList<Censista> censistasAsignados;
	private ThreadTime threadTiempo;

	public AsignadorFBSW(ArrayList<Censista> instanciaCensistas, ArrayList<Censista> censistasAsignados,
			RadioCensal radioCensal, JProgressBar progressBar, DefaultTableModel modeloTablaCensistas,
			JFrame frmAsignadorDeCensistas) {
		this.instanciaCensistas = instanciaCensistas;
		this.radioCensal = radioCensal;
		this.modeloTablaCensistas = modeloTablaCensistas;
		this.progressBar = progressBar;
		this.frmAsignadorDeCensistas = frmAsignadorDeCensistas;
		this.threadTiempo = new ThreadTime();
		this.threadTiempo.start();
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
		if (!this.isCancelled()) {
			progressBar.setIndeterminate(false);
			FuncionesAuxiliares.mostrarCensistasEnTabla(modeloTablaCensistas, censistasAsignados);
			progressBar.setBackground(new Color(204, 255, 51));
		} else if (this.isCancelled()) {
			progressBar.setIndeterminate(false);
			progressBar.setBackground(Color.RED);
		}
	}

}
