<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid py-4">
            <div class="card shadow-lg">
                <div class="card-header bg-success text-white">
                    <h3 class="card-title">
                        <i class="fas fa-dumbbell me-2"></i> Mis Rutinas Asignadas
                    </h3>
                    <div class="card-tools">
                        <span class="badge bg-light text-dark me-2">
                            Total: ${totalRegistros} registros
                        </span>
                    </div>
                </div>

                <!-- Filtros -->
                <div class="card-body border-bottom">
                    <form action="${pageContext.request.contextPath}/RutinaServlet" method="get" class="row g-3">
                        <div class="col-md-8">
                            <label for="filtroTipo" class="form-label">Filtrar por tipo:</label>
                            <div class="input-group">
                                <select class="form-select" id="filtroTipo" name="tipoEntrenamiento">
                                    <option value="">Todos los tipos</option>
                                    <option value="Fuerza" ${param.tipoEntrenamiento == 'Fuerza' ? 'selected' : ''}>Fuerza</option>
                                    <option value="Resistencia" ${param.tipoEntrenamiento == 'Resistencia' ? 'selected' : ''}>Resistencia</option>
                                    <option value="Hipertrofia" ${param.tipoEntrenamiento == 'Hipertrofia' ? 'selected' : ''}>Hipertrofia</option>
                                    <option value="Cardio y Quema de Grasa" ${param.tipoEntrenamiento == 'Cardio y Quema de Grasa' ? 'selected' : ''}>Cardio y Quema de Grasa</option>
                                    <option value="Flexibilidad y Movilidad" ${param.tipoEntrenamiento == 'Flexibilidad y Movilidad' ? 'selected' : ''}>Flexibilidad y Movilidad</option>
                                    <option value="Entrenamiento Funcional" ${param.tipoEntrenamiento == 'Entrenamiento Funcional' ? 'selected' : ''}>Entrenamiento Funcional</option>
                                    <option value="Rehabilitación" ${param.tipoEntrenamiento == 'Rehabilitación' ? 'selected' : ''}>Rehabilitación</option>
                                    <option value="CrossFit / HIIT" ${param.tipoEntrenamiento == 'CrossFit / HIIT' ? 'selected' : ''}>CrossFit / HIIT</option>
                                </select>
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-search me-1"></i> Buscar
                                </button>
                                <c:if test="${not empty param.tipoEntrenamiento}">
                                    <a href="${pageContext.request.contextPath}/RutinaServlet"
                                       class="btn btn-outline-secondary">
                                        <i class="fas fa-times"></i>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-md-4 d-flex align-items-end">
                            <div class="ms-auto">
                                <a href="${pageContext.request.contextPath}/AgregarRutinaServlet"
                                   class="btn btn-primary rounded-pill px-4 py-2 fw-bold shadow">
                                    <i class="fas fa-plus-circle me-2"></i> Crear Rutina
                                </a>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty rutinas}">
                            <div class="alert alert-info text-center py-4">
                                <i class="fas fa-info-circle fa-2x mb-3"></i>
                                <h4>No tienes rutinas asignadas</h4>
                                <c:if test="${not empty param.tipoEntrenamiento}">
                                    <p>para el tipo: <strong>${param.tipoEntrenamiento}</strong></p>
                                </c:if>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover table-striped">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>ID Rutina</th>
                                        <th>Cliente</th>
                                        <th>Tipo Entrenamiento</th>
                                        <th>Observaciones</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <!-- Cambio clave: Eliminar los atributos begin y end del forEach -->
                                    <c:forEach items="${rutinas}" var="rutina">
                                        <tr>
                                            <td>${rutina.idRutina}</td>
                                            <td>
                                                    ${rutina.clienteNombre} ${rutina.clienteApellido}
                                                <span class="badge bg-primary">${rutina.clienteRol}</span>
                                                <br>
                                                <small class="text-muted">ID: ${rutina.idCliente}</small>
                                            </td>
                                            <td>
                                                <span class="badge bg-warning text-dark">${rutina.tipoEntrenamiento}</span>
                                            </td>
                                            <td>${rutina.observaciones}</td>
                                            <td class="text-center">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/ActualizarRutinaServlet?id=${rutina.idRutina}"
                                                       class="btn btn-warning" title="Editar">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/EliminarRutinaServlet?id=${rutina.idRutina}"
                                                       class="btn btn-danger" title="Eliminar">
                                                        <i class="fas fa-trash-alt"></i>
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

<!-- Script para mejorar la experiencia de usuario -->
<script>
    $(document).ready(function() {
        // Inicializar tooltips
        $('[title]').tooltip();

        // Mostrar mensaje si hay parámetros de búsqueda
        <c:if test="${not empty param.tipoEntrenamiento}">
        toastr.info('Filtro aplicado: Tipo de entrenamiento - ${param.tipoEntrenamiento}');
        </c:if>
    });
</script>