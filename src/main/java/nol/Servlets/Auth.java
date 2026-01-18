package nol.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import nol.HTTPRequests.Requests;

import java.io.IOException;


public class Auth extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Auth() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String dni = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        
    	try {
        	
            // Autenticación en Tomcat
            request.login(dni, password);

            // 2. Autenticación en API CentroEducativo
            Requests req = new Requests();
            String key = req.autenticarEnAPI(dni, password);

            if (key == null || key.isBlank()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error en CentroEducativo");
                return;
            }
            RequestDispatcher rd = request.getRequestDispatcher("pages/Error.jsp");

            //  datos en sesión
            HttpSession session = request.getSession(true);
            session.setAttribute("key", key);
            
            // Redirección según rol
            if (request.isUserInRole("rolalu")) {
                response.sendRedirect(request.getContextPath() + "/alumno/asignaturas");
            } else if (request.isUserInRole("rolpro")) {
                response.sendRedirect(request.getContextPath() + "/profesor/asignaturas");
            } else {
            	rd.forward(request, response);
            }

        } catch (ServletException e) {
        	RequestDispatcher rd = request.getRequestDispatcher("pages/Error.jsp");
            rd.forward(request, response);
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(true); // No crear si no existe

        if (session != null && session.getAttribute("key") != null) {
            // Ya está autenticado en CentroEducativo, redirigir según rol
            if (request.isUserInRole("rolalu")) {
                response.sendRedirect(request.getContextPath() + "/alumno/asignaturas");
            } else if (request.isUserInRole("rolpro")) {
                response.sendRedirect(request.getContextPath() + "/profesor/asignaturas");
            } else {
                response.sendRedirect("pages/Error.jsp");
            }
        } else {
        	RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
            rd.forward(request, response);
        }
    }
}
