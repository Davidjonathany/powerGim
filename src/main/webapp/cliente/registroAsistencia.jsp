<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="card card-primary">
                <div class="card-header">
                    <h3 class="card-title">Registrar Mi Asistencia</h3>
                </div>

                <form action="${pageContext.request.contextPath}/registrar-asistencia" method="post">
                    <div class="card-body">
                        <input type="hidden" name="cedula" value="${sessionScope.usuario.cedula}">

                        <div class="form-group">
                            <label>Tipo de Asistencia</label>
                            <select class="form-control" name="tipoAsistencia" required>
                                <option value="Entrada">Entrada</option>
                                <option value="Salida">Salida</option>
                                <option value="Clase">Clase Grupal</option>
                                <option value="Uso Libre">Uso Libre</option>
                            </select>
                        </div>
                    </div>

                    <div class="card-footer">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-check-circle"></i> Registrar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>
