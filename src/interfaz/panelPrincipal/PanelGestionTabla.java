package interfaz.panelPrincipal;

import java.awt.Image;

import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.subPaneles.*;

public class PanelGestionTabla extends JPanel {
    
    private static PanelGestionTablaBotones panelBotones;
    private PanelPrincipal panelPrincipal;
    public JPanel panelDeGestiones;
    public String nombreTablaSeleccionada;

    public PanelGestionTabla (PanelPrincipal panelPrin) {

        setBackground(null); 
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.45, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.21, 0.01);
        setLayout(null);

        // Panel de gestiones
        panelDeGestiones = new JPanel();
        Auxiliar.calcularSize(getSize(), panelDeGestiones, 0.98, 0.76);
        Auxiliar.calcularLocation(getSize(), panelDeGestiones, 0.01, 0.07);
        panelDeGestiones.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
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
        botonCambiarNombreTabla.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/editPencil.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()* 0.04), (int)(Auxiliar.dimensionVentana.getHeight()* 0.04), Image.SCALE_SMOOTH)));
        botonCambiarNombreTabla.addActionListener(accion -> {

            String nombreNuevoTabla = null;
			boolean nombreValido = false;
            while (!nombreValido) {

                nombreNuevoTabla = JOptionPane.showInputDialog(null, "Ingrese el nuevo nombre de la tabla:");
                nombreValido = nombreNuevoTabla == null || (nombreNuevoTabla.length() > 0 && nombreNuevoTabla.length() <= 16);
                if (nombreNuevoTabla != null && (nombreNuevoTabla.length() == 0 || nombreNuevoTabla.length() > 16)) JOptionPane.showMessageDialog(null, "El nombre de la tabla debe contener entre 1 y 16 caracteres.");
            }

            if (nombreNuevoTabla != null) {

                Auxiliar.conexionSQL.cambiarNombreTabla(nombreTablaSeleccionada, nombreNuevoTabla);
                panelPrincipal.panelResumenTablas.actualizarPanelResumenTablas();
                nombreTablaSeleccionada = nombreNuevoTabla;
            }
            revalidate();
            repaint();
        });
        add(botonCambiarNombreTabla);

        // Texto tabla seleccionada
        JLabel textoTablas = new JLabel("Tabla seleccionada: " + nombreTablaSeleccionada);
        textoTablas.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), textoTablas, 0.95, 0.03);
        Auxiliar.calcularLocation(getSize(), textoTablas, 0.025, 0.04);
        add(textoTablas);

        revalidate();
        repaint();
    }

    public void elegirPanelDeGestiones (int opcion) {

        panelPrincipal.panelGestionTabla.panelDeGestiones.removeAll();
        
        switch (opcion) {

            // Mostrar datos de la tabla
            case 0: 

                panelPrincipal.panelGestionTabla.panelDeGestiones.add(new PanelMostarDatos(panelPrincipal));
                break;
            
            // Mostrar panel crear tabla
            case 1:

                panelPrincipal.panelGestionTabla.panelDeGestiones.add(new PanelCrearTabla(panelPrincipal));
                break;
                
            // Mostrar panel modificar tabla
            case 2:

                panelPrincipal.panelGestionTabla.panelDeGestiones.add(new PanelModificarTabla(panelPrincipal));
                break;

            // Mostrar panel agregar datos manualmente
            case 3:

                /// IMPLEMENTAR
                break;

            // Mostrar panel modificar datos
            case 4:

                /// IMPLEMENTAR
                break;

            // Mostrar panel eliminar datos
            case 5:

                /// IMPLEMENTAR
                break;
        }
        panelPrincipal.panelGestionTabla.actualizarPanelGestionTabla();
    }
}

class PanelGestionTablaBotones extends JPanel {

    private String nombreTablaSeleccionada;

    public PanelGestionTablaBotones (PanelPrincipal panelPrincipal) {

        nombreTablaSeleccionada = "";
        setBackground(null);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.45, 0.15);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0, 0.8);
        setLayout(null);

        // Boton para modificar la tabla
        JButton botonModificarTabla = new JButton("Modificar tabla");
        botonModificarTabla.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonModificarTabla, 0.14, 0.05);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonModificarTabla, 0.005, 0.016);
        botonModificarTabla.addActionListener(accion -> {

            if (nombreTablaSeleccionada != null && !nombreTablaSeleccionada.equals("")) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, false);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(2);

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para modificar.");
		});
        add(botonModificarTabla);

        // Boton para eliminar la tabla
        JButton botonEliminarTabla = new JButton("Eliminar tabla");
        botonEliminarTabla.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonEliminarTabla, 0.14, 0.05);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonEliminarTabla, 0.005, 0.083);
        botonEliminarTabla.addActionListener(accion -> {

            if (nombreTablaSeleccionada != null && !nombreTablaSeleccionada.equals("")) {

                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la tabla: "+ nombreTablaSeleccionada +"?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (respuesta == JOptionPane.YES_OPTION) Auxiliar.conexionSQL.eliminarTabla(nombreTablaSeleccionada);
                panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = "";
                panelPrincipal.panelGestionTabla.panelDeGestiones.removeAll();
                panelPrincipal.actualizarPanelPrincipal();

            } else JOptionPane.showMessageDialog(null, "Debes seleccionar una tabla para eliminar.");
		});
        add(botonEliminarTabla);

        // Boton para modificar datos existentes
        JButton botonModificarDatos = new JButton("Modificar datos");
        botonModificarDatos.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonModificarDatos, 0.14, 0.05);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonModificarDatos, 0.155, 0);
        botonModificarDatos.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonModificarDatos);

        // Boton para eliminar datos
        JButton botonEliminarDatos = new JButton("Eliminar datos");
        botonEliminarDatos.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonEliminarDatos, 0.14, 0.05);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonEliminarDatos, 0.305, 0);
        botonEliminarDatos.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonEliminarDatos);

        // Boton para agregar datos de forma manual
        JButton botonAgregarDatosManual = new JButton("<html>Agregar datos<br><center>manualmente</center></html>");
        botonAgregarDatosManual.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonAgregarDatosManual, 0.14, 0.09);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonAgregarDatosManual, 0.155, 0.06);
        botonAgregarDatosManual.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonAgregarDatosManual);

        // Boton para agregar datos desde un CSV o un XLSX
        JButton botonAgregarDatosDesdeArchivo = new JButton("<html>Agregar datos<br><center>desde archivo</center></html>");
        botonAgregarDatosDesdeArchivo.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, botonAgregarDatosDesdeArchivo, 0.14, 0.09);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, botonAgregarDatosDesdeArchivo, 0.305, 0.06);
        botonAgregarDatosDesdeArchivo.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonAgregarDatosDesdeArchivo);
    }

    public void actualizarTablaSeleccionada(String nombreTabla) {

        nombreTablaSeleccionada = nombreTabla;
    }
}