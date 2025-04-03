<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1>${esAdministrador ? 'Gestión de Membresías' : (esEntrenador ? 'Membresías de Mis Clientes' : 'Mi Membresía')}</h1>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success">${param.success}</div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger">${param.error}</div>
    </c:if>

    <c:if test="${esAdministrador || esEntrenador}">
        <!-- Filtros de búsqueda -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <i class="fas fa-filter"></i> Filtros de Búsqueda
            </div>
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/membresias" class="row g-3">
                    <!-- Filtro por nombre y apellido (visible para Admin y Entrenador) -->
                    <div class="col-md-4">
                        <label for="nombreFilter" class="form-label">Buscar por nombre:</label>
                        <input type="text" class="form-control" id="nombreFilter" name="nombre"
                               placeholder="Ingrese nombre" value="${param.nombre}">
                    </div>
                    <div class="col-md-4">
                        <label for="apellidoFilter" class="form-label">Buscar por apellido:</label>
                        <input type="text" class="form-control" id="apellidoFilter" name="apellido"
                               placeholder="Ingrese apellido" value="${param.apellido}">
                    </div>

                    <!-- Filtro por cédula (solo visible para Administrador) -->
                    <c:if test="${esAdministrador}">
                        <div class="col-md-4">
                            <label for="cedulaFilter" class="form-label">Buscar por cédula:</label>
                            <input type="text" class="form-control" id="cedulaFilter" name="cedula"
                                   placeholder="Ingrese cédula" value="${param.cedula}">
                        </div>
                    </c:if>

                    <div class="col-12 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="fas fa-search"></i> Buscar
                        </button>
                        <c:if test="${not empty param.nombre || not empty param.apellido || not empty param.cedula}">
                            <a href="${pageContext.request.contextPath}/membresias" class="btn btn-outline-secondary">
                                <i class="fas fa-times"></i> Limpiar
                            </a>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </c:if>

    <div class="d-flex justify-content-between align-items-center mb-3">
        <c:if test="${esAdministrador}">
            <a href="${pageContext.request.contextPath}/membresias/crear" class="btn btn-primary">
                <i class="fas fa-plus"></i> Nueva Membresía
            </a>
        </c:if>

        <c:if test="${not empty param.cedula || not empty param.nombre || not empty param.apellido}">
            <div class="alert alert-info mb-0">
                <c:choose>
                    <c:when test="${not empty param.cedula}">
                        Mostrando resultados para cédula: <strong>${param.cedula}</strong>
                    </c:when>
                    <c:when test="${not empty param.nombre && not empty param.apellido}">
                        Mostrando resultados para: <strong>${param.nombre} ${param.apellido}</strong>
                    </c:when>
                    <c:when test="${not empty param.nombre}">
                        Mostrando resultados para nombre: <strong>${param.nombre}</strong>
                    </c:when>
                    <c:when test="${not empty param.apellido}">
                        Mostrando resultados para apellido: <strong>${param.apellido}</strong>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
    </div>

    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead class="table-dark">
            <tr>
                <c:if test="${esAdministrador}">
                    <th>ID</th>
                </c:if>
                <c:if test="${esAdministrador || esEntrenador}">
                    <th>Cliente</th>
                </c:if>
                <c:if test="${esAdministrador}">
                    <th>Cédula</th>
                </c:if>
                <th>Tipo</th>
                <th>Inicio</th>
                <th>Vencimiento</th>
                <th>Días Rest.</th>
                <th>Estado</th>
                <c:if test="${esAdministrador}">
                    <th>Acciones</th>
                </c:if>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty membresias}">
                    <tr>
                        <td colspan="${esAdministrador ? 8 : (esEntrenador ? 6 : 5)}" class="text-center">
                            <c:choose>
                                <c:when test="${not empty param.cedula || not empty param.nombre || not empty param.apellido}">
                                    No se encontraron membresías para los criterios de búsqueda
                                </c:when>
                                <c:otherwise>
                                    No hay membresías registradas
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${membresias}" var="membresia">
                        <tr>
                            <c:if test="${esAdministrador}">
                                <td>${membresia.id}</td>
                            </c:if>
                            <c:if test="${esAdministrador || esEntrenador}">
                                <td>${membresia.clienteNombre} ${membresia.clienteApellido}</td>
                            </c:if>
                            <c:if test="${esAdministrador}">
                                <td>${membresia.clienteCedula}</td>
                            </c:if>
                            <td>${membresia.tipo}</td>
                            <td><fmt:formatDate value="${membresia.fechaInicio}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${membresia.fechaVencimiento}" pattern="dd/MM/yyyy"/></td>
                            <td>
                                <span class="badge ${membresia.diasRestantes > 10 ? 'bg-success' : (membresia.diasRestantes > 0 ? 'bg-warning' : 'bg-danger')}">
                                        ${membresia.diasRestantes}
                                </span>
                            </td>
                            <td>
                                <span class="badge ${membresia.estado == 'Activa' ? 'bg-success' : (membresia.estado == 'Inactiva' ? 'bg-secondary' : 'bg-danger')}">
                                        ${membresia.estado}
                                </span>
                            </td>
                            <c:if test="${esAdministrador}">
                                <td>
                                    <div class="d-flex">
                                        <a href="${pageContext.request.contextPath}/membresias/actualizar?id=${membresia.id}"
                                           class="btn btn-sm btn-warning me-2" title="Editar">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/membresias/eliminar?id=${membresia.id}"
                                           class="btn btn-sm btn-danger"
                                           title="Eliminar">
                                            <i class="fas fa-trash-alt"></i>
                                        </a>
                                    </div>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../footer.jsp"/>