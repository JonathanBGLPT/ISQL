package sql;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ConexionGestionConsultas {
    
    private Connection conector = null;
    
    ConexionGestionConsultas (Connection con) {

        conector = con;
    }

    public ArrayList<String[]> obtenerConsultaFiltrada(String nombreTabla, ArrayList<String[]> filtros) {

        String sentenciaSQL = "SELECT ";
        for (String[] filtro : filtros) sentenciaSQL += filtro[0] + ", ";
        sentenciaSQL = sentenciaSQL.substring(0, sentenciaSQL.length()-2) + " FROM " + nombreTabla; 

        String sentenciaCondicional = " WHERE ";
        for (String[] filtro : filtros) {

            if (filtro[1] != "-") {

                if (filtro[2].equals("Texto")) {

                    if (filtro[1].equals("igual")) sentenciaCondicional += filtro[0] + " = '" + filtro[3] + "' AND ";
                    if (filtro[1].equals("contenga")) sentenciaCondicional += filtro[0] + " LIKE '%" + filtro[3] + "%' AND ";
                    if (filtro[1].equals("empiece")) sentenciaCondicional += filtro[0] + " LIKE '" + filtro[3] + "%' AND ";
                    if (filtro[1].equals("acabe en")) sentenciaCondicional += filtro[0] + " LIKE '%" + filtro[3] + "' AND ";

                } else sentenciaCondicional += filtro[1].equals("rango")? filtro[0] + " BETWEEN " + filtro[3] + " AND " + filtro[4] + " AND " : filtro[0] + " " + filtro[1] + " " + filtro[3] + " AND ";

            }
        }
        sentenciaSQL = sentenciaSQL + ((sentenciaCondicional.length() > 7)? sentenciaCondicional.substring(0, sentenciaCondicional.length()-5) + ";" : ";");

        ArrayList<String[]> resultado = new ArrayList<>();

        try (ResultSet sentenciaResultado = conector.createStatement().executeQuery(sentenciaSQL)) {

            int numeroCampos = sentenciaResultado.getMetaData().getColumnCount();
            while (sentenciaResultado.next()) {

                String[] fila = new String[numeroCampos];
                for (int c = 0; c < fila.length; c++) {

                    String dato = sentenciaResultado.getString(c+1);
                    if ("Fecha".equals(filtros.get(c)[2])) {

                        fila[c] = (dato == null)? "" : new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(dato)));

                    } else fila[c] = (dato == null)? "" : dato;
                }
                resultado.add(fila);
            }
            sentenciaResultado.close();
            
        } catch (SQLException e) { e.printStackTrace(); }

        return resultado;
     }
}
