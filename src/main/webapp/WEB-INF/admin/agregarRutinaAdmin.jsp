<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 19:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1 class="text-center">Agregar Nueva Rutina</h1>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger">${param.error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/AgregarRutinaServlet" method="post">
                <div class="mb-3">
                    <label for="idCliente" class="form-label">Cliente:</label>
                    <select class="form-select" id="idCliente" name="idCliente" required>
                        <option value="">Seleccione cliente</option>
                        <c:forEach items="${clientes}" var="cliente">
                            <option value="${cliente.id}">${cliente.nombre} ${cliente.apellido}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${empty clientes}">
                        <div class="text-danger mt-1">No se encontraron clientes</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="idEntrenador" class="form-label">Entrenador:</label>
                    <select class="form-select" id="idEntrenador" name="idEntrenador" required>
                        <option value="">Seleccione entrenador</option>
                        <c:forEach items="${entrenadores}" var="entrenador">
                            <option value="${entrenador.id}">${entrenador.nombre} ${entrenador.apellido}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${empty entrenadores}">
                        <div class="text-danger mt-1">No se encontraron entrenadores</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label for="tipoEntrenamiento" class="form-label">Tipo de Entrenamiento:</label>
                    <select class="form-select" id="tipoEntrenamiento" name="tipoEntrenamiento" required>
                        <option value="" disabled selected>Seleccione un tipo</option>
                        <option value="Fuerza">Fuerza</option>
                        <option value="Resistencia">Resistencia</option>
                        <option value="Hipertrofia">Hipertrofia</option>
                        <option value="Cardio y Quema de Grasa">Cardio y Quema de Grasa</option>
                        <option value="Flexibilidad y Movilidad">Flexibilidad y Movilidad</option>
                        <option value="Entrenamiento Funcional">Entrenamiento Funcional</option>
                        <option value="Rehabilitación">Rehabilitación</option>
                        <option value="CrossFit / HIIT">CrossFit / HIIT</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="observaciones" class="form-label">Observaciones:</label>
                    <textarea class="form-control" id="observaciones" name="observaciones" rows="3"></textarea>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <a href="${pageContext.request.contextPath}/RutinaServlet" class="btn btn-danger" style="background-color: #fd4242">
                        <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
                    <button type="submit" class="btn btn-primary">Guardar Rutina</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>