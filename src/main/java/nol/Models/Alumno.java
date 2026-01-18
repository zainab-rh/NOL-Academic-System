package nol.Models;

public class Alumno extends Usuario {
	
	private String apellidos;
	private String nombre;

	// Constructor vacío requerido por Gson
	public Alumno() {}

	public Alumno(String apellidos, String dni, String nombre, String password) {
		super(dni, password);
		this.apellidos = apellidos;
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getNombre() {
		return nombre;
	}
	

}
