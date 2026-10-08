/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.ConexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Vuelo;

/**
 *
 * @author Desiderio
 */

public class VueloDAO {
    
    // Lista los vuelos con fecha desde hoy en adelante (Paso 2)
    public List<Vuelo> listar() {
        List<Vuelo> lista = new ArrayList<>();
        String sql = "SELECT id_vuelo, numero_vuelo, origen, destino, fecha, hora, precio "
                   + "FROM Vuelo WHERE fecha >= CAST(GETDATE() AS DATE) ORDER BY fecha, hora";
        
        // Uso de PreparedStatement según la rúbrica
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Vuelo v = new Vuelo();
                v.setIdVuelo(rs.getInt("id_vuelo"));
                v.setNumeroVuelo(rs.getString("numero_vuelo"));
                v.setOrigen(rs.getString("origen"));
                v.setDestino(rs.getString("destino"));
                v.setFecha(rs.getDate("fecha"));
                v.setHora(rs.getTime("hora"));
                v.setPrecio(rs.getBigDecimal("precio"));
                lista.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // MÉTODO REQUERIDO: busca un vuelo por su ID
    // Este método es fundamental para el control de integridad de precios (Paso 4)
    
    public Vuelo buscar(int idVuelo) {
        Vuelo v = null;
        String sql = "SELECT id_vuelo, numero_vuelo, origen, destino, fecha, hora, precio "
                   + "FROM Vuelo WHERE id_vuelo = ?";
        
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idVuelo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new Vuelo();
                    v.setIdVuelo(rs.getInt("id_vuelo"));
                    v.setNumeroVuelo(rs.getString("numero_vuelo"));
                    v.setOrigen(rs.getString("origen"));
                    v.setDestino(rs.getString("destino"));
                    v.setFecha(rs.getDate("fecha"));
                    v.setHora(rs.getTime("hora"));
                    v.setPrecio(rs.getBigDecimal("precio"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
