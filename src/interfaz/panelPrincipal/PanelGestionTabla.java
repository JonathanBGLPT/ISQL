package interfaz.panelPrincipal;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.subPaneles.*;

public class PanelGestionTabla extends JPanel {
    
    private static PanelGestionTablaBotones panelBotones;
    private PanelPrincipal panelPrincipal;
    public JPanel panelDeGestiones;
    public String nombreTablaSeleccionada;
    public ArrayList<String[]> datosMostrarTabla;

    public PanelGestionTabla (PanelPrincipal panelPrin) {

        setBackground(null); 
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.4, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.21, 0.01);
        setLayout(null);

        // Panel de gestiones
        panelDeGestiones = new JPanel();
        panelDeGestiones.setBackground(Auxiliar.colorGrisFondo);
        Auxiliar.calcularSize(getSize(), panelDeGestiones, 0.98, 0.76);
        Auxiliar.calcularLocation(getSize(), panelDeGestiones, 0.01, 0.07);
        panelDeGestiones.setBorder(BorderFactory.createLineBorder(Auxiliar.colorGrisFondo, 2));
        panelDeGestiones.setLayout(null);

        nombreTablaSeleccionada = "";
        panelPrincipal = panelPrin;
        panelBotones = new PanelGestionTablaBotones(panelPrincipal); 
    }

    public void actualizarPanelGestionTabla () {

        removeAll();
        panelBotones.actualizarTablaSeleccionada(nombreTablaSeleccionada);
        add(panelBotones);
        add(panelDeGestiones);

        // Boton editar nombre de la tabla
        JButton botonCambiarNombreTabla = new JButton();
        botonCambiarNombreTabla.setForeground(Auxiliar.colorLetra);
        botonCambiarNombreTabla.setBackground(Auxiliar.colorGrisOscuro);
        botonCambiarNombreTabla.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCambiarNombreTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCambiarNombreTabla.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/editPencil.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()* 0.04), (int)(Auxiliar.dimensionVentana.getHeight()* 0.04), Image.SCALE_SMOOTH)));
        botonCambiarNombreTabla.setSize((int)(getSize().getWidth()*0.05), (int)(getSize().getWidth()*0.05));
        Auxiliar.calcularLocation(getSize(), botonCambiarNombreTabla, 0.94, 0.03);
        botonCambiarNombreTabla.addActionListener(accion -> {

            String nombreNuevoTabla = null;
			boolean nombreValido = nombreTablaSeleccionada.equals("") || !Auxiliar.botonesActivados;
            while (!nombreValido) {

                nombreNuevoTabla = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de la tabla '" + nombreTablaSeleccionada + "':");
                nombreValido = nombreNuevoTabla == null || (nombreNuevoTabla.length() > 0 && nombreNuevoTabla.length() <= 16);
                if (nombreNuevoTabla != null && (nombreNuevoTabla.length() == 0 || nombreNuevoTabla.length() > 16)) JOptionPane.showMessageDialog(null, "El nombre de la tabla debe contener entre 1 y 16 caracteres.");
            }

            if (nombreNuevoTabla != null) {

                ArrayList<String> nombreTablas = Auxiliar.conexionSQL.obtenerNombreTablas();
				boolean valido = true;
				for (int t = 0; t < nombreTablas.size() && valido; t++) valido = !(nombreTablas.get(t).equals(nombreNuevoTabla));

				if (valido) {

                    Auxiliar.conexionSQL.cambiarNombreTabla(nombreTablaSeleccionada, nombreNuevoTabla);
                    panelPrincipal.panelResumenTablas.actualizarPanelResumenTablas();
                    nombreTablaSeleccionada = nombreNuevoTabla;
                    elegirPanelDeGestiones(0);

				} else JOptionPane.showMessageDialog(null, "La tabla introducida ya existe.");
            }
            actualizarPanelGestionTabla();
        });
        add(botonCambiarNombreTabla);

        // Texto tabla seleccionada
        JLabel textoTablas = new JLabel("Tabla seleccionada: " + nombreTablaSeleccionada);
        textoTablas.setForeground(Auxiliar.colorLetra);
        textoTablas.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), textoTablas, 0.95, 0.03);
        Auxiliar.calcularLocation(getSize(), textoTablas, 0.025, 0.04);
        add(textoTablas);

        revalidate();
        repaint();
    }

    public void elegirPanelDeGestiones (int opcion) {

        panelDeGestiones.removeAll();
        panelPrincipal.panelConsultas.panelDeConsultas.removeAll();

        switch (opcion) {

            // Mostrar datos de la tabla
            case 0: 

                panelDeGestiones.add(new PanelMostarDatos(panelPrincipal, datosMostrarTabla, null));
                break;
            
            // Mostrar panel crear tabla
            case 1:

                datosMostrarTabla = null;
                panelDeGestiones.add(new PanelCrearTabla(panelPrincipal));
                break;
                
            // Mostrar panel modificar tabla
            case 2:

                panelDeGestiones.add(new PanelModificarTabla(panelPrincipal));
                break;

            // Mostrar panel agregar datos manualmente
            case 3:

                panelDeGestiones.add(new PanelAgregarDatos(panelPrincipal));
                break;
        }
        panelPrincipal.panelGestionTabla.actualizarPanelGestionTabla();
    }
}

class PanelGestionTablaBotones extends JPanel {

    private String nombreTablaSeleccionada;

    public PanelGestionTablaBotones (PanelPrincipal panelPrincipal) {

        nombreTablaSeleccionada = "";
        setBackground(Auxiliar.colorGrisFondo);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.4, 0.15);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0, 0.8);
        setLayout(null);

        // Boton para modificar la tabla
        JButton botonModificarTabla = new JButton("Modificar tabla");
        botonModificarTabla.setForeground(Auxiliar.colorLetra);
        botonModificarTabla.setBackground(Auxiliar.colorGrisOscuro);
        botonModificarTabla.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonModificarTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonModificarTabla.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonModificarTabla, 0.325, 0.325);
        Auxiliar.calcularLocation(getSize(), botonModificarTabla, 0, 0);
        botonModificarTabla.addActionListener(accion -> {

            if (!nombreTablaSeleccionada.equals("")) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, false);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(2);

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para modificar.");
		});
        add(botonModificarTabla);

        // Boton para eliminar la tabla
        JButton botonEliminarTabla = new JButton("Eliminar tabla");
        botonEliminarTabla.setForeground(Auxiliar.colorLetra);
        botonEliminarTabla.setBackground(Auxiliar.colorGrisOscuro);
        botonEliminarTabla.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonEliminarTabla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarTabla.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonEliminarTabla, 0.315, 0.325);
        Auxiliar.calcularLocation(getSize(), botonEliminarTabla, 0.3375, 0);
        botonEliminarTabla.addActionListener(accion -> {

            if (nombreTablaSeleccionada != null && !nombreTablaSeleccionada.equals("")) {

                String nombreTablaSeguridad = JOptionPane.showInputDialog(null, "¿Está seguro de que desea eliminar: "+ nombreTablaSeleccionada +"?\nPara ello ingrese el nombre de la tabla:");
                    
                if (nombreTablaSeguridad.equals(nombreTablaSeleccionada)) {
                    
                    Auxiliar.conexionSQL.eliminarTabla(nombreTablaSeleccionada);
                    panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = "";
                    panelPrincipal.panelGestionTabla.datosMostrarTabla = null;
                    panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
                    panelPrincipal.actualizarPanelPrincipal();

                } else JOptionPane.showMessageDialog(null, "El nombre introducido no coincide con el de la tabla.");
                    
            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para eliminar.");
		});
        add(botonEliminarTabla);

        // Boton para agregar datos de forma manual
        JButton botonAgregarDatosManual = new JButton("<html>Agregar datos<br><center>manualmente</center></html>");
        botonAgregarDatosManual.setForeground(Auxiliar.colorLetra);
        botonAgregarDatosManual.setBackground(Auxiliar.colorGrisOscuro);
        botonAgregarDatosManual.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonAgregarDatosManual.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAgregarDatosManual.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAgregarDatosManual, 0.325, 0.64);
        Auxiliar.calcularLocation(getSize(), botonAgregarDatosManual, 0, 0.36);
        botonAgregarDatosManual.addActionListener(accion -> {

            if (!nombreTablaSeleccionada.equals("")) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, false);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(3);

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para agregarle datos.");
		});
        add(botonAgregarDatosManual);

        // Boton para agregar datos desde un CSV
        JButton botonAgregarDatosDesdeCSV = new JButton("<html>Agregar datos<br><center>desde CSV</center></html>");
        botonAgregarDatosDesdeCSV.setForeground(Auxiliar.colorLetra);
        botonAgregarDatosDesdeCSV.setBackground(Auxiliar.colorGrisOscuro);
        botonAgregarDatosDesdeCSV.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonAgregarDatosDesdeCSV.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAgregarDatosDesdeCSV.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAgregarDatosDesdeCSV, 0.315, 0.64);
        Auxiliar.calcularLocation(getSize(), botonAgregarDatosDesdeCSV, 0.3375, 0.36);
        botonAgregarDatosDesdeCSV.addActionListener(accion -> {

            if (!nombreTablaSeleccionada.equals("")) {

                JFileChooser selectorDeCarpeta = new JFileChooser();
                selectorDeCarpeta.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Desktop"));
                selectorDeCarpeta.removeChoosableFileFilter(selectorDeCarpeta.getFileFilter());
                selectorDeCarpeta.setFileFilter(new FileNameExtensionFilter(".csv", "csv"));

                if (selectorDeCarpeta.showOpenDialog(this) == 0) {

                    String ruta = selectorDeCarpeta.getSelectedFile().getAbsolutePath(); 
                    if (ruta.substring(ruta.length()-4, ruta.length()).equals(".csv")) {

                        String fila = "";
                        String rutaFallidos = ruta.substring(0, ruta.length()-4) + "FilasFallidas.csv";
                        
                        try (BufferedReader lectorCSV = new BufferedReader(new FileReader(ruta));
                            FileWriter escritorCSVFallidos = new FileWriter(rutaFallidos)) {

                            String cabecera = lectorCSV.readLine();
                            escritorCSVFallidos.append(cabecera).append("\n");
                            String[] cabeceraSeparada = cabecera.split(";");
                            if (comprobarCabeceraCSV(cabeceraSeparada)) {

                                int contadorTotal = 1;
                                int contadorFallos = 0;
                                String filasFallidas = "";

                                // Creo la sentencia para insertar los valores
                                String sentenciaSQL = "INSERT INTO " + nombreTablaSeleccionada + " (";
                                for (String campo : cabeceraSeparada) sentenciaSQL += campo + ", ";
                                sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + ") VALUES (";
                                for (int c = 0; c < cabeceraSeparada.length; c++) sentenciaSQL += "?, ";
                                sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + ");";

                                // Creo el array con los tipos
                                Map<String, String> mapaTipos = new HashMap<>();
                                ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTablaSeleccionada);
                                for (String[] campo : campos) mapaTipos.put(campo[0].toLowerCase(), campo[1]);
                                String[] tipos = new String[cabeceraSeparada.length];
                                for (int c = 0; c < cabeceraSeparada.length; c++) tipos[c] = mapaTipos.get(cabeceraSeparada[c].toLowerCase());

                                while ((fila = lectorCSV.readLine()) != null) {  

                                    contadorTotal++;
                                    String[] filaSeparada = fila.split(";");
                                    String[] filaTrim = new String[cabeceraSeparada.length];
                                    for (int f = 0; f < filaSeparada.length; f++) filaTrim[f] = filaSeparada[f].trim();
                                    for (int f = filaSeparada.length; f < filaTrim.length; f++) filaTrim[f] = "";

                                    if (!Auxiliar.conexionSQL.insertarFila(sentenciaSQL, filaTrim, tipos)) {

                                        contadorFallos++;
                                        escritorCSVFallidos.append(fila).append("\n");
                                        filasFallidas += contadorTotal + ", ";
                                    }
                                }
                                JOptionPane.showMessageDialog(null, "Se han agregado " + (contadorTotal - contadorFallos - 1) + " filas y han fallado " + contadorFallos + " lineas" + ((contadorFallos == 0)? "." : ": " + filasFallidas.substring(0, filasFallidas.length()-2) + "."));
                                panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
                                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);

                                lectorCSV.close();
                                escritorCSVFallidos.close();

                                if (contadorFallos > 0) {

                                    JOptionPane.showMessageDialog(null, "Se ha generado un CSV para que puedas rellenar las filas faltantes en esta ruta: " + rutaFallidos);

                                } else new File(rutaFallidos).delete();
                            }                    
                        } catch (IOException e) { e.printStackTrace(); }
                        
                    } else JOptionPane.showMessageDialog(null, "El archivo seleccionado no es valido, debe ser un CSV.");
                } 
            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla a la que introducir los datos.");

            
		});
        add(botonAgregarDatosDesdeCSV);

        // Boton para modificar datos existentes
        JButton botonModificarDatos = new JButton("Modificar datos");
        botonModificarDatos.setForeground(Auxiliar.colorLetra);
        botonModificarDatos.setBackground(Auxiliar.colorGrisOscuro);
        botonModificarDatos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonModificarDatos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonModificarDatos.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonModificarDatos, 0.335, 0.315);
        Auxiliar.calcularLocation(getSize(), botonModificarDatos, 0.665, 0);
        botonModificarDatos.addActionListener(accion -> {

            if (panelPrincipal.panelGestionTabla.nombreTablaSeleccionada != "" && panelPrincipal.panelGestionTabla.panelDeGestiones.getComponent(0) instanceof PanelMostarDatos) {

                PanelMostarDatos panelMostrarDatos = (PanelMostarDatos)panelPrincipal.panelGestionTabla.panelDeGestiones.getComponent(0);
                panelMostrarDatos.actualizarDatos();

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para modificar datos.");
		});
        add(botonModificarDatos);

        // Boton para eliminar datos
        JButton botonEliminarDatos = new JButton("Eliminar datos");
        botonEliminarDatos.setForeground(Auxiliar.colorLetra);
        botonEliminarDatos.setBackground(Auxiliar.colorGrisOscuro);
        botonEliminarDatos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonEliminarDatos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarDatos.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonEliminarDatos, 0.335, 0.315);
        Auxiliar.calcularLocation(getSize(), botonEliminarDatos, 0.665, 0.3425);
        botonEliminarDatos.addActionListener(accion -> {

            if (panelPrincipal.panelGestionTabla.nombreTablaSeleccionada != "" && panelPrincipal.panelGestionTabla.panelDeGestiones.getComponent(0) instanceof PanelMostarDatos) {

                PanelMostarDatos panelMostrarDatos = (PanelMostarDatos)panelPrincipal.panelGestionTabla.panelDeGestiones.getComponent(0);
                panelMostrarDatos.eliminarDatos();

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para eliminar datos.");
		});
        add(botonEliminarDatos);

        // Boton para generar CSV
        JButton botonGenerarCSV = new JButton("Generar CSV");
        botonGenerarCSV.setForeground(Auxiliar.colorLetra);
        botonGenerarCSV.setBackground(Auxiliar.colorGrisOscuro);
        botonGenerarCSV.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonGenerarCSV.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonGenerarCSV.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonGenerarCSV, 0.335, 0.315);
        Auxiliar.calcularLocation(getSize(), botonGenerarCSV, 0.665, 0.685);
        botonGenerarCSV.addActionListener(accion -> {

            if (nombreTablaSeleccionada != "") {

                try (FileWriter escritorCSV = new FileWriter(nombreTablaSeleccionada + ".csv")) {
                    
                    ArrayList<String[]> cabecera = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTablaSeleccionada);
                    for (String[] campoCabecera : cabecera) escritorCSV.append(campoCabecera[0]).append(";");
                    escritorCSV.append("\n");

                    ArrayList<String[]> datos = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(nombreTablaSeleccionada);
                    for (String[] fila : datos) {

                        for (String dato : fila) escritorCSV.append(dato).append(";");
                        escritorCSV.append("\n");
                    }

                } catch (IOException e) { e.printStackTrace(); }

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para generar el CSV.");
		});
        add(botonGenerarCSV);
    }

    public void actualizarTablaSeleccionada(String nombreTabla) {

        nombreTablaSeleccionada = nombreTabla;
    }

    private boolean comprobarCabeceraCSV(String[] cabecera) {

        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTablaSeleccionada);
        Set<String> camposBusqueda = new HashSet<>();
        for (String[] campo : campos) if (!campo[0].equals("id_" + nombreTablaSeleccionada)) camposBusqueda.add(campo[0].toLowerCase());
        boolean resultado = cabecera.length == camposBusqueda.size();

        if (resultado) {

            for (String campoCabecera : cabecera) camposBusqueda.remove(campoCabecera.toLowerCase());
            if (camposBusqueda.size() > 0) {

                String camposFaltantes = "";
                for (String campoFaltante : camposBusqueda) camposFaltantes += campoFaltante + ", ";
                JOptionPane.showMessageDialog(null, "Faltan los siguientes campos en el CSV: " + camposFaltantes.substring(0, camposFaltantes.length()-2) + ".");
                resultado = false;
            }

        } else JOptionPane.showMessageDialog(null, "La cabecera del CSV debe contener todos los campos excepto 'id_" +  nombreTablaSeleccionada + "'.");

        return resultado;
    }
}