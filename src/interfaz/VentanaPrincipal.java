package interfaz;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame {
    
    // Atributos
    private PanelGestionBBDD panelGestionBBDD;

    // Constructor
    private VentanaPrincipal () {

        setTitle("ISQL");
        setDefaultCloseOperation(3);
        setSize(Auxiliar.dimensionVentana);
        Auxiliar.calcularLocation(this, 0.045, 0.045);
        setResizable(false);

        panelGestionBBDD = new PanelGestionBBDD();
        add(panelGestionBBDD);
    }


    public static void main (String[] args) {

        Auxiliar.inicializarAjustes();
        VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setVisible(true);
    }
}
