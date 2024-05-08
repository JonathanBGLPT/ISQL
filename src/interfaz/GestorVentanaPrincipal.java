package interfaz;

import java.awt.event.*;
import javax.swing.*;
import interfaz.panelPrincipal.PanelPrincipal;

public class GestorVentanaPrincipal extends JFrame {
    
    private PanelGestionBBDD panelGestionBBDD;
    private PanelPrincipal panelPrincipal;

    private GestorVentanaPrincipal () {

        setTitle("ISQL");
        setDefaultCloseOperation(3);
        setIconImage(new ImageIcon(getClass().getResource("/logoSimple.png")).getImage());
        setSize(Auxiliar.dimensionVentana);
        Auxiliar.calcularLocation(Auxiliar.dimensionVentana, this, 0.045, 0.045);
        setResizable(false);

        panelGestionBBDD = new PanelGestionBBDD(this);
        add(panelGestionBBDD);

        panelPrincipal = new PanelPrincipal(this);
        add(panelPrincipal);
        
        panelGestionBBDD.setVisible(true);

        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                
                if ((e.getNewState() & JFrame.NORMAL) == JFrame.NORMAL) {
                    
                    revalidate();
                    repaint();
                }
            }
        });
    }

    public void mostrarPanelPrincipal (String nombreBD) {

        setTitle(nombreBD);
        panelGestionBBDD.setVisible(false);
        panelPrincipal.actualizarPanelPrincipal();
        panelPrincipal.setVisible(true);
        panelPrincipal.panelGestionTabla.elegirPanelDeGestiones(0);
    }

    public static void main (String[] args) {

        Auxiliar.inicializarAjustes();
        GestorVentanaPrincipal ventanaPrincipal = new GestorVentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}
