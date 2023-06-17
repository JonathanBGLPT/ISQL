package sql;

import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

public class ConexionPrincipal  {

    // Conector con la BD
    Connection conector = null;

    // Datos compartidos
    Map<String,String[]> tablasBD;

    // Abre la conexion con la BD
    public boolean abrirConexion(String patch) {

		boolean resultado = false;
        
		try {

            if (conector == null || conector.isClosed()) {

			    conector = DriverManager.getConnection("jdbc:sqlite:" + patch);
			    resultado = true;
		    }
        } catch (SQLException e) { e.printStackTrace(); }

		return resultado;
	}

    // Cierra la conexion con la BD
    public boolean cerrarConexion() {

        boolean resultado = false;

        try {

            if (conector != null) {
            
                conector.close();
                resultado = true;
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    // Devuelve si esta conectado a alguna BD
    public boolean estaConectado() {

        return conector != null;
    }

    // Devuelve las tablas y sus columnas
    public Map<String,String[]> getTablasBG() {

        return (tablasBD == null)? calcularTablasBD() : tablasBD;
    }

    // Calcula todas las tablas y sus columnas
    public Map<String,String[]> calcularTablasBD() {

        tablasBD = new HashMap<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            
            statement = conector.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
            while (resultSet.next()) tablasBD.put(resultSet.getString("name"),null);

            for (String tabla : tablasBD.keySet()) {

                ResultSetMetaData metaData = statement.executeQuery("SELECT * FROM " + tabla + " LIMIT 1").getMetaData();
                String[] nombres = new String[metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) nombres[i-1] = metaData.getColumnName(i);
                tablasBD.put(tabla,nombres);
            }

        } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Ha ocurrido un problema al cargar las tablas y sus columnas."); } finally {

            try {

                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();

            } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Ha ocurrido un problema al cargar las tablas y sus columnas."); }

        }
        return tablasBD;
    }

}
