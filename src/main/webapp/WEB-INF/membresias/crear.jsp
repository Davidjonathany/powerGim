<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1>Nueva Membresía</h1>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form id="formMembresia" action="${pageContext.request.contextPath}/membresias/crear" method="post">
        <div class="row mb-3">
            <div class="col-md-6">
                <label for="cedulaCliente" class="form-label">Cédula del Cliente:</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="cedulaCliente" name="cedulaCliente" required>
                    <button type="button" class="btn btn-primary" onclick="verificarCliente()">
                        <i class="fas fa-search"></i> Verificar
                    </button>
                </div>
                <div id="resultadoVerificacion" class="mt-2"></div>
                <input type="hidden" id="idCliente" name="idCliente">
                <input type="hidden" id="accion" name="accion" value="crear">
                <input type="hidden" id="idMembresia" name="idMembresia">
                <div id="infoCliente" class="mt-2 p-2 bg-light rounded" style="display:none;">
                    <strong>Cliente seleccionado:</strong>
                    <span id="nombreCliente"></span>
                </div>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="tipo" class="form-label">Tipo de Membresía:</label>
                <select class="form-select" id="tipo" name="tipo" onchange="calcularVencimiento()" required>
                    <option value="">Seleccione un tipo</option>
                    <option value="Mensual">Mensual (30 días)</option>
                    <option value="Trimestral">Trimestral (90 días)</option>
                    <option value="Anual">Anual (365 días)</option>
                </select>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="fechaInicio" class="form-label">Fecha de Inicio:</label>
                <input type="date" class="form-control" id="fechaInicio" name="fechaInicio"
                       onchange="calcularVencimiento()" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="fechaVencimiento" class="form-label">Fecha de Vencimiento:</label>
                <input type="date" class="form-control" id="fechaVencimiento" name="fechaVencimiento" readonly>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label for="diasRestantes" class="form-label">Días Restantes:</label>
                <input type="number" class="form-control" id="diasRestantes" name="diasRestantes" readonly>
            </div>
        </div>

        <button type="submit" class="btn btn-primary" id="btnSubmit" disabled>Guardar Membresía</button>
        <a href="${pageContext.request.contextPath}/membresias" class="btn btn-danger" style="background-color: #fd4242">
            <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
    </form>
</div>

<script>
    function verificarCliente() {
        const cedula = document.getElementById('cedulaCliente').value.trim();
        if (cedula.length < 3) {
            alert('Ingrese una cédula válida');
            return;
        }

        // Construir la URL sin usar EL para encodeURIComponent
        const url = '${pageContext.request.contextPath}/membresias/verificar-cliente?cedula=' + encodeURIComponent(cedula);

        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error('Error en la verificación');
                return response.json();
            })
            .then(data => {
                const resultadoDiv = document.getElementById('resultadoVerificacion');
                resultadoDiv.innerHTML = '';
                const btnSubmit = document.getElementById('btnSubmit');
                btnSubmit.disabled = true;

                if (data.error) {
                    resultadoDiv.innerHTML = `<div class="alert alert-danger">${data.error}</div>`;
                    return;
                }

                if (data.existe) {
                    document.getElementById('idCliente').value = data.idCliente;

                    // Mostrar información del cliente
                    document.getElementById('nombreCliente').textContent = `Cliente ID: ${data.idCliente}`;
                    document.getElementById('infoCliente').style.display = 'block';

                    if (data.tieneMembresia) {
                        if (data.membresiaActiva) {
                            resultadoDiv.innerHTML = `
                                <div class="alert alert-warning">
                                    El cliente ya tiene una membresía activa. No se puede crear otra.
                                </div>`;
                        } else {
                            resultadoDiv.innerHTML = `
                                <div class="alert alert-info">
                                    El cliente tiene una membresía inactiva. ¿Desea renovarla?
                                    <button type="button" class="btn btn-sm btn-success ms-2"
                                        onclick="prepararRenovacion(${data.idMembresia})">
                                        Renovar Membresía
                                    </button>
                                </div>`;
                        }
                    } else {
                        resultadoDiv.innerHTML = `<div class="alert alert-success">Cliente encontrado. Puede crear nueva membresía.</div>`;
                        btnSubmit.disabled = false;
                    }
                } else {
                    resultadoDiv.innerHTML = `<div class="alert alert-danger">Cliente no encontrado. Verifique la cédula.</div>`;
                }
            })
            .catch(error => {
                console.error('Error:', error);
                document.getElementById('resultadoVerificacion').innerHTML =
                    '<div class="alert alert-danger">Error al verificar cliente: ' + error.message + '</div>';
            });
    }

    function prepararRenovacion(idMembresia) {
        document.getElementById('idMembresia').value = idMembresia;
        document.getElementById('accion').value = 'renovar';
        document.getElementById('btnSubmit').disabled = false;
        document.getElementById('resultadoVerificacion').innerHTML =
            '<div class="alert alert-success">Preparado para renovar membresía existente.</div>';
    }

    function calcularVencimiento() {
        const tipo = document.getElementById('tipo').value;
        const fechaInicio = document.getElementById('fechaInicio').value;

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

        // Actualizar campos
        document.getElementById('fechaVencimiento').valueAsDate = vencimiento;
        document.getElementById('diasRestantes').value = diffDays > 0 ? diffDays : 0;
    }

    // Validación antes de enviar el formulario
    document.getElementById('formMembresia').addEventListener('submit', function(e) {
        if (!document.getElementById('idCliente').value) {
            e.preventDefault();
            alert('Debe verificar un cliente primero');
            return false;
        }

        const tipo = document.getElementById('tipo').value;
        if (!tipo) {
            e.preventDefault();
            alert('Seleccione un tipo de membresía');
            return false;
        }

        const fechaInicio = document.getElementById('fechaInicio').value;
        if (!fechaInicio) {
            e.preventDefault();
            alert('Seleccione una fecha de inicio');
            return false;
        }
    });
</script>

<jsp:include page="../footer.jsp"/>