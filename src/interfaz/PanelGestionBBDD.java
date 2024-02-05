package interfaz;

import java.io.File;
import javax.swing.*;

class PanelGestionBBDD extends JPanel {

    private File carpetaBBDD;
    private String nombreBD;

    public PanelGestionBBDD (VentanaPrincipal ventanaPrincipal) {

        setSize(Auxiliar.dimensionVentana);
        setBackground(Auxiliar.colorAzulPalido);
        setLayout(null);

        carpetaBBDD = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "ISQL");
        carpetaBBDD.mkdirs();

        // Boton para crear una nueva BBDD
        JButton botonCrearBBDD = new JButton("Crear BBDD");
        botonCrearBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(botonCrearBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(botonCrearBBDD, 0.35, 0.5);
        botonCrearBBDD.addActionListener(accion -> {

			nombreBD = JOptionPane.showInputDialog(null, "Ingrese el nombre de la nueva base de datos:");
			if (nombreBD != null) {

				File[] archivos = carpetaBBDD.listFiles();
				boolean valido = true;
				for (int f = 0; f < archivos.length && valido; f++) valido = !(archivos[f].getName().equals(nombreBD + ".db"));

				if (valido) {

					Auxiliar.conexionSQL.cerrarConexion();
					Auxiliar.conexionSQL.abrirConexion(carpetaBBDD.getAbsolutePath() + File.separator + nombreBD + ".db");
                    ventanaPrincipal.setTitle(nombreBD);
                    ventanaPrincipal.setPanelActual(1);

				} else JOptionPane.showMessageDialog(null, "La base de datos introducida ya existe.");
			}
		});
        add(botonCrearBBDD);

        // Boton para abrir una BBDD existente
        JButton botonAbrirBBDD = new JButton("Abrir BBDD");
        botonAbrirBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(botonAbrirBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(botonAbrirBBDD, 0.35, 0.65);
        add(botonAbrirBBDD);
        botonAbrirBBDD.addActionListener(accion -> {

			JFileChooser selectorDeCarpeta = new JFileChooser();
			selectorDeCarpeta.setCurrentDirectory(carpetaBBDD);

            if (selectorDeCarpeta.showOpenDialog(this) == 0) {

                String ruta = selectorDeCarpeta.getSelectedFile().getAbsolutePath(); 
				if (ruta.substring(ruta.length()-3, ruta.length()).equals(".db")) {

					Auxiliar.conexionSQL.cerrarConexion();
					Auxiliar.conexionSQL.abrirConexion(ruta);
                    ventanaPrincipal.setTitle(ruta.substring(selectorDeCarpeta.getSelectedFile().getParent().length()+1, ruta.length()-3));
					ventanaPrincipal.setPanelActual(1);
                    
                } else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});

        // Boton para borrar una BBDD existente
        JButton botonBorrarBBDD = new JButton("Borrar BBDD");
        botonBorrarBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(botonBorrarBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(botonBorrarBBDD, 0.35, 0.8);
        botonBorrarBBDD.addActionListener(accion -> {

			JFileChooser selectorDeCarpeta = new JFileChooser();
			selectorDeCarpeta.setCurrentDirectory(carpetaBBDD);

            if (selectorDeCarpeta.showOpenDialog(this) == 0) {

                String ruta = selectorDeCarpeta.getSelectedFile().getAbsolutePath(); 
				if (ruta.substring(ruta.length()-3, ruta.length()).equals(".db")) {

					new File(ruta).delete();
                    
                } else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});
        add(botonBorrarBBDD);
    }
}
