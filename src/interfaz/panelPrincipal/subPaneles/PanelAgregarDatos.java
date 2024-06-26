package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelAgregarDatos extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<String[]> campos;
    private String[] tipos;
    private ArrayList<JTextField> datos;
    private String[] valores;
    private String sentenciaSQL;

    public PanelAgregarDatos (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2));
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        panelPrincipal = panelPrin;
        campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        campos.remove(0);

        // Creo la sentencia SQL que introduce datos en esta tabla
        sentenciaSQL = "INSERT INTO " + panelPrincipal.panelGestionTabla.nombreTablaSeleccionada + " (";
        for (String[] campo : campos) sentenciaSQL += campo[0] + ", ";
        sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + ") VALUES (";
        for (int c = 0; c < campos.size(); c++) sentenciaSQL += "?, ";
        sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + ");";

        // Guardo los tipos para poder pasarselos a insertarFila()
        tipos = new String[campos.size()];
        for (int c = 0; c < campos.size(); c++) tipos[c] = campos.get(c)[1];

        // Cabecera con el nombre del campo, tipo de dato y campo para introducir el valor
        JLabel cabeceraNombre = new JLabel("Nombre del campo");
        cabeceraNombre.setForeground(Auxiliar.colorLetra);
        cabeceraNombre.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraNombre.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraNombre, 0.355, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraNombre, 0.01, 0.01);
        add(cabeceraNombre);

        JLabel cabeceraTipoDato = new JLabel("Tipo de dato");
        cabeceraTipoDato.setForeground(Auxiliar.colorLetra);
        cabeceraTipoDato.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraTipoDato.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraTipoDato, 0.17, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraTipoDato, 0.365, 0.01);
        add(cabeceraTipoDato);

        JLabel cabeceraValorIntroducir = new JLabel("Valor a introducir");
        cabeceraValorIntroducir.setForeground(Auxiliar.colorLetra);
        cabeceraValorIntroducir.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraValorIntroducir.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraValorIntroducir, 0.44, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraValorIntroducir, 0.535, 0.01);
        add(cabeceraValorIntroducir);

        // Boton salir
        JButton botonSalir = new JButton("Salir");
        botonSalir.setForeground(Auxiliar.colorLetra);
        botonSalir.setBackground(Auxiliar.colorGrisOscuro);
        botonSalir.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonSalir.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonSalir, 0.485, 0.07);
        Auxiliar.calcularLocation(getSize(), botonSalir, 0.01, 0.92);
        botonSalir.addActionListener(accion -> {

            Auxiliar.habilitacionDeBotones(panelPrincipal, true);
            panelPrincipal.panelGestionTabla.datosMostrarTabla = Auxiliar.conexionSQL.obtenerTodosLosDatosTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
            panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
		});
        add(botonSalir);

        // Declacion del panel que contioene los input de los datos
        JPanel panelContenedorCampos = new JPanel();
        Auxiliar.calcularSize(getSize(), panelContenedorCampos, 0.98, 0.85);
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));
        panelContenedorCampos.setBackground(Auxiliar.colorGrisClaro);
        
        // Boton agregar fila 
        JButton botonAgregarFila = new JButton("Agregar datos");
        botonAgregarFila.setForeground(Auxiliar.colorLetra);
        botonAgregarFila.setBackground(Auxiliar.colorGrisOscuro);
        botonAgregarFila.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonAgregarFila.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAgregarFila.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonAgregarFila, 0.485, 0.07);
        Auxiliar.calcularLocation(getSize(), botonAgregarFila, 0.505, 0.92);
        botonAgregarFila.addActionListener(accion -> {

            if (comprobarDatosCorrectos()) {

                if (Auxiliar.conexionSQL.insertarFila(sentenciaSQL, valores, tipos)) {

                    resetearCamposIntroducirDatos(panelContenedorCampos);
                    revalidate();
                    repaint();

                } else JOptionPane.showMessageDialog(null, "No se pueden introducir los datos debido a que una clave foranea no existe.");
                
            }
		});
        add(botonAgregarFila); 

        // Scroll donde se introducen los datos
        resetearCamposIntroducirDatos(panelContenedorCampos);
        JScrollPane panelAgregarCampos = new JScrollPane(panelContenedorCampos);
        panelAgregarCampos.setBackground(Auxiliar.colorGrisFondo);
        panelAgregarCampos.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        Auxiliar.ajustarScrollBar(panelAgregarCampos.getVerticalScrollBar());
        Auxiliar.calcularSize(getSize(), panelAgregarCampos, 0.98, 0.85);
        Auxiliar.calcularLocation(getSize(), panelAgregarCampos, 0.01, 0.06);
        panelAgregarCampos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelAgregarCampos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelAgregarCampos.getVerticalScrollBar().setUnitIncrement(20);
        add(panelAgregarCampos);
        revalidate();
        repaint();
    }

    private void resetearCamposIntroducirDatos(JPanel panelContenedorCampos) {

        datos = new ArrayList<>();
        panelContenedorCampos.removeAll();

        for (String[] campo : campos) {

            JPanel panelCampo = new JPanel();
            panelCampo.setLayout(new BoxLayout(panelCampo, BoxLayout.X_AXIS));
            panelCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.97), (int)(getSize().getHeight()*0.07)));
            panelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.97), (int)(getSize().getHeight()*0.07)));
            panelCampo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelCampo.setBackground((datos.size() % 2 == 0)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro);

            // Nombre 
            JLabel nombreDelCampo = new JLabel("<html>" + campo[0] + "</html>");
            nombreDelCampo.setForeground(Auxiliar.colorLetra);
            nombreDelCampo.setFont(Auxiliar.fuenteNormal);
            nombreDelCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
            nombreDelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
            nombreDelCampo.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.355), (int)(getSize().getHeight()*0.05)));
            nombreDelCampo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            panelCampo.add(nombreDelCampo);

            // Tipo de dato
            JLabel tipoDeDato = new JLabel("<html>: " + campo[1] + "</html>");
            tipoDeDato.setForeground(Auxiliar.colorLetra);
            tipoDeDato.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.17), (int)(getSize().getHeight()*0.05)));
            tipoDeDato.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.17), (int)(getSize().getHeight()*0.05)));
            tipoDeDato.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.17), (int)(getSize().getHeight()*0.05)));
            tipoDeDato.setFont(Auxiliar.fuenteNormal);
            panelCampo.add(tipoDeDato);

            // Campo para introducir el valor
            JTextField valorDelCampo = new JTextField();
            valorDelCampo.setCaretColor(Auxiliar.colorLetra);
            valorDelCampo.setForeground(Auxiliar.colorLetra);
            valorDelCampo.setBackground(Auxiliar.colorGrisOscuro);
            valorDelCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
            valorDelCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.4), (int)(getSize().getHeight()*0.05)));
            valorDelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.4), (int)(getSize().getHeight()*0.05)));
            valorDelCampo.setMinimumSize(new Dimension((int)(getSize().getWidth()*0.4), (int)(getSize().getHeight()*0.05)));
            valorDelCampo.setFont(Auxiliar.fuenteNormal);
            panelCampo.add(valorDelCampo);
            datos.add(valorDelCampo);

            
            panelContenedorCampos.add(panelCampo);
            revalidate();
            repaint();
        }
    }

    private boolean comprobarDatosCorrectos() {

        boolean resultado = true;
        valores = new String[datos.size()];

        for (int d = 0; d < datos.size(); d++) {

            String dato = datos.get(d).getText().trim();
            valores[d] = dato;

            if (!dato.equals("")) {

                switch (tipos[d]) {

                    case "Entero":

                        try {

                            Integer.parseInt(dato);

                        } catch (NumberFormatException e) {

                            resultado = false;
                            JOptionPane.showMessageDialog(null, "El campo '" + campos.get(d)[0] + "' no se puede convertir a un número entero.");
                        }
                        break;

                    case "Decimal":

                        try {

                            Double.parseDouble(dato.replace(",", "."));

                        } catch (NumberFormatException e) {

                            resultado = false;
                            JOptionPane.showMessageDialog(null, "El campo '" + campos.get(d)[0] + "' no se puede convertir a un número con decimales.");
                        }
                        break;

                    case "Fecha":

                        if (!Auxiliar.comprobarFechaNumeros(dato) || !comprobarFechaValida(dato)) {

                            resultado = false;
                            JOptionPane.showMessageDialog(null, "El campo '" + campos.get(d)[0] + "' no tiene un formato de fecha valido.");
                        }
                        break;
                }

            }
        }
        return resultado;
    }

    private boolean comprobarFechaValida(String fecha) {

        SimpleDateFormat formato;
        if (fecha.contains("/")) {

            if (fecha.indexOf('/') == 4) {

                formato = new SimpleDateFormat("yyyy/MM/dd");

            } else formato = new SimpleDateFormat("dd/MM/yyyy");

        } else if (fecha.contains("-")) {

            if (fecha.indexOf('-') == 4) {

                formato = new SimpleDateFormat("yyyy-MM-dd");

            } else formato = new SimpleDateFormat("dd-MM-yyyy");

        } else return false;

        try {

            new Date(formato.parse(fecha).getTime());

        } catch (ParseException e) { return false; }

        return true;
    }
}
