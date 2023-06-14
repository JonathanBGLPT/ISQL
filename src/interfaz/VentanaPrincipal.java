package interfaz;

import java.awt.*;
import javax.swing.*;
import sql.*;
import java.io.File;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private Dimension pantallaDim;
	private File basesDeDatos;
	private transient ConexionPrincipal sql;
	private Font fuente;

	JPanel menuPrincipal;

    public VentanaPrincipal () {

    	pantallaDim = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.6), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.6));
		basesDeDatos = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "NombreProvisional" + File.separator + "ISQL" + File.separator + "BBDD");
    	basesDeDatos.mkdirs();
		sql = new ConexionPrincipal();
		fuente = new Font("Arial", Font.PLAIN, (int)(pantallaDim.getHeight()*0.025));

    	setTitle("ISQL");
    	setSize(pantallaDim);
		setLocation((int)(pantallaDim.getWidth()*0.3),(int)(pantallaDim.getHeight()*0.3));
		setResizable(false);
		setDefaultCloseOperation(3);
		crearMenuPrincipal();
    }


	private void crearMenuPrincipal() {

		menuPrincipal = new JPanel();

        JButton botonCrearBBDD = new JButton("Crear BBDD");

        Auxiliar.calcularSize(botonCrearBBDD, pantallaDim, 0.15, 0.08);
		Auxiliar.calcularLocation(botonCrearBBDD, pantallaDim, true,0.5,0.75);
		botonCrearBBDD.setFont(fuente);
    	botonCrearBBDD.addActionListener(accion -> {

			sql.abrirConexion(basesDeDatos.getAbsolutePath() + File.separator + "prueba");
		});
		menuPrincipal.add(botonCrearBBDD);

		add(menuPrincipal);
	}
	public static void main(String[] args) {

		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.setVisible(true);
	}
}