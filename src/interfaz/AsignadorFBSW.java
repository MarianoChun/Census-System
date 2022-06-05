package interfaz;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import logica.Censista;
import logica.RadioCensal;
import logica.Sistema;

public class AsignadorFBSW extends SwingWorker<ArrayList<Censista>, Double> {
	private JProgressBar progressBar;
	private ArrayList<Censista> censistas;
	private RadioCensal radioCensal;

	public AsignadorFBSW(JProgressBar progressBar, ArrayList<Censista> censistas, RadioCensal radioCensal) {
		this.censistas = censistas;
		this.progressBar = progressBar;
		this.radioCensal = radioCensal;
	}

	@Override
	protected ArrayList<Censista> doInBackground() throws Exception {
		progressBar.setIndeterminate(true);
		return new Sistema(radioCensal, censistas).obtenerCensistasAsignadosFB();
	}
	
	@Override
	public void done() {
		try {
			if(this.isCancelled() == false) {
				progressBar.setIndeterminate(false);
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

}
