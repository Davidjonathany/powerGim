<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1 class="text-center">Confirmar Eliminación de Asistencia</h1>

  <%-- Mostrar mensajes de error/success --%>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  <c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
  </c:if>

  <c:if test="${empty asistencia}">
    <div class="alert alert-warning text-center">
      No se encontraron datos de la asistencia a eliminar
    </div>
  </c:if>
  <c:if test="${not empty asistencia}">
    <div class="row justify-content-center">
      <div class="col-md-10">
        <div class="card border-danger">
          <div class="card-header bg-danger text-white">
            <h4 class="card-title">
              <i class="fas fa-exclamation-triangle me-2"></i>¿Está seguro de eliminar esta asistencia?
            </h4>
          </div>
          <div class="card-body">
              <%-- Detalles de la asistencia --%>
            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">ID Asistencia:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="${asistencia.idAsistencia}">
              </div>
            </div>

            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">Cliente:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="${asistencia.nombre} ${asistencia.apellido}">
              </div>
            </div>

            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">Cédula:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="${asistencia.cedula}">
              </div>
            </div>

            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">Fecha y Hora:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="<fmt:formatDate value="${asistencia.fechaAsistencia}" pattern="dd/MM/yyyy HH:mm"/>">
              </div>
            </div>

            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">Tipo Asistencia:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="${asistencia.tipoAsistencia}">
              </div>
            </div>

            <div class="mb-3 row">
              <label class="col-sm-3 col-form-label">Registrado por:</label>
              <div class="col-sm-9">
                <input type="text" readonly class="form-control-plaintext"
                       value="${asistencia.nombreRegistrador} ${asistencia.apellidoRegistrador} (${asistencia.rolRegistrador})">
              </div>
            </div>

              <%-- Formulario de confirmación --%>
                <form action="${pageContext.request.contextPath}/eliminar-asistencia" method="post">
                  <input type="hidden" name="id" value="${asistencia.idAsistencia}">

                  <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                    <!-- Usar el nombre actual del servlet para cancelar -->
                    <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet"
                       class="btn btn-danger" style="background-color: #fd4242">
                      <i class="fa fa-arrow-circle-left"></i> Cancelar
                    </a>
                    <button type="submit" class="btn btn-danger">
                      <i class="fas fa-trash-alt me-1"></i> Confirmar Eliminación
                    </button>
                  </div>
                </form>

          </div>
        </div>
      </div>
    </div>
  </c:if>
</div>

<jsp:include page="../footer.jsp"/>