<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">Registrar Asistencia</h3>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty warning}">
                    <div class="alert alert-warning">${warning}</div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty usuarios}">
                        <form action="${pageContext.request.contextPath}/registrar-asistencia" method="post">
                            <div class="card-body">
                                <div class="form-group">
                                    <label for="selectUsuario">Seleccionar Usuario</label>
                                    <select class="form-control" id="selectUsuario" name="cedula" required>
                                        <option value="">-- Seleccione un usuario --</option>
                                        <c:forEach items="${usuarios}" var="usuario">
                                            <option value="${usuario.cedula}">
                                                    ${usuario.cedula} - ${not empty usuario.nombre ? usuario.nombre : 'N/A'}
                                                    ${not empty usuario.apellido ? usuario.apellido : 'N/A'}
                                                (${usuario.rol})
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="selectTipo">Tipo de Asistencia</label>
                                    <select class="form-control" id="selectTipo" name="tipoAsistencia" required>
                                        <option value="">-- Seleccione tipo --</option>
                                        <option value="Entrada">Entrada</option>
                                        <option value="Salida">Salida</option>
                                        <option value="Clase Grupal">Clase Grupal</option>
                                        <option value="Entrenamiento Personal">Entrenamiento Personal</option>
                                        <option value="Evaluación Física">Evaluación Física</option>
                                        <option value="Uso Libre">Uso Libre</option>
                                    </select>
                                </div>
                            </div>

                            <div class="card-footer">
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Registrar Asistencia
                                </button>
                                <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet" class="btn btn-danger" style="background-color: #fd4242">
                                    <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
                                </a>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div class="card-body">
                            <div class="alert alert-info">
                                No hay usuarios registrados en el sistema.
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>
