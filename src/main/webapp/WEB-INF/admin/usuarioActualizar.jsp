<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.example.modelos.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title"><strong>Actualizar Usuario</strong></h1>
                    </div>

                    <div class="panel-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/UsuarioActualizar" method="post">
                            <input type="hidden" name="id" value="${usuario.id}">

                            <div class="form-group">
                                <label for="nombre">Nombre</label>
                                <input type="text" class="form-control" name="nombre" id="nombre" value="${usuario.nombre}" required>
                            </div>

                            <div class="form-group">
                                <label for="apellido">Apellido</label>
                                <input type="text" class="form-control" name="apellido" id="apellido" value="${usuario.apellido}" required>
                            </div>

                            <div class="form-group">
                                <label for="usuario">Nombre de Usuario</label>
                                <input type="text" class="form-control" name="usuario" id="usuario" value="${usuario.usuario}" required>
                            </div>

                            <div class="form-group">
                                <label for="clave">Contraseña</label>
                                <input type="password" class="form-control" name="clave" id="clave" value="${usuario.clave}" required>
                            </div>

                            <div class="form-group">
                                <label for="rol">Rol</label>
                                <select name="rol" id="rol" class="form-control" required>
                                    <option value="Administrador" ${usuario.rol == 'Administrador' ? 'selected' : ''}>Administrador</option>
                                    <option value="Entrenador" ${usuario.rol == 'Entrenador' ? 'selected' : ''}>Entrenador</option>
                                    <option value="Cliente" ${usuario.rol == 'Cliente' ? 'selected' : ''}>Cliente</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="correo">Correo</label>
                                <input type="email" class="form-control" name="correo" id="correo" value="${usuario.correo}" required>
                            </div>

                            <div class="form-group">
                                <label for="telefono">Teléfono</label>
                                <input type="text" class="form-control" name="telefono" id="telefono"
                                       value="${usuario.telefono}" required pattern="\d{10}"
                                       title="El teléfono es invalido revise porfavor"
                                       oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0, 10)">
                            </div>

                            <div class="form-group">
                                <label for="cedula">Cédula</label>
                                <input type="text" class="form-control" name="cedula" id="cedula"
                                       value="${usuario.cedula}" required pattern="\d{10}"
                                       title="La cédula es invalida revise porfavor"
                                       oninput="this.value = this.value.replace(/[^0-9]/g, '').slice(0, 10)">
                            </div>

                            <div class="form-group">
                                <label for="direccion">Dirección</label>
                                <input type="text" class="form-control" name="direccion" id="direccion" value="${usuario.direccion}" required>
                            </div>

                            <button type="submit" class="btn btn-primary">Actualizar</button>
                            <a href="${pageContext.request.contextPath}/UsuarioServlet" class="btn btn-danger" style="background-color: #fd4242">
                                <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script>
    function validarFormulario() {
        const telefono = document.getElementById("telefono").value;
        const cedula = document.getElementById("cedula").value;
        const correo = document.getElementById("correo").value;

        if (!/^\d{10}$/.test(telefono)) {
            alert("El teléfono es invalido revise porfavor");
            return false;
        }

        if (!/^\d{10}$/.test(cedula)) {
            alert("La cédula es invalida revise porfavor");
            return false;
        }
        const regex = /^[^@]+@[^@]+\.[^@]+$/;
        if (!regex.test(correo)) {
            alert("Correo electrónico no válido");
            return false;
        }

        const dominio = correo.split("@")[1];
        if (!dominio.endsWith(".com") && !dominio.endsWith(".ec")) {
            alert("Correo electrónico no válido");
            return false;
        }

        return true;
    }

    // Bloquea el ingreso de caracteres no numéricos
    document.getElementById("telefono").addEventListener("keypress", function(e) {
        if (isNaN(String.fromCharCode(e.keyCode))) {
            e.preventDefault();
        }
    });

    document.getElementById("cedula").addEventListener("keypress", function(e) {
        if (isNaN(String.fromCharCode(e.keyCode))) {
            e.preventDefault();
        }
    });
</script>

<jsp:include page="../footer.jsp"></jsp:include>

