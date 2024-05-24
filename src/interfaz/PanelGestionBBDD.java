package interfaz;

import java.io.File;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelGestionBBDD extends JPanel {

    private File carpetaBBDD;
    private String nombreBD;

    public PanelGestionBBDD (GestorVentanaPrincipal ventanaPrincipal) {

        setSize(Auxiliar.dimensionVentana);
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        carpetaBBDD = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "ISQL");
        carpetaBBDD.mkdirs();

        // Icono de la aplicacion
        JLabel iconoISQL = new JLabel();
        iconoISQL.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/logo.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()*0.4), (int)(Auxiliar.dimensionVentana.getHeight()*0.4), Image.SCALE_SMOOTH)));
        Auxiliar.calcularSize(getSize(), iconoISQL, 0.4, 0.4);
        Auxiliar.calcularLocation(getSize(), iconoISQL, 0.385, 0.05);
        add(iconoISQL);

        // Boton para crear una nueva BBDD
        JButton botonCrearBBDD = new JButton("Crear una Base de Datos");
        botonCrearBBDD.setForeground(Auxiliar.colorLetra);
        botonCrearBBDD.setBackground(Auxiliar.colorGrisOscuro);
        botonCrearBBDD.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCrearBBDD.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCrearBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonCrearBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(getSize(), botonCrearBBDD, 0.35, 0.5);
        botonCrearBBDD.addActionListener(accion -> {

			nombreBD = JOptionPane.showInputDialog(null, "Ingrese el nombre de la nueva base de datos:");
			if (nombreBD != null) {

				File[] archivos = carpetaBBDD.listFiles();
				boolean valido = true;
				for (int f = 0; f < archivos.length && valido; f++) valido = !(archivos[f].getName().equals(nombreBD + ".db"));

				if (valido) {

					Auxiliar.conexionSQL.cerrarConexion();
					Auxiliar.conexionSQL.abrirConexion(carpetaBBDD.getAbsolutePath() + File.separator + nombreBD + ".db");
                    ventanaPrincipal.mostrarPanelPrincipal(nombreBD);

				} else JOptionPane.showMessageDialog(null, "La base de datos introducida ya existe.");
			}
		});
        add(botonCrearBBDD);

        // Boton para abrir una BBDD existente
        JButton botonAbrirBBDD = new JButton("Abrir una Base de Datos");
        botonAbrirBBDD.setForeground(Auxiliar.colorLetra);
        botonAbrirBBDD.setBackground(Auxiliar.colorGrisOscuro);
        botonAbrirBBDD.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonAbrirBBDD.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAbrirBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAbrirBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(getSize(), botonAbrirBBDD, 0.35, 0.65);
        botonAbrirBBDD.addActionListener(accion -> {

			JFileChooser selectorDeCarpeta = new JFileChooser();
			selectorDeCarpeta.setCurrentDirectory(carpetaBBDD);
            selectorDeCarpeta.removeChoosableFileFilter(selectorDeCarpeta.getFileFilter());
            selectorDeCarpeta.setFileFilter(new FileNameExtensionFilter(".db", "db"));

            if (selectorDeCarpeta.showOpenDialog(this) == 0) {

                String ruta = selectorDeCarpeta.getSelectedFile().getAbsolutePath(); 
				if (ruta.substring(ruta.length()-3, ruta.length()).equals(".db")) {

					Auxiliar.conexionSQL.cerrarConexion();
					Auxiliar.conexionSQL.abrirConexion(ruta);
					ventanaPrincipal.mostrarPanelPrincipal(ruta.substring(selectorDeCarpeta.getSelectedFile().getParent().length()+1, ruta.length()-3));
                    
                } else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});
        add(botonAbrirBBDD);

        // Boton para borrar una BBDD existente
        JButton botonBorrarBBDD = new JButton("Borrar una Base de Datos");
        botonBorrarBBDD.setForeground(Auxiliar.colorLetra);
        botonBorrarBBDD.setBackground(Auxiliar.colorGrisOscuro);
        botonBorrarBBDD.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonBorrarBBDD.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonBorrarBBDD.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonBorrarBBDD, 0.3, 0.1);
        Auxiliar.calcularLocation(getSize(), botonBorrarBBDD, 0.35, 0.8);
        botonBorrarBBDD.addActionListener(accion -> {

			JFileChooser selectorDeCarpeta = new JFileChooser();
			selectorDeCarpeta.setCurrentDirectory(carpetaBBDD);
            selectorDeCarpeta.removeChoosableFileFilter(selectorDeCarpeta.getFileFilter());
            selectorDeCarpeta.setFileFilter(new FileNameExtensionFilter(".db", "db"));

            if (selectorDeCarpeta.showOpenDialog(this) == 0) {

                String ruta = selectorDeCarpeta.getSelectedFile().getAbsolutePath(); 
				if (ruta.substring(ruta.length()-3, ruta.length()).equals(".db")) {

                    nombreBD = ruta.substring(selectorDeCarpeta.getSelectedFile().getParent().length()+1, ruta.length()-3);
                    String nombreBDSeguridad = JOptionPane.showInputDialog(null, "¿Está seguro de que desea eliminar: "+ nombreBD +"?\nPara ello ingrese el nombre de la base de datos:");
                    
                    if (nombreBDSeguridad.equals(nombreBD)) {
                        
                        new File(ruta).delete();

                    } else JOptionPane.showMessageDialog(null, "El nombre introducido no coincide con el de la base de datos.");
					
                } else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es una base de datos.");
			} 
		});
        add(botonBorrarBBDD);
    }
}
