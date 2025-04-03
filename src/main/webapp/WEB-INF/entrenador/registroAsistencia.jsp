<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista de registro de asistencias para entrenadores
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="card card-success">
                <div class="card-header">
                    <h3 class="card-title">Registrar Asistencia de Cliente</h3>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty warning}">
                    <div class="alert alert-warning">${warning}</div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty clientes}">
                        <form action="${pageContext.request.contextPath}/registrar-asistencia" method="post">
                            <div class="card-body">
                                <div class="form-group">
                                    <label>Seleccionar Cliente</label>
                                    <select class="form-control" name="cedula" required>
                                        <option value="">-- Seleccione un cliente --</option>
                                        <c:forEach items="${clientes}" var="cliente">
                                            <option value="${cliente.cedula}">
                                                    ${cliente.nombre} ${cliente.apellido}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Tipo de Actividad</label>
                                    <select class="form-control" name="tipoAsistencia" required>
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
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-dumbbell"></i> Registrar Entrenamiento
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
                                No tienes clientes asignados para registrar asistencias.
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>