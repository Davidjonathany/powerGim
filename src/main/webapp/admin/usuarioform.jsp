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
                        <h1 class="box-title"><strong>Agregar Nuevo Usuario</strong></h1>
                    </div>

                    <div class="panel-body">
                        <%-- Mostrar mensaje de error si existe --%>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/UsuarioFormServlet" method="POST">
                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" name="nombre" id="nombre" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="apellido">Apellido</label>
                                <input type="text" name="apellido" id="apellido" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="usuario">Nombre de Usuario</label>
                                <input type="text" name="usuario" id="usuario" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="clave">Contraseña</label>
                                <input type="password" name="clave" id="clave" class="form-control" required>
                            </div>

                            <div class="form-group">
                                <label for="rol">Rol</label>
                                <select name="rol" id="rol" class="form-control" required>
                                    <option value="Administrador">Administrador</option>
                                    <option value="Entrenador">Entrenador</option>
                                    <option value="Cliente">Cliente</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="correo">Correo</label>
                                <input type="email" name="correo" id="correo" class="form-control"
                                       required
                                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com|ec|edu|net|org|gov|mil|biz|info|mobi|name|aero|jobs|museum)$"
                                       title="Correo electrónico no válido">
                            </div>

                            <div class="form-group">
                                <label for="telefono">Teléfono</label>
                                <input type="text" name="telefono" id="telefono" class="form-control"
                                       required pattern="\d{10}" title="El teléfono ingresado no es valido">
                            </div>

                            <div class="form-group">
                                <label for="cedula">Cédula</label>
                                <input type="text" name="cedula" id="cedula" class="form-control"
                                       required pattern="\d{10}" title="La cédula ingresada no es valida.">
                            </div>

                            <div class="form-group">
                                <label for="direccion">Dirección</label>
                                <input type="text" name="direccion" id="direccion" class="form-control" required>
                            </div>

                            <button type="submit" class="btn btn-success">Agregar Usuario</button>
                            <a href="<%=request.getContextPath()%>/UsuarioServlet" class="btn btn-danger" style="background-color: #fd4242">
                                <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script>
    document.querySelector("form").addEventListener("submit", function(event) {
        let telefono = document.getElementById("telefono").value;
        let cedula = document.getElementById("cedula").value;
        let correo = document.getElementById("correo").value;

        if (!/^\d{10}$/.test(telefono)) {
            alert("Teléfono inválido. Intente nuevamente.");
            event.preventDefault();
        }

        if (!/^\d{10}$/.test(cedula)) {
            alert("Cédula inválida. Intente nuevamente.");
            event.preventDefault();
        }
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com|ec|edu|net|org|gov|mil|biz|info|mobi|name|aero|jobs|museum)$/i;
        if (!emailRegex.test(correo)) {
            alert("Correo electrónico no válido");
            event.preventDefault();
        }
    });
    // Validación en tiempo real para el correo
    document.getElementById("correo").addEventListener("blur", function() {
        const correo = this.value;
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com|ec|edu|net|org|gov|mil|biz|info|mobi|name|aero|jobs|museum)$/i;

        if (correo && !emailRegex.test(correo)) {
            this.setCustomValidity("Correo electrónico no válido");
            this.reportValidity();
        } else {
            this.setCustomValidity("");
        }
    });
</script>

<jsp:include page="../footer.jsp"></jsp:include>
