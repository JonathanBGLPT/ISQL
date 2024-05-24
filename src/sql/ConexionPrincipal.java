package sql;

import java.sql.*;
import java.util.*;

import javax.swing.JPanel;

public class ConexionPrincipal {

    public String ultimaRutaBD;

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
                conector.createStatement().execute("PRAGMA foreign_keys = ON;");
                ultimaRutaBD = ruta;
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
    public void crearTabla(String nombreTabla, ArrayList<JPanel> campos) {

        gestionTablas.crearTabla(nombreTabla, campos);
    }

    public void eliminarTabla(String nombreTabla) {

        gestionTablas.eliminarTabla(nombreTabla);
    }

    public void cambiarNombreTabla(String nombreTablaAntiguo, String nombreTablaNuevo) {

        gestionTablas.cambiarNombreTabla(nombreTablaAntiguo, nombreTablaNuevo);
    }

    public void modificarTabla(String nombreTabla, Map<String,String> nombresCambiados, Set<String> camposBorrados, ArrayList<JPanel> camposNuevos) {

        gestionTablas.modificarTabla(nombreTabla, nombresCambiados, camposBorrados, camposNuevos);
    }

    public ArrayList<String> obtenerNombreTablas() {

        return gestionTablas.obtenerNombreTablas();
    }

    public ArrayList<String[]> obtenerCamposTabla(String nombreTabla) {

        return gestionTablas.obtenerCamposTabla(nombreTabla);
    }

    public boolean comprobarClaveForanea(String nombreTabla, String campo) {

        return gestionTablas.comprobarClaveForanea(nombreTabla, campo);
    }

    public String obtenerTablaOriginalClaveForanea (String nombreTabla, String campo) {

        return gestionTablas.obtenerTablaOriginalClaveForanea(nombreTabla, campo);
    }

    /*****************
     * GESTION DATOS *
     *****************/
    public ArrayList<String[]> obtenerTodosLosDatosTabla(String nombreTabla) {

        return gestionDatos.obtenerTodosLosDatosTabla(nombreTabla);
    }

    public boolean insertarFila(String sentenciaSQL, String[] valores, String[] tipos) {

        return gestionDatos.insertarFila(sentenciaSQL, valores, tipos);
    }

    public void eliminarListaDeDatos(String nombreTabla, ArrayList<Integer> listaIds) {

        gestionDatos.eliminarListaDeDatos(nombreTabla, listaIds);
    }

    public void actualizarListaDeDatos(String nombreTabla, Map<Integer,JPanel> filasCambiadas, ArrayList<String> cabecera) {

        gestionDatos.actualizarListaDeDatos(nombreTabla, filasCambiadas, cabecera);
    }
     /********************
     * GESTION CONSULTAS *
     *********************/

     public ArrayList<String[]> obtenerConsultaFiltrada(String nombreTabla, ArrayList<String[]> filtros) {

        return gestionConsultas.obtenerConsultaFiltrada(nombreTabla, filtros);
     }
}