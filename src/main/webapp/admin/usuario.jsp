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
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title"><strong>Usuarios</strong>
                            <a href="${pageContext.request.contextPath}/UsuarioFormServlet" class="btn btn-success">
                            <i class="fa fa-plus-circle"></i> Agregar</a>
                        </h1>
                    </div>

                    <div class="panel-body table-responsive">
                        <table class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Usuario</th>
                                <th>Rol</th>
                                <th>Correo</th>
                                <th>Teléfono</th>
                                <th>Cédula</th>
                                <th>Dirección</th>
                                <th>Acciones</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr>
                                    <td>${usuario.id}</td>
                                    <td>${usuario.nombre}</td>
                                    <td>${usuario.apellido}</td>
                                    <td>${usuario.usuario}</td>
                                    <td>${usuario.rol}</td>
                                    <td>${usuario.correo}</td>
                                    <td>${usuario.telefono}</td>
                                    <td>${usuario.cedula}</td>
                                    <td>${usuario.direccion}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/UsuarioActualizar?id=${usuario.id}" class="btn btn-success">
                                            <i class="fa fa-pencil-square-o"></i> Editar
                                        </a>
                                        <a href="${pageContext.request.contextPath}/EliminarUsuario?id=${usuario.id}" class="btn btn-danger">
                                            <i class="fa fa-trash"></i> Eliminar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty usuarios}">
                                <tr>
                                    <td colspan="10" class="text-center">No hay usuarios disponibles.</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
