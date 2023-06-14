package sql;

import java.sql.*;

public class ConexionPrincipal {

    Connection conector = null;

    public boolean abrirConexion(String patch) {

		boolean resultado = false;
        
		try {

            if (conector == null || conector.isClosed()) {

			    conector = DriverManager.getConnection("jdbc:sqlite:" + patch + ".db");
			    resultado = true;
		    }
        } catch (SQLException e) { e.printStackTrace(); }

		return resultado;
	}

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
}
