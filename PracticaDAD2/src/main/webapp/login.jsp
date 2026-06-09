<%@ page import="edu.ucam.config.*" %>
<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INICIAR SESION</title>
</head>
<body>
<form action="LoginServlet" method="post">
	
    <h2>Iniciar sesión</h2>

    Usuario:
    <input type="text" name="<%= edu.ucam.config.Parameters.USERNAME %>" value="admin" required><br>
    Contraseña:
    <input type="password" name="<%= edu.ucam.config.Parameters.PASSWORD %>" value="admin" required><br>
    <input type="submit" value="Entrar">
	<br>
	<br>
	<% 
        	String error = (String) request.getSession().getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
        	if(error != null) {
        		%><p style="color:red;">Error: <%= error %></p><%
        	}
    %>
    
    <p>
    ¿No tienes cuenta?
    <a href="<%= request.getContextPath() %>/registry.jsp">Regístrate aquí</a>
	</p>
    
</form>
</body>
</html>