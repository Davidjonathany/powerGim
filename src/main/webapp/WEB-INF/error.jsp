<%--
  Created by IntelliJ IDEA.
  User: DAVID
  Date: 30/3/2025
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%-- error.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../cabecero.jsp"/>

<div class="content-wrapper">
    <h1>Ocurrió un error</h1>
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>
    <a href="${pageContext.request.contextPath}/LoginServlet">Volver al inicio</a>
</div>

<jsp:include page="../footer.jsp"/>