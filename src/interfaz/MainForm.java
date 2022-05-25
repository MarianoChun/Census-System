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
import java.security.KeyStore.Entry;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import logica.CargadorCensistas;
import logica.Censista;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainForm {

	private JFrame frame;
	private CargadorCensistas cargadorCensistas;
	private DefaultTableModel modeloTablaCensistas;
	private JTable tablaCensistas;
	private JFileChooser selectorArchivos;
	
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPaneCensistas = new JScrollPane();
		scrollPaneCensistas.setBounds(33, 27, 237, 162);
		frame.getContentPane().add(scrollPaneCensistas);
		
		tablaCensistas =  new JTable();
		tablaCensistas.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tablaCensistas.setBounds(33, 27, 127, 162);
		tablaCensistas.setDefaultRenderer(Object.class, new ImagenTabla());
		
		String[] nombresColumnas = {"Nombre censista", "Foto censista"};
		modeloTablaCensistas = new DefaultTableModel(null, nombresColumnas);

		tablaCensistas.setModel(modeloTablaCensistas);
		
		
		scrollPaneCensistas.setViewportView(tablaCensistas);
		
		JButton btnCargarCensistas = new JButton("Cargar censistas");
		btnCargarCensistas.setBounds(33, 200, 127, 23);
		btnCargarCensistas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File directorioAMostrar = new File(System.getProperty("user.dir") + "/src/listas_de_cencistas");
				selectorArchivos = new JFileChooser();
				selectorArchivos.setCurrentDirectory(directorioAMostrar);
				
				int valor = selectorArchivos.showOpenDialog(selectorArchivos);

				if (valor == JFileChooser.APPROVE_OPTION) {
					try {
						File archivo = selectorArchivos.getSelectedFile();
						String path = archivo.getAbsolutePath().replaceAll("\\\\", "/");
						
						cargadorCensistas = new CargadorCensistas(path);
						cargadorCensistas.cargarCensistasDesdeExcel();
						
						Map<Integer, Censista> censistas = cargadorCensistas.getCensistas();
						for(Censista censista : censistas.values()) {
							ImageIcon fotoCensista = new ImageIcon(new ImageIcon(censista.getFoto()).getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
							JLabel fotoAColocar = new JLabel();
							fotoAColocar.setIcon(fotoCensista);
							System.out.println(fotoCensista.getIconHeight());
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
		
		JButton btnCargarManzanas = new JButton("CargarManzanas");
		btnCargarManzanas.setBounds(292, 200, 113, 23);
		frame.getContentPane().add(btnCargarManzanas);
	
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
