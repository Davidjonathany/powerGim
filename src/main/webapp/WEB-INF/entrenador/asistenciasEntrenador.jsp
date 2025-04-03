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

                <!-- Filtros avanzados -->
                <div class="card-body border-bottom">
                    <form action="${pageContext.request.contextPath}/AsistenciaVistaServlet" method="get" class="row g-3">
                        <!-- Filtro por tipo de asistencia -->
                        <div class="col-md-3">
                            <label for="filtroTipo" class="form-label">Tipo:</label>
                            <select class="form-select" id="filtroTipo" name="tipo">
                                <option value="">Todos</option>
                                <option value="Entrada" ${param.tipo == 'Entrada' ? 'selected' : ''}>Entrada</option>
                                <option value="Salida" ${param.tipo == 'Salida' ? 'selected' : ''}>Salida</option>
                                <option value="Clase" ${param.tipo == 'Clase Grupal' ? 'selected' : ''}>Clase Grupal</option>
                                <option value="Entrenamiento" ${param.tipo == 'Entrenamiento Personal' ? 'selected' : ''}>Entrenamiento Personalizado</option>
                                <option value="Evaluación Física"${param.tipo == 'Evaluación Física' ? 'selected' : ''}>Evaluación Física</option>
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
                            <div class="alert alert-warning text-center py-4">
                                <i class="fas fa-exclamation-circle fa-2x mb-3"></i>
                                <h4>No se encontraron asistencias</h4>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>Cliente</th>
                                        <th>Fecha/Hora</th>
                                        <th>Tipo</th>
                                        <th>Registrado por</th>
                                        <th>Acción</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${asistencias}" var="asistencia">
                                        <tr>
                                            <td>
                                                    ${asistencia.nombre} ${asistencia.apellido}
                                                <span class="badge bg-info">${asistencia.rol}</span>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${asistencia.fechaAsistencia}"
                                                                pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <span class="badge ${asistencia.tipoAsistencia == 'Entrada' ? 'bg-primary' :
                                                  asistencia.tipoAsistencia == 'Salida' ? 'bg-danger' : 'bg-success'}">
                                                        ${asistencia.tipoAsistencia}
                                                </span>
                                            </td>
                                            <td>
                                                    ${asistencia.nombreRegistrador}
                                                <small class="text-muted d-block">(${asistencia.rolRegistrador})</small>
                                            </td>
                                            <td class="text-center">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/actualizar-asistencia?id=${asistencia.idAsistencia}"
                                                       class="btn btn-warning" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                </div>
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
