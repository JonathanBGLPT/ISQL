package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConexionGestionTablas {
    
    private Connection conector = null;
    
    ConexionGestionTablas (Connection con) {

        conector = con;
    }

    @SuppressWarnings("rawtypes")
    public void crearTabla(String nombreTabla, ArrayList<JPanel> campos) {

        String sentenciaSQLite = "CREATE TABLE " + nombreTabla + " ( id_" + nombreTabla + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        String sentenciaClavesForaneas = "";
        Map<String, String> diccionario = getMapaConvertirTiposNaturalASQL();

        for (int c = 0; c < campos.size(); c++) {

            String nombre = ((JTextField)campos.get(c).getComponent(1)).getText();
            String tipoDeDato = (String)(((JComboBox)campos.get(c).getComponent(3)).getSelectedItem());
            String claveForanea = (String)(((JComboBox)campos.get(c).getComponent(5)).getSelectedItem());

            sentenciaSQLite += nombre + " " + diccionario.get(tipoDeDato) + ",";
            if (tipoDeDato.equals("Entero") && !claveForanea.equals("-")) sentenciaClavesForaneas += "FOREIGN KEY (" + nombre + ") REFERENCES " + claveForanea + "(id_" + claveForanea + "),";
        };
        sentenciaSQLite += sentenciaClavesForaneas;
        sentenciaSQLite = sentenciaSQLite.substring(0, sentenciaSQLite.length()-1) + ");";

        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute(sentenciaSQLite);
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static Map<String,String> getMapaConvertirTiposNaturalASQL() {

        Map<String, String> diccionario = new HashMap<>();
        diccionario.put("Entero", "INTEGER");
        diccionario.put("Decimal", "REAL");
        diccionario.put("Texto", "TEXT");
        diccionario.put("Fecha", "DATE");
        diccionario.put("Imagen", "BLOB");

        return diccionario;
    }

    private static Map<String,String> getMapaConvertirTiposSQLANatural() {

        Map<String, String> diccionario = new HashMap<>();
        diccionario.put("INTEGER", "Entero");
        diccionario.put("REAL", "Decimal");
        diccionario.put("TEXT", "Texto");
        diccionario.put("DATE", "Fecha");
        diccionario.put("BLOB", "Imagen");

        return diccionario;
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

    public ArrayList<String> obtenerCamposTabla(String nombreTabla) {

        ArrayList<String> resultado = new ArrayList<>();
        Map<String,String> diccionario = getMapaConvertirTiposSQLANatural();

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("PRAGMA table_info(" + nombreTabla + ");")) {

            while (sentenciaResultado.next()) {

                String tipoDeDato = sentenciaResultado.getString("type");
                tipoDeDato = diccionario.get(tipoDeDato) + (comprobarClaveForanea(nombreTabla, sentenciaResultado.getString("name"))? "*" : "");
                resultado.add("   - " + sentenciaResultado.getString("name") + ": " + tipoDeDato);
            }

        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    private boolean comprobarClaveForanea(String nombreTabla, String campo) {

        boolean resultado = false;

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("PRAGMA foreign_key_list(" + nombreTabla + ");")) {

            while (!resultado && sentenciaResultado.next()) resultado = campo.equals(sentenciaResultado.getString("from"));

        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }
}
