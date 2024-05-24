package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import sql.ConexionPrincipal;

public class Auxiliar {
    
  public static ConexionPrincipal conexionSQL;
    
  public static Color colorFaltaDato = new Color(12, 12, 12);
  public static Color colorGrisFondo = new Color(24, 24, 24);
  public static Color colorGrisOscuro = new Color(31, 31, 31);
  public static Color colorGrisClaro = new Color(40, 40, 40);
  public static Color colorSeleccionado = new Color(96, 96, 96);
  public static Color colorBordes = new Color(192, 192, 192);
  public static Color colorLetra = new Color(255, 255, 255);

  public static Dimension dimensionVentana;
  public static Font fuenteGrande;
  public static Font fuenteNormal;
  public static Font fuentePequenia;

  public static boolean botonesActivados = true;

  public static void inicializarAjustes () {

    conexionSQL = new ConexionPrincipal();
    dimensionVentana = new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9));

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

      if (componente instanceof JButton || componente instanceof JComboBox || componente instanceof JTextField) {

        componente.setEnabled(activar);

      } else if (componente instanceof Container) habilitacionDeBotones((Container)componente, activar);
    }
  }

  public static boolean comprobarFechaNumeros (String fecha) {

    boolean resultado = true;

    for (int c = 0; resultado && c < fecha.length(); c++) {

      char caracter = fecha.charAt(c);
      resultado = caracter == '-' || caracter == '/' || (caracter >= 48 && caracter <= 57);
    }

    return resultado;
  }

  public static void ajustarScrollBar (JScrollBar scrollBar) {

        scrollBar.setUnitIncrement(20);
        scrollBar.setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {

              this.thumbColor = colorGrisFondo;
              this.trackColor = colorBordes;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {

              return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {

              return createZeroButton();
            }

            private JButton createZeroButton() {

              JButton button = new JButton();
              button.setPreferredSize(new Dimension(0, 0));
              button.setMinimumSize(new Dimension(0, 0));
              button.setMaximumSize(new Dimension(0, 0));
              return button;
            }
        });
    }
}
