<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title"><strong>Agregar Asistencia</strong></h1>
                    </div>

                    <div class="panel-body">
                        <form action="${pageContext.request.contextPath}/AsistenciaFormServlet" method="POST">
                            <div class="form-group">
                                <label for="cedula">Cédula del Cliente</label>
                                <input type="text" class="form-control" id="cedula" name="cedula" placeholder="Cédula del Cliente" required>
                            </div>

                            <div class="form-group">
                                <label for="fechaAsistencia">Fecha de Asistencia</label>
                                <input type="date" class="form-control" id="fechaAsistencia" name="fechaAsistencia" required>
                            </div>

                            <input type="hidden" id="idCliente" name="idCliente"> <!-- Campo oculto para el ID -->

                            <button type="submit" class="btn btn-success">Guardar</button>
                            <a href="${pageContext.request.contextPath}/AsistenciaVistaServlet" class="btn btn-default">Cancelar</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"></jsp:include>

