package interfaz;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import logica.Censista;
import logica.Manzana;

public class FuncionesAuxiliares {
	static void mostrarCensistasEnTabla(DefaultTableModel modeloTablaCensistas, ArrayList<Censista> censistas) {
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
	
	static void removerRegistrosTabla(DefaultTableModel modeloTabla) {
		int cantRegistros = modeloTabla.getRowCount();
		if (cantRegistros > 1) {
			modeloTabla.getDataVector().removeAllElements();
			modeloTabla.fireTableDataChanged();
		}
	}
	
	static JLabel setFotoEnLabel(ImageIcon fotoCensista) {
		JLabel fotoAColocar = new JLabel();
		fotoAColocar.setIcon(fotoCensista);
		return fotoAColocar;
	}

	static Image setTamanoFotoCensista(Censista censista, int ancho, int alto) {
		return new ImageIcon(censista.getFoto()).getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
	}
	
	static String obtenerValoresStringManzanas(ArrayList<Manzana> manzanasAsignadas) {
		StringBuffer ret = new StringBuffer();
		ret.append("[ ");
		for (Manzana manzana : manzanasAsignadas) {
			ret.append(manzana.getNroManzana()).append(" ");
		}
		ret.append(" ]");

		return ret.toString();
	}
	
	static void popUpInfoTiempoDeEjecución(JFrame frmAsignadorDeCensistas, long tiempo) {
		StringBuilder str = new StringBuilder();

		JOptionPane.showMessageDialog(frmAsignadorDeCensistas,
				str.append("El tiempo de ejecución del algoritmo fue: ").append(tiempo).append(" ms."));
	}
}
