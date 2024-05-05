package sql;

import java.sql.*;
import java.util.*;

import javax.swing.*;

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
            if (tipoDeDato.equals("Entero") && !claveForanea.equals("-")) sentenciaClavesForaneas += "FOREIGN KEY (" + nombre + ") REFERENCES " + claveForanea + "(id_" + claveForanea + ") ON DELETE CASCADE,";
        };
        sentenciaSQLite += sentenciaClavesForaneas;
        sentenciaSQLite = sentenciaSQLite.substring(0, sentenciaSQLite.length()-1) + ");";

        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute(sentenciaSQLite);
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void cambiarNombreTabla(String nombreTablaAntiguo, String nombreTablaNuevo) {

        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute("ALTER TABLE " + nombreTablaAntiguo + " RENAME TO " + nombreTablaNuevo + ";");
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("rawtypes")
    public void modificarTabla(String nombreTabla, Map<String,String> nombresCambiados, Set<String> camposBorrados, ArrayList<JPanel> camposNuevos) {

        Map<String, String> diccionario = getMapaConvertirTiposNaturalASQL();

        try (Statement sentencia = conector.createStatement()) {

            // Cambiar nombres
            for (String nombreOriginal : nombresCambiados.keySet()) sentencia.execute("ALTER TABLE " + nombreTabla + " RENAME COLUMN " + nombreOriginal + " TO " + nombresCambiados.get(nombreOriginal) + ";");

            // Borrar campos y agregar campos foraneos
            if (camposBorrados.size() > 0 || contieneUnaNuevaClaveForanea(camposNuevos)) {

                ArrayList<String[]> campos = obtenerCamposTabla(nombreTabla);
                String sentenciaSQLite = "CREATE TABLE nuevaTablaTemporal ( id_" + nombreTabla + " INTEGER PRIMARY KEY AUTOINCREMENT,";
                String sentenciaClavesForaneas = "";

                // Elimino los campos
                for (String[] campo : campos) {
        
                    if (!camposBorrados.contains(campo[0]) && !campo[0].equals("id_" + nombreTabla)) {

                        sentenciaSQLite += campo[0] + " " + diccionario.get(campo[1]) + ",";
                        if (campo[1].equals("Entero") && !campo[2].equals("")) sentenciaClavesForaneas += "FOREIGN KEY (" + campo[0] + ") REFERENCES " + campo[2] + "(id_" + campo[2] + ") ON DELETE CASCADE,";
                    }
                };

                // Agrego las claves foraneas
                for (JPanel panelAgregado : camposNuevos) {

                    String tipo = (String)(((JComboBox)panelAgregado.getComponent(3)).getSelectedItem());
                    if (tipo.equals("Entero") && !((String)(((JComboBox)panelAgregado.getComponent(5)).getSelectedItem())).equals("-")) {
    
                        String nombre = ((JTextField)panelAgregado.getComponent(1)).getText();
                        String claveForanea = (String)(((JComboBox)panelAgregado.getComponent(5)).getSelectedItem());
                        sentenciaSQLite += nombre + " " + diccionario.get(tipo) + ",";
                        sentenciaClavesForaneas += "FOREIGN KEY (" + nombre + ") REFERENCES " + claveForanea + "(id_" + claveForanea + ") ON DELETE CASCADE,";
                    }
                }
                // Creo la copia de la tabla y le paso los datos
                sentenciaSQLite += sentenciaClavesForaneas;
                sentenciaSQLite = sentenciaSQLite.substring(0, sentenciaSQLite.length()-1) + ");";
                sentencia.execute(sentenciaSQLite);

                ArrayList<String[]> campos2 = obtenerCamposTabla("nuevaTablaTemporal");
                sentenciaSQLite = "INSERT INTO nuevaTablaTemporal (";
                sentenciaClavesForaneas = "SELECT ";
                for (String[] campo : campos2) {

                    if (!esUnCampoNuevo(camposNuevos, campo[0])) {

                        sentenciaSQLite += campo[0] + ", ";
                        sentenciaClavesForaneas += campo[0] + ", ";
                    }
                }
                sentenciaSQLite = sentenciaSQLite.substring(0, sentenciaSQLite.length()-2) + ") ";
                sentenciaClavesForaneas = sentenciaClavesForaneas.substring(0, sentenciaClavesForaneas.length()-2) + " FROM " + nombreTabla + ";";
                sentencia.execute(sentenciaSQLite + sentenciaClavesForaneas);

                eliminarTabla(nombreTabla);
                cambiarNombreTabla("nuevaTablaTemporal", nombreTabla);
            }

            // Agregar campos normales
            for (JPanel panelAgregado : camposNuevos) {

                String tipo = (String)(((JComboBox)panelAgregado.getComponent(3)).getSelectedItem());
                if (!tipo.equals("Entero") || ((String)(((JComboBox)panelAgregado.getComponent(5)).getSelectedItem())).equals("-")) sentencia.execute("ALTER TABLE " + nombreTabla + " ADD COLUMN " + ((JTextField)panelAgregado.getComponent(1)).getText() + " " + diccionario.get(tipo) + ";");
            }
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("rawtypes")
    private boolean contieneUnaNuevaClaveForanea (ArrayList<JPanel> camposNuevos) {

        boolean resultado = false;

        for (int c = 0; c < camposNuevos.size() && !resultado; c++) {

            JPanel panelAgregado = camposNuevos.get(c);
            resultado = ((String)(((JComboBox)panelAgregado.getComponent(3)).getSelectedItem())).equals("Entero") && !((String)(((JComboBox)panelAgregado.getComponent(5)).getSelectedItem())).equals("-");
        }
        return resultado;
    }

    private boolean esUnCampoNuevo(ArrayList<JPanel> camposNuevos, String campo) {

        boolean resultado = false;

        for (int c = 0; c < camposNuevos.size() && !resultado; c++) resultado = ((JTextField)camposNuevos.get(c).getComponent(1)).getText().equals(campo);

        return resultado;
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

    public ArrayList<String[]> obtenerCamposTabla(String nombreTabla) {

        ArrayList<String[]> resultado = new ArrayList<>();
        Map<String,String> diccionario = getMapaConvertirTiposSQLANatural();

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("PRAGMA table_info(" + nombreTabla + ");")) {

            while (sentenciaResultado.next()) {

                String[] campos = new String[3];
                campos[0] = sentenciaResultado.getString("name");
                campos[1] = diccionario.get(sentenciaResultado.getString("type"));
                campos[2] = (comprobarClaveForanea(nombreTabla, sentenciaResultado.getString("name"))? obtenerTablaOriginalClaveForanea(nombreTabla,campos[0]) : "");
                resultado.add(campos);
            }
            sentenciaResultado.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    public boolean comprobarClaveForanea(String nombreTabla, String campo) {

        boolean resultado = false;

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("PRAGMA foreign_key_list(" + nombreTabla + ");")) {

            while (!resultado && sentenciaResultado.next()) resultado = campo.equals(sentenciaResultado.getString("from"));
            sentenciaResultado.close();

        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    public String obtenerTablaOriginalClaveForanea (String nombreTabla, String campo) {

        String resultado = null;

        try (ResultSet clavesForaneas = conector.getMetaData().getImportedKeys(conector.getCatalog(), null, nombreTabla)) {

            while (resultado == null && clavesForaneas.next()) {

                if (clavesForaneas.getString("FKCOLUMN_NAME").equals(campo)) resultado = clavesForaneas.getString("PKTABLE_NAME");
            }
            clavesForaneas.close();
            
        } catch (Exception e) { e.printStackTrace(); }

        return resultado;
    }
}
