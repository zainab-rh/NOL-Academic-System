<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="nol.Models.Asignatura" %>
<%@ page import="nol.Models.Profesor" %>


<jsp:include page="header.jsp" />
    
    <main class="flex-grow-1">
        <div class="container-fluid py-5 ">
            <div class="m-5">
<h2 class="mt-3 display-5 fw-bold border-bottom pb-2 mb-4">Lista de asignaturas</h2>
  <%
                    List<Asignatura> asignaturas = (List<Asignatura>) session.getAttribute("asignaturas");
                    if (asignaturas != null && !asignaturas.isEmpty()) {
                %>
                <div class="accordion mt-4" id="asignaturasAccordion">
                    <%
                        for (int i = 0; i < asignaturas.size(); i++) {
                            Asignatura a = asignaturas.get(i);
                            String acronimo = a.getAcronimo();
                            String collapseId = "collapse-" + acronimo;
                    %>
                    <div class="accordion-item">
                        <h2 class="accordion-header">
                            <button 
                                class="accordion-button <%= (i != 0) ? "collapsed" : "" %>" 
                                type="button" 
                                data-bs-toggle="collapse" 
                                data-bs-target="#<%= collapseId %>"
                                aria-expanded="<%= (i == 0) ? "true" : "false" %>"
                            >
                                <%= acronimo %>
                            </button>
                        </h2>
                        <div 
                            id="<%= collapseId %>" 
                            class="accordion-collapse collapse <%= (i == 0) ? "show" : "" %>" 
                            data-bs-parent="#asignaturasAccordion"
                        >
                            <div class="accordion-body">
                                <h4><%= a.getNombre() %> (<%= acronimo %>)</h4>
                                <p class="mt-2 mb-1"><strong>Descripción:</strong> <%= a.getDescripcion() %></p>
                                <p><strong>Créditos:</strong> <%= (""+a.getCreditos()).replace(".",",") %></p>
                                <p><strong>Curso:</strong> <%= a.getCurso() %></p>
                                <p><strong>Cuatrimestre:</strong> <%= a.getCuatrimestre() %></p>
                                <p><strong>Nota:</strong> 
                                    <%= (a.getNota() == null || a.getNota().isEmpty()) ? "Sin calificación" : a.getNota() %>
                                </p>
                                <div class="mt-3">
                                    <h5 class="mb-2">Profesores:</h5>
                                    <%
                                        List<Profesor> profesores = a.getProfesores();
                                        if (profesores != null && !profesores.isEmpty()) {
                                    %>
                                        <ol class="list-group list-group-numbered">
                                            <% for (Profesor p : profesores) { %>
                                                <li class="list-group-item d-flex justify-content-between align-items-start">
                                                    <div class="ms-2 me-auto">
                                                        <div class="fw-bold"><%= p.getNombre() %> <%= p.getApellidos() %></div>
                                                    </div>
                                                </li>
                                            <% } %>
                                        </ol>
                                    <% } else { %>
                                        <div class="alert alert-warning mt-2">No hay profesores asignados.</div>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
                <% } else { %>
                    <div class="alert alert-warning mt-3">El usuario no está inscrito en asignaturas ahora mismo.</div>
                <% } %>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp" />
