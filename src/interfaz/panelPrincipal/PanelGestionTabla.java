package interfaz.panelPrincipal;

import javax.swing.*;
import interfaz.Auxiliar;

public class PanelGestionTabla extends JPanel {
    
    private static PanelGestionTablaBotones panelBotones;

    public PanelGestionTabla (PanelPrincipal panelPrincipal) {

        panelBotones = new PanelGestionTablaBotones(panelPrincipal); 
        setBackground(java.awt.Color.RED); /// CAMBIAR A NULL
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.45, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.21, 0.01);
        setLayout(null);
    }

    public void actualizarPanelGestionTabla (String nombreTabla) {

        removeAll();
        panelBotones.actualizarTablaSeleccionada(nombreTabla);
        add(panelBotones);

        // Texto tabla seleccionada
        JLabel textoTablas = new JLabel("Tabla seleccionada: " + nombreTabla);
        textoTablas.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), textoTablas, 0.9, 0.03);
        Auxiliar.calcularLocation(getSize(), textoTablas, 0.025, 0.04);
        add(textoTablas);

        revalidate();
        repaint();
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

            /// IMPLEMENTAR
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
                panelPrincipal.actualizarPanelPrincipal("");
            }
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