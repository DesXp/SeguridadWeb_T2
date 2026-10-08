/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.PasajeroDAO;
import dao.VentaDAO;
import dao.VueloDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Pasajero;
import modelo.Vuelo;

/**
 *
 * @author Desiderio
 */
@WebServlet(name = "CompraServlet", urlPatterns = {"/compra"})
public class CompraServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // Al hacer clic en "Comprar" desde el catálogo, se carga el formulario con los datos del vuelo
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("idVuelo");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int idVuelo = Integer.parseInt(idParam.trim());
                VueloDAO vueloDAO = new VueloDAO();
                Vuelo vuelo = vueloDAO.buscar(idVuelo);
                
                if (vuelo != null) {
                    request.setAttribute("vuelo", vuelo);
                    request.getRequestDispatcher("compra.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                // Parámetro no numérico
            }
        }
        response.sendRedirect("catalogo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Soporte de caracteres para tildes y letra ñ
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("idVuelo");
        String dni = request.getParameter("dni");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");

        int idVuelo = 0;
        try {
            idVuelo = Integer.parseInt(idParam);
        } catch (Exception e) {
            response.sendRedirect("catalogo");
            return;
        }

        // CONTROL DE SEGURIDAD 1 (Integridad / Manipulación de Parámetros):
        // Consultamos el vuelo directamente en la base de datos para obtener el precio oficial.
        // NUNCA debemos leer el precio desde request.getParameter("precio").
        VueloDAO vueloDAO = new VueloDAO();
        Vuelo vuelo = vueloDAO.buscar(idVuelo);

        if (vuelo == null) {
            response.sendRedirect("catalogo");
            return;
        }

        // CONTROL DE SEGURIDAD 2 (Validación en Servidor - Rúbrica Paso 4):
        // Validar que el DNI tenga exactamente 8 dígitos numéricos
        if (dni == null || !dni.trim().matches("^[0-9]{8}$")) {
            request.setAttribute("error", "DNI inválido: debe contener exactamente 8 dígitos numéricos.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
            return;
        }

        // Validar que los campos obligatorios no estén vacíos
        if (nombres == null || nombres.trim().isEmpty() ||
            apellidos == null || apellidos.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos del formulario son obligatorios.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
            return;
        }

        // Reutilizar o registrar pasajero en la base de datos
        Pasajero pasajero = new Pasajero();
        pasajero.setDni(dni.trim());
        pasajero.setNombres(nombres.trim());
        pasajero.setApellidos(apellidos.trim());
        pasajero.setCorreo(correo.trim());

        PasajeroDAO pasajeroDAO = new PasajeroDAO();
        int idPasajero = pasajeroDAO.guardar(pasajero);

        // Registrar venta con el precio extraído de la BD y código generado
        VentaDAO ventaDAO = new VentaDAO();
        String codigoVenta = ventaDAO.registrar(idPasajero, vuelo);

        if (codigoVenta != null) {
            // Enviar datos hacia la pantalla de éxito (Paso 5)
            request.setAttribute("codigoPasaje", codigoVenta);
            request.setAttribute("pasajeroNombre", pasajero.getNombres() + " " + pasajero.getApellidos());
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("exito.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Error al procesar la compra en el sistema.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
        }
    }
}