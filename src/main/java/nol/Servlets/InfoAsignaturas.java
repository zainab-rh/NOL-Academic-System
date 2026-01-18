package nol.Servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nol.Models.Asignatura;
import nol.Models.AsignaturaConNota;
import nol.Models.Profesor;
import nol.HTTPRequests.Requests;

/**
 * Servlet implementation class InfoAsignaturas
 */

public class InfoAsignaturas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoAsignaturas() {
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
		List<AsignaturaConNota> asignaturas = req.getAsignaturasDeAlumno(dni, key);
		List<Asignatura> asignaturasInfo = new ArrayList<Asignatura>();
		if (asignaturas!=null && !asignaturas.isEmpty()) {
			for(AsignaturaConNota a : asignaturas) {
				Asignatura completa = req.getInfoAsignatura(a.getAsignatura(), key);
				List<Profesor> profesores = req.getProfesorAsignatura(a.getAsignatura(), key);
				if (completa != null) {
					completa.setNota(a.getNota().replace(".", ","));
					completa.setProfesores(profesores);
					asignaturasInfo.add(completa);
				}
			}	
		}

        // Guardar la lista de asignaturas en la sesión con el nombre "asignaturas" para usarla en el .jsp
		session.setAttribute("asignaturas", asignaturasInfo);
		session.setAttribute("asignaturasNota", asignaturas);
        
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/InfoAsignaturas.jsp");
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
