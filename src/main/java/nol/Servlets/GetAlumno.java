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
import nol.Models.AsignaturaConNota;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class AsignaturasProfesor
 */

public class GetAlumno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAlumno() {
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
        String dni = (String) httpRequest.getParameter("dni");
        
        
        Requests req = new Requests();
        String alumno = req.getAlumnoPorDNIJSON(dni, key);
        List<AsignaturaConNota> listaAsignaturas = req.getAsignaturasDeAlumno(dni, key);
        
        Gson gson = new Gson();
        
		String textoRelleno = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non augue id nisl blandit gravida. Integer id aliquam lectus. Proin vitae mauris non nunc maximus iaculis at sed quam. Nullam rutrum tempus dui, vitae porttitor nunc gravida commodo. Nullam ligula tellus, commodo non odio ac, posuere accumsan dolor. In fringilla, risus a venenatis consequat, dolor nulla condimentum enim, vel gravida nunc lectus id leo. Vivamus in consequat erat. ";
        String json = "{\"alumno\": " + alumno + ", \"asignaturas\": " + gson.toJson(listaAsignaturas).replace(".", ",") + ", \"relleno\": \"" + textoRelleno + "\"}";
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Obtener la sesión
	    HttpSession session = request.getSession();
	    String key = (String) session.getAttribute("key");

	    // Leer el cuerpo del request
	    StringBuilder jsonBuffer = new StringBuilder();
	    String line;
	    try (BufferedReader reader = request.getReader()) {
	        while ((line = reader.readLine()) != null) {
	            jsonBuffer.append(line);
	        }
	    }

	    String json = jsonBuffer.toString();

	    // Parsear el JSON sin DTO
	    Gson gson = new Gson();
	    JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

	    String acr = jsonObject.get("acronimo").getAsString();
	    String dni = jsonObject.get("dni").getAsString();
	    String nota = jsonObject.get("nota").getAsString();

	    // Lógica para actualizar la nota
	    Requests req = new Requests();
	    req.setNotaAlumno(acr, dni, nota, key);

	    // Enviar respuesta al cliente
	    response.setContentType("application/json");
	    response.getWriter().write("{\"status\":\"ok\"}");
	}

}
