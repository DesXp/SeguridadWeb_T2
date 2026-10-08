/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.ConexionSQL;
import modelo.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Desiderio
 */
public class PasajeroDAO {

    public Pasajero obtenerPorDni(String dni) {
        Pasajero p = null;
        String sql = "SELECT id_pasajero, dni, nombres, apellidos, correo FROM Pasajero WHERE dni = ?";
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Pasajero();
                    p.setIdPasajero(rs.getInt("id_pasajero"));
                    p.setDni(rs.getString("dni"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setCorreo(rs.getString("correo"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public int guardar(Pasajero pasajero) {
        Pasajero existente = obtenerPorDni(pasajero.getDni());
        if (existente != null) {
            return existente.getIdPasajero();
        }

        String sql = "INSERT INTO Pasajero (dni, nombres, apellidos, correo) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pasajero.getDni());
            ps.setString(2, pasajero.getNombres());
            ps.setString(3, pasajero.getApellidos());
            ps.setString(4, pasajero.getCorreo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}