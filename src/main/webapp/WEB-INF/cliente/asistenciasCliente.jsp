<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 29/3/2025
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%--
  Vista de asistencias personales para clientes
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid py-4">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h3 class="card-title">
                        <i class="fas fa-clipboard-list mr-2"></i>Mis Asistencias
                    </h3>
                    <div class="ms-auto">
                        <a href="${pageContext.request.contextPath}/registrar-asistencia" class="btn btn-success">
                            <i class="fas fa-plus"></i> Agregar
                        </a>
                    </div>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty asistencias}">
                            <div class="alert alert-info">
                                No tienes asistencias registradas.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead class="table-dark">
                                    <tr>
                                        <th>Fecha</th>
                                        <th>Tipo</th>
                                        <th>Registrado por</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${asistencias}" var="asistencia">
                                        <tr>
                                            <td>${asistencia.fechaAsistencia}</td>
                                            <td>
                                                    <span class="badge bg-primary">
                                                            ${asistencia.tipoAsistencia}
                                                    </span>
                                            </td>
                                            <td>
                                                    ${asistencia.nombreRegistrador}
                                                (${asistencia.rolRegistrador})
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>
