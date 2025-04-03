<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="container">
    <h1 class="text-center">Confirmar Eliminación</h1>

    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card border-danger">
                <div class="card-header bg-danger text-white">
                    <h4 class="card-title">¿Está seguro de eliminar esta rutina?</h4>
                </div>
                <div class="card-body">
                    <div class="mb-3 row">
                        <label class="col-sm-3 col-form-label">ID Rutina:</label>
                        <div class="col-sm-9">
                            <input type="text" readonly class="form-control-plaintext" value="${rutina.idRutina}">
                        </div>
                    </div>

                    <div class="mb-3 row">
                        <label class="col-sm-3 col-form-label">Cliente:</label>
                        <div class="col-sm-9">
                            <input type="text" readonly class="form-control-plaintext"
                                   value="${rutina.clienteNombre} ${rutina.clienteApellido}">
                        </div>
                    </div>

                    <div class="mb-3 row">
                        <label class="col-sm-3 col-form-label">Entrenador:</label>
                        <div class="col-sm-9">
                            <input type="text" readonly class="form-control-plaintext"
                                   value="${rutina.entrenadorNombre} ${rutina.entrenadorApellido}">
                        </div>
                    </div>

                    <div class="mb-3 row">
                        <label class="col-sm-3 col-form-label">Tipo Entrenamiento:</label>
                        <div class="col-sm-9">
                            <input type="text" readonly class="form-control-plaintext"
                                   value="${rutina.tipoEntrenamiento}">
                        </div>
                    </div>

                    <div class="mb-3 row">
                        <label class="col-sm-3 col-form-label">Observaciones:</label>
                        <div class="col-sm-9">
                            <textarea readonly class="form-control-plaintext" rows="3">${rutina.observaciones}</textarea>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/EliminarRutinaServlet" method="post">
                        <input type="hidden" name="idRutina" value="${rutina.idRutina}">

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/RutinaServlet"
                               class="btn btn-secondary me-md-2">Cancelar</a>
                            <button type="submit" cclass="btn btn-danger" style="background-color: #fd4242">
                                <i class="fa fa-arrow-circle-left"></i>Confirmar Eliminación</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>