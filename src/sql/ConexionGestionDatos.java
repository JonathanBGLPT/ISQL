package sql;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

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

                    if (campos.get(c)[1].equals("Fecha")) {

                        fila[c] = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(sentenciaResultado.getString(c+1))));

                    } else if (campos.get(c)[1].equals("Imagen")) {

                        /// IMPLEMENTAR COMO DEVUELVO LAS IMAGENES

                    } else fila[c] = sentenciaResultado.getString(c+1);
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

                        sentenciaPreparada.setInt(v+1, Integer.parseInt(valores[v]));
                        break;

                    case "Decimal":

                        sentenciaPreparada.setDouble(v+1, Double.parseDouble(valores[v]));
                        break;

                    case "Texto":

                        sentenciaPreparada.setString(v+1, valores[v]);
                        break;

                    case "Fecha":

                        sentenciaPreparada.setDate(v+1, convertirFechaAFormatoSQL(valores[v]));
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
