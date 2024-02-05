package interfaz;

import javax.swing.JFrame;

class VentanaPrincipal extends JFrame {
    
    // Atributos
    private PanelGestionBBDD panelGestionBBDD;
    private PanelPrincipal panelPrincipal;

    // Constructor
    private VentanaPrincipal () {

        setTitle("ISQL");
        setDefaultCloseOperation(3);
        setSize(Auxiliar.dimensionVentana);
        Auxiliar.calcularLocation(this, 0.045, 0.045);
        setResizable(false);

        panelGestionBBDD = new PanelGestionBBDD(this);
        add(panelGestionBBDD);

        panelPrincipal = new PanelPrincipal(this);
        add(panelPrincipal);
        
        setPanelActual(0);
    }

    public void setPanelActual (int opcion) {

        panelGestionBBDD.setVisible(opcion == 0);
        panelPrincipal.setVisible(opcion == 1);
    }

    public static void main (String[] args) {

        Auxiliar.inicializarAjustes();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}
