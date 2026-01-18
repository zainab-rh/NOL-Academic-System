<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, nol.Models.Asignatura, nol.HTTPRequests.Requests" %>
<%@ page session="true" %>
<jsp:include page="header.jsp" />
<main class="flex-grow-1">
   <div class="container-fluid py-5">
      <div class="m-5">
         <div class="row justify-content-center"> 
            <div class="col-10 col-md-10 mx-auto">
               <h2 class="mt-3 display-5 fw-bold border-bottom pb-2 mb-4 text-center text-md-start">Asignaturas que impartes</h2> 
         <%
            String key = (String) session.getAttribute("key");
            Requests req = new Requests();
            @SuppressWarnings("unchecked")
            List<Asignatura> asignaturas =
              (List<Asignatura>) session.getAttribute("asignaturas");
            
            if (asignaturas != null && !asignaturas.isEmpty()) {
            %>
         <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-2 mt-2"> 
            <% for (Asignatura a : asignaturas) {
               String acronimo = a.getAcronimo();
               %>
            <div class="col">
               <div class="card h-100 asignatura-card hover-shadow" 
                  data-acronimo="<%= acronimo %>" 
                  style="cursor: pointer;">
                  <div class="card-body p-4 d-flex flex-column">
                     <div class="d-flex align-items-center mb-3">
                        <div class="icon-circle bg-primary text-white rounded-circle p-3 me-3">
                           <i class="fas fa-book-open fa-lg"></i>
                        </div>
                        <div>
                           <h5 class="card-title mb-0 text-dark"><%= a.getNombre() %></h5>
                           <small class="text-muted"><%= acronimo %></small>
                        </div>
                     </div>
                     <div class="card-text flex-grow-1">
                        <p class="mb-1 text-secondary">
                           <i class="fas fa-calendar-alt me-1"></i>
                           Curso: <span class="fw-bold"><%= a.getCurso() %></span> &bull; Cuatrimestre: <span class="fw-bold"><%= a.getCuatrimestre() %></span>
                        </p>
                        <p class="mb-0 text-secondary">
                           <i class="fas fa-coins me-1"></i>
                           Créditos: <span class="fw-bold"><%= (""+a.getCreditos()).replace(".",",") %></span>
                        </p>
                        <p class="mb-0 text-secondary">
                           <i class="fas fa-chart-line me-1"></i>
                           Nota Media: <span class="fw-bold"><%= req.getNotaMediaAsignatura(acronimo, key) %></span>
                        </p>
                     </div>
                  </div>
               </div>
            </div>
            <%  }  %>
         </div>
         <%
            } else {
            %>
         <div class="alert alert-warning text-center mt-5 shadow-sm" role="alert">
            No impartes ninguna asignatura en este momento.
         </div>
         <%
            }
            %>
            </div> 
         </div> 
      </div>
   </div>
</main>
<<script type="text/javascript">
document.querySelectorAll(".asignatura-card").forEach(card => {
    card.addEventListener("click", (event) => {
      const acr = card.dataset.acronimo;
      const base = window.location.origin + '<%= request.getContextPath() %>';
      const url = base + '/detalleAsignatura.html?acronimo=' + acr;
      window.open(url, "_blank");
    });
  });
  
  document.addEventListener('visibilitychange', () => {
	    if (document.visibilityState === 'visible') {
	      location.reload();
	    }
	  });
</script>
<jsp:include page="footer.jsp" />
