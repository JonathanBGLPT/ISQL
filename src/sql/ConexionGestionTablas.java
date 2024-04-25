package sql;

import java.sql.*;
import java.util.ArrayList;

public class ConexionGestionTablas {
    
    private Connection conector = null;
    
    ConexionGestionTablas (Connection con) {

        conector = con;
    }

    public void crearTabla(String nombreTabla) {

        /// CAMBIAR PARA QUE LA CREE CON TODOS LOS ATRIBUTOS
        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute("CREATE TABLE " + nombreTabla + " (id INTEGER PRIMARY KEY autoincrement, nombre VARCHAR(32));");
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminarTabla(String nombreTabla) {

        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute("DROP TABLE " + nombreTabla + ";");
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ArrayList<String> obtenerNombreTablas() {

        ArrayList<String> resultado = new ArrayList<>();

       try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("SELECT name FROM sqlite_master WHERE type='table';")) {
 
            while (sentenciaResultado.next()) {

                String nombre = sentenciaResultado.getString("name");
                if (!nombre.equals("sqlite_sequence")) resultado.add(nombre);
            }
            sentenciaResultado.close();

       } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    public ArrayList<String> obtenerAtributosTabla(String nombreTabla) {

        ArrayList<String> resultado = new ArrayList<>();

        /// CAMBIAR PARA QUE DEVUELVA CLAVES FORANEAS, CLAVES, TIPOS, ETC.
        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("PRAGMA table_info(" + nombreTabla + ");")) {

            while (sentenciaResultado.next()) resultado.add("   - " + sentenciaResultado.getString("name") + ": " + sentenciaResultado.getString("type"));

        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }
}
