package interfaz.panelPrincipal;

import java.awt.Cursor;
import javax.swing.*;
import interfaz.Auxiliar;

import interfaz.panelPrincipal.subPaneles.PanelConsultaSimple;

public class PanelConsultas extends JPanel {
    
    private PanelPrincipal panelPrincipal;

    public JPanel panelDeConsultas;

    public PanelConsultas (PanelPrincipal panelPrin) {

        setBackground(null);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.355, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.615, 0.01);
        setLayout(null);

        panelPrincipal = panelPrin;
    }

    public void actualizarPanelConsultas () {

        removeAll();

        // Texto consultas
        JLabel textoConsultas = new JLabel("Consultas:");
        textoConsultas.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), textoConsultas, 0.9, 0.03);
        Auxiliar.calcularLocation(getSize(), textoConsultas, 0.025, 0.04);
        add(textoConsultas);

        // Panel de consultas
        panelDeConsultas = new JPanel();
        Auxiliar.calcularSize(getSize(), panelDeConsultas, 0.98, 0.82);
        Auxiliar.calcularLocation(getSize(), panelDeConsultas, 0.01, 0.07);
        panelDeConsultas.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        panelDeConsultas.setLayout(null);
        add(panelDeConsultas);

        // Realizar consulta simple
        JButton botonAConsultaSimple = new JButton("<html>Realizar consulta<br><center>simple</center></html>");
        botonAConsultaSimple.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAConsultaSimple.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAConsultaSimple, 0.48, 0.1);
        Auxiliar.calcularLocation(getSize(), botonAConsultaSimple, 0.01, 0.9);
        botonAConsultaSimple.addActionListener(accion -> {

            if (!panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("")) {

                elegirPanelConsulta(1);

            } else JOptionPane.showMessageDialog(null,  "Debes seleccionar una tabla para consultar.");
		});
        add(botonAConsultaSimple);

        // Realizar consulta chatGPT
        JButton botonAConsultaGPT = new JButton("<html>Realizar consulta<br><center>con ChatGPT</center></html>");
        botonAConsultaGPT.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonAConsultaGPT.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAConsultaGPT, 0.48, 0.1);
        Auxiliar.calcularLocation(getSize(), botonAConsultaGPT, 0.51, 0.9);
        botonAConsultaGPT.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonAConsultaGPT);

        revalidate();
        repaint();
    }

    public void elegirPanelConsulta(int opcion) {

        panelDeConsultas.removeAll();

        switch (opcion) {

            // Salir de la consulta
            case 0:

                Auxiliar.habilitacionDeBotones(panelPrincipal, true);

            break;

            // Mostrar consulta simple
            case 1:

                Auxiliar.habilitacionDeBotones(panelPrincipal, false);
                panelDeConsultas.add(new PanelConsultaSimple(panelPrincipal));

            break;

            // Mostrar consulta GPT
            case 2:

                /// IMPLEMENTAR

            break;

            // Mostrar resultado consulta
            case 3:

                /// IMPLEMENTAR

            break;
        }
        revalidate();
        repaint();
    }
}
