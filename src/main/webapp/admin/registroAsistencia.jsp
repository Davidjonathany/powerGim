<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 11:26
  To change this template use File | Settings | File Templates.
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
                </div>

                <!-- Filtros avanzados -->
                <div class="card-body border-bottom">
                    <form action="${pageContext.request.contextPath}/AsistenciaVistaServlet" method="get" class="row g-3">
                        <!-- Filtro por cédula -->
                        <div class="col-md-4">
                            <label for="filtroCedula" class="form-label">Cédula:</label>
                            <input type="text" class="form-control" id="filtroCedula"
                                   name="filtro" value="${param.filtro == 'cedula' ? param.valor : ''}"
                                   placeholder="Buscar por cédula">
                            <input type="hidden" name="filtro" value="cedula">
                        </div>

                        <!-- Filtro por tipo de asistencia -->
                        <div class="col-md-3">
                            <label for="filtroTipo" class="form-label">Tipo:</label>
                            <select class="form-select" id="filtroTipo" name="valor">
                                <option value="">Todos</option>
                                <option value="Entrada" ${param.filtro == 'tipo' && param.valor == 'Entrada' ? 'selected' : ''}>Entrada</option>
                                <option value="Salida" ${param.filtro == 'tipo' && param.valor == 'Salida' ? 'selected' : ''}>Salida</option>
                                <option value="Clase" ${param.filtro == 'tipo' && param.valor == 'Clase' ? 'selected' : ''}>Clase</option>
                                <option value="Reunión" ${param.filtro == 'tipo' && param.valor == 'Reunión' ? 'selected' : ''}>Reunión</option>
                            </select>
                            <input type="hidden" name="filtro" value="tipo">
                        </div>

                        <!-- Filtro por fecha -->
                        <div class="col-md-3">
                            <label for="filtroFecha" class="form-label">Fecha:</label>
                            <input type="date" class="form-control" id="filtroFecha"
                                   name="valor" value="${param.filtro == 'fecha' ? param.valor : ''}">
                            <input type="hidden" name="filtro" value="fecha">
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
                    </form>
                </div>

                <!-- Tabla de asistencias -->
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty asistencias}">
                            <div class="alert alert-warning text-center">
                                <i class="fas fa-exclamation-circle me-2"></i>
                                No se encontraron asistencias
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
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${asistencias}" var="asistencia">
                                        <tr>
                                            <td>${asistencia.idAsistencia}</td>
                                            <td>${asistencia.nombre} ${asistencia.apellido}</td>
                                            <td>${asistencia.cedula}</td>
                                            <td>${asistencia.rol}</td>
                                            <td>
                                                <fmt:formatDate value="${asistencia.fechaAsistencia}"
                                                                pattern="dd/MM/yyyy HH:mm:ss"/>
                                            </td>
                                            <td>${asistencia.tipoAsistencia}</td>
                                            <td>
                                                    ${asistencia.nombreRegistrador} ${asistencia.apellidoRegistrador}
                                                <small class="text-muted d-block">(${asistencia.rolRegistrador})</small>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>
