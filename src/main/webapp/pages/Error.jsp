<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Error de acceso</title>
  <base href="${pageContext.request.contextPath}/">
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    :root {
      --color-primario: #7a6ad8;       /* Color base proporcionado */
      --color-secundario: #ffd84a;     /* Amarillo contrastante */
      --color-terciario: #f0f0f0;      /* Gris claro para fondos */
      --color-texto: #2a2a2a;          /* Texto principal */
    }

    body {
      background: linear-gradient(135deg, var(--color-primario) 0%, #6655d1 100%);
      min-height: 100vh;
    }

    .error-container {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 1rem;
      box-shadow: 0 10px 30px rgba(0,0,0,0.15);
      padding: 2.5rem;
      max-width: 500px;
      transition: transform 0.3s ease;
    }

    .lock-icon {
      filter: drop-shadow(0 3px 5px rgba(122, 106, 216, 0.3));
    }

    .btn-accent {
      background: var(--color-secundario);
      color: var(--color-texto);
      border: none;
      padding: 0.8rem 2rem;
      border-radius: 50px;
      font-weight: 600;
      transition: all 0.3s ease;
    }

    .btn-accent:hover {
      transform: scale(1.05);
      box-shadow: 0 5px 15px rgba(255, 216, 74, 0.4);
    }

    .error-message {
      border-left: 4px solid var(--color-secundario);
      padding-left: 1.5rem;
      margin: 1.5rem 0;
    }
  </style>
</head>
<body class="d-flex justify-content-center align-items-center p-3">

  <div class="error-container text-center">
    <!-- Ícono de candado moderno -->
    <svg xmlns="http://www.w3.org/2000/svg" 
         width="80" height="80" fill="var(--color-primario)"
         class="lock-icon mb-4" viewBox="0 0 24 24">
      <path d="M12 2C9.243 2 7 4.243 7 7v3H6a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-8a2 2 0 0 0-2-2h-1V7c0-2.757-2.243-5-5-5zm0 13.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3zM9 7c0-1.654 1.346-3 3-3s3 1.346 3 3v3H9V7z"/>
    </svg>

    <h1 class="h3 mb-3" style="color: var(--color-primario);">Acceso no autorizado</h1>
    
    <div class="error-message text-start">
    <h5>Causas:</h5>
    <ul>
    	<li>Credenciales incorrectas.</li>
    	<li>El acceso al recurso pedido ha sido denegado/prohibido.</li>
    </ul>
     
    </div>

    <div class="mt-4">
      <a href="<%= request.getContextPath() %>/" class="btn btn-accent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-left me-2" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
        </svg>
        Volver al inicio
      </a>
    </div>
  </div>

  <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>