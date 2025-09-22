<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Agente</title>
</head>
<body>
    <h2>Iniciar Sesión</h2>

    <!-- Mensaje de error -->
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <!-- Mensaje de éxito (por ejemplo después de registrarse) -->
    <c:if test="${not empty exito}">
        <p style="color:green;">${exito}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="usuario">Usuario:</label><br>
        <input type="text" name="usuario" value="${usuario}" required><br><br>

        <label for="clave">Clave:</label><br>
        <input type="password" name="clave" required><br><br>

        <button type="submit">Ingresar</button>
    </form>

    <br>
    <p>¿No tienes cuenta?
        <a href="${pageContext.request.contextPath}/registro">Registrarse</a>
    </p>
</body>
</html>
