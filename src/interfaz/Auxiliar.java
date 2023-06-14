package interfaz;

import java.awt.Component;
import java.awt.Dimension;

public class Auxiliar {

    
  private Auxiliar() {}
  
  /************************
   * FUNCIONES_AUXILIARES *
   ************************/
  public static void calcularSize(Component comp, Dimension dim, double x, double y) {
    	
		comp.setSize((int)(dim.getWidth()*x), (int)(dim.getHeight()*y));
  }

	public static void calcularLocation(Component comp, Dimension dim, boolean centrado, double x, double y) {

		comp.setLocation((int)(dim.getWidth()*x - ((centrado)? comp.getWidth()/2.0 : 0)), (int)(dim.getHeight()*y - ((centrado)? comp.getHeight()/2.0 : 0)));
	}
}
