<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 23:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1>Editar Ejercicio</h1>

  <c:if test="${not empty param.error}">
    <div class="alert alert-danger">${param.error}</div>
  </c:if>

  <form action="${pageContext.request.contextPath}/ejercicios/editar" method="post">
    <!-- Campos ocultos esenciales -->
    <input type="hidden" name="id" value="${ejercicio.id}">
    <input type="hidden" name="idRutina" value="${ejercicio.idRutina}">

    <!-- Sección de Rutina Asociada (SOLO VISUALIZACIÓN) -->
    <div class="card mb-4 border-primary">
      <div class="card-header bg-primary text-white">
        <i class="fas fa-link me-2"></i> Información de Rutina Asociada
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-4">
            <h6 class="text-primary">Tipo de Entrenamiento</h6>
            <p class="lead">${rutina.tipoEntrenamiento}</p>
          </div>
          <div class="col-md-4">
            <h6 class="text-primary">Cliente Asignado</h6>
            <p class="lead">${rutina.clienteNombre} ${rutina.clienteApellido}</p>
          </div>
          <c:if test="${not empty rutina.entrenadorNombre}">
            <div class="col-md-4">
              <h6 class="text-primary">Entrenador Responsable</h6>
              <p class="lead">${rutina.entrenadorNombre} ${rutina.entrenadorApellido}</p>
            </div>
          </c:if>
        </div>
      </div>
      <div class="card-footer bg-light">
        <small class="text-muted">
          <i class="fas fa-info-circle me-1"></i> La rutina asociada no puede ser modificada desde esta pantalla
        </small>
      </div>
    </div>

    <!-- Campos editables del ejercicio -->
    <div class="row mb-3">
      <div class="col-md-6">
        <label for="nombre" class="form-label">Nombre del Ejercicio *</label>
        <input type="text" class="form-control" id="nombre" name="nombre"
               value="${ejercicio.nombre}" required>
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-3">
        <label for="series" class="form-label">Series *</label>
        <input type="number" class="form-control" id="series" name="series"
               value="${ejercicio.series}" required min="1">
      </div>
      <div class="col-md-3">
        <label for="repeticiones" class="form-label">Repeticiones *</label>
        <input type="number" class="form-control" id="repeticiones" name="repeticiones"
               value="${ejercicio.repeticiones}" required min="1">
      </div>
    </div>

    <div class="row mb-4">
      <div class="col-md-3">
        <label for="tiempo" class="form-label">Tiempo (segundos)</label>
        <input type="number" class="form-control" id="tiempo" name="tiempo"
               value="${ejercicio.tiempo}" min="0">
      </div>
      <div class="col-md-3">
        <label for="descanso" class="form-label">Descanso (segundos)</label>
        <input type="number" class="form-control" id="descanso" name="descanso"
               value="${ejercicio.descanso}" min="0">
      </div>
    </div>

    <!-- Botones de acción -->
    <div class="d-flex justify-content-end gap-3">
      <a href="${pageContext.request.contextPath}/ejercicios" class="btn btn-danger" style="background-color: #fd4242">
        <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
      <button type="submit" class="btn btn-primary">
        <i class="fas fa-save me-1"></i> Guardar Cambios
      </button>
    </div>
  </form>
</div>

<jsp:include page="../footer.jsp"/>