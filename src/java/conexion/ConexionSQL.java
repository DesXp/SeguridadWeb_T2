/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Desiderio
 */
public class ConexionSQL {

    // Contraseña de prueba / no oficional
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_LatamT2;user=sa;password=123;encrypt=false;";

       public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver JDBC de SQL Server.", e);
        }
        return DriverManager.getConnection(URL);
    }
}
