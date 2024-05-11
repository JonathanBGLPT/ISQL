package interfaz.panelPrincipal.subPaneles;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

import interfaz.Auxiliar;
import interfaz.panelPrincipal.PanelPrincipal;

public class PanelMostarDatos extends JPanel {
    
    private PanelPrincipal panelPrincipal;
    private ArrayList<String[]> datosTabla;

    public PanelMostarDatos (PanelPrincipal panelPrin, ArrayList<String[]> datos) {

        Auxiliar.calcularSize(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 1, 1);
        Auxiliar.calcularLocation(panelPrin.panelGestionTabla.panelDeGestiones.getSize(), this, 0, 0);
        setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 2));
        setLayout(null);

        panelPrincipal = panelPrin;
        datosTabla = datos;

        // Scroll con los campos de la tabla
        JPanel panelContenedorCampos = new JPanel();
        panelContenedorCampos.setLayout(new BoxLayout(panelContenedorCampos, BoxLayout.Y_AXIS));
        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(panelPrincipal.panelGestionTabla.nombreTablaSeleccionada);
        JPanel panelCabecera = new JPanel();
        panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.X_AXIS));
        if (!panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("")) {

            for (String[] campo : campos) {

                JLabel nombreCampo = new JLabel("<html>"+ campo[0] + "</html>");
                nombreCampo.setHorizontalAlignment(JLabel.CENTER);
                nombreCampo.setFont(Auxiliar.fuentePequenia);
                nombreCampo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                nombreCampo.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 1));
                panelCabecera.add(nombreCampo);
            }
        }
        JScrollPane panelMostarCampos = new JScrollPane(panelCabecera);
        Auxiliar.calcularSize(getSize(), panelMostarCampos, 0.978, 0.05);
        Auxiliar.calcularLocation(getSize(), panelMostarCampos, 0.005, 0.005);
        panelMostarCampos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelMostarCampos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        panelMostarCampos.getVerticalScrollBar().setUnitIncrement(0);
        add(panelMostarCampos);

        // Scroll que muestra los datos de la tabla
        JPanel panelContenedorDatos = new JPanel();
        panelContenedorDatos.setLayout(new BoxLayout(panelContenedorDatos, BoxLayout.Y_AXIS));
        
        boolean datosValidos = !panelPrincipal.panelGestionTabla.nombreTablaSeleccionada.equals("") && datosTabla != null;
        for (int f = 0; datosValidos && f < datosTabla.size(); f++) {

            String[] fila = datosTabla.get(f);
            JPanel panelFila = new JPanel();
            panelFila.setLayout(new BoxLayout(panelFila, BoxLayout.X_AXIS));
            panelFila.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25*campos.size()), (int)(getSize().getHeight()*0.03)));
            panelFila.setBackground((f % 2 == 0)? java.awt.Color.WHITE : java.awt.Color.LIGHT_GRAY);
            for (String dato : fila) {

                if (dato.length() > 16) dato = dato.substring(0, 16) + "...";
                JLabel campo = new JLabel("<html>" + dato + "</html>");
                campo.setFont(Auxiliar.fuentePequenia);
                campo.setPreferredSize(new Dimension((int)(getSize().getWidth()*0.25), (int)(getSize().getHeight()*0.05)));
                campo.setBorder(BorderFactory.createLineBorder(Auxiliar.coloAzulOscuro, 1));
                panelFila.add(campo);
            }
            panelContenedorDatos.add(panelFila);
        }
        JScrollPane panelMostarDatos = new JScrollPane(panelContenedorDatos);
        Auxiliar.calcularSize(getSize(), panelMostarDatos, 0.993, 0.943);
        Auxiliar.calcularLocation(getSize(), panelMostarDatos, 0.005, 0.055);
        panelMostarDatos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelMostarDatos.getHorizontalScrollBar().setUnitIncrement(20);
        panelMostarDatos.getVerticalScrollBar().setUnitIncrement(20);
        panelMostarDatos.getHorizontalScrollBar().addAdjustmentListener(accion -> {

            panelMostarCampos.getHorizontalScrollBar().setValue(panelMostarDatos.getHorizontalScrollBar().getValue());
        });
        add(panelMostarDatos);
    }
}
