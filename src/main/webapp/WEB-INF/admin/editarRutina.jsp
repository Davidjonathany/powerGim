<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 29/3/2025
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1 class="text-center">Editar Rutina</h1>

  <div class="row justify-content-center">
    <div class="col-md-8">
      <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/ActualizarRutinaServlet" method="post">
        <input type="hidden" name="idRutina" value="${rutina.idRutina}">

        <div class="mb-3">
          <label class="form-label">Cliente:</label>
          <input type="text" class="form-control"
                 value="${rutina.clienteNombre} ${rutina.clienteApellido}"
                 readonly>
        </div>

        <div class="mb-3">
          <label for="idEntrenador" class="form-label">Entrenador:</label>
          <select class="form-select" id="idEntrenador" name="idEntrenador" required>
            <c:forEach items="${entrenadores}" var="entrenador">
              <option value="${entrenador.id}"
                ${entrenador.id == rutina.idEntrenador ? 'selected' : ''}>
                  ${entrenador.nombre} ${entrenador.apellido}
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="mb-3">
          <label for="tipoEntrenamiento" class="form-label">Tipo de Entrenamiento:</label>
          <select class="form-select" id="tipoEntrenamiento" name="tipoEntrenamiento" required>
            <option value="" disabled ${empty rutina.tipoEntrenamiento ? 'selected' : ''}>Seleccione un tipo</option>
            <option value="Fuerza" ${rutina.tipoEntrenamiento == 'Fuerza' ? 'selected' : ''}>Fuerza</option>
            <option value="Resistencia" ${rutina.tipoEntrenamiento == 'Resistencia' ? 'selected' : ''}>Resistencia</option>
            <option value="Hipertrofia" ${rutina.tipoEntrenamiento == 'Hipertrofia' ? 'selected' : ''}>Hipertrofia</option>
            <option value="Cardio y Quema de Grasa" ${rutina.tipoEntrenamiento == 'Cardio y Quema de Grasa' ? 'selected' : ''}>Cardio y Quema de Grasa</option>
            <option value="Flexibilidad y Movilidad" ${rutina.tipoEntrenamiento == 'Flexibilidad y Movilidad' ? 'selected' : ''}>Flexibilidad y Movilidad</option>
            <option value="Entrenamiento Funcional" ${rutina.tipoEntrenamiento == 'Entrenamiento Funcional' ? 'selected' : ''}>Entrenamiento Funcional</option>
            <option value="Rehabilitación" ${rutina.tipoEntrenamiento == 'Rehabilitación' ? 'selected' : ''}>Rehabilitación</option>
            <option value="CrossFit / HIIT" ${rutina.tipoEntrenamiento == 'CrossFit / HIIT' ? 'selected' : ''}>CrossFit / HIIT</option>
          </select>
        </div>

        <div class="mb-3">
          <label for="observaciones" class="form-label">Observaciones:</label>
          <textarea class="form-control" id="observaciones"
                    name="observaciones" rows="3">${rutina.observaciones}</textarea>
        </div>

        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
          <a href="${pageContext.request.contextPath}/RutinaServlet"
             class="btn btn-danger" style="background-color: #fd4242">
            <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
          <button type="submit" class="btn btn-primary">Guardar Cambios</button>
        </div>
      </form>
    </div>
  </div>
</div>

<jsp:include page="../footer.jsp"/>