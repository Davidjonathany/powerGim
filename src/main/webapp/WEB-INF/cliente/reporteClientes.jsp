<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="container-fluid">
            <div class="row mt-4">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header bg-dark text-white">
                            <h3 class="card-title"><i class="fas fa-users mr-2"></i>Reporte de Clientes</h3>
                            <div class="card-tools">
                                <a href="#" class="btn btn-sm btn-success">
                                    <i class="fas fa-file-excel"></i> Exportar
                                </a>
                            </div>
                        </div>

                        <!-- Filtros -->
                        <div class="card-body border-bottom">
                            <form class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Estado Membresía</label>
                                        <select class="form-control form-control-sm">
                                            <option value="">Todos</option>
                                            <option value="Activa">Activa</option>
                                            <option value="Vencida">Vencida</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Fecha desde</label>
                                        <input type="date" class="form-control form-control-sm">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <label>Fecha hasta</label>
                                        <input type="date" class="form-control form-control-sm">
                                    </div>
                                </div>
                                <div class="col-md-3 align-self-end">
                                    <button type="submit" class="btn btn-primary btn-sm btn-block">
                                        <i class="fas fa-filter"></i> Filtrar
                                    </button>
                                </div>
                            </form>
                        </div>

                        <!-- Tabla -->
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead class="thead-dark">
                                    <tr>
                                        <th style="width: 5%">ID</th>
                                        <th style="width: 20%">Cliente</th>
                                        <th style="width: 15%">Contacto</th>
                                        <th style="width: 15%">Membresía</th>
                                        <th style="width: 10%">Estado</th>
                                        <th style="width: 15%">Entrenador</th>
                                        <th style="width: 10%">Últ. Asistencia</th>
                                        <th style="width: 10%">Acciones</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="reporte" items="${reportes}">
                                        <tr>
                                            <td>${reporte.idCliente}</td>
                                            <td>
                                                <strong>${reporte.nombreCliente}</strong><br>
                                                <small class="text-muted">${reporte.correo}</small>
                                            </td>
                                            <td>${reporte.contactoCliente}</td>
                                            <td>
                                                    ${reporte.tipoMembresia}<br>
                                                <small class="text-danger">
                                                    <i class="far fa-calendar-alt"></i> ${reporte.fechaVencimiento}
                                                </small>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${reporte.estadoMembresia eq 'Activa'}">
                                                        <span class="badge bg-success">${reporte.estadoMembresia}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-danger">${reporte.estadoMembresia}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                    ${reporte.nombreEntrenador}<br>
                                                <small class="text-primary">
                                                    <i class="fas fa-phone"></i> ${reporte.contactoEntrenador}
                                                </small>
                                            </td>
                                            <td>
                                                <c:if test="${not empty reporte.fechaAsistencia}">
                                                        <span class="badge bg-info">
                                                                ${reporte.fechaAsistencia}
                                                        </span>
                                                </c:if>
                                                <c:if test="${empty reporte.fechaAsistencia}">
                                                        <span class="badge bg-warning">
                                                            Sin registros
                                                        </span>
                                                </c:if>
                                            </td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-sm btn-outline-info" title="Detalles">
                                                        <i class="fas fa-eye"></i>
                                                    </button>
                                                    <button class="btn btn-sm btn-outline-secondary" title="Historial">
                                                        <i class="fas fa-history"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <!-- Paginación -->
                        <div class="card-footer clearfix">
                            <ul class="pagination pagination-sm m-0 float-right">
                                <li class="page-item"><a class="page-link" href="#">«</a></li>
                                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                <li class="page-item"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item"><a class="page-link" href="#">»</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"/>