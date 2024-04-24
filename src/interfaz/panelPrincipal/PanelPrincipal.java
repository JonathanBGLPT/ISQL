package interfaz.panelPrincipal;

import javax.swing.*;

import interfaz.*;

public class PanelPrincipal extends JPanel {
    
    private PanelResumenTablas panelResumenTablas;
    private PanelGestionTabla panelGestionTabla;
    private PanelConsultas panelConsultas;

    public PanelPrincipal (GestorVentanaPrincipal ventanaPrincipal) {

        setSize(Auxiliar.dimensionVentana);
        setBackground(Auxiliar.colorAzulPalido);
        setLayout(null);

        panelResumenTablas = new PanelResumenTablas();
        add(panelResumenTablas);

        panelGestionTabla = new PanelGestionTabla();
        add(panelGestionTabla);

        panelConsultas = new PanelConsultas();
        add(panelConsultas);
    }

    public void actualizarPanelPrincipal () {

        panelResumenTablas.actualizarPanelResumenTablas();
        panelGestionTabla.actualizarPanelGestionTabla();
        panelConsultas.actualizarPanelConsultas();
    }
}
