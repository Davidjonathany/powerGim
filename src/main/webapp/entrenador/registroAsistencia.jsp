<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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

                <form action="${pageContext.request.contextPath}/registrar-asistencia" method="post">
                    <div class="card-body">
                        <div class="form-group">
                            <label>Seleccionar Cliente</label>
                            <select class="form-control" name="cedula" required>
                                <c:forEach items="${clientes}" var="cliente">
                                    <option value="${cliente.cedula}">
                                            ${cliente.cedula} - ${cliente.nombre} ${cliente.apellido}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Tipo de Actividad</label>
                            <select class="form-control" name="tipoAsistencia" required>
                                <option value="Entrenamiento Personal">Entrenamiento Personal</option>
                                <option value="Entrenamiento Grupal">Entrenamiento Grupal</option>
                                <option value="Evaluación Física">Evaluación Física</option>
                            </select>
                        </div>
                    </div>

                    <div class="card-footer">
                        <button type="submit" class="btn btn-success">
                            <i class="fas fa-dumbbell"></i> Registrar Entrenamiento
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>