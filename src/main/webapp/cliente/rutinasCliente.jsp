<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 16:17
  To change this template use File | Settings | File Templates.
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
            <i class="fas fa-dumbbell me-2"></i> Mis Rutinas
          </h3>
        </div>

        <div class="card-body">
          <c:choose>
            <c:when test="${empty rutinas}">
              <div class="alert alert-info text-center py-4">
                <i class="fas fa-info-circle fa-2x mb-3"></i>
                <h4>No tienes rutinas asignadas</h4>
                <p>Consulta con tu entrenador para que te asigne una rutina personalizada</p>
              </div>
            </c:when>
            <c:otherwise>
              <div class="row">
                <c:forEach items="${rutinas}" var="rutina">
                  <div class="col-md-6 mb-4">
                    <div class="card h-100 border-primary">
                      <div class="card-header bg-light">
                        <h5 class="card-title mb-0">
                            ${rutina.tipoEntrenamiento}
                              <span class="badge bg-success float-end">
                    Entrenador: ${rutina.entrenadorNombre} ${rutina.entrenadorApellido}
                  </span>
                        </h5>
                      </div>
                      <div class="card-body">
                        <h6 class="text-muted">Detalles:</h6>
                        <p class="card-text">${rutina.observaciones}</p>

                        <!-- Mostrar información adicional -->
                        <div class="mt-3">
                          <small class="text-muted">
                            <i class="fas fa-id-card"></i> ID Rutina: ${rutina.idRutina}
                          </small>
                        </div>
                        <!--
                        <!-- Mostrar información adicional
                        <div class="mt-3" style="display: none;">
                          <small class="text-muted">
                            <i class="fas fa-id-card"></i> ID Rutina: ${rutina.idRutina}
                          </small>
                        </div>
                      -->
                      </div>
                      <div class="card-footer bg-transparent">
                        <small class="text-muted">
                          <i class="fas fa-user-tie"></i> Entrenador asignado:
                            ${rutina.entrenadorNombre} ${rutina.entrenadorApellido}
                        </small>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>
        </div>

      </div>
    </div>
  </section>
</div>

<jsp:include page="../footer.jsp"/>
