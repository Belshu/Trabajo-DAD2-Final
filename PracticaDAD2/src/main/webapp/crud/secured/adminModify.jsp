<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.User, edu.ucam.domain.Subject" %>
<%@ page import="java.util.Hashtable, java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar usuario</title>
</head>
<body>

<%
	String username = request.getParameter(Parameters.USERNAME);
	Hashtable<String, User> users = (Hashtable<String, User>) request.getServletContext().getAttribute(Attributes.USUARIOS);
	
	User user = users.get(username);
%>

<h1>Modificar usuario</h1>

<form action="<%= request.getContextPath() %>/Control" method="post">
	<!-- Acción -->
	<input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%=ActionID.MODIFYTIT%>">

	<!-- Username (solo lectura) -->
	Usuario:
	<input type="text" value="<%= user.getUsername() %>" disabled>
	<input type="hidden" name="<%= Parameters.USERNAME %>" value="<%= user.getUsername() %>">

	<br><br>
	
	<!-- Contraseña editable -->
	Nueva contraseña:
	<input type="text" name="<%= Parameters.PASSWORD %>" value="<%= user.getPassword() %>" required>

	<br><br>
	
	<input type="submit" value="Guardar cambios">
</form>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>

</body>
</html>