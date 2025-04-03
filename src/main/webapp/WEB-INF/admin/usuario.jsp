<%--
  Created by IntelliJ IDEA.
  User: David
  Date: 27-03-2025
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.modelos.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-wrapper">
    <section class="content">
        <div class="row mb-4">
            <div class="col-12">
                <div class="card shadow-sm">
                    <div class="card-header bg-white border-bottom-0">
                        <div class="d-flex justify-content-between align-items-center">
                            <h3 class="card-title mb-0">
                                <i class="fas fa-users me-2 text-primary"></i>
                                <strong>Gestión de Usuarios</strong>
                            </h3>
                            <a href="${pageContext.request.contextPath}/UsuarioFormServlet" class="btn btn-success btn-lg">
                                <i class="fas fa-plus-circle me-2"></i> Agregar Usuario
                            </a>
                        </div>
                    </div>

                    <!-- Formulario de búsqueda -->
                    <div class="card-body bg-light">
                        <form action="${pageContext.request.contextPath}/UsuarioServlet" method="get" class="row g-3">
                            <div class="col-md-8">
                                <div class="input-group input-group-lg">
                                    <input type="text" name="searchTerm" class="form-control border-primary"
                                           placeholder="Buscar por nombre, apellido o cédula" value="${param.searchTerm}">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search me-2"></i> Buscar
                                    </button>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <a href="${pageContext.request.contextPath}/UsuarioServlet" class="btn btn-outline-secondary btn-lg w-100">
                                    <i class="fas fa-sync-alt me-2"></i> Limpiar
                                </a>
                            </div>
                        </form>
                    </div>

                    <div class="card-body p-0">
                        <div class="table-responsive">
                            <table class="table table-striped table-hover table-bordered mb-0">
                                <thead class="table-dark">
                                <tr>
                                    <th class="text-center" style="width: 5%">ID</th>
                                    <th style="width: 10%">Nombre</th>
                                    <th style="width: 10%">Apellido</th>
                                    <th style="width: 10%">Usuario</th>
                                    <th style="width: 10%">Rol</th>
                                    <th style="width: 15%">Correo</th>
                                    <th style="width: 10%">Teléfono</th>
                                    <th style="width: 10%">Cédula</th>
                                    <th style="width: 15%">Dirección</th>
                                    <th class="text-center" style="width: 15%">Acciones</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="usuario" items="${usuarios}">
                                    <tr>
                                        <td class="text-center">${usuario.id}</td>
                                        <td>${usuario.nombre}</td>
                                        <td>${usuario.apellido}</td>
                                        <td>${usuario.usuario}</td>
                                        <td>
                                            <span class="badge
                                                ${usuario.rol == 'Administrador' ? 'bg-danger' :
                                                  usuario.rol == 'Entrenador' ? 'bg-warning text-dark' : 'bg-primary'}">
                                                    ${usuario.rol}
                                            </span>
                                        </td>
                                        <td><a href="mailto:${usuario.correo}" class="text-decoration-none">${usuario.correo}</a></td>
                                        <td>${usuario.telefono}</td>
                                        <td>${usuario.cedula}</td>
                                        <td>${usuario.direccion}</td>
                                        <td class="text-center">
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/UsuarioActualizar?id=${usuario.id}"
                                                   class="btn btn-sm btn-outline-primary" title="Editar">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/EliminarUsuario?id=${usuario.id}"
                                                   class="btn btn-sm btn-outline-danger" title="Eliminar"
                                                   onclick="return confirm('¿Estás seguro de eliminar este usuario?');">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty usuarios}">
                                    <tr>
                                        <td colspan="10" class="text-center py-4">
                                            <i class="fas fa-user-slash fa-2x text-muted mb-2"></i>
                                            <h5 class="text-muted">No hay usuarios registrados</h5>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"></jsp:include>