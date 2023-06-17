package fabricas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import interfaz.*;
import sql.*;
import java.util.*;

public class FabricaTablasBD {
    
    // Variable generales y el panel
    private Dimension pantallaDim;
    private ConexionPrincipal sql;
    private JScrollPane panelTablasBD;

    // Constructor
    public FabricaTablasBD(Dimension dim, ConexionPrincipal con) {

        pantallaDim = dim;
        sql = con;
    }

    // Devuelve el panel
    public JScrollPane getPanelTablasBD() {

        return panelTablasBD;
    }

    // Devuelve el panel con las tablas y sus columnas
    public JScrollPane setTablasBD() {

        JPanel panelTablasAux = new JPanel();
		panelTablasAux.setLayout(new BoxLayout(panelTablasAux, BoxLayout.Y_AXIS));

        Map<String,String[]> tablas = sql.calcularTablasBD();
        RepresentacionTabla.setPantallaDim(pantallaDim);

        for (Map.Entry<String,String[]> entry : tablas.entrySet()) panelTablasAux.add(new RepresentacionTabla(entry.getKey(), entry.getValue()));

        panelTablasBD = new JScrollPane(panelTablasAux);
        panelTablasBD.setVerticalScrollBarPolicy(22);
        panelTablasBD.setHorizontalScrollBarPolicy(31);
        panelTablasBD.getVerticalScrollBar().setUnitIncrement(50);
        Auxiliar.calcularSize(panelTablasBD, pantallaDim, 0.205, 0.85);
		Auxiliar.calcularLocation(panelTablasBD, pantallaDim, false, 0.005, 0.05);

        return panelTablasBD;
    }
}

class RepresentacionTabla extends JPanel {

    private static Dimension pantallaDim;
    private static int contador = 0;


    public RepresentacionTabla(String nombre, String[] columnas) {


        Font fuenteTablas = new Font("Arial", Font.BOLD, (int)(pantallaDim.getHeight()*0.015));
        Font fuenteColumnas = new Font("Arial", Font.PLAIN, (int)(pantallaDim.getHeight()*0.015));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, (int)(pantallaDim.getWidth()*0.2)));
        
        if (contador % 2 != 0) setBackground(new Color(219,219,219));
        contador++;

        JLabel tabla = new JLabel(nombre);
        tabla.setFont(fuenteTablas);
        add(tabla);

        for (int i = 0; i < columnas.length; i++) {

            JLabel columna = new JLabel("  -  " + columnas[i]);
            columna.setFont(fuenteColumnas);
            add(columna);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                // IMPLEMENTAR
            }
        });
    }

    public static void setPantallaDim(Dimension dim) {

        pantallaDim = dim;
    }
}
