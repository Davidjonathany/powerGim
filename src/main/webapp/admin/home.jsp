<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 15:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-main py-4"> <!-- Contenedor adicional -->
    <div class="text-center">
        <h2 class="mb-4">Bienvenido a PowerGim</h2>
        <br>
        <h2 class="mb-4">Panel de Administración</h2>
        <br>
        <p class="fs-5 text-muted">Bienvenido Administrador, gestiona los usuarios y la asistencia del gimnasio.</p>

        <div class="d-flex justify-content-center gap-3">
            <form action="../UsuarioServlet" method="get">
                <button type="submit" class="btn btn-primary px-4 py-2">
                    <i class="fas fa-users me-2"></i>Usuarios
                </button>
            </form>

            <form action="../AsistenciaVistaServlet" method="get">
                <button type="submit" class="btn btn-success px-4 py-2">
                    <i class="fas fa-clipboard-list me-2"></i>Ver Asistencias
                </button>
            </form>

            <form action="../RutinaServlet" method="get">
                <button type="submit" class="btn btn-info px-4 py-2">
                    <i class="fas fa-dumbbell me-2"></i>Rutinas
                </button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>