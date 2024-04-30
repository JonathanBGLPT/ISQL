package interfaz.panelPrincipal.subPaneles;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Image;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelCrearTabla extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<JPanel> campos;

    public PanelCrearTabla (PanelPrincipal panelPrin) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;
        campos = new ArrayList<>();

        // Boton cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelar, 0.3, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelar, 0.36, 0.92);
        botonCancelar.addActionListener(accion -> {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la creacion de la tabla?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = "";
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };
		});
        add(botonCancelar);

        // Boton finalizar 
        JButton botonFinalizar = new JButton("Finalizar creacion");
        botonFinalizar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonFinalizar, 0.3, 0.07);
        Auxiliar.calcularLocation(getSize(), botonFinalizar, 0.69, 0.92);
        botonFinalizar.addActionListener(accion -> {

            if (comprobarNombres()) {

                /// IMPLEMENTAR
    
                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            }
		});
        add(botonFinalizar);

        // Panel para gestionar los campos
        JPanel panelContenedorCampos = new JPanel();
        Auxiliar.calcularSize(getSize(), panelContenedorCampos, 0.98, 0.9);
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));

        // Boton crear nuevo campo
        JButton botonCrearCampo = new JButton("Crear campo");
        botonCrearCampo.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCrearCampo, 0.3, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCrearCampo, 0.03, 0.92);
        botonCrearCampo.addActionListener(accion -> {

            JPanel panelCampo = new JPanel();
            panelCampo.setLayout(new BoxLayout(panelCampo, BoxLayout.X_AXIS));
            panelCampo.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelCampo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            panelCampo.setBackground((campos.size() % 2 == 0)? java.awt.Color.WHITE : java.awt.Color.LIGHT_GRAY);

            // Numero del campo
            JLabel numeroDelCampo = new JLabel((campos.size()+1) + ((campos.size() > 8)? " " : "   "));
            numeroDelCampo.setFont(Auxiliar.fuenteNormal);
            panelCampo.add(numeroDelCampo);

            // Nombre 
            JTextField nombreDelCampo = new JTextField();
            nombreDelCampo.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.4), (int)(getSize().getHeight()*0.05)));
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
            String[] tiposDeDatos = {"Entero", "Decimal", "Texto", "Fecha", "Imagen"};
            JComboBox<String> comboElegirTipoDeDato = new JComboBox<>(tiposDeDatos);
            comboElegirTipoDeDato.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.15), (int)(getSize().getHeight()*0.05)));
            comboElegirTipoDeDato.setFont(Auxiliar.fuenteNormal);
            comboElegirTipoDeDato.addActionListener(accion2 -> {

                comboClavesForaneas.setEnabled(((String)comboElegirTipoDeDato.getSelectedItem()).equals("Entero"));
            });
            panelCampo.add(comboElegirTipoDeDato);

            Component espacioHorizontal2 = Box.createHorizontalStrut(20);
            espacioHorizontal2.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.01), (int)(getSize().getHeight()*0.05)));
            panelCampo.add(espacioHorizontal2);
            
            // Configuracion de clave foranea
            comboClavesForaneas.setMaximumSize(new Dimension((int)(getSize().getWidth()*0.3), (int)(getSize().getHeight()*0.05)));
            comboClavesForaneas.setFont(Auxiliar.fuenteNormal);
            panelCampo.add(comboClavesForaneas);

            // Eliminar
            JButton botonEliminarCampo = new JButton();
            botonEliminarCampo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/removeBin.png")).getImage().getScaledInstance((int)(Auxiliar.dimensionVentana.getHeight()* 0.04), (int)(Auxiliar.dimensionVentana.getHeight()* 0.04), Image.SCALE_SMOOTH)));
            botonEliminarCampo.setBorderPainted(false);
            botonEliminarCampo.setContentAreaFilled(false);
            botonEliminarCampo.addActionListener(accion2 -> {

                for (int c = campos.size()-1; c >= Integer.parseInt(numeroDelCampo.getText().trim()); c--) {

                    ((JLabel)campos.get(c).getComponent(0)).setText(((JLabel)campos.get(c-1).getComponent(0)).getText());
                    campos.get(c).setBackground((campos.get(c).getBackground() == java.awt.Color.LIGHT_GRAY)? java.awt.Color.WHITE : java.awt.Color.LIGHT_GRAY);
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
        });
        add(botonCrearCampo);

        JScrollPane panelAgregarCampos = new JScrollPane(panelContenedorCampos);
        Auxiliar.calcularSize(getSize(), panelAgregarCampos, 0.98, 0.9);
        Auxiliar.calcularLocation(getSize(), panelAgregarCampos, 0.01, 0.01);
        panelAgregarCampos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelAgregarCampos.getVerticalScrollBar().setUnitIncrement(20);
        add(panelAgregarCampos);
    }

    private boolean comprobarNombres() {

        boolean resultado = true;
        String nombresVacios = "";
        String nombresRepetidos = "";

        String[] nombresCampos = new String[campos.size()];
        for (int c = 0; c < campos.size(); c++) {

            nombresCampos[c] = ((JTextField)campos.get(c).getComponent(1)).getText();
            if (nombresCampos[c] == null || nombresCampos[c].equals("")) {

                nombresVacios += "campo " + (c+1) + ", ";
                resultado = false;
            }
        }
        for (int c1 = 0; c1 < nombresCampos.length; c1++) {
            for (int c2 = c1+1; c2 < nombresCampos.length; c2++) {

                if (nombresCampos[c1] != null && nombresCampos[c2] != null && !nombresCampos[c1].equals("") && nombresCampos[c1].equals(nombresCampos[c2])) {

                    nombresRepetidos += "campos " + (c1+1) + " y " + (c2+1) + ", "; 
                    resultado = false;
                }
            }
        }
        if (!resultado && nombresVacios.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan vacios: " + nombresVacios.substring(0, nombresVacios.length()-2) + ".");
        if (!resultado && nombresRepetidos.length() != 0) JOptionPane.showMessageDialog(null, "Los siguientes campos estan repetidos: " + nombresRepetidos.substring(0, nombresRepetidos.length()-2) + ".");
        return resultado;
    }
}