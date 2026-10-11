
package dao;

import conexion.ConexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import modelo.Vuelo;

public class VueloDAO {
    
  
    public List<Vuelo> listar() throws SQLException {
        List<Vuelo> lista = new ArrayList<>();
        String sql = "SELECT id_vuelo, numero_vuelo, origen, destino, fecha, hora, precio "
                   + "FROM Vuelo WHERE fecha >= CAST(GETDATE() AS DATE) ORDER BY fecha, hora";
        
       
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                 Vuelo vuelo = new Vuelo();
                vuelo.setIdVuelo(rs.getInt("id_vuelo"));
                vuelo.setNumeroVuelo(rs.getString("numero_vuelo"));
                vuelo.setOrigen(rs.getString("origen"));
                vuelo.setDestino(rs.getString("destino"));
                vuelo.setFecha(rs.getDate("fecha"));
                vuelo.setHora(rs.getTime("hora"));
                vuelo.setPrecio(rs.getBigDecimal("precio"));
                lista.add(vuelo);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar vuelos: " + ex.getMessage());
            throw ex;
        }
        return lista;
    }
    
    // busca un vuelo
 
    
    public Vuelo buscar(int idVuelo) throws SQLException {
       
        String sql = "SELECT id_vuelo, numero_vuelo, origen, destino, fecha, hora, precio "
                   + "FROM Vuelo WHERE id_vuelo = ?";
        
        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idVuelo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   Vuelo vuelo = new Vuelo();
                    vuelo.setIdVuelo(rs.getInt("id_vuelo"));
                    vuelo.setNumeroVuelo(rs.getString("numero_vuelo"));
                    vuelo.setOrigen(rs.getString("origen"));
                    vuelo.setDestino(rs.getString("destino"));
                    vuelo.setFecha(rs.getDate("fecha"));
                    vuelo.setHora(rs.getTime("hora"));
                    vuelo.setPrecio(rs.getBigDecimal("precio"));
                    return vuelo;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar vuelo: " + ex.getMessage());
            throw ex;
        }
        return null;
    }
}
