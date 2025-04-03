<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 2/4/2025
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1 class="mb-4">Actualizar Registro de Peso</h1>

  <c:if test="${not empty param.error}">
    <div class="alert alert-danger alert-dismissible fade show">
      <strong>Error!</strong> ${param.error}
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/pesos/actualizar" method="post" class="needs-validation" novalidate>
    <input type="hidden" name="id" value="${peso.id}">

    <!-- Información del Cliente (solo lectura) -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Datos del Cliente</h5>
      </div>
      <div class="card-body">
        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Cliente</label>
            <input type="text" class="form-control"
                   value="${peso.nombreCliente} ${peso.apellidoCliente}" readonly>
          </div>
          <div class="col-md-6">
            <label class="form-label">Fecha de Inicio</label>
            <input type="text" class="form-control"
                   value="${peso.fechaInicio}" readonly>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <label class="form-label">Peso Inicial</label>
            <input type="text" class="form-control"
                   value='<fmt:formatNumber value="${peso.pesoInicial}" pattern="0.00"/> kg' readonly>
          </div>
          <div class="col-md-6">
            <label class="form-label">Última Actualización</label>
            <input type="text" class="form-control"
                   value="${peso.fechaRegistro}" readonly>
          </div>
        </div>
      </div>
    </div>

    <!-- Actualización de Peso -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Actualizar Peso</h5>
      </div>
      <div class="card-body">
        <div class="row mb-3">
          <div class="col-md-6">
            <label for="pesoActual" class="form-label">Nuevo Peso Actual (kg)</label>
            <input type="number" class="form-control" id="pesoActual" name="pesoActual"
                   step="0.01" max="300" required
                   value='<fmt:formatNumber value="${peso.pesoActual}" pattern="0.00"/>'>
            <small class="text-muted">Ingrese el nuevo peso en kilogramos (ej. 75.5)</small>
            <div class="invalid-feedback">
              Por favor ingrese un peso válido (máximo 300kg)
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Botones de acción -->
    <div class="d-flex justify-content-between">
      <a href="${pageContext.request.contextPath}/pesos" class="btn btn-secondary">
        <i class="fas fa-arrow-left me-2"></i> Volver
      </a>
      <button type="submit" class="btn btn-success">
        <i class="fas fa-save me-2"></i> Actualizar Peso
      </button>
    </div>
  </form>
</div>

<!-- Validación de formulario -->
<script>
  (() => {
    'use strict'
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
      form.addEventListener('submit', event => {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
  })()
</script>

<jsp:include page="../footer.jsp"/>
