package interfaz.panelPrincipal;

import javax.swing.*;

import interfaz.*;

public class PanelPrincipal extends JPanel {
    
    public PanelResumenTablas panelResumenTablas;
    public PanelGestionTabla panelGestionTabla;
    public PanelConsultas panelConsultas;

    public PanelPrincipal (GestorVentanaPrincipal ventanaPrincipal) {

        setSize(Auxiliar.dimensionVentana);
        setBackground(Auxiliar.colorGrisFondo);
        setLayout(null);

        panelResumenTablas = new PanelResumenTablas(this);
        add(panelResumenTablas);

        panelGestionTabla = new PanelGestionTabla(this);
        add(panelGestionTabla);

        panelConsultas = new PanelConsultas(this);
        add(panelConsultas);
    }

    public void actualizarPanelPrincipal () {

        panelResumenTablas.actualizarPanelResumenTablas();
        panelGestionTabla.actualizarPanelGestionTabla();
        panelConsultas.actualizarPanelConsultas();
    }
}
