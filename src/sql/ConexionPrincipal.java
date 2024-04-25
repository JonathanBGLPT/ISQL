package sql;

import java.sql.*;
import java.util.ArrayList;

public class ConexionPrincipal {

    private Connection conector = null;
    private ConexionGestionTablas gestionTablas;
    private ConexionGestionDatos gestionDatos;
    private ConexionGestionConsultas gestionConsultas;

    public boolean abrirConexion(String ruta) {

		boolean resultado = false;
		try {

            if (conector == null || conector.isClosed()) {

			    conector = DriverManager.getConnection("jdbc:sqlite:" + ruta);
                gestionTablas = new ConexionGestionTablas(conector);
                gestionDatos = new ConexionGestionDatos(conector);
                gestionConsultas = new ConexionGestionConsultas(conector);
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
                gestionTablas = null;
                gestionDatos = null;
                gestionConsultas = null;
                resultado = true;
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    /******************
     * GESTION TABLAS *
     ******************/
    public void crearTabla(String nombreTabla) {

        gestionTablas.crearTabla(nombreTabla);
    }

    public void eliminarTabla(String nombreTabla) {

        gestionTablas.eliminarTabla(nombreTabla);
    }

    public ArrayList<String> obtenerNombreTablas() {

        return gestionTablas.obtenerNombreTablas();
    }

    public ArrayList<String> obtenerAtributosTabla(String nombreTabla) {

        return gestionTablas.obtenerAtributosTabla(nombreTabla);
    }

    /*****************
     * GESTION DATOS *
     *****************/


     /********************
     * GESTION CONSULTAS *
     *********************/
}