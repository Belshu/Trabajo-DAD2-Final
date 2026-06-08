<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page import="edu.ucam.config.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gestión administrador</title>
</head>
<body>
<h1>Lista de usuarios</h1>
<form action="<%= request.getContextPath() %>/crud/index.jsp">
	<input type="submit" value="Volver a opciones de usuario">
</form>

<!-- FORMULARIO PARA AÑADIR UN NUEVO USUARIO -->
<h2>Añadir nuevo usuario</h2>

<form action="<%= request.getContextPath() %>/Control" method="post">

    <input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%=ActionID.ADDUSER%>">

    Usuario:
    <input type="text" name="<%= Parameters.USERNAME %>" required>
	<br><br>
    Contraseña:
    <input type="text" name="<%= Parameters.PASSWORD %>" required>
	<br><br>
    Tipo:
    <select name="<%= Parameters.USERTYPE %>">
        <option value="<%= UserTypes.STUDENT %>">Alumno</option>
        <option value="<%= UserTypes.ADMIN %>">Administrador</option>
        <option value="<%= UserTypes.TEACHER %>">Profesor</option>
    </select>
	<br><br>
    <input type="submit" value="Añadir usuario">
    <br><br>
</form>


<dad2:listusers/>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>

</body>
</html>