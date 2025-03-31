<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista home para entrenadores - PowerGim
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../cabecero.jsp"/>

<div class="content-main py-4">
    <div class="text-center">
        <h2 class="mb-4">Bienvenido Entrenador</h2>
        <p class="fs-5 text-muted">Gestión de tus clientes</p>

        <div class="d-flex justify-content-center gap-3 flex-wrap">
            <!-- Botón para asistencias -->
            <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet"
               class="btn btn-success btn-lg">
                <i class="fas fa-clipboard-list me-2"></i> Asistencias de mis clientes
            </a>

            <!-- Botón adicional para gestión de rutinas (opcional)-->
            <a href="${pageContext.request.contextPath}/RutinaServlet"
               class="btn btn-primary btn-lg">
                <i class="fas fa-dumbbell me-2"></i> Ver Rutinas
            </a>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>

