package fabricas;

import java.awt.*;
import javax.swing.*;
import interfaz.*;
import sql.*;

public class FabricaTablas {
    
    // Variable generales y el panel
    private Dimension pantallaDim;
    private ConexionPrincipal sql;
    private JPanel panelTablas;

    // Constructor
    public FabricaTablas(Dimension dim, ConexionPrincipal con) {

        pantallaDim = dim;
        sql = con;
    }

    // Devuelve el panel
    public JPanel getPanelTablas() {

        return panelTablas;
    }

    public JPanel setTablas(String tabla) {

        panelTablas = new JPanel();
		panelTablas.setLayout(null);
		panelTablas.setBackground(Color.WHITE);
		panelTablas.setBorder(BorderFactory.createLineBorder(new Color(122, 138, 153)));
		Auxiliar.calcularSize(panelTablas, pantallaDim, 0.31, 0.85);
		Auxiliar.calcularLocation(panelTablas, pantallaDim, false, 0.01, 0.12);

        if (tabla != null) {

            // IMPLEMENTAR
        }

        return panelTablas;
    }

    // Devuelve el panel en modo crear
    public JPanel setTablasCrear() {

        // IMPLEMENTAR

        return panelTablas;
    }

    // Devuelve el panel en modo modificar
    public JPanel setTablasModificar() {

        // IMPLEMENTAR

        return panelTablas;
    }
}
