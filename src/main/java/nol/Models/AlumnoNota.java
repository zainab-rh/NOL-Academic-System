package nol.Models;

public class AlumnoNota {
	
	private String alumno;
	private String nota;
	
	public AlumnoNota() {}
	
	public AlumnoNota(String alumno, String nota) {
		this.alumno=alumno;
		this.nota=nota;
	}
	
	public String getNota() {
		return this.nota;
	}
	
	public String getAlumno() {
		return this.alumno;
	}

}
