<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.Titulation" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="edu.ucam.domain.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar Titulacion</title>
</head>
<body>

<%
	String id = request.getParameter(Parameters.ID_TIT);
	Hashtable<String, Titulation> titu = (Hashtable<String, Titulation>) request.getServletContext().getAttribute(Attributes.TITULACIONES);

	Titulation t = titu.get(id);
%>

<h1>Modificar titulación</h1>

<form action="<%= request.getContextPath() %>/Control" method="post">

	<!-- Acción -->
	<input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%=ActionID.MODIFYTIT%>">
	
	<!-- ID (solo lectura) -->
	ID:
	<input type="text" name="<%= Parameters.ID_TIT %>" value="<%= t.getId() %>" readonly>
	<br><br>

	<!-- Nombre editable -->
	Nombre:
	<input type="text" name="<%= Parameters.NAME_TIT %>" value="<%= t.getNombre() %>" required>
	<br><br>
	

	<input type="submit" value="Guardar cambios">
</form>


<a href="<%= request.getContextPath() %>/crud/titIndex.jsp">Volver</a>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>
	
</body>
</html>