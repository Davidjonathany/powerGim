<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 1/4/2025
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container mt-4">
  <div class="card shadow-sm">
    <div class="card-header bg-primary text-white">
      <h3 class="card-title mb-0">
        <i class="fas fa-weight me-2"></i>
        ${esAdministrador ? 'Registros de Peso' : (esEntrenador ? 'Pesos de Mis Clientes' : 'Mi Evolución de Peso')}
      </h3>
    </div>

    <div class="card-body">
      <!-- Mensajes de éxito/error -->
      <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show">
          <i class="fas fa-check-circle me-2"></i> ${param.success}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>
      <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show">
          <i class="fas fa-exclamation-circle me-2"></i> ${param.error}
          <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
      </c:if>

      <!-- Filtros según rol -->
      <c:if test="${esAdministrador || esEntrenador}">
        <div class="row mb-4 g-3 align-items-center">
          <div class="col-md-6">
            <a href="${pageContext.request.contextPath}/pesos/gestion" class="btn btn-success btn-lg">
              <i class="fas fa-plus-circle me-2"></i> Nuevo Registro
            </a>
          </div>
          <div class="col-md-6">
            <form method="get" action="${pageContext.request.contextPath}/pesos" class="row g-2">
              <div class="col-8">
                <div class="input-group">
                  <span class="input-group-text bg-light">
                    <i class="fas fa-search"></i>
                  </span>
                  <input type="text" id="filtro" name="filtro" class="form-control form-control-lg"
                         value="${param.filtro}" placeholder="${esAdministrador ? 'Nombre, Apellido o Cédula' : 'Nombre o Apellido'}">
                </div>
              </div>
              <div class="col-4">
                <button type="submit" class="btn btn-primary btn-lg w-100">
                  <i class="fas fa-filter me-2"></i> Filtrar
                </button>
              </div>
              <c:if test="${not empty param.filtro}">
                <div class="col-12 mt-2">
                  <a href="${pageContext.request.contextPath}/pesos" class="btn btn-outline-danger btn-sm">
                    <i class="fas fa-times me-2"></i> Limpiar filtros
                  </a>
                </div>
              </c:if>
            </form>
          </div>
        </div>
      </c:if>

      <!-- Mensaje cuando no hay resultados -->
      <c:if test="${empty pesos}">
        <div class="alert alert-warning text-center py-4">
          <i class="fas fa-weight-hanging fa-3x mb-3 text-muted"></i>
          <h4>No se encontraron registros de peso</h4>
          <c:if test="${not empty param.filtro}">
            <p class="mb-0">para: <strong>${param.filtro}</strong></p>
          </c:if>
        </div>
      </c:if>

      <c:if test="${not empty pesos}">
        <div class="table-responsive">
          <table class="table table-striped table-hover table-bordered">
            <thead class="table-dark">
            <tr>
              <th class="text-center">ID</th>
              <c:if test="${esAdministrador || esEntrenador}">
                <th>Cliente</th>
              </c:if>
              <c:if test="${esAdministrador}">
                <th>Cédula</th>
              </c:if>
              <th class="text-center">Peso Inicial (kg)</th>
              <th class="text-center">Peso Actual (kg)</th>
              <th class="text-center">Diferencia</th>
              <th class="text-center">Fecha Inicio</th>
              <th class="text-center">Último Registro</th>
              <c:if test="${esAdministrador || esEntrenador}">
                <th class="text-center">Acciones</th>
              </c:if>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pesos}" var="peso">
              <tr>
                <td class="text-center">${peso.id}</td>
                <c:if test="${esAdministrador || esEntrenador}">
                  <td>
                    <span class="fw-bold">${peso.nombreCliente} ${peso.apellidoCliente}</span>
                  </td>
                </c:if>
                <c:if test="${esAdministrador}">
                  <td>${peso.cedulaCliente}</td>
                </c:if>
                <td class="text-center">
                    <span class="badge bg-light text-dark fs-6">
                      <fmt:formatNumber value="${peso.pesoInicial}" pattern="0.00"/>
                    </span>
                </td>
                <td class="text-center">
                    <span class="badge bg-primary text-white fs-6">
                      <fmt:formatNumber value="${peso.pesoActual}" pattern="0.00"/>
                    </span>
                </td>
                <td class="text-center">
                    <span class="badge ${peso.diferenciaPeso > 0 ? 'bg-success' : 'bg-danger'} text-white fs-6">
                      <fmt:formatNumber value="${peso.diferenciaPeso}" pattern="0.00"/> kg
                    </span>
                </td>
                <td class="text-center">${peso.fechaInicio}</td>
                <td class="text-center">${peso.fechaRegistro}</td>
                <c:if test="${esAdministrador || esEntrenador}">
                  <td class="text-center">
                    <div class="btn-group btn-group-sm" role="group">
                      <a href="${pageContext.request.contextPath}/pesos/actualizar?id=${peso.id}"
                         class="btn btn-outline-warning" title="Editar">
                        <i class="fas fa-edit"></i>
                      </a>
                      <c:if test="${sessionScope.rol eq 'Administrador'}">
                        <a href="${pageContext.request.contextPath}/pesos/eliminar?id=${peso.id}"
                           class="btn btn-outline-danger" title="Eliminar">
                          <i class="fas fa-trash-alt"></i>
                        </a>
                      </c:if>
                    </div>
                  </td>
                </c:if>
              </tr>
            </c:forEach>
            </tbody>
          </table>
        </div>
      </c:if>

      <!-- Gráfico de progreso para clientes -->
      <c:if test="${!esAdministrador && !esEntrenador && not empty pesos}">
        <div class="card mt-4 border-primary">
          <div class="card-header bg-light">
            <h5 class="card-title mb-0 text-primary">
              <i class="fas fa-chart-line me-2"></i> Mi Progreso
            </h5>
          </div>
          <div class="card-body">
            <canvas id="pesoChart" height="120"></canvas>
          </div>
          <div class="card-footer bg-light text-muted small">
            Última actualización: <span id="fechaActual"></span>
          </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
          // Fecha actual
          document.getElementById('fechaActual').textContent = new Date().toLocaleString();

          // Configuración del gráfico
          const ctx = document.getElementById('pesoChart');
          new Chart(ctx, {
            type: 'line',
            data: {
              labels: [<c:forEach items="${pesos}" var="peso">"${peso.fechaRegistro}",</c:forEach>],
              datasets: [{
                label: 'Peso Inicial (kg)',
                data: [<c:forEach items="${pesos}" var="peso">${peso.pesoInicial},</c:forEach>],
                borderColor: 'rgba(75, 192, 192, 0.8)',
                backgroundColor: 'rgba(75, 192, 192, 0.1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true
              },{
                label: 'Peso Actual (kg)',
                data: [<c:forEach items="${pesos}" var="peso">${peso.pesoActual},</c:forEach>],
                borderColor: 'rgba(255, 99, 132, 0.8)',
                backgroundColor: 'rgba(255, 99, 132, 0.1)',
                borderWidth: 2,
                tension: 0.3,
                fill: true
              }]
            },
            options: {
              responsive: true,
              plugins: {
                legend: {
                  position: 'top',
                },
                tooltip: {
                  callbacks: {
                    label: function(context) {
                      return context.dataset.label + ': ' + context.raw.toFixed(2) + ' kg';
                    }
                  }
                }
              },
              scales: {
                y: {
                  beginAtZero: false,
                  title: {
                    display: true,
                    text: 'Peso (kg)'
                  }
                },
                x: {
                  title: {
                    display: true,
                    text: 'Fecha de Registro'
                  }
                }
              }
            }
          });
        </script>
      </c:if>
    </div>
  </div>
</div>

<jsp:include page="../footer.jsp"/>