
package controlador;

import modelo.Vuelo;
import dao.VueloDAO;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "CatalogoServlet", urlPatterns = {"/catalogo"})
public class CatalogoServlet extends HttpServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       VueloDAO dao = new VueloDAO();
        try {
            List<Vuelo> vuelos = dao.listar();
            request.setAttribute("listaVuelos", vuelos);
        } catch (SQLException e) {
            System.out.println("No se pudo cargar el catalogo. " + e.getMessage());
            request.setAttribute("error", "No se pudo cargar el catalogo. Intente nuevamente.");
        }
        
      
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    
}
