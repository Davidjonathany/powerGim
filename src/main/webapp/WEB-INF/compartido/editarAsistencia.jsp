<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
  <section class="content">
    <div class="container-fluid">
      <div class="card ${sessionScope.usuario.rol eq 'Administrador' ? 'card-primary' :
                         sessionScope.usuario.rol eq 'Entrenador' ? 'card-info' : 'card-success'}">
        <div class="card-header">
          <h3 class="card-title">Editar Asistencia</h3>
        </div>

        <c:if test="${not empty error}">
          <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/actualizar-asistencia" method="post">
          <input type="hidden" name="idAsistencia" value="${asistencia.idAsistencia}">

          <div class="card-body">
            <!-- Sección Usuario -->
            <div class="form-group">
              <label>Usuario Asistente</label>
              <c:choose>

                <c:when test="${sessionScope.usuario.rol eq 'Administrador'}">
                  <select class="form-control" name="idUsuario" required>
                    <option value="">Seleccione un usuario</option>
                    <c:forEach items="${usuarios}" var="usuario">
                      <c:if test="${not empty usuario.cedula and not empty usuario.nombre}">
                        <option value="${usuario.id}" ${usuario.id eq asistencia.idUsuario ? 'selected' : ''}>
                            ${usuario.cedula} - ${usuario.nombre} ${usuario.apellido}
                        </option>
                      </c:if>
                    </c:forEach>
                  </select>
                </c:when>

                <c:otherwise>
                  <input type="text" class="form-control"
                         value="${asistencia.nombre} ${asistencia.apellido} (${asistencia.cedula})"
                         readonly>
                  <input type="hidden" name="idUsuario" value="${asistencia.idUsuario}">
                </c:otherwise>
              </c:choose>
            </div>

            <!-- Campos editables -->
            <div class="form-group">
              <label>Tipo de Asistencia</label>
              <select class="form-control" name="tipoAsistencia" required>
                <option value="Entrada" ${asistencia.tipoAsistencia eq 'Entrada' ? 'selected' : ''}>Entrada</option>
                <option value="Salida" ${asistencia.tipoAsistencia eq 'Salida' ? 'selected' : ''}>Salida</option>
                <c:if test="${sessionScope.usuario.rol ne 'Cliente'}">
                  <option value="Clase Grupal" ${asistencia.tipoAsistencia eq 'Clase Grupal' ? 'selected' : ''}>Clase Grupal</option>
                  <option value="Entrenamiento Personal" ${asistencia.tipoAsistencia eq 'Entrenamiento Personal' ? 'selected' : ''}>Entrenamiento Personal</option>
                  <option value="Evaluación Física" ${asistencia.tipoAsistencia eq 'Evaluación Física' ? 'selected' : ''}>Evaluación Física</option>
                  <option value="Uso Libre" ${asistencia.tipoAsistencia eq 'Uso Libre' ? 'selected' : ''}>Uso Libre</option>
                </c:if>
              </select>
            </div>

            <div class="form-group">
              <label>Fecha y Hora</label>
              <input type="datetime-local" class="form-control" name="fechaAsistencia"
                     value="<fmt:formatDate value="${asistencia.fechaAsistencia}" pattern="yyyy-MM-dd'T'HH:mm" />"
                     <c:if test="${sessionScope.usuario.rol eq 'Cliente'}">readonly</c:if>>
          </div>

          <div class="card-footer">
            <button type="submit" class="btn ${sessionScope.usuario.rol eq 'Administrador' ? 'btn-primary' :
                                          sessionScope.usuario.rol eq 'Entrenador' ? 'btn-info' : 'btn-success'}">
              <i class="fas fa-save"></i> Guardar Cambios
            </button>
              <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet" class="btn btn-danger" style="background-color: #fd4242">
                <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
              </a>
          </div>
        </form>
      </div>
    </div>
  </section>
</div>

<jsp:include page="../footer.jsp"/>