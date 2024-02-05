package interfaz;

import java.awt.*;

import sql.ConexionPrincipal;

class Auxiliar {
    
    public static ConexionPrincipal conexionSQL;

    public static Color colorAzulPalido = new Color(150, 200, 220);

    public static Dimension dimensionVentana;
    public static Font fuenteGrande;
    public static Font fuenteNormal;


    public static void inicializarAjustes () {

      conexionSQL = new ConexionPrincipal();
      dimensionVentana = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9));
      fuenteGrande = new Font("Arial", Font.PLAIN, (int)(dimensionVentana.getHeight()*0.03));
      fuenteNormal = new Font("Arial", Font.PLAIN, (int)(dimensionVentana.getHeight()*0.015));
    }

    public static void calcularSize(Component componente, double x, double y) {

		componente.setSize((int)(dimensionVentana.getWidth()*x), (int)(dimensionVentana.getHeight()*y));
    }

	public static void calcularLocation(Component componente, double x, double y) {

		componente.setLocation((int)(dimensionVentana.getWidth()*x), (int)(dimensionVentana.getHeight()*y));
	}
}
