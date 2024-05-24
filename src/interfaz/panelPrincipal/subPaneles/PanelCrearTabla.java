package interfaz.panelPrincipal.subPaneles;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelCrearTabla extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<JPanel> campos;

    public PanelCrearTabla (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2));
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        panelPrincipal = panelPrin;
        campos = new ArrayList<>();

        // Boton cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setForeground(Auxiliar.colorLetra);
        botonCancelar.setBackground(Auxiliar.colorGrisOscuro);
        botonCancelar.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCancelar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelar, 0.275, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelar, 0.35, 0.92);
        botonCancelar.addActionListener(accion -> {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la creación de la tabla?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = "";
                panelPrincipal.panelGestionTabla.actualizarPanelGestionTabla();
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };
		});
        add(botonCancelar);

        // Boton finalizar 
        JButton botonFinalizar = new JButton("Finalizar creación");
        botonFinalizar.setForeground(Auxiliar.colorLetra);
        botonFinalizar.setBackground(Auxiliar.colorGrisOscuro);
        botonFinalizar.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonFinalizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonFinalizar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonFinalizar, 0.35, 0.07);
        Auxiliar.calcularLocation(getSize(), botonFinalizar, 0.64, 0.92);
        botonFinalizar.addActionListener(accion -> {

            if (comprobarNombresYClavesForaneas()) {

                Auxiliar.conexionSQL.crearTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada, campos);
                panelPrincipal.panelResumenTablas.actualizarPanelResumenTablas();
                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            }
		});
        add(botonFinalizar);

        // Cabecera con el numero, nombre del campo, tipo de dato, clave foranea y borrar
        JLabel cabeceraNumero = new JLabel("Nº");
        cabeceraNumero.setForeground(Auxiliar.colorLetra);
        cabeceraNumero.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraNumero.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraNumero, 0.06, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraNumero, 0.01, 0.01);
        add(cabeceraNumero);
        
        JLabel cabeceraNombre = new JLabel("Nombre del campo");
        cabeceraNombre.setForeground(Auxiliar.colorLetra);
        cabeceraNombre.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraNombre.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraNombre, 0.355, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraNombre, 0.07, 0.01);
        add(cabeceraNombre);

        JLabel cabeceraTipoDato = new JLabel("Tipo de dato");
        cabeceraTipoDato.setForeground(Auxiliar.colorLetra);
        cabeceraTipoDato.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraTipoDato.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraTipoDato, 0.17, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraTipoDato, 0.425, 0.01);
        add(cabeceraTipoDato);

        JLabel cabeceraClaveForanea = new JLabel("Clave foránea");
        cabeceraClaveForanea.setForeground(Auxiliar.colorLetra);
        cabeceraClaveForanea.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraClaveForanea.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraClaveForanea, 0.27, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraClaveForanea, 0.595, 0.01);
        add(cabeceraClaveForanea);

        JLabel cabeceraBorrar = new JLabel("Borrar");
        cabeceraBorrar.setForeground(Auxiliar.colorLetra);
        cabeceraBorrar.setHorizontalAlignment(SwingConstants.CENTER);
        cabeceraBorrar.setFont(Auxiliar.fuentePequenia);
        Auxiliar.calcularSize(getSize(), cabeceraBorrar, 0.11, 0.05);
        Auxiliar.calcularLocation(getSize(), cabeceraBorrar, 0.865, 0.01);
        add(cabeceraBorrar);

    

        // Panel para gestionar los campos
        JPanel panelContenedorCampos = new JPanel();
        Auxiliar.calcularSize(getSize(), panelContenedorCampos, 0.98, 0.85);
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));
        panelContenedorCampos.setBackground(Auxiliar.colorGrisClaro);

        // Boton crear nuevo campo
        JButton botonCrearCampo = new JButton("Crear campo");
        botonCrearCampo.setForeground(Auxiliar.colorLetra);
        botonCrearCampo.setBackground(Auxiliar.colorGrisOscuro);
        botonCrearCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 2, true));
        botonCrearCampo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCrearCampo.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCrearCampo, 0.325, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCrearCampo, 0.01, 0.92);
        botonCrearCampo.addActionListener(accion -> {

            crearCampoNuevo(panelContenedorCampos);
        });
        add(botonCrearCampo);

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
    }

    @SuppressWarnings("rawtypes")
    private boolean comprobarNombresYClavesForaneas() {

        boolean resultado = campos.size() > 0;
        if (!resultado) JOptionPane.showMessageDialog(null, "La tabla debe contenedor al menos un campo.");

        String nombresVacios = "";
        String nombresRepetidos = "";
        String clavesForaneasRepetidas = "";

        String[] clavesForaneasElegidas = new String[campos.size()];
        String[] nombresCampos = new String[campos.size()];
        for (int c = 0; c < campos.size(); c++) {

            nombresCampos[c] = ((JTextField)campos.get(c).getComponent(1)).getText();
            clavesForaneasElegidas[c] = (String)(((JComboBox)campos.get(c).getComponent(5)).getSelectedItem());
            if (nombresCampos[c] == null || nombresCampos[c].equals("")) {

                nombresVacios += "campo " + (c+1) + ", ";
                resultado = false;
            }
        }
        for (int c1 = 0; c1 < nombresCampos.length; c1++) {
            for (int c2 = c1+1; c2 < nombresCampos.length; c2++) {

                if (nombresCampos[c1] != null && nombresCampos[c2] != null && !nombresCampos[c1].equals("") && nombresCampos[c1].toUpperCase().equals(nombresCampos[c2].toUpperCase())) {

                    nombresRepetidos += "campos " + (c1+1) + " y " + (c2+1) + ", "; 
                    resultado = false;
                }
                if (((String)((JComboBox)campos.get(c1).getComponent(3)).getSelectedItem()).equals("Entero") && ((String)((JComboBox)campos.get(c2).getComponent(3)).getSelectedItem()).equals("Entero")
                    && !clavesForaneasElegidas[c1].equals("-") && clavesForaneasElegidas[c1].equals(clavesForaneasElegidas[c2])) {

                    clavesForaneasRepetidas += "campos " + (c1+1) + " y " + (c2+1) + ", "; 
                    resultado = false;
                }
            }
        }
        if (!resultado && nombresVacios.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan vacíos: " + nombresVacios.substring(0, nombresVacios.length()-2) + ".");
        if (!resultado && nombresRepetidos.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan repetidos: " + nombresRepetidos.substring(0, nombresRepetidos.length()-2) + ".");
        if (!resultado && clavesForaneasRepetidas.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos comparten clave foránea: " + clavesForaneasRepetidas.substring(0, clavesForaneasRepetidas.length()-2) + ".");
        return resultado;
    }

    private void crearCampoNuevo(JPanel panelContenedorCampos) {

        JPanel panelCampo = new JPanel();
        panelCampo.setLayout(new BoxLayout(panelCampo, BoxLayout.X_AXIS));
        panelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelCampo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCampo.setBackground((campos.size() % 2 == 0)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro);

        // Numero del campo
        JLabel numeroDelCampo = new JLabel((campos.size()+1) + ((campos.size() > 8)? " " : "   "));
        numeroDelCampo.setForeground(Auxiliar.colorLetra);
        numeroDelCampo.setFont(Auxiliar.fuenteNormal);
        panelCampo.add(numeroDelCampo);

        // Nombre 
        JTextField nombreDelCampo = new JTextField();
        nombreDelCampo.setCaretColor(Auxiliar.colorLetra);
        nombreDelCampo.setForeground(Auxiliar.colorLetra);
        nombreDelCampo.setBackground(Auxiliar.colorGrisOscuro);
        nombreDelCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        nombreDelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.5), (int)(getSize().getHeight()*0.05)));
        nombreDelCampo.setFont(Auxiliar.fuenteNormal);
        ((PlainDocument)nombreDelCampo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

                if (fb.getDocument().getText(0, fb.getDocument().getLength()).length() - length + (text != null ? text.length() : 0) <= 16) super.replace(fb, offset, length, text, attrs);
            }
        });
        panelCampo.add(nombreDelCampo);

        Component espacioHorizontal1 = Box.createHorizontalStrut(20);
        espacioHorizontal1.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.01), (int)(getSize().getHeight()*0.05)));
        panelCampo.add(espacioHorizontal1);

        // Declaracion de clave foranea
        ArrayList<String> clavesForaneas = Auxiliar.conexionSQL.obtenerNombreTablas();
        clavesForaneas.add(0, "-");
        JComboBox<String> comboClavesForaneas = new JComboBox<>(clavesForaneas.toArray(new String[0]));
        
        // Tipo de dato
        String[] tiposDeDatos = {"Entero", "Decimal", "Texto", "Fecha"};
        JComboBox<String> comboElegirTipoDeDato = new JComboBox<>(tiposDeDatos);
        comboElegirTipoDeDato.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboElegirTipoDeDato.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.175), (int)(getSize().getHeight()*0.05)));
        comboElegirTipoDeDato.setForeground(Auxiliar.colorLetra);
        comboElegirTipoDeDato.setBackground(Auxiliar.colorGrisOscuro);
        comboElegirTipoDeDato.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        comboElegirTipoDeDato.setFont(Auxiliar.fuenteNormal);
        comboElegirTipoDeDato.addActionListener(accion2 -> {

            comboClavesForaneas.setEnabled(((String)comboElegirTipoDeDato.getSelectedItem()).equals("Entero"));
        });
        panelCampo.add(comboElegirTipoDeDato);

        Component espacioHorizontal2 = Box.createHorizontalStrut(20);
        espacioHorizontal2.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.01), (int)(getSize().getHeight()*0.05)));
        panelCampo.add(espacioHorizontal2);
        
        // Configuracion de clave foranea
        comboClavesForaneas.setForeground(Auxiliar.colorLetra);
        comboClavesForaneas.setBackground(Auxiliar.colorGrisOscuro);
        comboClavesForaneas.setBorder(BorderFactory.createLineBorder(Auxiliar.colorBordes, 1));
        comboClavesForaneas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboClavesForaneas.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.3), (int)(getSize().getHeight()*0.05)));
        comboClavesForaneas.setFont(Auxiliar.fuenteNormal);
        panelCampo.add(comboClavesForaneas);

        // Eliminar
        JButton botonEliminarCampo = new JButton();
        botonEliminarCampo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonEliminarCampo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/removeBin.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()* 0.04), (int)(Auxiliar.dimensionVentana.getHeight()* 0.04), Image.SCALE_SMOOTH)));
        botonEliminarCampo.setBorderPainted(false);
        botonEliminarCampo.setContentAreaFilled(false);
        botonEliminarCampo.addActionListener(accion2 -> {

            for (int c = campos.size()-1; c >= Integer.parseInt(numeroDelCampo.getText().trim()); c--) {

                ((JLabel)campos.get(c).getComponent(0)).setText(((JLabel)campos.get(c-1).getComponent(0)).getText());
                campos.get(c).setBackground((campos.get(c).getBackground() == Auxiliar.colorGrisOscuro)? Auxiliar.colorGrisClaro : Auxiliar.colorGrisOscuro);
            }
            campos.remove(panelCampo);
            panelContenedorCampos.remove(panelCampo);
            revalidate();
            repaint();
        });
        panelCampo.add(botonEliminarCampo);

        panelContenedorCampos.add(panelCampo);
        campos.add(panelCampo);
        revalidate();
        repaint();
    }
}
