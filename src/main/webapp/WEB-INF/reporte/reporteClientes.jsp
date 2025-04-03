<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 2/4/2025
  Time: 1:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../cabecero.jsp"/>

<%
  // Obtener el rol del usuario desde la sesión
  String rol = (String) session.getAttribute("rol");
  pageContext.setAttribute("userRol", rol);
%>

<div class="container mt-4">
  <div class="card border-primary mb-4">
    <div class="card-header bg-primary text-white">
      <h2 class="text-center mb-0"><i class="fas fa-chart-line me-2"></i>Reporte de Clientes</h2>
    </div>

    <!-- Formulario de Filtros - Solo visible para Administrador y Entrenador -->
    <c:if test="${userRol != 'Cliente'}">
      <div class="card mb-4 shadow-sm">
        <div class="card-header bg-light">
          <h5 class="mb-0"><i class="fas fa-filter me-2"></i>Filtrar Clientes</h5>
        </div>
        <div class="card-body bg-light bg-gradient">
          <form action="${pageContext.request.contextPath}/ReporteClienteServlet" method="get" class="row g-3">
            <c:if test="${userRol == 'Administrador'}">
              <div class="col-md-4">
                <label for="cedula" class="form-label fw-bold">Cédula</label>
                <input type="text" class="form-control border-primary" id="cedula" name="cedula" placeholder="Buscar por cédula">
              </div>
            </c:if>
            <div class="col-md-4">
              <label for="nombre" class="form-label fw-bold">Nombre</label>
              <input type="text" class="form-control border-primary" id="nombre" name="nombre" placeholder="Buscar por nombre">
            </div>
            <div class="col-md-4">
              <label for="apellido" class="form-label fw-bold">Apellido</label>
              <input type="text" class="form-control border-primary" id="apellido" name="apellido" placeholder="Buscar por apellido">
            </div>
            <div class="col-12">
              <button type="submit" class="btn btn-primary"><i class="fas fa-search me-2"></i>Filtrar</button>
              <a href="${pageContext.request.contextPath}/ReporteClienteServlet" class="btn btn-outline-secondary"><i class="fas fa-broom me-2"></i>Limpiar Filtros</a>
            </div>
          </form>
        </div>
      </div>
    </c:if>

    <!-- Botones de Exportación  -->
      <div class="mb-3 text-end">
        <a href="${pageContext.request.contextPath}/ExportarExcelServlet" class="btn btn-success">
          <i class="fas fa-file-excel me-2"></i> Exportar a Excel
        </a>
        <a href="${pageContext.request.contextPath}/ExportarPDFServlet" class="btn btn-danger ms-2">
          <i class="fas fa-file-pdf me-2"></i> Exportar a PDF
        </a>
      </div>


    <div class="table-responsive">
      <table class="table table-bordered table-hover table-striped align-middle">
        <thead class="table-dark">
        <tr>
          <th class="text-center">ID</th>
          <th>Nombre</th>
          <th>Usuario</th>
          <th>Correo</th>
          <c:if test="${userRol != 'Entrenador'}">
            <th>Teléfono</th>
            <th>Cédula</th>
          </c:if>
          <th>Membresía</th>
          <th>Inicio</th>
          <th>Vencimiento</th>
          <th class="text-center">Días Restantes</th>
          <th class="text-center">Estado</th>
          <th class="text-center">Peso Inicial</th>
          <th class="text-center">Peso Actual</th>
          <th class="text-center">Progreso</th>
          <th>Última Act. Peso</th>
          <th class="text-center">Total Asistencias</th>
          <th>Última Asistencia</th>
          <th class="text-center">Asistencias Semana</th>
          <th class="text-center">Asistencias Mes</th>
          <th class="text-center">Rutinas</th>
          <th>Entrenador(es)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reporte" items="${reportes}">
          <tr>
            <td class="text-center">${reporte.idCliente}</td>
            <td class="fw-bold">${reporte.nombre} ${reporte.apellido}</td>
            <td>${reporte.usuario}</td>
            <td><a href="mailto:${reporte.correo}" class="text-decoration-none">${reporte.correo}</a></td>
            <c:if test="${userRol != 'Entrenador'}">
              <td>${reporte.telefono}</td>
              <td>${reporte.cedula}</td>
            </c:if>
            <td>
                <span class="badge ${reporte.tipoMembresia == 'Premium' ? 'bg-warning text-dark' : 'bg-info text-dark'}">
                    ${reporte.tipoMembresia}
                </span>
            </td>
            <td>${reporte.fechaInicio}</td>
            <td>${reporte.fechaVencimiento}</td>
            <td class="text-center ${reporte.diasRestantes < 7 ? 'text-danger fw-bold' : ''}">
                ${reporte.diasRestantes}
            </td>
            <td class="text-center">
                <span class="badge ${reporte.estadoMembresia == 'Activa' ? 'bg-success' : 'bg-secondary'}">
                    ${reporte.estadoMembresia}
                </span>
            </td>
            <td class="text-center">${reporte.pesoInicial} kg</td>
            <td class="text-center">${reporte.pesoActual} kg</td>
            <td class="text-center">
              <c:choose>
                <c:when test="${reporte.progresoPeso > 0}">
                  <span class="text-success fw-bold">▲ ${reporte.progresoPeso}%</span>
                </c:when>
                <c:when test="${reporte.progresoPeso < 0}">
                  <span class="text-danger fw-bold">▼ ${reporte.progresoPeso}%</span>
                </c:when>
                <c:otherwise>
                  <span class="text-muted">${reporte.progresoPeso}%</span>
                </c:otherwise>
              </c:choose>
            </td>
            <td>${reporte.ultimaActualizacionPeso}</td>
            <td class="text-center">${reporte.totalAsistencias}</td>
            <td>${reporte.ultimaAsistencia}</td>
            <td class="text-center">${reporte.asistenciasUltimaSemana}</td>
            <td class="text-center">${reporte.asistenciasUltimoMes}</td>
            <td class="text-center">${reporte.totalRutinas}</td>
            <td>${reporte.entrenadores}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</div>

<jsp:include page="../footer.jsp"/>