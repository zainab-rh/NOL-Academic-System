package nol.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nol.HTTPRequests.Requests;
import nol.Models.Alumno;
import nol.Models.Asignatura;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

/**
 * Servlet implementation class AsignaturasProfesor
 */

public class AlumnosAsignatura extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlumnosAsignatura() {
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
        String acr = (String) httpRequest.getParameter("acronimo");
        
        
        Requests req = new Requests();
        String alumnos = req.getAlumnosAsignaturaJSON(acr, key);
        Asignatura asig = req.getInfoAsignatura(acr, key);
        
        String json = "{\"titulo\": \"" + asig.getNombre() + "\", \"alumnos\": " + alumnos + "}";
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
