<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 27-03-2025
  Time: 13:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PowerGym - Inicio de Sesión</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Favicon -->
    <link rel="icon" href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4jzskVF5G-MAOrP0SpS0Wv36qeN21X4yfslF3eMDryzRon7tjA7XuEdfmATA4NEBI7Fg&usqp=CAU" type="image/png">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            margin: 0;
        }
        /* Contenedor principal del carrusel */
        .carousel-container {
            height: 100vh;
            background-color: #000;
            display: flex;
            align-items: center;
            justify-content: center;
            position: relative;
        }
        /* Contenedor de cada imagen */
        .carousel-img-container {
            height: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        /* Imágenes del carrusel */
        .carousel-img {
            max-height: 100%;
            max-width: 100%;
            width: auto;
            height: auto;
            margin: auto;
        }
        /* Texto del carrusel */
        .carousel-caption {
            bottom: 10%;
            background: rgba(0,0,0,0.6);
            border-radius: 10px;
            padding: 15px;
            left: 50%;
            transform: translateX(-50%);
            width: 80%;
            text-align: center;
        }
        /* Estilos del formulario (sin cambios) */
        .login-form {
            background-color: white;
            padding: 3rem;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.05);
        }
        .form-icon {
            font-size: 5rem;
            color: #0d6efd;
            margin-bottom: 1.5rem;
        }
        .btn-login {
            background-color: #0d6efd;
            border: none;
            padding: 10px 25px;
            font-weight: 500;
        }
        .btn-login:hover {
            background-color: #0b5ed7;
        }
    </style>
</head>
<body>
<div class="container-fluid p-0 h-100">
    <div class="row g-0 h-100">
        <!-- Carrusel de imágenes - Versión mejorada -->
        <div class="col-lg-8 d-none d-lg-block carousel-container">
            <div id="loginCarousel" class="carousel slide h-100" data-bs-ride="carousel">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#loginCarousel" data-bs-slide-to="0" class="active"></button>
                    <button type="button" data-bs-target="#loginCarousel" data-bs-slide-to="1"></button>
                    <button type="button" data-bs-target="#loginCarousel" data-bs-slide-to="2"></button>
                    <button type="button" data-bs-target="#loginCarousel" data-bs-slide-to="3"></button>
                </div>
                <div class="carousel-inner h-100">
                    <!-- Item 1 -->
                    <div class="carousel-item active h-100">
                        <div class="carousel-img-container">
                            <img src="https://cdn.pensador.com/es/imagenes/cree-en-los-milagros-pero-no-dependas-de-ellos-immanuel-kant.jpg"
                                 alt="Gimnasio 1" class="carousel-img">
                        </div>
                        <div class="carousel-caption">
                            <h5>Bienvenido a PowerGym</h5>
                            <p>Transforma tu cuerpo, transforma tu vida</p>
                        </div>
                    </div>
                    <!-- Item 2 -->
                    <div class="carousel-item h-100">
                        <div class="carousel-img-container">
                            <img src="https://nsgnutrition.com/wp-content/uploads/2016/04/portada-gym.jpg"
                                 alt="Gimnasio 2" class="carousel-img">
                        </div>
                    </div>
                    <!-- Item 3 -->
                    <div class="carousel-item h-100">
                        <div class="carousel-img-container">
                            <img src="https://cdn.pensador.com/es/imagenes/deje-la-pereza-durmiendo-y-estoy-aqui-para-demostrar-que-puedo.jpg"
                                 alt="Gimnasio 3" class="carousel-img">
                        </div>
                    </div>
                    <!-- Item 4 -->
                    <div class="carousel-item h-100">
                        <div class="carousel-img-container">
                            <img src="https://cdn.pensador.com/es/imagenes/imagina-una-nueva-historia-para-tu-vida-y-cree-en-ella-paulo-coelho-0.jpg"
                                 alt="Gimnasio 4" class="carousel-img">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Formulario de Login (sin cambios) -->
        <div class="col-lg-4 d-flex align-items-center">
            <div class="container py-5 px-4">
                <div class="login-form">
                    <div class="text-center mb-4">
                        <i class="fas fa-dumbbell form-icon"></i>
                        <h2>Iniciar Sesión</h2>
                        <p class="text-muted">Ingresa tus credenciales para acceder</p>
                    </div>

                    <% if (request.getParameter("error") != null) { %>
                    <p style="color: red;">
                        <%
                            String error = request.getParameter("error");
                            if ("nouser".equals(error)) {
                                out.print("Usuario o contraseña incorrectos. Verifica tus credenciales.");
                            } else if ("wrongrole".equals(error)) {
                                out.print("No tienes permisos para acceder con este rol.");
                            } else if ("invalidrole".equals(error)) {
                                out.print("Rol no reconocido.");
                            } else {
                                out.print("Error desconocido.");
                            }
                        %>
                    </p>
                    <% } %>


                    <form action="LoginServlet" method="post">
                        <div class="mb-3">
                            <label for="usuario" class="form-label">
                                <i class="fas fa-user me-2"></i>Usuario
                            </label>
                            <input type="text" class="form-control" id="usuario" name="usuario" required>
                        </div>

                        <div class="mb-3">
                            <label for="clave" class="form-label">
                                <i class="fas fa-lock me-2"></i>Contraseña
                            </label>
                            <input type="password" class="form-control" id="clave" name="clave" required>
                        </div>

                        <div class="mb-4">
                            <label for="rol" class="form-label">
                                <i class="fas fa-user-tag me-2"></i>Rol
                            </label>
                            <select class="form-select" id="rol" name="rol" required>
                                <option value="Administrador">Administrador</option>
                                <option value="Entrenador">Entrenador</option>
                                <option value="Cliente">Cliente</option>
                            </select>
                        </div>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-login">
                                <i class="fas fa-sign-in-alt me-2"></i>Ingresar
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>