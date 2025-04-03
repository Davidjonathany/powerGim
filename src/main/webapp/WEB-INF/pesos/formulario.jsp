<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 1/4/2025
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1 class="mb-4">${modo eq 'editar' ? 'Editar Registro de Peso' : 'Nuevo Registro de Peso'}</h1>

  <c:if test="${not empty param.error}">
    <div class="alert alert-danger alert-dismissible fade show">
      <strong>Error!</strong>
      <c:choose>
        <c:when test="${param.error.contains('ya tiene')}">
          El cliente ya tiene un registro de peso.
          <c:if test="${not empty peso and not empty peso.id}">
          </c:if>
        </c:when>
        <c:otherwise>
          ${param.error}
        </c:otherwise>
      </c:choose>
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <c:if test="${rol eq 'Cliente' and not empty peso and modo eq 'editar'}">
    <div class="alert alert-info">
      <i class="fas fa-info-circle me-2"></i>
      Ya tienes un registro de peso. Puedes actualizarlo aquí.
    </div>
  </c:if>

  <form action="${pageContext.request.contextPath}/pesos/gestion" method="post" class="needs-validation" novalidate>
    <c:if test="${modo eq 'editar'}">
      <input type="hidden" name="id" value="${peso.id}">
    </c:if>

    <!-- Sección Cliente -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Datos del Cliente</h5>
      </div>
      <div class="card-body">
        <div class="row mb-3">
          <div class="col-md-6">
            <label for="idCliente" class="form-label">Cliente</label>
            <c:choose>
              <c:when test="${modo eq 'editar' or not empty param.idCliente}">
                <!-- En modo edición o cuando hay error de registro existente -->
                <input type="text" class="form-control"
                       value="${peso.nombreCliente} ${peso.apellidoCliente}" readonly>
                <input type="hidden" name="idCliente" value="${not empty param.idCliente ? param.idCliente : peso.idCliente}">
              </c:when>
              <c:when test="${rol eq 'Cliente'}">
                <!-- Para clientes normales, mostrar solo su nombre -->
                <input type="text" class="form-control" value="Tus datos (${sessionScope.cedula})" readonly>
                <input type="hidden" name="idCliente" value="${sessionScope.idUsuario}">
              </c:when>
              <c:otherwise>
                <!-- Para admin/entrenador, mostrar select -->
                <select class="form-select" id="idCliente" name="idCliente" required
                        <c:if test="${not empty param.idCliente}">disabled</c:if>>
                  <option value="">Seleccione un cliente</option>
                  <c:forEach items="${clientes}" var="cliente">
                    <option value="${cliente.idCliente}"
                      ${(not empty param.idCliente and param.idCliente eq cliente.idCliente.toString())
                              or (modo eq 'editar' and peso.idCliente eq cliente.idCliente) ? 'selected' : ''}>
                        ${cliente.nombreCliente} ${cliente.apellidoCliente}
                      <c:if test="${rol eq 'Administrador'}">- Cédula: ${cliente.cedulaCliente}</c:if>
                    </option>
                  </c:forEach>
                </select>
                <c:if test="${not empty param.idCliente}">
                  <input type="hidden" name="idCliente" value="${param.idCliente}">
                </c:if>
                <div class="invalid-feedback">
                  Por favor seleccione un cliente
                </div>
              </c:otherwise>
            </c:choose>

            <c:if test="${not empty param.error and param.error.contains('ya tiene') and empty peso.id}">
              <div class="alert alert-warning mt-2">
                <i class="fas fa-exclamation-triangle me-2"></i>
                El cliente seleccionado ya tiene un registro de peso.
              </div>
            </c:if>

            <c:if test="${(rol eq 'Administrador' or rol eq 'Entrenador') and empty clientes}">
              <div class="alert alert-warning mt-2">
                No se encontraron clientes registrados
                <c:if test="${rol eq 'Entrenador'}">asociados a usted</c:if>
              </div>
            </c:if>
          </div>
        </div>
      </div>
    </div>

    <!-- Sección Peso -->
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <h5 class="mb-0">Registro de Peso</h5>
      </div>
      <div class="card-body">
        <div class="row mb-3">
          <div class="col-md-6">
            <label for="pesoActual" class="form-label">Peso Actual (kg)</label>
            <input type="number" class="form-control" id="pesoActual" name="pesoActual"
                   step="0.01" max="250" required
                   value='<fmt:formatNumber value="${modo eq 'editar' ? peso.pesoActual : ''}" pattern="0.00"/>'>
            <small class="text-muted">Ingrese el peso en kilogramos</small>
            <div class="invalid-feedback">
              Por favor ingrese un peso válido (máximo 250kg)
            </div>
          </div>
        </div>

        <div class="row mb-3">
          <div class="col-md-6">
            <div class="alert alert-info">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" id="mismoPesoInicial" checked disabled>
                <label class="form-check-label" for="mismoPesoInicial">
                  <strong>El peso inicial será igual al peso actual</strong>
                </label>
              </div>
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
        <i class="fas fa-save me-2"></i> ${modo eq 'editar' ? 'Actualizar' : 'Guardar'}
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