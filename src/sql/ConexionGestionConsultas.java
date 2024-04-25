package sql;

import java.sql.Connection;

public class ConexionGestionConsultas {
    
    private Connection conector = null;
    
    ConexionGestionConsultas (Connection con) {

        conector = con;
    }
}
