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

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_LatamT2;user=sa;password=123;encrypt=false;";

    public static Connection getConexion() {

        Connection cn = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.err.println("Error de conexión; " + e.getMessage());
        }
        return cn;
    }

}
