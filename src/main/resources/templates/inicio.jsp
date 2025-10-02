<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Inicio Agente</title>
</head>
<body>
    <h2>Bienvenido, ${agentesession.nombreApellido}!</h2>

    <p>Usuario: ${agentesession.usuario}</p>
    <p>Email: ${agentesession.email}</p>

    <br>
    <a href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
</body>
</html>
