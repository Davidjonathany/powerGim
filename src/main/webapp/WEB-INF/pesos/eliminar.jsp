<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 2/4/2025
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1>Eliminar Registro de Peso</h1>

  <%-- Manejo de errores --%>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">
      <i class="fas fa-exclamation-circle"></i> ${error}
      <a href="${pageContext.request.contextPath}/pesos" class="float-end">
        <i class="fas fa-arrow-left"></i> Volver a la lista
      </a>
    </div>
  </c:if>

  <c:if test="${empty peso}">
    <div class="alert alert-warning">
      No se encontraron datos del registro de peso.
      <a href="${pageContext.request.contextPath}/pesos" class="float-end">
        <i class="fas fa-arrow-left"></i> Volver
      </a>
    </div>
  </c:if>

  <c:if test="${not empty peso}">
    <div class="card mb-4">
      <div class="card-header bg-danger text-white">
        <h4><i class="fas fa-exclamation-triangle"></i> Confirmar Eliminación</h4>
      </div>
      <div class="card-body">
        <h5 class="card-title">Detalles del Registro</h5>

        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Cliente:</label>
            <div class="p-2 bg-light rounded">
              <strong>${peso.nombreCliente} ${peso.apellidoCliente}</strong>
              <div>Cédula: ${peso.cedulaCliente}</div>
            </div>
          </div>
          <div class="col-md-6">
            <label class="form-label">Fecha de Registro:</label>
            <div class="p-2 bg-light rounded">${peso.fechaRegistro}</div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-md-4">
            <label class="form-label">Peso Inicial:</label>
            <div class="p-2 bg-light rounded">
              <fmt:formatNumber value="${peso.pesoInicial}" pattern="0.00"/> kg
            </div>
          </div>
          <div class="col-md-4">
            <label class="form-label">Peso Actual:</label>
            <div class="p-2 bg-light rounded">
              <fmt:formatNumber value="${peso.pesoActual}" pattern="0.00"/> kg
            </div>
          </div>
          <div class="col-md-4">
            <label class="form-label">Diferencia:</label>
            <div class="p-2 bg-light rounded">
              <fmt:formatNumber value="${peso.pesoInicial - peso.pesoActual}" pattern="0.00"/> kg
            </div>
          </div>
        </div>

        <div class="alert alert-warning mt-4">
          <h5><i class="fas fa-exclamation-circle"></i> ¿Está seguro que desea eliminar este registro?</h5>
          <p class="mb-0">Esta acción no se puede deshacer y se perderán todos los datos históricos.</p>
        </div>

        <form action="${pageContext.request.contextPath}/pesos/eliminar" method="post">
          <input type="hidden" name="id" value="${peso.id}">

          <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/pesos" class="btn btn-secondary">
              <i class="fas fa-arrow-left"></i> Cancelar
            </a>
            <button type="submit" class="btn btn-danger">
              <i class="fas fa-trash-alt"></i> Confirmar Eliminación
            </button>
          </div>
        </form>
      </div>
    </div>
  </c:if>
</div>

<jsp:include page="../footer.jsp"/>
