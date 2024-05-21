package sql;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import javax.swing.*;

import interfaz.Auxiliar;

public class ConexionGestionDatos {
    
    private Connection conector = null;
    
    ConexionGestionDatos (Connection con) {

        conector = con;
    }

    public ArrayList<String[]> obtenerTodosLosDatosTabla(String nombreTabla) {

        ArrayList<String[]> resultado = new ArrayList<>();
        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTabla);

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("SELECT * FROM " + nombreTabla + ";")) {

            int numeroCampos = sentenciaResultado.getMetaData().getColumnCount();
            while (sentenciaResultado.next()) {

                String[] fila = new String[numeroCampos];
                for (int c = 0; c < fila.length; c++) {

                    String dato = sentenciaResultado.getString(c+1);
                    if (campos.get(c)[1].equals("Fecha")) {

                        fila[c] = (dato == null)? "" : new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(dato)));

                    } else if (campos.get(c)[1].equals("Imagen")) {

                        /// IMPLEMENTAR COMO DEVUELVO LAS IMAGENES

                    } else fila[c] = (dato == null)? "" : dato;
                }
                resultado.add(fila);
            }
            sentenciaResultado.close();
            
        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }

    public boolean insertarFila(String sentenciaSQL, String[] valores, String[] tipos) {

        boolean resultado = false;

        try (PreparedStatement sentenciaPreparada = conector.prepareStatement(sentenciaSQL)) {

            for (int v = 0; v < valores.length; v++) {

                switch (tipos[v]) {

                    case "Entero":

                        if (!valores[v].equals("")) {

                            sentenciaPreparada.setInt(v+1, Integer.parseInt(valores[v]));

                        } else sentenciaPreparada.setNull(v+1, java.sql.Types.INTEGER);
                        break;

                    case "Decimal":

                        if (!valores[v].equals("")) {

                            sentenciaPreparada.setDouble(v+1, Double.parseDouble(valores[v].replace(",", ".")));

                        } else sentenciaPreparada.setNull(v+1, java.sql.Types.DOUBLE);
                        break;

                    case "Texto":

                        if (!valores[v].equals("")) {

                            sentenciaPreparada.setString(v+1, valores[v]);

                        } else sentenciaPreparada.setNull(v+1, java.sql.Types.VARCHAR);
                        break;

                    case "Fecha":

                        if (!valores[v].equals("") && Auxiliar.comprobarFechaNumeros(valores[v])) {

                            sentenciaPreparada.setDate(v+1, convertirFechaAFormatoSQL(valores[v]));

                        } else sentenciaPreparada.setNull(v+1, java.sql.Types.DATE);
                        break;

                    case "Imagen":

                        /// IMPLEMENTAR
                        break;
                }
            }
            sentenciaPreparada.executeUpdate();
            resultado = true;

        } catch (Exception e) { return false; }

        return resultado;
    }

    public void eliminarTodosLosDatos(String nombreTabla) {

        try (Statement sentencia = conector.createStatement()) {

            sentencia.execute("DELETE FROM " + nombreTabla + ";");
            sentencia.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminarListaDeDatos(String nombreTabla, ArrayList<Integer> listaIds) {

        String sentenciaSQL = "DELETE FROM " + nombreTabla + " WHERE id_" + nombreTabla + " = ?;";

        try (PreparedStatement sentenciaPreparada = conector.prepareStatement(sentenciaSQL)) {

            for (Integer id : listaIds) {

                sentenciaPreparada.setInt(1, id);
                sentenciaPreparada.execute();
            }
            sentenciaPreparada.close();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void actualizarListaDeDatos(String nombreTabla, Map<Integer,JPanel> filasCambiadas) {

        ArrayList<Integer> idsConErrores = new ArrayList<>();
        ArrayList<String[]> campos = Auxiliar.conexionSQL.obtenerCamposTabla(nombreTabla);

        String sentenciaSQL = "UPDATE " + nombreTabla + " SET ";
        for (int c = 1; c < campos.size(); c++) sentenciaSQL += campos.get(c)[0] + " = ?, ";
        sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + " WHERE id_" + nombreTabla + " = ?;";
        
        try (PreparedStatement sentenciaPreparada = conector.prepareStatement(sentenciaSQL)) {

            for (int id : filasCambiadas.keySet()) {

                int c = 5;
                boolean valido = true;
                JPanel panelFila = filasCambiadas.get(id);
                if (((JCheckBox)panelFila.getComponent(1)).isSelected()) {

                    try {

                        while (valido && c < panelFila.getComponentCount()) {

        
                            String dato = ((JTextField)panelFila.getComponent(c)).getText().trim();
                            switch (campos.get(c-4)[1]) {
    
                                case "Entero":
            
                                    if (!dato.equals("")) {
            
                                        sentenciaPreparada.setInt(c-4, Integer.parseInt(dato));
            
                                    } else sentenciaPreparada.setNull(c-4, java.sql.Types.INTEGER);
                                    break;
            
                                case "Decimal":
            
                                    if (!dato.equals("")) {
            
                                        sentenciaPreparada.setDouble(c-4, Double.parseDouble(dato.replace(",", ".")));
            
                                    } else sentenciaPreparada.setNull(c-4, java.sql.Types.DOUBLE);
                                    break;
            
                                case "Texto":
            
                                    if (!dato.equals("")) {
            
                                        sentenciaPreparada.setString(c-4, dato);
            
                                    } else sentenciaPreparada.setNull(c-4, java.sql.Types.VARCHAR);
                                    break;
            
                                case "Fecha":
            
                                    if (!dato.equals("")) {
            
                                        if (!Auxiliar.comprobarFechaNumeros(dato)) throw new IllegalArgumentException();
                                        sentenciaPreparada.setDate(c-4, convertirFechaAFormatoSQL(dato));
            
                                    } else sentenciaPreparada.setNull(c-4, java.sql.Types.DATE);
                                    break;
            
                                case "Imagen":
            
                                    /// IMPLEMENTAR
                                    break;
                            }
                            c++;
                        }
                        sentenciaPreparada.setInt(c-4, id);
                        if (valido) sentenciaPreparada.execute();

                    } catch (Exception e) { 
        
                        idsConErrores.add(id);
                        valido = false;
                    }
                }
            }
            sentenciaPreparada.close();

        } catch (SQLException e) { e.printStackTrace(); }

        String idsErroneos = "";
        for (int id : idsConErrores) idsErroneos += id + ", ";
        if (idsErroneos.length() == 0) {

            JOptionPane.showMessageDialog(null, "Todos los datos se han actualizado correctamente."); 

        } else JOptionPane.showMessageDialog(null, "No se han podido actualizar los datos correspondientes a los siguientes ids:\n" + idsErroneos.substring(0, idsErroneos.length()-2) + "."); 
    }

    private java.sql.Date convertirFechaAFormatoSQL(String fecha) {

        SimpleDateFormat formato;

        if (fecha.contains("/")) {

            if (fecha.indexOf('/') == 4) {

                formato = new SimpleDateFormat("yyyy/MM/dd");

            } else formato = new SimpleDateFormat("dd/MM/yyyy");

        } else if (fecha.contains("-")) {

            if (fecha.indexOf('-') == 4) {

                formato = new SimpleDateFormat("yyyy-MM-dd");

            } else formato = new SimpleDateFormat("dd-MM-yyyy");

        } else return null;

        try {

            return new java.sql.Date(formato.parse(fecha).getTime());

        } catch (ParseException e) { return null; }
    }
}
