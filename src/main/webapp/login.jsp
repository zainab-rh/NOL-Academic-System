<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
  <meta http-equiv="Cache-Control" content="no-store, no-cache, must-revalidate, private">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="0">
  <base href="${pageContext.request.contextPath}/">
  <title>Notas Online</title>

  <!-- Fuentes y CSS -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="assets/css/fontawesome.css" />
  <link rel="stylesheet" href="assets/css/templatemo-scholar.css" />
  <link rel="stylesheet" href="assets/css/owl.css" />
  <link rel="stylesheet" href="assets/css/animate.css" />
  <link rel="stylesheet" href="assets/css/style.css" />
</head>

<body>
  <!-- Preloader -->
  <div id="js-preloader" class="js-preloader">
    <div class="preloader-inner">
      <span class="dot"></span>
      <div class="dots"><span></span><span></span><span></span></div>
    </div>
  </div>

  <!-- Header -->
  <header class="header-area header-sticky">
      <div class="container mt-4">
        <div class="row">
          <div class="col-12">
            <nav class="main-nav">
              <!-- ***** Logo Start ***** -->
              <a href="index.html" class="logo">
                <h1>NOL</h1>
              </a>
              <!-- ***** Logo End ***** -->
              <!-- ***** Menu Start ***** -->
              <ul class="nav">
                <li class="scroll-to-section">
                  <a href="index.html#top" class="active">Inicio</a>
                </li>
                <li class="scroll-to-section">
                  <a href="index.html#services">Funcionalidades</a>
                </li>
                <li class="scroll-to-section">
                  <a href="index.html#about">Acerca de</a>
                </li>
                <li class="scroll-to-section">
                  <a href="index.html#team">Equipo</a>
                </li>
              </ul>
              <a class="menu-trigger">
                <span>Menu</span>
              </a>
              <!-- ***** Menu End ***** -->
            </nav>
          </div>
        </div>
      </div>
  </header>

  <!-- Login -->
  <div class="contact-us section" id="contact">
    <div class="container">
      <div class="row align-items-center min-vh-70">
        <div class="col-lg-6 mb-5 mb-lg-0">
          <div class="section-heading">
            <h6>Acceso al Sistema</h6>
            <h2>Ingrese sus credenciales</h2>
            <p>
              Ingrese sus credenciales para acceder al sistema de gestión académica.
              Si tiene problemas de acceso, contacte con el administrador del sistema.
            </p>
          </div>
        </div>

        <div class="col-lg-6 mt-3">
          <div class="contact-us-content login-box">
            <form id="login-form" action="auth" method="post">
              <div class="row g-4">
                <div class="col-12">
                  <input type="text" name="j_username" id="usuario" class="form-control py-3" placeholder="Nombre de usuario" autocomplete="username" required />
                </div>
                <div class="col-12">
                  <input type="password" name="j_password" id="password" class="form-control py-3" placeholder="Contraseña" autocomplete="current-password" required />
                </div>
                <div class="col-12 text-center mt-5">
                  <button type="submit" id="form-submit" class="btn btn-warning px-5 py-2" disabled>Acceder al Sistema</button>
                </div>
              </div>
            </form>
          </div>
        </div>

      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer class="mt-5">
    <div class="container foot text-center">
      <p class="mb-0">
        &copy; 2025 Notas Online - Desarrollado para el curso DEW 2024/2025 - Universidad Politécnica de Valencia.
        Todos los derechos reservados para Grupo G10.
      </p>
    </div>
  </footer>

  <!-- Scripts -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.min.js"></script>
  <script src="assets/js/isotope.min.js"></script>
  <script src="assets/js/owl-carousel.js"></script>
  <script src="assets/js/counter.js"></script>
  <script src="assets/js/custom.js"></script>
  <script src="assets/js/app.js"></script>
</body>
</html>