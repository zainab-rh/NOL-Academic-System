package nol.Filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletMapping;
//Mejor ponerlo por si acaso, si no no funciona
import jakarta.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet Filter implementation class Logs
 */
public class Logs implements Filter {

	private String logFilePath;
	private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
	   
	    String path = request.getServletPath();

	    // Descartar recursos estáticos y páginas no servlets:
	    if (path.matches(".*\\.(html|css|js|png|jpg|gif|jpeg|jsp)$")
	        || path.startsWith("/assets/")
	        || path.startsWith("/vendor/")) {
	        // No loguees, sigue con la cadena
	        chain.doFilter(request, response);
	        return;
	    }
	    
	    // Obtener el nombre del servlet que ha atendido la peticion.
	    HttpServletMapping mapping = request.getHttpServletMapping();
	    String servletName = mapping.getServletName();
	    
	    // --- Creamos la entrada a persistir en el archivo de logs ---
	    String timestamp   = LocalDateTime.now().format(fmt);
	    String user        = request.getRemoteUser() != null ? request.getRemoteUser() : "anónimo";
	    String ip          = request.getRemoteAddr();
	    String method      = request.getMethod();

	    String logEntry = String.format("%s %s %s %s %s\n",
	            timestamp, user, ip, servletName, method);
	    
	    try (FileWriter fw = new FileWriter(logFilePath, true); //modo append
		         BufferedWriter bw = new BufferedWriter(fw)) {
		        bw.write(logEntry);
		    }
	    
		//Siguiente filtro
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//Ruta del archivo desde web.xml
		logFilePath = fConfig.getServletContext().getInitParameter("logFilePath");
	}

}
