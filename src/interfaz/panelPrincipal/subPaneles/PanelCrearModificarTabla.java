package interfaz.panelPrincipal.subPaneles;

import java.awt.Component;
import javax.swing.*;
import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelCrearModificarTabla extends JPanel {
    
    private PanelPrincipal panelPrincipal;

    public PanelCrearModificarTabla (PanelPrincipal panelPrin, boolean crear) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;

        // Boton cancelar
        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonCancelar, 0.3, 0.07);
        Auxiliar.calcularLocation(getSize(), botonCancelar, 0.36, 0.92);
        botonCancelar.addActionListener(accion -> {

            int respuesta = JOptionPane.showConfirmDialog(null, "¿Deseas cancelar la " + ((crear)? "creacion" : "modificacion") + " de la tabla?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (respuesta == JOptionPane.YES_OPTION) {

                Auxiliar.habilitacionDeBotones(panelPrincipal, true);
                if (crear) panelPrincipal.panelGestionTabla.nombreTablaSeleccionada = "";
                panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
            };
		});
        add(botonCancelar);

        // Boton finalizar 
        JButton botonFinalizar = new JButton("Finalizar " + ((crear)? "creacion" : "modificacion"));
        botonFinalizar.setFont(Auxiliar.fuenteNormal);
        Auxiliar.calcularSize(getSize(), botonFinalizar, 0.3, 0.07);
        Auxiliar.calcularLocation(getSize(), botonFinalizar, 0.69, 0.92);
        botonFinalizar.addActionListener(accion -> {

            if (crear) {

                /// IMPLEMENTAR
                
            } else {

                /// IMPLEMENTAR
            }

            Auxiliar.habilitacionDeBotones(panelPrincipal, true);
            panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
		});
        add(botonFinalizar);

        // Elijo si agregar un panel de creacion o modificacion
        if (crear) {

            JPanel panelContenedorAtributos = new JPanel();
            panelContenedorAtributos.setLayout(new BoxLayout(panelContenedorAtributos, BoxLayout.Y_AXIS));

            /// Boton crear nuevo atributo
            JButton botonCrearAtributo = new JButton("Crear atributo");
            botonCrearAtributo.setFont(Auxiliar.fuenteNormal);
            Auxiliar.calcularSize(getSize(), botonCrearAtributo, 0.3, 0.07);
            Auxiliar.calcularLocation(getSize(), botonCrearAtributo, 0.03, 0.92);
            botonCrearAtributo.addActionListener(accion -> {

                /// ARREGLAR
                JPanel panelAtributo = new JPanel();
                panelAtributo.setLayout(new BoxLayout(panelAtributo, BoxLayout.X_AXIS));
                panelAtributo.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelAtributo.setBackground(c? java.awt.Color.RED : java.awt.Color.BLUE);
                c = !c;

                JLabel prueba = new JLabel("prueba");
                Auxiliar.calcularSize(getSize(), prueba, 0.98, 0.1);
                prueba.setFont(Auxiliar.fuenteGrande);
                panelAtributo.add(prueba);

                panelContenedorAtributos.add(panelAtributo);
                revalidate();
                repaint();
            });
            add(botonCrearAtributo);

            JScrollPane panelAgregarAtributos = new JScrollPane(panelContenedorAtributos);
            Auxiliar.calcularSize(getSize(), panelAgregarAtributos, 0.98, 0.9);
            Auxiliar.calcularLocation(getSize(), panelAgregarAtributos, 0.01, 0.01);
            panelAgregarAtributos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panelAgregarAtributos.getVerticalScrollBar().setUnitIncrement(20);
            add(panelAgregarAtributos);

        } else {

            /// IMPLEMENTAR
        }
    }
    public static boolean c = false;
}
