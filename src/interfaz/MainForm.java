package interfaz;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import logica.CargadorCensistas;
import logica.CargadorManzanas;
import logica.Censista;
import logica.Coordenada;
import logica.Manzana;
import logica.RadioCensal;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainForm {

	private JFrame frame;
	private CargadorCensistas cargadorCensistas;
	private CargadorManzanas cargadorManzanas;
	private DefaultTableModel modeloTablaCensistas;
	private DefaultTableModel modeloTablaManzanas;
	private RadioCensal radioCensal;
	private JTable tablaCensistas;
	private JTable tablaManzanas;
	private JFileChooser selectorArchivos;
	private JMapViewer mapa;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 1072, 727);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panelMapa = new JPanel();
		panelMapa.setBounds(210, 299, 529, 264);
		frame.getContentPane().add(panelMapa);
		
		mapa = new JMapViewer();
		mapa.setDisplayPosition(new Coordinate(-34.542826297705645, -58.71398400055889), 12);
		mapa.setBounds(panelMapa.getBounds());
		panelMapa.add(mapa);
		
		JScrollPane scrollPaneCensistas = new JScrollPane();
		scrollPaneCensistas.setBounds(41, 27, 386, 227);
		frame.getContentPane().add(scrollPaneCensistas);
		
		tablaCensistas =  new JTable();
		tablaCensistas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaCensistas.setBounds(33, 27, 127, 162);
		tablaCensistas.setDefaultRenderer(Object.class, new ImagenTabla());
		modeloTablaCensistas = new DefaultTableModel(new Object[][] {},
				new String[] { "Nombre censista", "Foto censista" });

		tablaCensistas.setModel(modeloTablaCensistas);
		tablaCensistas.getTableHeader().setReorderingAllowed(false);
		tablaCensistas.getTableHeader().setResizingAllowed(false);
		tablaCensistas.getColumnModel().getColumn(0).setPreferredWidth(40);
		scrollPaneCensistas.setViewportView(tablaCensistas);

		
		JScrollPane scrollPaneManzanas = new JScrollPane();
		scrollPaneManzanas.setBounds(437, 27, 591, 227);
		frame.getContentPane().add(scrollPaneManzanas);
		
		tablaManzanas = new JTable();
		modeloTablaManzanas = new DefaultTableModel(new Object[][] {}, new String[] { "Nro Manzana", "Coordenadas" });
		tablaManzanas.setModel(modeloTablaManzanas);
	
		tablaManzanas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaManzanas.setBounds(477, 27, 350, 227);
		tablaManzanas.getTableHeader().setReorderingAllowed(false);
		tablaManzanas.getTableHeader().setResizingAllowed(false);
		tablaManzanas.getColumnModel().getColumn(0).setPreferredWidth(40);
		
		scrollPaneManzanas.setViewportView(tablaManzanas);

		JButton btnCargarCensistas = new JButton("Cargar censistas");
		btnCargarCensistas.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnCargarCensistas.setBounds(276, 574, 200, 34);
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
						
						int cantRegistros = modeloTablaCensistas.getRowCount();
						if(cantRegistros > 1 ) {
							removerRegistrosTabla(modeloTablaCensistas);
						}
						
						for(Censista censista : censistas.values()) {
							ImageIcon fotoCensista = new ImageIcon(new ImageIcon(censista.getFoto()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
							JLabel fotoAColocar = new JLabel();
							fotoAColocar.setIcon(fotoCensista);
							modeloTablaCensistas.addRow(new Object[] {censista.getNombre(), fotoAColocar});

						}

						tablaCensistas.setPreferredScrollableViewportSize(tablaCensistas.getPreferredSize());
						tablaCensistas.setModel(modeloTablaCensistas);
						
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				} else {
					System.out.println("No se ha seleccionado ningún fichero");
				}
			}
		});
		frame.getContentPane().add(btnCargarCensistas);
		
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
						 
						int cantRegistros = modeloTablaManzanas.getRowCount();
						if(cantRegistros > 1 ) {
							removerRegistrosTabla(modeloTablaManzanas);
						}
						for(Manzana manzana : manzanas.values()) {
							Coordenada coordenada = manzana.getCoordenada();
							System.out.println(manzana.getNroManzana());
							modeloTablaManzanas.addRow(new Object[] {manzana.getNroManzana(), 
									"X = " + coordenada.getX() + " , Y = " + coordenada.getY()});
					
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
			}

		
			
		});
		btnCargarManzanas.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnCargarManzanas.setBounds(512, 574, 200, 34);
		frame.getContentPane().add(btnCargarManzanas);
		
		JButton btnAsignarManzanas = new JButton("Asignar manzanas a censistas");
		btnAsignarManzanas.setFont(new Font("Verdana", Font.PLAIN, 13));
		btnAsignarManzanas.setBounds(276, 619, 436, 34);
		frame.getContentPane().add(btnAsignarManzanas);
		
	}
	
	private ArrayList<Coordinate> obtenerCoordenadasVecinoManzana(Manzana manzana, Manzana vecino) {
		// MapPolygon no dibuja aquellas listas de coordenadas de menos de 2 elementos, por eso, repetimos la coordenada del vecino para que se dibuje una linea recta entre ambas manzanas
		ArrayList<Coordinate> coordenadasVecino =  new ArrayList<Coordinate>();
		Coordenada coordManzana = manzana.getCoordenada();
		Coordenada coordVecino = vecino.getCoordenada();
		
		coordenadasVecino.add(new Coordinate(coordManzana.getX(),coordManzana.getY()));
		coordenadasVecino.add(new Coordinate(coordVecino.getX(),coordVecino.getY()));
		coordenadasVecino.add(new Coordinate(coordVecino.getX(),coordVecino.getY()));
		
		return coordenadasVecino;
	}
	
	private String formateoPath() {
		File archivo = selectorArchivos.getSelectedFile();
		String path = archivo.getAbsolutePath().replaceAll("\\\\", "/");
		return path;
	}

	private void agregarMapPolygonPorCadaVecino(Manzana manzana) {
		ArrayList<Coordinate> vecinosManzana;
		for(Integer manzanaVecina : radioCensal.manzanasVecinas(manzana.getNroManzana())) {
			vecinosManzana = obtenerCoordenadasVecinoManzana(manzana, radioCensal.getManzana(manzanaVecina));
			mapa.addMapPolygon(new MapPolygonImpl(vecinosManzana));
		}
	}

	private void agregarMapMarkerManzana(Manzana manzana) {
		Coordenada coordManzana = manzana.getCoordenada();
		MapMarkerDot marcadorManzana = new MapMarkerDot(Color.RED, coordManzana.getX(), coordManzana.getY());
		marcadorManzana.setName(""+manzana.getNroManzana());
		mapa.addMapMarker(marcadorManzana);
	}

	private void removerRegistrosTabla(DefaultTableModel modeloTabla) {
		modeloTabla.getDataVector().removeAllElements();
		modeloTabla.fireTableDataChanged();
	}
	
	public class ImagenTabla extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable JTable, Object value, boolean bln, boolean bln1, int i, int j) {
			if(value instanceof JLabel) {
				JLabel lbl = (JLabel) value;
				return lbl;
			}
			
			return super.getTableCellRendererComponent(JTable, value, bln, bln1, i, j);
		}

	}
	
	
}
