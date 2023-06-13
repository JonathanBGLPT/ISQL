package interfaz;

import java.awt.*;
import javax.swing.*;

public class VentanaPrincipal extends JFrame {
	
	// Variables globales
	private static final long serialVersionUID = 1L;
	private Dimension pantallaDim;
	private int pantallaX = 1920;
	private int pantallaY = 1080;

	// Paneles
	
	// Constructor
    public VentanaPrincipal () {
       
    	setTitle("ISQL");
		pantallaDim = new Dimension(pantallaX, pantallaY);
    	setSize(pantallaDim);
		setResizable(false);
		setDefaultCloseOperation(3);

    }

	public static void main(String[] args) {


		VentanaPrincipal ventanaPrincipal = new VentanaPrincipal();
		ventanaPrincipal.setVisible(true);
	}
}