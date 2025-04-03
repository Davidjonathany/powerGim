<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1>Renovar Membresía</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form id="formMembresia" action="${pageContext.request.contextPath}/membresias/actualizar" method="post">
        <input type="hidden" name="id" value="${membresia.id}">
        <input type="hidden" name="idCliente" value="${membresia.idCliente}">

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Cliente:</label>
                <div class="p-2 bg-light rounded">
                    <strong>${membresia.clienteNombre} ${membresia.clienteApellido}</strong>
                    <div>Cédula: ${membresia.clienteCedula}</div>
                    <input type="hidden" name="cedulaCliente" value="${membresia.clienteCedula}">
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="tipo" class="form-label">Tipo de Membresía:</label>
                <select class="form-select" id="tipo" name="tipo" onchange="calcularVencimiento()" required>
                    <option value="">Seleccione un tipo</option>
                    <option value="Mensual" ${membresia.tipo == 'Mensual' ? 'selected' : ''}>Mensual (30 días)</option>
                    <option value="Trimestral" ${membresia.tipo == 'Trimestral' ? 'selected' : ''}>Trimestral (90 días)</option>
                    <option value="Anual" ${membresia.tipo == 'Anual' ? 'selected' : ''}>Anual (365 días)</option>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="fechaInicio" class="form-label">Fecha de Inicio:</label>
                <input type="date" class="form-control" id="fechaInicio" name="fechaInicio"
                       value="<fmt:formatDate value='${membresia.fechaInicio}' pattern='yyyy-MM-dd' />"
                       onchange="calcularVencimiento()" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="fechaVencimiento" class="form-label">Fecha de Vencimiento:</label>
                <input type="date" class="form-control" id="fechaVencimiento" name="fechaVencimiento"
                       value="<fmt:formatDate value='${membresia.fechaVencimiento}' pattern='yyyy-MM-dd' />" readonly>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="diasRestantes" class="form-label">Días Restantes:</label>
                <input type="number" class="form-control" id="diasRestantes" name="diasRestantes"
                       value="${membresia.diasRestantes}" readonly>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="estado" class="form-label">Estado:</label>
                <select class="form-select" id="estado" name="estado" required>
                    <option value="Activa" ${membresia.estado == 'Activa' ? 'selected' : ''}>Activa</option>
                    <option value="Inactiva" ${membresia.estado == 'Inactiva' ? 'selected' : ''}>Inactiva</option>
                    <option value="Cancelada" ${membresia.estado == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Actualizar Membresía</button>
        <a href="${pageContext.request.contextPath}/membresias" class="btn btn-danger" style="background-color: #fd4242">
            <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
    </form>
</div>

<script>
    function calcularVencimiento() {
        const tipo = document.getElementById('tipo').value;
        const fechaInicio = document.getElementById('fechaInicio').value;
        const estadoSelect = document.getElementById('estado');

        if (!tipo || !fechaInicio) return;

        const inicio = new Date(fechaInicio);
        const vencimiento = new Date(inicio);

        switch(tipo) {
            case 'Mensual':
                vencimiento.setMonth(inicio.getMonth() + 1);
                break;
            case 'Trimestral':
                vencimiento.setMonth(inicio.getMonth() + 3);
                break;
            case 'Anual':
                vencimiento.setFullYear(inicio.getFullYear() + 1);
                break;
        }

        // Calcular días restantes desde hoy
        const hoy = new Date();
        const diffTime = vencimiento - hoy;
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        const diasRestantes = diffDays > 0 ? diffDays : 0;

        // Actualizar campos
        document.getElementById('fechaVencimiento').valueAsDate = vencimiento;
        document.getElementById('diasRestantes').value = diasRestantes;

        // Actualizar estado automáticamente según días restantes
        if (diasRestantes <= 0) {
            estadoSelect.value = 'Inactiva';
        } else if (estadoSelect.value === 'Inactiva') {
            // Solo cambiar a Activa si estaba Inactiva (para no sobreescribir Cancelada)
            estadoSelect.value = 'Activa';
        }
    }

    // Calcular al cargar la página
    document.addEventListener('DOMContentLoaded', function() {
        calcularVencimiento();
    });
</script>

<jsp:include page="../footer.jsp"/>
