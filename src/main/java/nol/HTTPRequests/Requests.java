package nol.HTTPRequests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import nol.Models.AlumnoNota;
import nol.Models.Asignatura;
import nol.Models.Profesor;
import nol.Models.AsignaturaConNota;
import nol.Models.Usuario;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Requests {
	
	File cookieFile = new File("/tmp/cucu");  
	private final OkHttpClient client = new OkHttpClient.Builder()
			.cookieJar(new FileCookieJar(cookieFile))
			.connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .build();
	
	private final String UrlBase = "http://localhost:9090/CentroEducativo/";
	private MediaType JSON = MediaType.get("application/json; charset=utf-8");
	
	public String autenticarEnAPI(String dni, String password) {
        try {
            String url = UrlBase+"login";
            
            Usuario usuario = new Usuario(dni, password);
            Gson gson = new Gson();
            String jsonBody = gson.toJson(usuario);

            RequestBody body = RequestBody.create(jsonBody, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return response.body().string(); // Devuelve la clave (key)
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
        }
        return null;
    }
	
	public List<AsignaturaConNota> getAsignaturasDeAlumno(String dni, String key) {

        // Construimos la URL con el DNI y Key de session
        String url = UrlBase + "alumnos/" + dni + "/asignaturas?key="+key;

        // Creamos la solicitud
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        // Enviamos la solicitud y obtenemos la respuesta
        try (Response response = client.newCall(request).execute()) {
        	    if (!response.isSuccessful()) {
        	        System.out.println("Ha ocurrido un error al hacer la petición, el código de error: " + response.code());
        	        System.out.println("Respuesta del servidor: " + response.body().string());
        	        return null;
        	    }

        	    Gson gson = new Gson();
        	    String json = response.body().string();  
        	    AsignaturaConNota[] asignaturasArray = gson.fromJson(json, AsignaturaConNota[].class);
        	    return List.of(asignaturasArray);
        }
		catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	    }
        return null;
    }
	
	public Asignatura getInfoAsignatura(String acronimo, String key) {
	    String url = UrlBase + "asignaturas/" + acronimo + "?key=" + key;

	    Request request = new Request.Builder()
	            .url(url)
	            .addHeader("Content-Type", "application/json")
	            .build();

	    try (Response response = client.newCall(request).execute()) {
	        if (!response.isSuccessful()) {
	            System.out.println("Error HTTP: " + response.code());
	            return null;
	        }

	        String json = response.body().string();  
	        Gson gson = new Gson();
	        return gson.fromJson(json, Asignatura.class);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public List<Profesor> getProfesorAsignatura(String acronimo, String key) {
	    String url = UrlBase + "asignaturas/" + acronimo + "/profesores?key=" + key;

	    Request request = new Request.Builder()
	            .url(url)
	            .addHeader("Content-Type", "application/json")
	            .build();

	    try (Response response = client.newCall(request).execute()) {
	        if (!response.isSuccessful()) {
	            System.out.println("Error HTTP: " + response.code());
	            return null;
	        }

	        String json = response.body().string();  
	        Gson gson = new Gson();
	        Profesor[] profesoresArray = gson.fromJson(json, Profesor[].class);
	        return Arrays.asList(profesoresArray);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public List<Asignatura> getAsignaturasDeProfesor(String dni, String key) {

        // Construimos la URL con el DNI y Key de session
        String url = UrlBase + "profesores/" + dni + "/asignaturas?key="+key;

        // Creamos la solicitud
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        // Enviamos la solicitud y obtenemos la respuesta
        try (Response response = client.newCall(request).execute()) {
        	    if (!response.isSuccessful()) {
        	        System.out.println("Ha ocurrido un error al hacer la petición, el código de error: " + response.code());
        	        System.out.println("Respuesta del servidor: " + response.body().string());
        	        return null;
        	    }

        	    Gson gson = new Gson();
        	    String json = response.body().string();  
        	    Asignatura[] asignaturasArray = gson.fromJson(json, Asignatura[].class);
        	    return List.of(asignaturasArray);
        }
		catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	    }
        return null;
    }
	
	public String getAlumnosAsignaturaJSON(String acr, String key) {

        // Construimos la URL con el DNI y Key de session
        String url = UrlBase + "asignaturas/" + acr + "/alumnos?key="+key;

        // Creamos la solicitud
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        // Enviamos la solicitud y obtenemos la respuesta
        try (Response response = client.newCall(request).execute()) {
        	    if (!response.isSuccessful()) {
        	        System.out.println("Ha ocurrido un error al hacer la petición, el código de error: " + response.code());
        	        System.out.println("Respuesta del servidor: " + response.body().string());
        	        return null;
        	    }

        	    return response.body().string();
        }
		catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	    }
        return null;
    }
	
	public void setNotaAlumno(String acr, String dni, String nota, String key) {
	    String url = UrlBase + "alumnos/" + dni + "/asignaturas/" + acr + "?key=" + key;

	    // Suponiendo que nota es un número, sin comillas
	    RequestBody body = RequestBody.create(
	        nota, // nota debe ser como "8.5"
	        MediaType.parse("application/json")
	    );

	    Request request = new Request.Builder()
	            .url(url)
	            .put(body)
	            .addHeader("Content-Type", "application/json")
	            .build();

	    try (Response response = client.newCall(request).execute()) {
	        if (!response.isSuccessful()) {
	            System.out.println("Error en la petición. Código de estado: " + response.code());
	            System.out.println("Respuesta del servidor: " + response.body().string());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public String getAlumnoPorDNIJSON(String dni, String key) {
		// Construimos la URL con el DNI y Key de session
		
        String url = UrlBase + "alumnos/" + dni + "?key="+key;

        // Creamos la solicitud
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        // Enviamos la solicitud y obtenemos la respuesta
        try (Response response = client.newCall(request).execute()) {
        	    if (!response.isSuccessful()) {
        	        System.out.println("Ha ocurrido un error al hacer la petición, el código de error: " + response.code());
        	        System.out.println("Respuesta del servidor: " + response.body().string());
        	        return null;
        	    }
        	    return response.body().string();
        }
		catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	    }
        return null;
	}
	
	public String getNotaMediaAsignatura(String acr, String key) {
       
		 // Construimos la URL con el DNI y Key de session
        String url = UrlBase + "asignaturas/" + acr + "/alumnos?key="+key;

        // Creamos la solicitud
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .build();

        // Enviamos la solicitud y obtenemos la respuesta
        try (Response response = client.newCall(request).execute()) {
        	    if (!response.isSuccessful()) {
        	        System.out.println("Ha ocurrido un error al hacer la petición, el código de error: " + response.code());
        	        System.out.println("Respuesta del servidor: " + response.body().string());
        	        return null;
        	    }

        	    Gson gson = new Gson();
        	    String json = response.body().string(); 
            AlumnoNota[] array = gson.fromJson(json, AlumnoNota[].class);

            
            if (array != null) {
            	int contador = 0;
            	double suma = 0;
                for (AlumnoNota an : array) {
                   
                   try {
                	   String nota = an.getNota();
                	   double n = Double.parseDouble(nota);
                       suma += n;
                       contador++;
                   } catch (NumberFormatException e) {
                	   System.out.println("Hay error al calcular nota media de "+acr);
                   }
                   
                }
                double media = 0;
                if (contador > 0) {
                	media = suma / contador;
                }
                return (Math.round(media * 100.0) / 100.0 +"").replace(".", ",");
            }
        }
		catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	    }
        return null;

    }

}
