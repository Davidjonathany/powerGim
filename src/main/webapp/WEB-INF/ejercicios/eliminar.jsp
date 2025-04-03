<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 1/4/2025
  Time: 0:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1>Eliminar Ejercicio</h1>

  <%-- Manejo de errores --%>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">
      <i class="fas fa-exclamation-circle"></i> ${error}
      <a href="${pageContext.request.contextPath}/ejercicios" class="float-end">
        <i class="fas fa-arrow-left"></i> Volver a la lista
      </a>
    </div>
  </c:if>

  <c:if test="${empty ejercicio}">
    <div class="alert alert-warning">
      No se encontraron datos del ejercicio.
      <a href="${pageContext.request.contextPath}/ejercicios" class="float-end">
        <i class="fas fa-arrow-left"></i> Volver
      </a>
    </div>
  </c:if>

  <c:if test="${not empty ejercicio}">
    <div class="card mb-4">
      <div class="card-header bg-danger text-white">
        <h4><i class="fas fa-exclamation-triangle"></i> Confirmar Eliminación</h4>
      </div>
      <div class="card-body">
        <h5 class="card-title">Detalles del Ejercicio</h5>

        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Cliente:</label>
            <div class="p-2 bg-light rounded">
              <strong>${ejercicio.nombreCliente} ${ejercicio.apellidoCliente}</strong>
              <div>Cédula: ${ejercicio.cedulaCliente}</div>
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Nombre del Ejercicio:</label>
            <div class="p-2 bg-light rounded">${ejercicio.nombreEjercicio}</div> <!-- Cambiado a nombreEjercicio -->
          </div>
          <div class="col-md-6">
            <label class="form-label">Rutina ID:</label>
            <div class="p-2 bg-light rounded">${ejercicio.idRutina}</div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-md-3">
            <label class="form-label">Series:</label>
            <div class="p-2 bg-light rounded">${ejercicio.series}</div>
          </div>
          <div class="col-md-3">
            <label class="form-label">Repeticiones:</label>
            <div class="p-2 bg-light rounded">${ejercicio.repeticiones}</div>
          </div>
          <div class="col-md-3">
            <label class="form-label">Tiempo (seg):</label>
            <div class="p-2 bg-light rounded">${ejercicio.tiempo}</div>
          </div>
          <div class="col-md-3">
            <label class="form-label">Descanso (seg):</label>
            <div class="p-2 bg-light rounded">${ejercicio.descanso}</div>
          </div>
        </div>

        <div class="alert alert-warning mt-4">
          <h5><i class="fas fa-exclamation-circle"></i> ¿Está seguro que desea eliminar este ejercicio?</h5>
          <p class="mb-0">Esta acción no se puede deshacer.</p>
        </div>

        <form action="${pageContext.request.contextPath}/ejercicios/eliminar" method="post">
          <input type="hidden" name="id" value="${ejercicio.id}">

          <div class="d-flex justify-content-between">
            <a href="${pageContext.request.contextPath}/ejercicios" class="btn btn-danger" style="background-color: #fd4242">
              <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
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