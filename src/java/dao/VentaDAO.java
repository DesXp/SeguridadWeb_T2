
package dao;


import conexion.ConexionSQL;
import modelo.Pasajero;
import java.math.BigDecimal;
import modelo.Venta;
import modelo.Vuelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class VentaDAO {

     private final PasajeroDAO pasajeroDAO = new PasajeroDAO();


    public String registrar(int idPasajero, Vuelo vuelo) throws SQLException {
        try (Connection cn = ConexionSQL.getConexion()) {
            return registrar(cn, idPasajero, vuelo);
        }
    }

   
    public String registrarCompra(Pasajero pasajero, Vuelo vuelo) throws SQLException {
        Connection cn = null;
        try {
            cn = ConexionSQL.getConexion();
            cn.setAutoCommit(false);

            int idPasajero = pasajeroDAO.guardar(cn, pasajero);
            String codigo = registrar(cn, idPasajero, vuelo);
            cn.commit();
            return codigo;

        } catch (SQLException ex) {
            System.out.println("Error al registrar la compra: " + ex.getMessage());
            if (cn != null) {
                try {
                    cn.rollback();
                } catch (SQLException exRollback) {
                    System.out.println("Error en rollback: " + exRollback.getMessage());
                }
            }
            throw ex;
        } finally {
            if (cn != null) {
                try {
                    cn.setAutoCommit(true);
                } catch (SQLException exRestaurar) {
                    System.out.println("Error al restaurar la conexion: " + exRestaurar.getMessage());
                }
                try {
                    cn.close();
                } catch (SQLException exCierre) {
                    System.out.println("Error al cerrar la conexion: " + exCierre.getMessage());
                }
            }
        }
    }

    private String registrar(Connection cn, int idPasajero, Vuelo vuelo)
            throws SQLException {
     
        String sqlPrecio = "SELECT precio FROM Vuelo WHERE id_vuelo = ?";
        BigDecimal precio;
        try (PreparedStatement ps = cn.prepareStatement(sqlPrecio)) {
            ps.setInt(1, vuelo.getIdVuelo());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("El vuelo no existe.");
                }
                precio = rs.getBigDecimal("precio");
            }
        }

        String codigo = UUID.randomUUID().toString().replace("-", "")
                .substring(0, 6).toUpperCase();
        String sqlInsertar = "INSERT INTO Venta (codigo, id_pasajero, id_vuelo, precio) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sqlInsertar)) {
            ps.setString(1, codigo);
            ps.setInt(2, idPasajero);
            ps.setInt(3, vuelo.getIdVuelo());
            ps.setBigDecimal(4, precio);
            ps.executeUpdate();
        }
        vuelo.setPrecio(precio);
        return codigo;
    }

    public List<Venta> listarPorDni(String dni) throws SQLException {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.id_venta, v.codigo, v.id_pasajero, v.id_vuelo, "
                + "v.precio AS precio_venta, v.fecha_compra, "
                + "p.dni, p.nombres, p.apellidos, p.correo, "
                + "vu.numero_vuelo, vu.origen, vu.destino, vu.fecha, vu.hora, "
                + "vu.precio AS precio_vuelo "
                + "FROM Venta v "
                + "INNER JOIN Pasajero p ON v.id_pasajero = p.id_pasajero "
                + "INNER JOIN Vuelo vu ON v.id_vuelo = vu.id_vuelo "
                + "WHERE p.dni = ? ORDER BY v.fecha_compra DESC, v.id_venta DESC";

        try (Connection cn = ConexionSQL.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setIdVenta(rs.getInt("id_venta"));
                    venta.setCodigo(rs.getString("codigo"));
                    venta.setIdPasajero(rs.getInt("id_pasajero"));
                    venta.setIdVuelo(rs.getInt("id_vuelo"));
                    venta.setPrecio(rs.getBigDecimal("precio_venta"));
                    venta.setFechaCompra(rs.getTimestamp("fecha_compra"));

                    Pasajero pasajero = new Pasajero();
                    pasajero.setIdPasajero(venta.getIdPasajero());
                    pasajero.setDni(rs.getString("dni"));
                    pasajero.setNombres(rs.getString("nombres"));
                    pasajero.setApellidos(rs.getString("apellidos"));
                    pasajero.setCorreo(rs.getString("correo"));
                    venta.setPasajero(pasajero);

                    Vuelo vuelo = new Vuelo();
                    vuelo.setIdVuelo(venta.getIdVuelo());
                    vuelo.setNumeroVuelo(rs.getString("numero_vuelo"));
                    vuelo.setOrigen(rs.getString("origen"));
                    vuelo.setDestino(rs.getString("destino"));
                    vuelo.setFecha(rs.getDate("fecha"));
                    vuelo.setHora(rs.getTime("hora"));
                    vuelo.setPrecio(rs.getBigDecimal("precio_vuelo"));
                    venta.setVuelo(vuelo);
                    lista.add(venta);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar pasajes: " + ex.getMessage());
            throw ex;
        }
        return lista;
    }
}
