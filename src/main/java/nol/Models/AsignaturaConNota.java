package nol.Models;

public class AsignaturaConNota {
    private String asignatura;
    private String nota;

    // Constructor vacío requerido por Gson
    public AsignaturaConNota() {}

    public AsignaturaConNota(String asignatura, String nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public String getNota() {
        return nota;
    }
}