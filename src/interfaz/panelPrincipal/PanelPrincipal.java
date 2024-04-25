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

        panelGestionTabla = new PanelGestionTabla(this);
        add(panelGestionTabla);

        panelConsultas = new PanelConsultas();
        add(panelConsultas);
    }

    public void actualizarPanelPrincipal (String tablaSeleccionada) {

        panelResumenTablas.actualizarPanelResumenTablas(panelGestionTabla);
        panelGestionTabla.actualizarPanelGestionTabla(tablaSeleccionada);
        panelConsultas.actualizarPanelConsultas();
    }
}
