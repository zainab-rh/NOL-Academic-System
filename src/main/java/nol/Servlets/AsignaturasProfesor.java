package nol.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nol.HTTPRequests.Requests;
import nol.Models.Asignatura;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class AsignaturasProfesor
 */

public class AsignaturasProfesor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AsignaturasProfesor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String key = (String) session.getAttribute("key");
        String dni = httpRequest.getRemoteUser();
		Requests req = new Requests();
		List<Asignatura> asignaturas = req.getAsignaturasDeProfesor(dni, key);
		
		// Guardar la lista de asignaturas en la sesión con el nombre "asignaturas" para usarla en el .jsp
		session.setAttribute("asignaturas", asignaturas);
		        
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/AsignaturasProfesor.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
