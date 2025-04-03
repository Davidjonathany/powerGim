<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 27/03/2025
  Time: 13:48
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>PowerGim</title>
    <!-- Favicon -->
    <link rel="icon" href="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4jzskVF5G-MAOrP0SpS0Wv36qeN21X4yfslF3eMDryzRon7tjA7XuEdfmATA4NEBI7Fg&usqp=CAU" type="image/png">
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Estilo para el footer -->
    <style>
        html, body {
            height: 100%;
        }
        body {
            display: flex;
            flex-direction: column;
        }
        .content-wrapper {
            flex: 1 0 auto;
            padding-bottom: 20px;
        }
    </style>
</head>
<body class="d-flex flex-column">

<header class="bg-dark bg-gradient text-white p-3">
    <div class="container">
        <div class="d-flex justify-content-between align-items-center">
            <h1 class="m-0">PowerGim</h1>
            <nav>
                <!-- Opción "Inicio" dinámica por rol -->
                <a href="${pageContext.request.contextPath}/home" class="text-white mx-2 text-decoration-none">
                <i class="fas fa-home"></i> Inicio
                </a>

                <!-- Opciones para Administrador -->
                <c:if test="${sessionScope.rol eq 'Administrador'}">
                    <a href="${pageContext.request.contextPath}/UsuarioServlet" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-users"></i> Usuarios
                    </a>
                </c:if>

                <!-- Opciones para Administrador, Entrenador y Cliente -->
                <c:if test="${sessionScope.rol eq 'Administrador' or sessionScope.rol eq 'Entrenador' or sessionScope.rol eq 'Cliente'}">
                    <a href="${pageContext.request.contextPath}/RutinaServlet" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-dumbbell"></i> Rutinas
                    </a>
                    <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-clipboard-check"></i> Asistencias
                    </a>
                    <a href="${pageContext.request.contextPath}/membresias" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-id-card"></i> Membresias
                    </a>
                    <a href="${pageContext.request.contextPath}/ejercicios" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-running"></i> Ejercicios
                    </a>
                    <a href="${pageContext.request.contextPath}/pesos" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-balance-scale me-2"></i>Pesos
                    </a>
                    <a href="${pageContext.request.contextPath}/ReporteClienteServlet" class="text-white mx-2 text-decoration-none">
                        <i class="fas fa-chart-bar me-2"></i> Reportes
                    </a>
                </c:if>

                <!-- Botón "Salir" común a todos -->
                <a href="${pageContext.request.contextPath}/LoginServlet" class="text-white mx-2 text-decoration-none">
                    <i class="fas fa-sign-out-alt"></i> Salir
                </a>
            </nav>
        </div>
    </div>
</header>
<!-- Aquí comienza el content-wrapper -->
<div class="content-wrapper container mt-4">

    <aside class="">
        <section class=""></section>
    </aside>