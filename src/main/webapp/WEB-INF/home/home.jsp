<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 2/4/2025
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-main py-4">
    <div class="text-center">
        <h2 class="mb-4">Bienvenido a PowerGim</h2>
        <br>
        <h2 class="mb-4">Panel de ${userRol}</h2>
        <br>

        <p class="fs-5 text-muted">
            <c:choose>
                <c:when test="${userRol eq 'Administrador'}">
                    Bienvenido Administrador, gestiona el sistema completo del gimnasio.
                </c:when>
                <c:when test="${userRol eq 'Entrenador'}">
                    Bienvenido Entrenador, gestiona rutinas y asistencias.
                </c:when>
                <c:otherwise>
                    Bienvenido Cliente, consulta tus rutinas y registra tu progreso.
                </c:otherwise>
            </c:choose>
        </p>

        <!-- Contenedor grid con 3 columnas -->
        <div class="container">
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <!-- Botón EXTRA solo para Administrador -->
                <c:if test="${userRol eq 'Administrador'}">
                    <div class="col">
                        <form action="${pageContext.request.contextPath}/UsuarioServlet" method="get">
                            <button type="submit" class="btn btn-primary btn-lg w-100 py-3">
                                <i class="fas fa-users fa-2x me-2"></i><br>Usuarios
                            </button>
                        </form>
                    </div>
                </c:if>

                <!-- Botones COMUNES para todos los roles -->
                <div class="col">
                    <form action="${pageContext.request.contextPath}/AsistenciaVistaServlet" method="get">
                        <button type="submit" class="btn btn-success btn-lg w-100 py-3">
                            <i class="fas fa-clipboard-list fa-2x me-2"></i><br>Asistencias
                        </button>
                    </form>
                </div>

                <div class="col">
                    <form action="${pageContext.request.contextPath}/RutinaServlet" method="get">
                        <button type="submit" class="btn btn-info btn-lg w-100 py-3">
                            <i class="fas fa-dumbbell fa-2x me-2"></i><br>Rutinas
                        </button>
                    </form>
                </div>

                <div class="col">
                    <form action="${pageContext.request.contextPath}/membresias" method="get">
                        <button type="submit" class="btn btn-warning btn-lg w-100 py-3">
                            <i class="fas fa-id-card fa-2x me-2"></i><br>Membresías
                        </button>
                    </form>
                </div>

                <div class="col">
                    <form action="${pageContext.request.contextPath}/ejercicios" method="get">
                        <button type="submit" class="btn btn-danger btn-lg w-100 py-3">
                            <i class="fas fa-running fa-2x me-2"></i><br>Ejercicios
                        </button>
                    </form>
                </div>

                <div class="col">
                    <form action="${pageContext.request.contextPath}/pesos" method="get">
                        <button type="submit" class="btn btn-purple btn-lg w-100 py-3" style="background-color: #6f42c1; color: white;">
                            <i class="fas fa-balance-scale fa-2x me-2"></i><br>Pesos
                        </button>
                    </form>
                </div>

                <div class="col">
                    <form action="${pageContext.request.contextPath}/ReporteClienteServlet" method="get">
                        <button type="submit" class="btn btn-dark btn-lg w-100 py-3">
                            <i class="fas fa-chart-line fa-2x me-2"></i><br>Reportes
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"></jsp:include>
