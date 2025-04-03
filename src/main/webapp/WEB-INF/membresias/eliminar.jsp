<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1>Eliminar Membresía</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="card mb-4">
        <div class="card-header bg-danger text-white">
            <h4><i class="fas fa-exclamation-triangle"></i> Confirmar Eliminación</h4>
        </div>
        <div class="card-body">
            <h5 class="card-title">Detalles de la Membresía</h5>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Cliente:</label>
                    <div class="p-2 bg-light rounded">
                        <strong>${membresia.clienteNombre} ${membresia.clienteApellido}</strong>
                        <div>Cédula: ${membresia.clienteCedula}</div>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Tipo de Membresía:</label>
                    <div class="p-2 bg-light rounded">${membresia.tipo}</div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Fecha de Inicio:</label>
                    <div class="p-2 bg-light rounded">
                        <fmt:formatDate value="${membresia.fechaInicio}" pattern="dd/MM/yyyy"/>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Fecha de Vencimiento:</label>
                    <div class="p-2 bg-light rounded">
                        <fmt:formatDate value="${membresia.fechaVencimiento}" pattern="dd/MM/yyyy"/>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Días Restantes:</label>
                    <div class="p-2 bg-light rounded">${membresia.diasRestantes}</div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Estado:</label>
                    <div class="p-2 bg-light rounded">${membresia.estado}</div>
                </div>
            </div>

            <!-- Advertencia especial para membresías activas -->
            <c:if test="${membresia.estado eq 'Activa'}">
                <div class="alert alert-danger mt-3">
                    <i class="fas fa-exclamation-triangle"></i>
                    ADVERTENCIA: Esta membresía está ACTIVA. Al eliminarla, el cliente perderá acceso inmediatamente.
                </div>
            </c:if>

            <div class="alert alert-warning mt-4">
                <h5><i class="fas fa-exclamation-circle"></i> ¿Está seguro que desea eliminar esta membresía?</h5>
                <p class="mb-0">Esta acción no se puede deshacer.</p>
            </div>

            <form action="${pageContext.request.contextPath}/membresias/eliminar" method="post" onsubmit="return confirmarEliminacion();">
                <input type="hidden" name="id" value="${membresia.id}">

                <button type="submit" class="btn btn-danger me-2">
                    <i class="fas fa-trash-alt"></i> Eliminar Definitivamente
                </button>
                <a href="${pageContext.request.contextPath}/membresias" class="btn btn-danger" style="background-color: #fd4242">
                    <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
            </form>
        </div>
    </div>
</div>

<script>
    function confirmarEliminacion() {
        return confirm('¿Confirmas que deseas eliminar esta membresía definitivamente?');
    }
</script>

<jsp:include page="../footer.jsp"/>