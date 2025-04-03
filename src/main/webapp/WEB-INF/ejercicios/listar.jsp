<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 31/3/2025
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container mt-4">
    <div class="card shadow-sm">
        <!-- Card Header -->
        <div class="card-header bg-primary text-white">
            <h3 class="card-title mb-0">
                <i class="fas fa-dumbbell me-2"></i>
                ${esAdministrador ? 'Gestión de Ejercicios' : (esEntrenador ? 'Ejercicios de Mis Clientes' : 'Mis Ejercicios')}
            </h3>
        </div>

        <!-- Card Body -->
        <div class="card-body">
            <!-- Alert Messages -->
            <c:if test="${not empty param.success}">
                <div class="alert alert-success alert-dismissible fade show">
                    <i class="fas fa-check-circle me-2"></i> ${param.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>
            <c:if test="${not empty param.error}">
                <div class="alert alert-danger alert-dismissible fade show">
                    <i class="fas fa-exclamation-circle me-2"></i> ${param.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <!-- Action Buttons and Filters -->
            <div class="row mb-4 g-3">
                <div class="col-md-6">
                    <c:if test="${esAdministrador || esEntrenador}">
                        <a href="${pageContext.request.contextPath}/ejercicios/gestion" class="btn btn-success btn-lg">
                            <i class="fas fa-plus-circle me-2"></i> Nuevo Ejercicio
                        </a>
                    </c:if>
                </div>

                <div class="col-md-6">
                    <c:if test="${esAdministrador || esEntrenador}">
                        <form method="get" action="${pageContext.request.contextPath}/ejercicios" class="row g-2">
                            <div class="col-md-5">
                                <div class="input-group">
                                    <span class="input-group-text bg-light">
                                        <i class="fas fa-user"></i>
                                    </span>
                                    <input type="text" id="nombre" name="nombre" class="form-control"
                                           value="${param.nombre}" placeholder="Nombre/Apellido">
                                </div>
                            </div>
                            <c:if test="${esAdministrador}">
                                <div class="col-md-4">
                                    <div class="input-group">
                                        <span class="input-group-text bg-light">
                                            <i class="fas fa-id-card"></i>
                                        </span>
                                        <input type="text" id="cedula" name="cedula" class="form-control"
                                               value="${param.cedula}" placeholder="Cédula">
                                    </div>
                                </div>
                            </c:if>
                            <div class="col-md-3">
                                <button type="submit" class="btn btn-primary w-100">
                                    <i class="fas fa-search me-2"></i> Buscar
                                </button>
                            </div>
                            <c:if test="${not empty param.nombre || not empty param.cedula}">
                                <div class="col-12 mt-2">
                                    <a href="${pageContext.request.contextPath}/ejercicios" class="btn btn-outline-danger btn-sm">
                                        <i class="fas fa-times me-2"></i> Limpiar filtros
                                    </a>
                                </div>
                            </c:if>
                        </form>
                    </c:if>
                </div>
            </div>

            <!-- Empty State -->
            <c:if test="${empty ejercicios}">
                <div class="alert alert-warning text-center py-4">
                    <i class="fas fa-dumbbell fa-3x mb-3 text-muted"></i>
                    <h4>No se encontraron ejercicios</h4>
                    <c:if test="${not empty param.cedula}">
                        <p class="mb-0">para la cédula: <strong>${param.cedula}</strong></p>
                    </c:if>
                    <c:if test="${not empty param.nombre}">
                        <p class="mb-0">para el nombre/apellido: <strong>${param.nombre}</strong></p>
                    </c:if>
                </div>
            </c:if>

            <!-- Exercises Table -->
            <c:if test="${not empty ejercicios}">
                <div class="table-responsive">
                    <table class="table table-striped table-hover table-bordered">
                        <thead class="table-dark">
                        <tr>
                            <th class="text-center">ID</th>
                            <c:if test="${esAdministrador || esEntrenador}">
                                <th>Cliente</th>
                                <c:if test="${esAdministrador}">
                                    <th>Cédula</th>
                                </c:if>
                            </c:if>
                            <th>Ejercicio</th>
                            <th class="text-center">Series</th>
                            <th class="text-center">Reps</th>
                            <th class="text-center">Tiempo</th>
                            <th class="text-center">Descanso</th>
                            <c:if test="${esAdministrador || esEntrenador}">
                                <th class="text-center">Acciones</th>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${ejercicios}" var="ejercicio">
                            <tr>
                                <td class="text-center">${ejercicio.id}</td>
                                <c:if test="${esAdministrador || esEntrenador}">
                                    <td>
                                        <span class="fw-bold">${ejercicio.nombreCliente} ${ejercicio.apellidoCliente}</span>
                                    </td>
                                    <c:if test="${esAdministrador}">
                                        <td>${ejercicio.cedulaCliente}</td>
                                    </c:if>
                                </c:if>
                                <td>
                                        <span class="badge bg-primary text-white">
                                                ${ejercicio.nombreEjercicio}
                                        </span>
                                </td>
                                <td class="text-center">
                                    <span class="badge bg-info text-dark">${ejercicio.series}</span>
                                </td>
                                <td class="text-center">
                                    <span class="badge bg-warning text-dark">${ejercicio.repeticiones}</span>
                                </td>
                                <td class="text-center">
                                    <span class="badge bg-secondary text-white">${ejercicio.tiempo}s</span>
                                </td>
                                <td class="text-center">
                                    <span class="badge bg-light text-dark">${ejercicio.descanso}s</span>
                                </td>
                                <c:if test="${esAdministrador || esEntrenador}">
                                    <td class="text-center">
                                        <div class="btn-group btn-group-sm" role="group">
                                            <a href="${pageContext.request.contextPath}/ejercicios/editar?id=${ejercicio.id}"
                                               class="btn btn-outline-warning" title="Editar">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/ejercicios/eliminar?id=${ejercicio.id}"
                                               class="btn btn-outline-danger" title="Eliminar">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </div>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>