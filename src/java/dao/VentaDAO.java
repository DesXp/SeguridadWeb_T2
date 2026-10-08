/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import conexion.ConexionSQL;
import modelo.Pasajero;
import modelo.Venta;
import modelo.Vuelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Desiderio
 */

public class VentaDAO {

    public String registrar(int idPasajero, Vuelo vuelo) {
        String codigo = UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        String sql = "INSERT INTO Venta (codigo, id_pasajero, id_vuelo, precio) VALUES (?, ?, ?, ?)";

        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.setInt(2, idPasajero);
            ps.setInt(3, vuelo.getIdVuelo());
            ps.setBigDecimal(4, vuelo.getPrecio()); // Garantiza el precio del catálogo en BD

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return codigo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Venta> listarPorDni(String dni) {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.id_venta, v.codigo, v.precio, v.fecha_compra, "
                   + "p.id_pasajero, p.dni, p.nombres, p.apellidos, p.correo, "
                   + "vu.id_vuelo, vu.numero_vuelo, vu.origen, vu.destino, vu.fecha, vu.hora "
                   + "FROM Venta v "
                   + "INNER JOIN Pasajero p ON v.id_pasajero = p.id_pasajero "
                   + "INNER JOIN Vuelo vu ON v.id_vuelo = vu.id_vuelo "
                   + "WHERE p.dni = ? ORDER BY v.fecha_compra DESC";

        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("id_venta"));
                    venta.setCodigo(rs.getString("codigo"));
                    venta.setPrecio(rs.getBigDecimal("precio"));
                    venta.setFechaCompra(rs.getTimestamp("fecha_compra"));

                    Pasajero p = new Pasajero();
                    p.setIdPasajero(rs.getInt("id_pasajero"));
                    p.setDni(rs.getString("dni"));
                    p.setNombres(rs.getString("nombres"));
                    p.setApellidos(rs.getString("apellidos"));
                    p.setCorreo(rs.getString("correo"));
                    venta.setPasajero(p);

                    Vuelo vu = new Vuelo();
                    vu.setIdVuelo(rs.getInt("id_vuelo"));
                    vu.setNumeroVuelo(rs.getString("numero_vuelo"));
                    vu.setOrigen(rs.getString("origen"));
                    vu.setDestino(rs.getString("destino"));
                    vu.setFecha(rs.getDate("fecha"));
                    vu.setHora(rs.getTime("hora"));
                    venta.setVuelo(vu);

                    lista.add(venta);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
