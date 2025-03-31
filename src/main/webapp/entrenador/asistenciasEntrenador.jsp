<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 29/3/2025
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista de asistencias para entrenadores - PowerGim
  Con filtro por cédula y botón de agregar en cabecera
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid py-4">
            <div class="card shadow-lg">
                <div class="card-header bg-success text-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-users me-2"></i> Asistencias de mis clientes
                        </h3>
                    </div>
                </div>

                <!-- Filtro por cédula -->
                <div class="card-body border-bottom">
                    <form action="${pageContext.request.contextPath}/AsistenciaVistaServlet" method="get" class="row g-3">
                        <div class="col-md-8">
                            <label for="filtroCedula" class="form-label">Filtrar por cédula:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="filtroCedula"
                                       name="cedula" value="${param.cedula}" placeholder="Ingrese cédula del cliente">
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-search me-1"></i> Buscar
                                </button>
                                <c:if test="${not empty param.cedula}">
                                    <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet"
                                       class="btn btn-outline-secondary">
                                        <i class="fas fa-times"></i>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-4 d-flex align-items-end">
                            <span class="badge bg-info">
                                <i class="fas fa-user-check me-1"></i> ${asistencias.size()} registros
                            </span>
                        </div>
                    </form>
                    <div>
                        <a href="<%= request.getContextPath() %>/registrar-asistencia" class="btn btn-success">
                            <i class="fas fa-plus"></i> Agregar
                        </a>
                    </div>
                </div>

                <!-- Tabla de asistencias -->
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty asistencias}">
                            <div class="alert alert-warning text-center py-4">
                                <i class="fas fa-exclamation-circle fa-2x mb-3"></i>
                                <h4>No se encontraron asistencias</h4>
                                <c:if test="${not empty param.cedula}">
                                    <p>para la cédula: <strong>${param.cedula}</strong></p>
                                </c:if>
                                <a href="${pageContext.request.contextPath}/registrar-asistencia"
                                   class="btn btn-success mt-2">
                                    <i class="fas fa-plus-circle me-1"></i> Registrar primera asistencia
                                </a>
                            </div>
                        </c:when>

                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>Cliente</th>
                                        <th>Cédula</th>
                                        <th>Fecha/Hora</th>
                                        <th>Tipo</th>
                                        <th>Registrado por</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${asistencias}" var="asistencia"
                                               begin="${(paginaActual-1)*registrosPorPagina}"
                                               end="${(paginaActual-1)*registrosPorPagina + registrosPorPagina - 1}">
                                        <tr>
                                            <td>
                                                    ${asistencia.nombre} ${asistencia.apellido}
                                                <span class="badge bg-info">${asistencia.rol}</span>
                                            </td>
                                            <td>${asistencia.cedula}</td>
                                            <td>
                                                <fmt:formatDate value="${asistencia.fechaAsistencia}"
                                                                pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td>
                        <span class="badge
                            ${asistencia.tipoAsistencia == 'Entrada' ? 'bg-primary' :
                              asistencia.tipoAsistencia == 'Salida' ? 'bg-danger' : 'bg-success'}">
                                ${asistencia.tipoAsistencia}
                        </span>
                                            </td>
                                            <td>
                                                    ${asistencia.nombreRegistrador}
                                                <small class="text-muted d-block">(${asistencia.rolRegistrador})</small>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Paginación -->
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <c:if test="${paginaActual > 1}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${paginaActual-1}&cedula=${param.cedula}">
                                                &laquo; Anterior
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                                        <li class="page-item ${i == paginaActual ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${i}&cedula=${param.cedula}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${paginaActual < totalPaginas}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${paginaActual+1}&cedula=${param.cedula}">
                                                Siguiente &raquo;
                                            </a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>

<!-- Script para mejorar la experiencia -->
<script>
    $(document).ready(function() {
        // Enfocar el campo de búsqueda al cargar
        $('#filtroCedula').focus();

        // Mostrar mensaje si hay filtro aplicado
        <c:if test="${not empty param.cedula}">
        setTimeout(function() {
            toastr.info('Filtrado por cédula: ${param.cedula}');
        }, 500);
        </c:if>
    });
</script>
