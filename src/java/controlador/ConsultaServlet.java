/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.VentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Venta;

/**
 *
 * @author Desiderio
 */
@WebServlet(name = "ConsultaServlet", urlPatterns = {"/consulta"})
public class ConsultaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirige al formulario de consulta por DNI
        request.getRequestDispatcher("consulta.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String dni = request.getParameter("dni");

        // Validación del DNI en el servidor
        if (dni == null || !dni.trim().matches("^[0-9]{8}$")) {
            request.setAttribute("mensaje", "Por favor ingrese un número de DNI válido de 8 dígitos.");
            request.getRequestDispatcher("consulta.jsp").forward(request, response);
            return;
        }

        // Búsqueda de pasajes asociados al DNI
        VentaDAO ventaDAO = new VentaDAO();
        List<Venta> pasajes = ventaDAO.listarPorDni(dni.trim());

        // Mensaje requerido si no se encuentran registros (Paso 6 de la rúbrica)
        if (pasajes == null || pasajes.isEmpty()) {
            request.setAttribute("mensaje", "No se encontraron pasajes comprados para ese DNI");
        } else {
            request.setAttribute("listaPasajes", pasajes);
        }
        
        request.setAttribute("dniBuscado", dni.trim());
        request.getRequestDispatcher("consulta.jsp").forward(request, response);
    }

}
