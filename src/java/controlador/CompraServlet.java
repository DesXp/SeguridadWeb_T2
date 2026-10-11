
package controlador;

import dao.VentaDAO;
import dao.VueloDAO;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Pasajero;
import modelo.Vuelo;


@WebServlet(name = "CompraServlet", urlPatterns = {"/compra"})
public class CompraServlet extends HttpServlet {

 
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
               
            } catch (SQLException e) {
                System.out.println("No se pudo cargar el vuelo para la compra. " + e.getMessage());
                request.setAttribute("error", "No se pudo cargar el vuelo. Intente nuevamente.");
                request.getRequestDispatcher("catalogo.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("catalogo");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
        request.setCharacterEncoding("UTF-8");
        String idParam = request.getParameter("idVuelo");
        String dni = request.getParameter("dni");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");

        int idVuelo = 0;
        try {
            idVuelo = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("catalogo");
            return;
        }

      
        VueloDAO vueloDAO = new VueloDAO();
        Vuelo vuelo;
        try {
            vuelo = vueloDAO.buscar(idVuelo);
        } catch (SQLException e) {
            System.out.println("No se pudo consultar el vuelo. " + e.getMessage());
            request.setAttribute("error", "No se pudo consultar el vuelo. Intente nuevamente.");
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
            return;
        }

        if (vuelo == null) {
            response.sendRedirect("catalogo");
            return;
        }

     
        if (dni == null || !dni.trim().matches("^[0-9]{8}$")) {
            request.setAttribute("error", "DNI inválido: debe contener exactamente 8 dígitos numéricos.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
            return;
        }

       
        if (nombres == null || nombres.trim().isEmpty() ||
            apellidos == null || apellidos.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty()) {
            request.setAttribute("error", "Todos los campos del formulario son obligatorios.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
            return;
        }

        Pasajero pasajero = new Pasajero();
        pasajero.setDni(dni.trim());
        pasajero.setNombres(nombres.trim());
        pasajero.setApellidos(apellidos.trim());
        pasajero.setCorreo(correo.trim());

        VentaDAO ventaDAO = new VentaDAO();
        try {
            String codigoVenta = ventaDAO.registrarCompra(pasajero, vuelo);
         
            request.setAttribute("codigoPasaje", codigoVenta);
            request.setAttribute("pasajeroNombre", pasajero.getNombres() + " " + pasajero.getApellidos());
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("exito.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println("No se pudo registrar la compra. " + e.getMessage());
            request.setAttribute("error", "No se pudo registrar la compra. Revise los datos e intente nuevamente.");
            request.setAttribute("vuelo", vuelo);
            request.getRequestDispatcher("compra.jsp").forward(request, response);
        }
    }
}
