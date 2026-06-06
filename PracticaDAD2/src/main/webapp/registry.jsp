<%@page import="edu.ucam.config.UserTypes"%>
<%@page import="edu.ucam.config.Parameters"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.UserTypes" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>REGISTRARSE</title>
</head>
<body>
<form action="RegistryServlet" method="post">

    <h2>Registrarse</h2>

    Usuario:
    <input type="text" name="<%= edu.ucam.config.Parameters.USERNAME %>" required><br>

    Contraseña:
    <input type="password" name="<%= edu.ucam.config.Parameters.PASSWORD %>" required><br>

    Tipo de usuario:
    <select name="<%= edu.ucam.config.Parameters.USERTYPE %>">
        <option value="<%= UserTypes.STUDENT %>">Alumno</option>
        <option value="<%= UserTypes.ADMIN %>">Administrador</option>
    </select><br>

    <input type="submit" value="Crear cuenta">
	<br><br>
	Error:
    <p style="color:red;">
        <%= request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG) %>
    </p>
</form>
</body>
</html>