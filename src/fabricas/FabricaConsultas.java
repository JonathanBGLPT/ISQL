package fabricas;

import java.awt.*;
import javax.swing.*;
import interfaz.*;
import sql.*;

public class FabricaConsultas {
    
    // Variable generales y el panel
    private Dimension pantallaDim;
    private ConexionPrincipal sql;
    private JPanel panelConsultas;

    // Constructor
    public FabricaConsultas(Dimension dim, ConexionPrincipal con) {

        pantallaDim = dim;
        sql = con;
    }

    // Devuelve el panel
    public JPanel getPanelConsultas() {

        return panelConsultas;
    }

    // Devuelve el panel vacio
    public JPanel setConsultasVacio() {

        panelConsultas = new JPanel();
		panelConsultas.setLayout(null);
		panelConsultas.setBackground(Color.WHITE);
		panelConsultas.setBorder(BorderFactory.createLineBorder(new Color(122, 138, 153)));
		Auxiliar.calcularSize(panelConsultas, pantallaDim, 0.42, 0.85);
		Auxiliar.calcularLocation(panelConsultas, pantallaDim, false, 0.01, 0.12);

        return panelConsultas;
    }

    // Devuelve el panel en modo filtro
    public JPanel setConsultasFiltrar() {

        // IMPLEMENTAR

        return panelConsultas;
    }

    // Devuelve el panel en modo consultar
    public JPanel setConsultasConsultar() {

        // IMPLEMENTAR

        return panelConsultas;
    }

    // Devuelve el panel en modo chatGPT
    public JPanel setConsultasChatGPT() {

        // IMPLEMENTAR
        
        return panelConsultas;
    }

    // Devuelve el panel en modo introducir
    public JPanel setConsultasIntroducir() {

        // IMPLEMENTAR

        return panelConsultas;
    }
}
