<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 29/3/2025
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista de asistencias para administradores - PowerGim
  Muestra TODAS las asistencias y permite filtrar por cédula
--%>
<%--
  Vista de asistencias para administradores - PowerGim
  Muestra TODAS las asistencias y permite filtrar por cédula, tipo, fecha, etc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid py-4">
            <div class="card shadow-lg">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title">
                        <i class="fas fa-clipboard-list me-2"></i> Registro Completo de Asistencias
                    </h3>

                    <div class="card-tools">
                        <span class="badge bg-light text-dark">
                            Total: ${totalAsistencias} registros
                        </span>
                    </div>
                </div>

                <!-- Filtros avanzados -->
                <div class="card-body border-bottom">
                    <form action="${pageContext.request.contextPath}/AsistenciaVistaServlet" method="get" class="row g-3">
                        <!-- Filtro por cédula -->
                        <div class="col-md-4">
                            <label for="filtroCedula" class="form-label">Cédula:</label>
                            <input type="text" class="form-control" id="filtroCedula"
                                   name="cedula" value="${param.cedula}" placeholder="Buscar por cédula">
                        </div>

                        <!-- Filtro por tipo de asistencia -->
                        <div class="col-md-3">
                            <label for="filtroTipo" class="form-label">Tipo:</label>
                            <select class="form-select" id="filtroTipo" name="tipo">
                                <option value="">Todos</option>
                                <option value="Entrada" ${param.tipo == 'Entrada' ? 'selected' : ''}>Entrada</option>
                                <option value="Salida" ${param.tipo == 'Salida' ? 'selected' : ''}>Salida</option>
                                <option value="Clase" ${param.tipo == 'Clase Grupal' ? 'selected' : ''}>Clase</option>
                                <option value="Entrenamiento" ${param.tipo == 'Entrenamiento' ? 'selected' : ''}>Entrenamiento Personalizado</option>
                                <option value="Uso Libre" ${param.tipo == 'Uso Libre' ? 'selected' : ''}>Uso Libre</option>
                            </select>
                        </div>

                        <!-- Filtro por fecha -->
                        <div class="col-md-3">
                            <label for="filtroFecha" class="form-label">Fecha:</label>
                            <input type="date" class="form-control" id="filtroFecha"
                                   name="fecha" value="${param.fecha}">
                        </div>

                        <!-- Botones de acción -->
                        <div class="col-md-2 d-flex align-items-end">
                            <div class="btn-group w-100">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-filter me-1"></i> Filtrar
                                </button>
                                <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet"
                                   class="btn btn-secondary">
                                    <i class="fas fa-sync-alt"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ms-auto">
                            <a href="${pageContext.request.contextPath}/registrar-asistencia" class="btn btn-success">
                                <i class="fas fa-plus"></i> Agregar
                            </a>
                        </div>
                    </form>
                </div>

                <!-- Tabla de asistencias -->
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty asistencias}">
                            <div class="alert alert-warning text-center">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                No se encontraron asistencias
                                <c:if test="${not empty param.cedula}">
                                    para la cédula: <strong>${param.cedula}</strong>
                                </c:if>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered table-striped">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Usuario</th>
                                        <th>Cédula</th>
                                        <th>Rol</th>
                                        <th>Fecha/Hora</th>
                                        <th>Tipo</th>
                                        <th>Registrado por</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${asistencias}" var="asistencia">
                                        <tr>
                                            <td>${asistencia.idAsistencia}</td>
                                            <td>${asistencia.nombre} ${asistencia.apellido}</td>
                                            <td>${asistencia.cedula}</td>
                                            <td>
                                                    <span class="badge
                                                        ${asistencia.rol == 'Administrador' ? 'bg-danger' :
                                                          asistencia.rol == 'Entrenador' ? 'bg-warning text-dark' : 'bg-primary'}">
                                                            ${asistencia.rol}
                                                    </span>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${asistencia.fechaAsistencia}"
                                                                pattern="dd/MM/yyyy HH:mm:ss"/>
                                            </td>
                                            <td>
                                                    <span class="badge
                                                        ${asistencia.tipoAsistencia == 'Entrada' ? 'bg-success' :
                                                          asistencia.tipoAsistencia == 'Salida' ? 'bg-danger' : 'bg-info'}">
                                                            ${asistencia.tipoAsistencia}
                                                    </span>
                                            </td>
                                            <td>
                                                    ${asistencia.nombreRegistrador} ${asistencia.apellidoRegistrador}
                                                <small class="text-muted d-block">(${asistencia.rolRegistrador})</small>
                                            </td>
                                            <td class="text-center">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/EditarAsistenciaServlet?id=${asistencia.idAsistencia}"
                                                       class="btn btn-warning" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/EliminarAsistenciaServlet?id=${asistencia.idAsistencia}"
                                                       class="btn btn-danger" title="Eliminar"
                                                       onclick="return confirm('¿Está seguro de eliminar esta asistencia?')">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                </div>
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
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${paginaActual-1}&cedula=${param.cedula}&tipo=${param.tipo}&fecha=${param.fecha}">
                                                Anterior
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:forEach begin="1" end="${totalPaginas}" var="i">
                                        <li class="page-item ${i == paginaActual ? 'active' : ''}">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${i}&cedula=${param.cedula}&tipo=${param.tipo}&fecha=${param.fecha}">
                                                    ${i}
                                            </a>
                                        </li>
                                    </c:forEach>

                                    <c:if test="${paginaActual < totalPaginas}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/AsistenciaVistaServlet?pagina=${paginaActual+1}&cedula=${param.cedula}&tipo=${param.tipo}&fecha=${param.fecha}">
                                                Siguiente
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

<!-- Script para mejorar la experiencia de usuario -->
<script>
    $(document).ready(function() {
        // Inicializar tooltips
        $('[title]').tooltip();

        // Mostrar mensaje si hay parámetros de búsqueda
        <c:if test="${not empty param.cedula or not empty param.tipo or not empty param.fecha}">
        toastr.info('Filtros aplicados: ' +
            '<c:if test="${not empty param.cedula}">Cédula: ${param.cedula} </c:if>' +
            '<c:if test="${not empty param.tipo}">Tipo: ${param.tipo} </c:if>' +
            '<c:if test="${not empty param.fecha}">Fecha: ${param.fecha} </c:if>');
        </c:if>
    });
</script>
