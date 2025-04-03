<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
  <h1>${modo eq 'editar' ? 'Editar Ejercicio' : 'Nuevo Ejercicio'}</h1>

  <form action="${pageContext.request.contextPath}/ejercicios/gestion" method="post">
    <c:if test="${modo eq 'editar'}">
      <input type="hidden" name="id" value="${ejercicio.id}">
    </c:if>

    <div class="row mb-3">
      <div class="col-md-6">
        <label for="idRutina" class="form-label">Rutina</label>
        <select class="form-select" id="idRutina" name="idRutina" required>
          <option value="">Seleccione una rutina</option>
          <c:forEach items="${rutinas}" var="rutina">
            <option value="${rutina.idRutina}"
              ${modo eq 'editar' && ejercicio.idRutina == rutina.idRutina ? 'selected' : ''}>
                ${rutina.tipoEntrenamiento} - Cliente: ${rutina.clienteNombre} ${rutina.clienteApellido}
              <c:if test="${not empty rutina.entrenadorNombre}">
                (Entrenador: ${rutina.entrenadorNombre} ${rutina.entrenadorApellido})
              </c:if>
            </option>
          </c:forEach>
        </select>
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-6">
        <label for="nombre" class="form-label">Nombre del Ejercicio</label>
        <input type="text" class="form-control" id="nombre" name="nombre"
               value="${modo eq 'editar' ? ejercicio.nombre : ''}" required>
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-3">
        <label for="series" class="form-label">Series</label>
        <input type="number" class="form-control" id="series" name="series"
               value="${modo eq 'editar' ? ejercicio.series : ''}" required min="1">
      </div>
      <div class="col-md-3">
        <label for="repeticiones" class="form-label">Repeticiones</label>
        <input type="number" class="form-control" id="repeticiones" name="repeticiones"
               value="${modo eq 'editar' ? ejercicio.repeticiones : ''}" required min="1">
      </div>
    </div>

    <div class="row mb-3">
      <div class="col-md-3">
        <label for="tiempo" class="form-label">Tiempo (segundos)</label>
        <input type="number" class="form-control" id="tiempo" name="tiempo"
               value="${modo eq 'editar' ? ejercicio.tiempo : ''}" min="0">
      </div>
      <div class="col-md-3">
        <label for="descanso" class="form-label">Descanso (segundos)</label>
        <input type="number" class="form-control" id="descanso" name="descanso"
               value="${modo eq 'editar' ? ejercicio.descanso : ''}" min="0">
      </div>
    </div>

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="${pageContext.request.contextPath}/ejercicios" class="btn btn-danger" style="background-color: #fd4242">
      <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
  </form>
</div>

<jsp:include page="../footer.jsp"/>
