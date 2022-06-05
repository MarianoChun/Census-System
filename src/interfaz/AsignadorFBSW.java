package interfaz;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
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

	public AsignadorFBSW(ArrayList<Censista> instanciaCensistas, ArrayList<Censista> censistasAsignados,
			RadioCensal radioCensal, JProgressBar progressBar, JTable tablaCensistas,
			DefaultTableModel modeloTablaCensistas, JFrame frmAsignadorDeCensistas) {
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
		if (this.isCancelled() == false) {
			progressBar.setIndeterminate(false);
			FuncionesAuxiliares.mostrarCensistasEnTabla(modeloTablaCensistas, censistasAsignados);
			progressBar.setBackground(new Color(204, 255, 51));
		} else if (this.isCancelled()) {
			progressBar.setIndeterminate(false);
			progressBar.setBackground(Color.RED);
		}

	}

}
