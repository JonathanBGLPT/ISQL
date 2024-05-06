package sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConexionGestionDatos {
    
    private Connection conector = null;
    
    ConexionGestionDatos (Connection con) {

        conector = con;
    }

    public ArrayList<String[]> obtenerTodosLosDatosTabla(String nombreTabla) {

        ArrayList<String[]> resultado = new ArrayList<>();

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery("SELECT * FROM " + nombreTabla + ";")) {

            int numeroCampos = sentenciaResultado.getMetaData().getColumnCount();
            while (sentenciaResultado.next()) {

                String[] fila = new String[numeroCampos];
                for (int c = 0; c < fila.length; c++) fila[c] = sentenciaResultado.getString(c+1);
                resultado.add(fila);
            }
            sentenciaResultado.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
    }
}
