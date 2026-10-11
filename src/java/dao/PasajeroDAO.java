
package dao;

import conexion.ConexionSQL;
import modelo.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PasajeroDAO {

  
    public int guardar(Pasajero pasajero) throws SQLException {
        try (Connection cn = ConexionSQL.getConexion()) {
            return guardar(cn, pasajero);
        }
    }

    public int guardar(Connection cn, Pasajero pasajero) throws SQLException {
        String sqlBuscar = "SELECT id_pasajero, dni, nombres, apellidos, correo "
                + "FROM Pasajero WHERE dni = ?";
        try (PreparedStatement ps = cn.prepareStatement(sqlBuscar)) {
            ps.setString(1, pasajero.getDni());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pasajero.setIdPasajero(rs.getInt("id_pasajero"));
                    pasajero.setDni(rs.getString("dni"));
                    pasajero.setNombres(rs.getString("nombres"));
                    pasajero.setApellidos(rs.getString("apellidos"));
                    pasajero.setCorreo(rs.getString("correo"));
                    return pasajero.getIdPasajero();
                }
            }
        }

        String sqlInsertar = "INSERT INTO Pasajero (dni, nombres, apellidos, correo) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(
                sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pasajero.getDni());
            ps.setString(2, pasajero.getNombres());
            ps.setString(3, pasajero.getApellidos());
            ps.setString(4, pasajero.getCorreo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pasajero.setIdPasajero(rs.getInt(1));
                    return pasajero.getIdPasajero();
                }
            }
        }
        throw new SQLException("No se pudo registrar al pasajero.");
    }
}
