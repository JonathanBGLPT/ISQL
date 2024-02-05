package sql;

import java.sql.*;

public class ConexionPrincipal {

    Connection conector = null;

    public boolean abrirConexion(String ruta) {

		boolean resultado = false;
        System.out.println(ruta);
		try {

            if (conector == null || conector.isClosed()) {

			    conector = DriverManager.getConnection("jdbc:sqlite:" + ruta);
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