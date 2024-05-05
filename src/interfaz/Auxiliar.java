package interfaz;

import java.awt.*;

import javax.swing.JButton;

import sql.ConexionPrincipal;

public class Auxiliar {
    
  public static ConexionPrincipal conexionSQL;

  public static Color colorAzulPalido = new Color(150, 200, 220);
  public static Color coloAzulOscuro = new Color(11, 38, 57);

  public static Dimension dimensionVentana;
  public static Font fuenteGrande;
  public static Font fuenteNormal;
  public static Font fuentePequenia;

  public static boolean botonesActivados = true;

  public static void inicializarAjustes () {

    conexionSQL = new ConexionPrincipal();
    /// CAMBIAR, SOLAMENTE ESTA PARA PROBAR AJUSTES
    int option = 2;
    if (option == 1) dimensionVentana = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9));
    if (option == 2) dimensionVentana = new Dimension((int)(1920*0.9), (int)(1080*0.9)); // FULL HD

    fuenteGrande = new Font("Arial", Font.PLAIN, (int)(dimensionVentana.getHeight()*0.03));
    fuenteNormal = new Font("Arial", Font.PLAIN, (int)(dimensionVentana.getHeight()*0.025));
    fuentePequenia = new Font("Arial", Font.PLAIN, (int)(dimensionVentana.getHeight()*0.02));
  }

  public static void calcularSize(Dimension dimensionReferencia, Component componente, double x, double y) {

    componente.setSize((int)(dimensionReferencia.getWidth()*x), (int)(dimensionReferencia.getHeight()*y));
  }

  public static void calcularLocation(Dimension dimensionReferencia, Component componente, double x, double y) {

    componente.setLocation((int)(dimensionReferencia.getWidth()*x), (int)(dimensionReferencia.getHeight()*y));
  }

  public static void habilitacionDeBotones(Container contenedor, boolean activar) {

    botonesActivados = activar;
    Component[] componentes = contenedor.getComponents();
    for (Component componente : componentes) {

      if (componente instanceof JButton) {

        ((JButton)componente).setEnabled(activar);

      } else if (componente instanceof Container) habilitacionDeBotones((Container)componente, activar);
    }
  }
}
