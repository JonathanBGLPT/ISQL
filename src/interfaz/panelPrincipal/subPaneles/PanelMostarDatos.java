package interfaz.panelPrincipal.subPaneles;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelMostarDatos extends JPanel {
    
    public PanelMostarDatos (PanelPrincipal panelPrin) {

        setBackground(java.awt.Color.GRAY); /// QUITAR
        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);


    }
}
