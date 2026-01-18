package nol.Models;

import java.util.ArrayList;
import java.util.List;

public class Asignatura {
	
	private String acronimo;
	private float creditos;
	private String cuatrimestre;
	private int curso;
	private String nombre;
	private String nota;
	private String descripcion;
	private List<Profesor> profesores;

	// Constructor vacío requerido por Gson
	public Asignatura() {
		this.descripcion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In cursus vehicula magna.";
        this.nota = "";
        this.profesores = new ArrayList<>();
	}

	public Asignatura(String acronimo, float creditos, String cuatrimestre, int curso, String nombre) {
		this.acronimo = acronimo;
		this.creditos = creditos;
		this.cuatrimestre = cuatrimestre;
		this.curso = curso;
		this.nombre = nombre;
		this.nota = "";
		this.descripcion = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In cursus vehicula magna, at tincidunt nisi interdum vel.";
		this.profesores = new ArrayList<Profesor>();
	}
	public String getAcronimo() {
		return acronimo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public float getCreditos() {
		return creditos;
	}
	public String getCuatrimestre() {
		return cuatrimestre;
	}
	public int getCurso() {
		return curso;
	}
	public String getNombre() {
		return nombre;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	
	public List<Profesor> getProfesores() {
		return profesores;
	}
	public void setProfesores(List<Profesor> profesores) {
		this.profesores = profesores;
	}
}
