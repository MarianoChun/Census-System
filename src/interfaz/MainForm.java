package interfaz;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import logica.CargadorCensistas;
import logica.CargadorManzanas;
import logica.Censista;
import logica.Coordenada;
import logica.Manzana;
import logica.RadioCensal;
import logica.Sistema;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

public class MainForm {

	private JFrame frmAsignadorDeCensistas;
	private CargadorCensistas cargadorCensistas;
	private CargadorManzanas cargadorManzanas;
	private DefaultTableModel modeloTablaCensistas;
	private DefaultTableModel modeloTablaManzanas;
	private RadioCensal radioCensal;
	private Sistema sistema;
	private JTable tablaCensistas;
	private JTable tablaManzanas;
	private JFileChooser selectorArchivos;
	private JMapViewer mapa;
	private JButton btnAsignarManzanasAG;
	private JButton btnAsignarManzanasFB;
	private JButton btnCancelar;
	private JProgressBar progressBar;
	private AsignadorFBSW asignadorFBSW;
	private AsignadorGolosoSW asignadorGolosoSW;
	private boolean asignadorFBSWon;
	private boolean asignadorGolosoSWon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmAsignadorDeCensistas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		crearFramePrincipal();

		crearMapa();

		crearTablaCensistas();

		crearTablaManzanas();

		crearBotonAsignarManzanasAG();

		crearBotonAsignarManzanasFB();

		crearBotonCargarCensistas();

		crearBotonCargarManzanas();

		crearBarraDeProgreso();

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (asignadorFBSWon) {
					asignadorFBSW.cancel(true);
					asignadorFBSWon = false;
				}
				if (asignadorGolosoSWon) {
					asignadorGolosoSW.cancel(true);
					asignadorGolosoSWon = false;
				}
			}
		});
		btnCancelar.setBounds(425, 721, 132, 34);
		frmAsignadorDeCensistas.getContentPane().add(btnCancelar);
	}

	@SuppressWarnings("deprecation")
	private void crearBarraDeProgreso() {
		progressBar = new JProgressBar();
		progressBar.setBackground(SystemColor.menu);
		progressBar.setForeground(new Color(204, 255, 51));
		progressBar.setBounds(293, 606, 403, 14);
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setBorder(new LineBorder(new Color(0, 0, 0)));
		progressBar.show(false);
		frmAsignadorDeCensistas.getContentPane().add(progressBar);
	}

	private void crearBotonCargarManzanas() {
		JButton btnCargarManzanas = new JButton("Cargar manzanas");
		btnCargarManzanas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File directorioAMostrar = new File(System.getProperty("user.dir") + "/src/listas_de_manzanas");
				selectorArchivos = new JFileChooser();
				selectorArchivos.setCurrentDirectory(directorioAMostrar);

				int valor = selectorArchivos.showOpenDialog(selectorArchivos);

				if (valor == JFileChooser.APPROVE_OPTION) {
					try {

						String path = formateoPath();

						cargadorManzanas = new CargadorManzanas(path);
						cargadorManzanas.cargarManzanasYVecinosDesdeExcel();
						radioCensal = cargadorManzanas.getRadioCensal();

						HashMap<Integer, Manzana> manzanas = radioCensal.getManzanas();
						removerRegistrosTabla(modeloTablaManzanas);
						limpiarMapaDePolygonsYMarkers();
						for (Manzana manzana : manzanas.values()) {
							Coordenada coordenada = manzana.getCoordenada();

							modeloTablaManzanas.addRow(new Object[] { manzana.getNroManzana(),
									"X = " + coordenada.getX() + " , Y = " + coordenada.getY() });

							agregarMapPolygonPorCadaVecino(manzana);
							agregarMapMarkerManzana(manzana);
						}

						tablaManzanas.setModel(modeloTablaManzanas);

					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				} else {
					System.out.println("No se ha seleccionado ningún fichero");
				}

				habilitarBtnsAsignarManzanas();
			}
		});
		btnCargarManzanas.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnCargarManzanas.setBounds(496, 631, 200, 34);
		frmAsignadorDeCensistas.getContentPane().add(btnCargarManzanas);
	}

	private void crearBotonCargarCensistas() {
		JButton btnCargarCensistas = new JButton("Cargar censistas");
		btnCargarCensistas.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnCargarCensistas.setBounds(293, 631, 200, 34);
		btnCargarCensistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File directorioAMostrar = new File(System.getProperty("user.dir") + "/src/listas_de_censistas");
				selectorArchivos = new JFileChooser();
				selectorArchivos.setCurrentDirectory(directorioAMostrar);

				int valor = selectorArchivos.showOpenDialog(selectorArchivos);

				if (valor == JFileChooser.APPROVE_OPTION) {
					try {
						String path = formateoPath();

						cargadorCensistas = new CargadorCensistas(path);
						cargadorCensistas.cargarCensistasDesdeExcel();

						Map<Integer, Censista> censistas = cargadorCensistas.getCensistas();

						removerRegistrosTabla(modeloTablaCensistas);
						for (Censista censista : censistas.values()) {
							ImageIcon fotoCensista = new ImageIcon(setTamanoFotoCensista(censista, 40, 40));
							JLabel fotoAColocar = new JLabel();
							fotoAColocar.setIcon(fotoCensista);
							modeloTablaCensistas.addRow(new Object[] { censista.getNombre(), fotoAColocar });

						}

						tablaCensistas.getColumnModel().getColumn(1)
								.setWidth(tablaCensistas.getColumnModel().getColumn(1).getPreferredWidth());
						tablaCensistas.setPreferredScrollableViewportSize(tablaCensistas.getPreferredSize());
						tablaCensistas.setModel(modeloTablaCensistas);

					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				} else {
					System.out.println("No se ha seleccionado ningún fichero");
				}

				habilitarBtnsAsignarManzanas();
			}
		});
		frmAsignadorDeCensistas.getContentPane().add(btnCargarCensistas);
	}

	private void crearBotonAsignarManzanasAG() {
		btnAsignarManzanasAG = new JButton("Asignar manzanas a censistas (Goloso)");
		btnAsignarManzanasAG.setEnabled(false);
		btnAsignarManzanasAG.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				modeloTablaCensistas.setRowCount(0);
				ArrayList<Censista> instanciaCensistas = clonarCensistas(cargadorCensistas.getCensistasArray());
				ArrayList<Censista> censistasAsignados = new ArrayList<Censista>();
				progressBar.show(true);
				progressBar.setBackground(Color.WHITE);
				asignadorGolosoSW = new AsignadorGolosoSW(instanciaCensistas, censistasAsignados, radioCensal,
						progressBar, tablaCensistas, modeloTablaCensistas, frmAsignadorDeCensistas);
				asignadorGolosoSW.execute();
				btnCancelar.setEnabled(true);
				asignadorGolosoSWon = true;
			}
		});
		btnAsignarManzanasAG.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnAsignarManzanasAG.setBounds(113, 676, 380, 34);
		frmAsignadorDeCensistas.getContentPane().add(btnAsignarManzanasAG);
	}

	private void crearBotonAsignarManzanasFB() {
		btnAsignarManzanasFB = new JButton("Asignar manzanas a censistas (Fuerza bruta)");
		btnAsignarManzanasFB.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				modeloTablaCensistas.setRowCount(0);
				ArrayList<Censista> instanciaCensistas = clonarCensistas(cargadorCensistas.getCensistasArray());
				ArrayList<Censista> censistasAsignados = new ArrayList<Censista>();
				progressBar.show(true);
				progressBar.setBackground(Color.WHITE);
				asignadorFBSW = new AsignadorFBSW(instanciaCensistas, censistasAsignados, radioCensal, progressBar,
						tablaCensistas, modeloTablaCensistas, frmAsignadorDeCensistas);
				asignadorFBSW.execute();
				btnCancelar.setEnabled(true);
				asignadorFBSWon = true;
			}
		});
		btnAsignarManzanasFB.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnAsignarManzanasFB.setEnabled(false);
		btnAsignarManzanasFB.setBounds(496, 676, 372, 34);
		frmAsignadorDeCensistas.getContentPane().add(btnAsignarManzanasFB);

	}

	private void crearFramePrincipal() {
		frmAsignadorDeCensistas = new JFrame();
		frmAsignadorDeCensistas.setTitle("Asignador de censistas");
		frmAsignadorDeCensistas.setResizable(false);
		frmAsignadorDeCensistas.setBounds(100, 100, 1094, 805);
		frmAsignadorDeCensistas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAsignadorDeCensistas.getContentPane().setLayout(null);
		frmAsignadorDeCensistas.setLocationRelativeTo(null);
	}

	private void crearTablaManzanas() {
		JScrollPane scrollPaneManzanas = new JScrollPane();
		scrollPaneManzanas.setBounds(496, 11, 549, 227);
		frmAsignadorDeCensistas.getContentPane().add(scrollPaneManzanas);

		tablaManzanas = new JTable();
		tablaManzanas.setFocusable(false);
		tablaManzanas.setRowSelectionAllowed(false);
		modeloTablaManzanas = new DefaultTableModel(new Object[][] {}, new String[] { "Nro Manzana", "Coordenadas" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tablaManzanas.setModel(modeloTablaManzanas);

		tablaManzanas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaManzanas.setBounds(477, 27, 350, 227);
		tablaManzanas.getTableHeader().setReorderingAllowed(false);
		tablaManzanas.getTableHeader().setResizingAllowed(false);
		tablaManzanas.getColumnModel().getColumn(0).setPreferredWidth(40);

		scrollPaneManzanas.setViewportView(tablaManzanas);
	}

	private void crearTablaCensistas() {
		JScrollPane scrollPaneCensistas = new JScrollPane();
		scrollPaneCensistas.setBounds(31, 11, 409, 227);
		frmAsignadorDeCensistas.getContentPane().add(scrollPaneCensistas);

		tablaCensistas = new JTable();
		tablaCensistas.setFocusable(false);
		tablaCensistas.setRowSelectionAllowed(false);
		tablaCensistas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaCensistas.setBounds(33, 27, 127, 162);
		tablaCensistas.setRowHeight(50);
		tablaCensistas.setDefaultRenderer(Object.class, new ImagenTabla());

		modeloTablaCensistas = new DefaultTableModel(new Object[][] {},
				new String[] { "Nombre censista", "Foto censista", "Manzana/s asignada/s" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		tablaCensistas.setModel(modeloTablaCensistas);
		tablaCensistas.getColumnModel().getColumn(0).setWidth(5);
		tablaCensistas.getColumnModel().getColumn(1).setWidth(5);
		tablaCensistas.getTableHeader().setReorderingAllowed(false);
		tablaCensistas.getTableHeader().setResizingAllowed(false);
		scrollPaneCensistas.setViewportView(tablaCensistas);
	}

	private void crearMapa() {
		JPanel panelMapa = new JPanel();
		panelMapa.setBounds(213, 243, 561, 352);
		frmAsignadorDeCensistas.getContentPane().add(panelMapa);

		mapa = new JMapViewer();
		mapa.setScrollWrapEnabled(true);
		mapa.setDisplayPosition(new Coordinate(-34.542826297705645, -58.71398400055889), 12);
		mapa.setBounds(panelMapa.getBounds());
		panelMapa.add(mapa);

		DefaultMapController controladorMapa = new DefaultMapController(mapa);
		controladorMapa.setMovementMouseButton(MouseEvent.BUTTON1);
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

	private ArrayList<Coordinate> obtenerCoordenadasVecinoManzana(Manzana manzana, Manzana vecino) {
		// MapPolygon no dibuja aquellas listas de coordenadas de menos de 2 elementos,
		// por eso, repetimos la coordenada del vecino para que se dibuje una linea
		// recta entre ambas manzanas
		ArrayList<Coordinate> coordenadasVecino = new ArrayList<Coordinate>();
		Coordenada coordManzana = manzana.getCoordenada();
		Coordenada coordVecino = vecino.getCoordenada();

		coordenadasVecino.add(new Coordinate(coordManzana.getX(), coordManzana.getY()));
		coordenadasVecino.add(new Coordinate(coordVecino.getX(), coordVecino.getY()));
		coordenadasVecino.add(new Coordinate(coordVecino.getX(), coordVecino.getY()));

		return coordenadasVecino;
	}

	public static ArrayList<Censista> clonarCensistas(ArrayList<Censista> censistas) {
		ArrayList<Censista> clonCensistas = new ArrayList<Censista>();
		censistas.stream().forEach(c -> clonCensistas.add(c.clone()));

		return clonCensistas;
	}

	private String formateoPath() {
		File archivo = selectorArchivos.getSelectedFile();
		String path = archivo.getAbsolutePath().replaceAll("\\\\", "/");
		return path;
	}

	private void agregarMapPolygonPorCadaVecino(Manzana manzana) {
		ArrayList<Coordinate> vecinosManzana;
		for (Integer manzanaVecina : radioCensal.manzanasVecinas(manzana.getNroManzana())) {
			vecinosManzana = obtenerCoordenadasVecinoManzana(manzana, radioCensal.getManzana(manzanaVecina));
			mapa.addMapPolygon(new MapPolygonImpl(vecinosManzana));
		}
	}

	private void agregarMapMarkerManzana(Manzana manzana) {
		Coordenada coordManzana = manzana.getCoordenada();
		MapMarkerDot marcadorManzana = new MapMarkerDot(Color.RED, coordManzana.getX(), coordManzana.getY());
		marcadorManzana.setName("" + manzana.getNroManzana());
		mapa.addMapMarker(marcadorManzana);
	}

	private void removerRegistrosTabla(DefaultTableModel modeloTabla) {
		int cantRegistros = modeloTabla.getRowCount();
		if (cantRegistros > 1) {
			modeloTabla.getDataVector().removeAllElements();
			modeloTabla.fireTableDataChanged();
		}
	}

	private boolean estanRegistrosManzanasCargados() {
		return modeloTablaManzanas.getRowCount() != 0;
	}

	private boolean estanRegistrosCensistasCargados() {
		return modeloTablaCensistas.getRowCount() != 0;
	}

	private void habilitarBtnsAsignarManzanas() {
		if (estanRegistrosCensistasCargados() && estanRegistrosManzanasCargados()) {
			btnAsignarManzanasAG.setEnabled(true);
			btnAsignarManzanasFB.setEnabled(true);
		}
	}

	private Image setTamanoFotoCensista(Censista censista, int ancho, int alto) {

		return new ImageIcon(censista.getFoto()).getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT);
	}

	private JLabel setFotoEnLabel(ImageIcon fotoCensista) {
		JLabel fotoAColocar = new JLabel();
		fotoAColocar.setIcon(fotoCensista);
		return fotoAColocar;
	}

	private void limpiarMapaDePolygonsYMarkers() {
		mapa.removeAllMapPolygons();
		mapa.removeAllMapMarkers();
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

	public class ImagenTabla extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable JTable, Object value, boolean bln, boolean bln1, int i,
				int j) {
			if (value instanceof JLabel) {
				JLabel lbl = (JLabel) value;
				return lbl;
			}

			return super.getTableCellRendererComponent(JTable, value, bln, bln1, i, j);
		}

	}
}
