package interfaz.panelPrincipal;

import javax.swing.JPanel;
import interfaz.Auxiliar;

public class PanelGestionTabla extends JPanel {
    
    public PanelGestionTabla () {

        setBackground(null);
        Auxiliar.calcularSize(Auxiliar.dimensionVentana, this, 0.45, 0.95);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.21, 0.01);
    }

    public void actualizarPanelGestionTabla () {

        removeAll();



        revalidate();
        repaint();
    }
}
