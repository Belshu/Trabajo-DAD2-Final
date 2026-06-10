<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page import="edu.ucam.config.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.Attributes ,edu.ucam.domain.User" %>
<%
    //Validamos si hay sesión iniciada
    User usuarioLogueado = (User) session.getAttribute(Attributes.LOGGED_USER); 
    
    if (usuarioLogueado == null) {
        request.setAttribute(Attributes.ERROR_MSG, "Acceso denegado. Por favor, inicia sesión.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return; 
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu principal</title>
</head>
<body>
<h1>Página principal</h1>
<form action="<%= request.getContextPath() %>/crud/secured/adminIndex.jsp">
	<input type="submit" value="Ir a opciones de administrador">
</form>

<!-- FORMULARIOS PARA IR A LA GESTIÓN DE ASIGNATURAS Y TITULACIONES -->
<h2>GESTIÓN DE ASIGNATURAS Y TITULACIONES</h2>

<form action="<%= request.getContextPath() %>/crud/titIndex.jsp">
    <input type="submit" value="Gestionar Titulaciones">
</form>

<form action="<%= request.getContextPath() %>/crud/subIndex.jsp">
    <input type="submit" value="Gestionar Asignaturas">
</form>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>

</body>
</html>