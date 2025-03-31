<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 16:17
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
            <i class="fas fa-dumbbell me-2"></i> Gestión Completa de Rutinas
          </h3>
          <div class="card-tools">
                        <span class="badge bg-light text-dark me-2">
                            Total: ${rutinas.size()} registros
                        </span>
          </div>
        </div>

        <!-- Filtros avanzados -->
        <div class="card-body border-bottom">
          <form action="${pageContext.request.contextPath}/RutinaServlet" method="get" class="row g-3">
            <!-- Filtro por cliente -->
            <div class="col-md-4">
              <label for="filtroCliente" class="form-label">Cliente:</label>
              <input type="text" class="form-control" id="filtroCliente"
                     name="nombreCliente" value="${param.nombreCliente}"
                     placeholder="Buscar por nombre de cliente">
            </div>

            <!-- Filtro por entrenador -->
            <div class="col-md-3">
              <label for="filtroEntrenador" class="form-label">Entrenador:</label>
              <input type="text" class="form-control" id="filtroEntrenador"
                     name="nombreEntrenador" value="${param.nombreEntrenador}"
                     placeholder="Buscar por entrenador">
            </div>

            <!-- Filtro por tipo de entrenamiento -->
            <div class="col-md-3">
              <label for="filtroTipo" class="form-label">Tipo Entrenamiento:</label>
              <select class="form-select" id="filtroTipo" name="tipoEntrenamiento">
                <option value="">Todos</option>
                <option value="Fuerza" ${param.tipoEntrenamiento == 'Fuerza' ? 'selected' : ''}>Fuerza</option>
                <option value="Resistencia" ${param.tipoEntrenamiento == 'Resistencia' ? 'selected' : ''}>Resistencia</option>
                <option value="Hipertrofia" ${param.tipoEntrenamiento == 'Hipertrofia' ? 'selected' : ''}>Hipertrofia</option>
                <option value="Cardio y Quema de Grasa" ${param.tipoEntrenamiento == 'Cardio y Quema de Grasa' ? 'selected' : ''}>Cardio y Quema de Grasa</option>
                <option value="Flexibilidad y Movilidad" ${param.tipoEntrenamiento == 'Flexibilidad y Movilidad' ? 'selected' : ''}>Flexibilidad y Movilidad</option>
                <option value="Entrenamiento Funcional" ${param.tipoEntrenamiento == 'Entrenamiento Funcional' ? 'selected' : ''}>Entrenamiento Funcional</option>
                <option value="Rehabilitación" ${param.tipoEntrenamiento == 'Rehabilitación' ? 'selected' : ''}>Rehabilitación</option>
                <option value="CrossFit / HIIT" ${param.tipoEntrenamiento == 'CrossFit / HIIT' ? 'selected' : ''}>CrossFit / HIIT</option>
              </select>
            </div>

            <!-- Botones de acción -->
            <div class="col-md-2 d-flex align-items-end">
              <div class="btn-group w-100">
                <button type="submit" class="btn btn-primary">
                  <i class="fas fa-filter me-1"></i> Filtrar
                </button>
                <a href="${pageContext.request.contextPath}/RutinaServlet"
                   class="btn btn-secondary">
                  <i class="fas fa-sync-alt"></i>
                </a>
              </div>
            </div>
            <br>
            <div class="ms-auto">
              <a href="${pageContext.request.contextPath}/AgregarRutinaServlet"
                 class="btn btn-success rounded-pill px-4 py-2 fw-bold shadow">
                <i class="fas fa-plus-circle me-2"></i> Crear Rutina
              </a>
            </div>
          </form>
        </div>

        <!-- Tabla de rutinas -->
        <div class="card-body">
          <c:choose>
            <c:when test="${empty rutinas}">
              <div class="alert alert-warning text-center">
                <i class="fas fa-exclamation-circle me-2"></i>
                No se encontraron rutinas
                <c:if test="${not empty param.nombreCliente}">
                  para el cliente: <strong>${param.nombreCliente}</strong>
                </c:if>
                <c:if test="${not empty param.tipoEntrenamiento}">
                  del tipo: <strong>${param.tipoEntrenamiento}</strong>
                </c:if>
              </div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover table-bordered table-striped">
                  <thead class="table-dark">
                  <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Entrenador</th>
                    <th>Tipo Entrenamiento</th>
                    <th>Observaciones</th>
                    <th>Acciones</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${rutinas}" var="rutina">
                    <tr>
                      <td>${rutina.idRutina}</td>
                      <td>
                          ${rutina.clienteNombre} ${rutina.clienteApellido}
                        <span class="badge bg-info">${rutina.clienteRol}</span>
                      </td>
                      <td>
                          ${rutina.entrenadorNombre} ${rutina.entrenadorApellido}
                        <span class="badge bg-warning text-dark">${rutina.entrenadorRol}</span>
                      </td>
                      <td>
                        <span class="badge bg-success">${rutina.tipoEntrenamiento}</span>
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

              <!-- Paginación -->
              <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                  <c:if test="${paginaActual > 1}">
                    <li class="page-item">
                      <a class="page-link"
                         href="${pageContext.request.contextPath}/RutinaServlet?pagina=${paginaActual-1}&nombreCliente=${param.nombreCliente}&tipoEntrenamiento=${param.tipoEntrenamiento}&nombreEntrenador=${param.nombreEntrenador}">
                        Anterior
                      </a>
                    </li>
                  </c:if>

                  <c:forEach begin="1" end="${totalPaginas}" var="i">
                    <li class="page-item ${i == paginaActual ? 'active' : ''}">
                      <a class="page-link"
                         href="${pageContext.request.contextPath}/RutinaServlet?pagina=${i}&nombreCliente=${param.nombreCliente}&tipoEntrenamiento=${param.tipoEntrenamiento}&nombreEntrenador=${param.nombreEntrenador}">
                          ${i}
                      </a>
                    </li>
                  </c:forEach>

                  <c:if test="${paginaActual < totalPaginas}">
                    <li class="page-item">
                      <a class="page-link"
                         href="${pageContext.request.contextPath}/RutinaServlet?pagina=${paginaActual+1}&nombreCliente=${param.nombreCliente}&tipoEntrenamiento=${param.tipoEntrenamiento}&nombreEntrenador=${param.nombreEntrenador}">
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
    <c:if test="${not empty param.nombreCliente or not empty param.tipoEntrenamiento or not empty param.nombreEntrenador}">
    toastr.info('Filtros aplicados: ' +
            '<c:if test="${not empty param.nombreCliente}">Cliente: ${param.nombreCliente} </c:if>' +
            '<c:if test="${not empty param.nombreEntrenador}">Entrenador: ${param.nombreEntrenador} </c:if>' +
            '<c:if test="${not empty param.tipoEntrenamiento}">Tipo: ${param.tipoEntrenamiento} </c:if>');
    </c:if>
  });
</script>