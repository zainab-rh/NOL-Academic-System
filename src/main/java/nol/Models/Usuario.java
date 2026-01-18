package nol.Models;

public class Usuario {
	
	private String dni;
	private String password;

	// Constructor vacío requerido por Gson
    public Usuario() {}

	public Usuario(String dni, String password) {
		this.dni = dni;
		this.password = password;
	}

    public String getDni() {
        return dni;
    }

    public String getPassword() {
        return password;
    }
	
}

