package sql;

import java.sql.Connection;

public class ConexionGestionDatos {
    
    private Connection conector = null;
    
    ConexionGestionDatos (Connection con) {

        conector = con;
    }
}
