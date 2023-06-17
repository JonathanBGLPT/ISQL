package interfaz;

import java.awt.*;
import javax.swing.*;
import fabricas.*;
import sql.*;
import java.io.File;

public class VentanaPrincipal extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private Dimension pantallaDim;
	private File basesDeDatos;
	private String nombreBD;
	private transient ConexionPrincipal sql;
	private transient FabricaTablasBD fabricaTablasBD;
	private transient FabricaTablas fabricaTablas;
	private transient FabricaConsultas fabricaConsultas;

	private JPanel menuPrincipal;
	private JPanel tablasBD;
	private JPanel tablas;
	private JPanel consultas;
	
	private Font fuente;

	// Crea la ventana principal
    public VentanaPrincipal () {

    	pantallaDim = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.8), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.8));
		basesDeDatos = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "NombreProvisional" + File.separator + "ISQL" + File.separator + "BBDD");
    	basesDeDatos.mkdirs();
		sql = new ConexionPrincipal();
		fuente = new Font("Arial", Font.PLAIN, (int)(pantallaDim.getHeight()*0.025));
		fabricaTablasBD = new FabricaTablasBD(pantallaDim, sql);
		fabricaTablas = new FabricaTablas(pantallaDim, sql);
		fabricaConsultas = new FabricaConsultas(pantallaDim, sql);

    	setTitle("ISQL");
    	setSize((int)pantallaDim.getWidth(),(int)pantallaDim.getHeight()+25);
		setLocation((int)(pantallaDim.getWidth()*0.125),(int)(pantallaDim.getHeight()*0.125));
		setResizable(false);
		setDefaultCloseOperation(3);

		cargarMenuPrincipal();
    }

	// Crea el menu principal
	private void cargarMenuPrincipal() {

		menuPrincipal = new JPanel();
		menuPrincipal.setLayout(null);
		menuPrincipal.setBackground(new Color(179, 210, 236));

		JButton botonCrearBD = new JButton("Crear BD");
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
					cargarInterfaz();

				} else JOptionPane.showMessageDialog(null, "La base de datos introducida ya existe.");
			}
		});
		menuPrincipal.add(botonCrearBD);

		JButton botonCargarBD = new JButton("Cargar BD");
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
					cargarInterfaz();

				} else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});
		menuPrincipal.add(botonCargarBD);

		add(menuPrincipal);
	}

	// Muestra el resto de la interfaz
	private void cargarInterfaz() {

		cargarTablasBD();
		cargarTablas(0);
		cargarConsultas();
	}

	// Muestra las tablas de la BD
	private void cargarTablasBD() {

		if (tablasBD != null) menuPrincipal.remove(tablasBD);
		tablasBD = new JPanel();
		tablasBD.setLayout(null);
		tablasBD.setBackground(new Color(179, 210, 236));
		Auxiliar.calcularSize(tablasBD, pantallaDim, 0.22, 0.93);
		Auxiliar.calcularLocation(tablasBD, pantallaDim, false, 0, 0.07);

		JLabel tituloBD = new JLabel("BD: " + nombreBD);
		tituloBD.setFont(fuente);
		Auxiliar.calcularSize(tituloBD, pantallaDim, 0.2, 0.06);
		Auxiliar.calcularLocation(tituloBD, pantallaDim, false, 0.008, 0);
		tablasBD.add(tituloBD);

		tablasBD.add(fabricaTablasBD.setTablasBD());

		menuPrincipal.add(tablasBD);
	}

	// Muestra la tabla actual
	private void cargarTablas(int select) {

		if(tablas != null) menuPrincipal.remove(tablas);
		tablas = new JPanel();
		tablas.setLayout(null);
		tablas.setBackground(new Color(163, 196, 224));
		Auxiliar.calcularSize(tablas, pantallaDim, 0.33, 1);
		Auxiliar.calcularLocation(tablas, pantallaDim, false, 0.22, 0);

		JButton botonCrearTabla = new JButton("Crear Tabla");
        Auxiliar.calcularSize(botonCrearTabla, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonCrearTabla, pantallaDim, false, 0.01, 0.01);
		botonCrearTabla.setFont(fuente);
    	botonCrearTabla.addActionListener(accion -> cargarTablas(1));
		tablas.add(botonCrearTabla);


		JButton botonModificarTabla = new JButton("Modificar");
        Auxiliar.calcularSize(botonModificarTabla, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonModificarTabla, pantallaDim, false, 0.115, 0.01);
		botonModificarTabla.setFont(fuente);
    	botonModificarTabla.addActionListener(accion -> cargarTablas(2));
		tablas.add(botonModificarTabla);


		JButton botonBorrarTabla = new JButton("Borrar");
        Auxiliar.calcularSize(botonBorrarTabla, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonBorrarTabla, pantallaDim, false, 0.22, 0.01);
		botonBorrarTabla.setFont(fuente);
    	botonBorrarTabla.addActionListener(accion -> {

			// IMPLEMENTAR
		});
		tablas.add(botonBorrarTabla);

		tablas.add((select == 1)? fabricaTablas.setTablasCrear() : (select == 2)? fabricaTablas.setTablasModificar() : fabricaTablas.setTablas(null));

		menuPrincipal.add(tablas);
	}

	// Muestra las consultas
	private void cargarConsultas() {

		if(consultas != null) menuPrincipal.remove(consultas);
		consultas = new JPanel();
		consultas.setLayout(null);
		consultas.setBackground(new Color(179, 210, 236));
		Auxiliar.calcularSize(consultas, pantallaDim, 0.45, 1);
		Auxiliar.calcularLocation(consultas, pantallaDim, false, 0.55, 0);

		JButton botonFiltrar = new JButton("Filtrar");
        Auxiliar.calcularSize(botonFiltrar, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonFiltrar, pantallaDim, false, 0.05, 0.01);
		botonFiltrar.setFont(fuente);
    	botonFiltrar.addActionListener(accion -> {

			// IMPLEMENTAR
		});
		consultas.add(botonFiltrar);


		JButton botonConsultar = new JButton("Consultar");
        Auxiliar.calcularSize(botonConsultar, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonConsultar, pantallaDim, false, 0.175, 0.01);
		botonConsultar.setFont(fuente);
    	botonConsultar.addActionListener(accion -> {

			// IMPLEMENTAR
			
		});
		consultas.add(botonConsultar);


		JButton botonChatGPT = new JButton("ChatGPT");
        Auxiliar.calcularSize(botonChatGPT, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonChatGPT, pantallaDim, false, 0.3, 0.01);
		botonChatGPT.setFont(fuente);
    	botonChatGPT.addActionListener(accion -> {

			// IMPLEMENTAR CONSULTAS CON CHATGTP
		});
		botonChatGPT.setEnabled(false);
		consultas.add(botonChatGPT);


		JButton botonIntroducirDatos = new JButton("Introducir Datos Manualmente");
        Auxiliar.calcularSize(botonIntroducirDatos, pantallaDim, 0.225, 0.05);
		Auxiliar.calcularLocation(botonIntroducirDatos, pantallaDim, false, 0.05, 0.065);
		botonIntroducirDatos.setFont(fuente);
    	botonIntroducirDatos.addActionListener(accion -> {

			// IMPLEMENTAR
		});
		consultas.add(botonIntroducirDatos);


		JButton botonCargarXML = new JButton("Cargar XML");
        Auxiliar.calcularSize(botonCargarXML, pantallaDim, 0.1, 0.05);
		Auxiliar.calcularLocation(botonCargarXML, pantallaDim, false, 0.3, 0.065);
		botonCargarXML.setFont(fuente);
    	botonCargarXML.addActionListener(accion -> {

			// IMPLEMENTAR CARGA DE UN XML
		});
		botonCargarXML.setEnabled(false);
		consultas.add(botonCargarXML);

		consultas.add(fabricaConsultas.setConsultasVacio());

		menuPrincipal.add(consultas);
	}


	public static void main(String[] args) {
		
		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.setVisible(true);
		ventanaPrincipal.run();
	}

	@Override
	public void run() {
		
		while(true) {

			try {

				Thread.sleep(50);
				revalidate();
				repaint();

			} catch (InterruptedException e) { JOptionPane.showMessageDialog(null, "Ha ocurrido un error dibujando la ventana."); }
		}
	}
}