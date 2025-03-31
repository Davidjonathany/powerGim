<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-main py-4">
    <div class="text-center">
        <h2 class="mb-4">Bienvenido a PowerGim</h2>
        <p class="fs-5 text-muted">¿Qué deseas hacer hoy?</p>
        <div class="d-flex justify-content-center gap-3 flex-wrap">
            <!-- Botón para reporte -->
            <a href="${pageContext.request.contextPath}/ReporteClienteServlet"
               class="btn btn-primary btn-lg">
                <i class="fas fa-chart-line me-2"></i>Ver Reporte
            </a>

            <!-- Botón para asistencias -->
            <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet"
               class="btn btn-success btn-lg">
                <i class="fas fa-clipboard-check me-2"></i>Mis Asistencias
            </a>
            <!-- Nuevo botón para rutinas -->
            <a href="${pageContext.request.contextPath}/RutinaServlet"
               class="btn btn-info btn-lg">
                <i class="fas fa-dumbbell me-2"></i>Mis Rutinas
            </a>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>