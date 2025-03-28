<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.example.modelos.AsistenciaVista" %>

<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title"><strong>Asistencias</strong>

                            <%-- Verificar si el usuario tiene permisos para agregar --%>
                            <c:set var="usuario" value="${sessionScope.usuario}" />
                            <c:if test="${usuario != null}">
                                <a href="${pageContext.request.contextPath}/AsistenciaFormServlet" class="btn btn-success">
                                    <i class="fa fa-plus-circle"></i> Agregar</a>
                            </c:if>
                        </h1>
                    </div>

                    <div class="panel-body table-responsive">
                        <table class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Cliente</th>
                                <th>Fecha de Asistencia</th>
                                <!-- Aquí no se usan "Estado de Asistencia" ni "Observaciones" ya que no están en la vista -->
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="asistencia" items="${asistencias}">
                                <tr>
                                    <td>${asistencia.idAsistencia}</td>
                                    <td>${asistencia.nombreCliente} ${asistencia.apellidoCliente}</td> <!-- Mostrar nombre y apellido del cliente -->
                                    <td>${asistencia.fechaAsistencia}</td>
                                    <!-- Si deseas agregar "Estado de Asistencia" u "Observaciones", debes agregar esos campos en la vista o adaptarlos aquí -->
                                </tr>
                            </c:forEach>

                            <c:if test="${empty asistencias}">
                                <tr>
                                    <td colspan="3" class="text-center">No hay asistencias disponibles.</td> <!-- Solo 3 columnas -->
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

