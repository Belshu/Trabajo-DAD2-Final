<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page import="edu.ucam.config.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.domain.User, edu.ucam.config.Attributes" %>
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
<h1>Lista de titulaciones</h1>
<form action="<%= request.getContextPath() %>/crud/secured/adminIndex.jsp">
	<input type="submit" value="Ir a opciones de administrador">
</form>

<!-- FORMULARIO PARA AÑADIR UNA NUEVA TITULACIÓN -->
<h2>Añadir nueva titulación</h2>

<form action="<%= request.getContextPath() %>/Control" method="post">

    <!-- ACCIÓN QUE SE VA A REALIZAR (AÑADIR) -->
    <input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%=edu.ucam.config.ActionID.ADDTIT%>">

    <!-- ID DE LA TITULACIÓN -->
    ID: <input type="text" name="<%= Parameters.ID_TIT %>" required>

    <br><br>

    <!-- NOMBRE DE LA TITULACIÓN -->
    Nombre: <input type="text" name="<%= Parameters.NAME_TIT %>" required>

    <br><br>

    <input type="submit" value="Añadir titulación">
</form>

<!-- VOLVER AL INDEX -->
<form action="<%= request.getContextPath() %>/crud/index.jsp">
    <input type="submit" value="Volver a la página principal">
</form>

<dad2:listtit/>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>

</body>
</html>