<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 27/3/2025
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.example.modelos.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Usuario usuario = (Usuario) request.getAttribute("usuario"); %>
<jsp:include page="../cabecero.jsp"></jsp:include>

<div class="content-wrapper" style="font-weight: bold; font-family: Times New Roman, serif";>
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title"><strong>Confirmar Eliminación</strong></h1>
                        <div class="box-tools pull-right"></div>
                    </div>
                    <div class="panel-body">
                        <form action="<%=request.getContextPath()%>/EliminarUsuario" method="post">
                            <input type="hidden" name="idUsuario" value="<%=usuario.getId()%>">
                            <p>¿Está seguro que desea eliminar el usuario con ID <%=usuario.getId()%> y nombre <%=usuario.getNombre()%> <%=usuario.getApellido()%>?</p>
                            <button type="submit" class="btn btn-danger" style="background-color: #00d1ff; border-color: #000000;">Confirmar Eliminación</button>
                            <a href="<%=request.getContextPath()%>/UsuarioServlet" class="btn btn-success" style="background-color: #fd4242">
                                <i class="fa fa-arrow-circle-left"></i> Cancelar</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../footer.jsp"></jsp:include>

