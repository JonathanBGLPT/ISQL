package interfaz.panelPrincipal;

import javax.swing.JPanel;
import interfaz.Auxiliar;

public class PanelConsultas extends JPanel {
    
    private PanelPrincipal panelPrincipal;

    public PanelConsultas (PanelPrincipal panelPrin) {

        setBackground(java.awt.Color.GREEN); /// CAMBIAR A NULL
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.325, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.665, 0.01);
        setLayout(null);

        panelPrincipal= panelPrin;
    }

    public void actualizarPanelConsultas () {

        removeAll();



        revalidate();
        repaint();
    }
}
