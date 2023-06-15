package interfaz;

import java.awt.*;
import javax.swing.*;

import sql.*;
import java.io.File;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private Dimension pantallaDim;
	private File basesDeDatos;
	private String nombreBD;
	private transient ConexionPrincipal sql;
	private Font fuente;

	JPanel menuPrincipal;
	JLabel tituloBD;
	JPanel tablasBD;

	// Crea la ventana principal
    public VentanaPrincipal () {

    	pantallaDim = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.8), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.8));
		basesDeDatos = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "NombreProvisional" + File.separator + "ISQL" + File.separator + "BBDD");
    	basesDeDatos.mkdirs();
		sql = new ConexionPrincipal();
		fuente = new Font("Arial", Font.PLAIN, (int)(pantallaDim.getHeight()*0.025));

    	setTitle("ISQL");
    	setSize(pantallaDim);
		setLocation((int)(pantallaDim.getWidth()*0.125),(int)(pantallaDim.getHeight()*0.125));
		setResizable(false);
		setDefaultCloseOperation(3);

		crearMenuPrincipal();
    }

	// Crea el menu principal
	private void crearMenuPrincipal() {

		menuPrincipal = new JPanel();
		menuPrincipal.setLayout(null);
		menuPrincipal.setBackground(new Color(179, 210, 236));

        JButton botonCrearBD = new JButton("Crear BD");
		JButton botonCargarBD = new JButton("Cargar BD");

		// BOTON: Crear una base de datos
        Auxiliar.calcularSize(botonCrearBD, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonCrearBD, pantallaDim, false, 0.005, 0.01);
		botonCrearBD.setFont(fuente);
    	botonCrearBD.addActionListener(accion -> {

			nombreBD = JOptionPane.showInputDialog(null, "Ingrese el nombre de la nueva base de datos:");
			if (nombreBD != null) {

				File[] archivos = basesDeDatos.listFiles();
				boolean valido = true;
				for (int f = 0; f < archivos.length && valido; f++) valido = !(archivos[f].getName().equals(nombreBD + ".db"));

				if (valido) {

					sql.cerrarConexion();
					sql.abrirConexion(basesDeDatos.getAbsolutePath() + File.separator + nombreBD + ".db");
					cargarTitulo();

				} else JOptionPane.showMessageDialog(null, "La base de datos introducida ya existe.");
			}
		});
		menuPrincipal.add(botonCrearBD);

		// BOTON: Cargar una base de datos
		Auxiliar.calcularSize(botonCargarBD, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonCargarBD, pantallaDim, false, 0.11, 0.01);
		botonCargarBD.setFont(fuente);
    	botonCargarBD.addActionListener(accion -> {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(basesDeDatos);

			if (fileChooser.showOpenDialog(this) == 0) {

				String path = fileChooser.getSelectedFile().getAbsolutePath();
				nombreBD = path.substring(fileChooser.getSelectedFile().getParent().length()+1, path.length()-3);
				if (path.substring(path.length()-3, path.length()).equals(".db")) {
					
					sql.cerrarConexion();
					sql.abrirConexion(path);
					cargarTitulo();

				} else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});
		menuPrincipal.add(botonCargarBD);

		crearTablasBD();

		add(menuPrincipal);
	}

	// Carga el titulo de la BD
	private void cargarTitulo() {

		tituloBD = new JLabel("BD: " + nombreBD);

		tituloBD.setFont(fuente);
		tituloBD.setForeground(new Color(105, 119, 157));
		Auxiliar.calcularSize(tituloBD, pantallaDim, 0.2, 0.06);
		Auxiliar.calcularLocation(tituloBD, pantallaDim, false, 0.008, 0.07);

		menuPrincipal.add(tituloBD);
		menuPrincipal.repaint(tituloBD.getX(),tituloBD.getY(),tituloBD.getWidth(),tituloBD.getHeight());
	}

	// Muestra las tablas de la BD
	private void crearTablasBD() {

		tablasBD = new JPanel();

		tablasBD.setLayout(null);
		tablasBD.setBackground(Color.WHITE);
		tablasBD.setBorder(BorderFactory.createLineBorder(new Color(122, 138, 153)));
		Auxiliar.calcularSize(tablasBD, pantallaDim, 0.205, 0.84);
		Auxiliar.calcularLocation(tablasBD, pantallaDim, false, 0.005, 0.12);
		menuPrincipal.add(tablasBD);
	}


	public static void main(String[] args) {

		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.setVisible(true);
	}
}