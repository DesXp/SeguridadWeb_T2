
package controlador;

import dao.VentaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Venta;


@WebServlet(name = "ConsultaServlet", urlPatterns = {"/consulta"})
public class ConsultaServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("consulta.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String dni = request.getParameter("dni");


        if (dni == null || !dni.trim().matches("^[0-9]{8}$")) {
            request.setAttribute("mensaje", "Por favor ingrese un número de DNI válido de 8 dígitos.");
            request.getRequestDispatcher("consulta.jsp").forward(request, response);
            return;
        }

 
        VentaDAO ventaDAO = new VentaDAO();
        List<Venta> pasajes;
        try {
            pasajes = ventaDAO.listarPorDni(dni.trim());
        } catch (SQLException e) {
            System.out.println("No se pudo consultar los pasajes. " + e.getMessage());
            request.setAttribute("mensaje", "No se pudo consultar los pasajes. Intente nuevamente.");
            request.setAttribute("dniBuscado", dni.trim());
            request.getRequestDispatcher("consulta.jsp").forward(request, response);
            return;
        }

  
        if (pasajes.isEmpty()) {
            request.setAttribute("mensaje", "No se encontraron pasajes comprados para ese DNI");
        } else {
            request.setAttribute("listaPasajes", pasajes);
        }
        
        request.setAttribute("dniBuscado", dni.trim());
        request.getRequestDispatcher("consulta.jsp").forward(request, response);
    }

}
