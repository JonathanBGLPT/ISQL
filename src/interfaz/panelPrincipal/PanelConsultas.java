package interfaz.panelPrincipal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import interfaz.Auxiliar;

public class PanelConsultas extends JPanel {
    
    private PanelPrincipal panelPrincipal;

    public PanelConsultas (PanelPrincipal panelPrin) {

        setBackground(null);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.325, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.665, 0.01);
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
        JPanel panelDeConsultas = new JPanel();
        Auxiliar.calcularSize(getSize(), panelDeConsultas, 0.98, 0.82);
        Auxiliar.calcularLocation(getSize(), panelDeConsultas, 0.01, 0.07);
        panelDeConsultas.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        panelDeConsultas.setLayout(null);
        add(panelDeConsultas);

        // Realizar consulta simple
        JButton botonAConsultaSimple = new JButton("<html>Realizar consulta<br><center>simple</center></html>");
        botonAConsultaSimple.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAConsultaSimple, 0.45, 0.1);
        Auxiliar.calcularLocation(getSize(), botonAConsultaSimple, 0.03, 0.9);
        botonAConsultaSimple.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonAConsultaSimple);

        // Realizar consulta chatGPT
        JButton botonAConsultaGPT = new JButton("<html>Realizar consulta<br><center>con ChatGPT</center></html>");
        botonAConsultaGPT.setFont(Auxiliar.fuenteGrande);
        Auxiliar.calcularSize(getSize(), botonAConsultaGPT, 0.45, 0.1);
        Auxiliar.calcularLocation(getSize(), botonAConsultaGPT, 0.51, 0.9);
        botonAConsultaGPT.addActionListener(accion -> {

            /// IMPLEMENTAR
		});
        add(botonAConsultaGPT);

        revalidate();
        repaint();
    }
}
