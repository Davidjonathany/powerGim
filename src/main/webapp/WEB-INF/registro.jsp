<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 2/4/2025
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PowerGym - Registro</title>
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
<div class="container-fluid">
    <div class="row w-100 g-0">
        <div class="col-lg-7 d-none d-lg-block carousel-container">
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

        <div class="col-lg-5 form-section d-flex align-items-center justify-content-center min-vh-100">
            <div class="form-container w-100 py-5" style="max-width: 500px;">
                <div class="text-center">
                    <i class="fas fa-user-plus form-icon"></i>
                    <h2>Registro de Cliente</h2>
                    <p class="mb-4">Crea tu cuenta para acceder a nuestros servicios</p>
                </div>

                <!-- Mostrar mensaje de error general si existe -->
                <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
                <% } %>

                <form action="${pageContext.request.contextPath}/registro" method="POST">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="form-control"
                                   value="${nombre}" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="apellido" class="form-label">Apellido</label>
                            <input type="text" name="apellido" id="apellido" class="form-control"
                                   value="${apellido}" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="usuario" class="form-label">Usuario</label>
                            <input type="text" name="usuario" id="usuario" class="form-control"
                                   value="${usuario}" required>
                            <% if (request.getAttribute("errorUsuario") != null) { %>
                            <div class="invalid-feedback d-block">
                                <%= request.getAttribute("errorUsuario") %>
                            </div>
                            <% } %>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="clave" class="form-label">Contraseña</label>
                            <input type="password" name="clave" id="clave" class="form-control" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="correo" class="form-label">Correo</label>
                            <input type="email" name="correo" id="correo" class="form-control"
                                   value="${correo}">
                            <% if (request.getAttribute("errorCorreo") != null) { %>
                            <div class="invalid-feedback d-block">
                                <%= request.getAttribute("errorCorreo") %>
                            </div>
                            <% } %>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="telefono" class="form-label">Teléfono</label>
                            <input type="text" name="telefono" id="telefono" class="form-control"
                                   value="${telefono}" required>
                            <% if (request.getAttribute("errorTelefono") != null) { %>
                            <div class="invalid-feedback d-block">
                                <%= request.getAttribute("errorTelefono") %>
                            </div>
                            <% } %>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="cedula" class="form-label">Cédula</label>
                            <input type="text" name="cedula" id="cedula" class="form-control"
                                   value="${cedula}" required>
                            <% if (request.getAttribute("errorCedula") != null) { %>
                            <div class="invalid-feedback d-block">
                                <%= request.getAttribute("errorCedula") %>
                            </div>
                            <% } %>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="direccion" class="form-label">Dirección</label>
                            <input type="text" name="direccion" id="direccion" class="form-control"
                                   value="${direccion}" required>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary btn-lg w-100">Registrarse</button>

                    <div class="mt-3 text-center">
                        <p>¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/LoginServlet">Inicia sesión</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


