<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.Titulation" %>
<%@ page import="java.util.Hashtable" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Asignaturas</title>
</head>

<body>

<%
	if(request.getAttribute("ERROR_MSG") != null){
%>
    <p style="color:red">
        <%= request.getSession().getAttribute(Attributes.ERROR_MSG) %>
    </p>
<%
	}

	String id = request.getParameter(Parameters.ID_TIT);
	Hashtable<String, Titulation> titulations = 
			(Hashtable<String, Titulation>) request.getServletContext().getAttribute(Attributes.TITULACIONES);
%>

<!-- FORMULARIO PARA AÑADIR UNA NUEVA TITULACIÓN -->
<h2>Añadir nueva asignatura</h2>

<form action="<%= request.getContextPath() %>/Control" method="post">

    <!-- ACCIÓN QUE SE VA A REALIZAR (AÑADIR) -->
    <input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%=edu.ucam.config.ActionID.ADDSUB %>">

    <!-- ID DE LA ASIGNATURA -->
    ID: <input type="text" name="<%= Parameters.ID_SUB %>" required>

    <br><br>

    <!-- NOMBRE DE LA ASIGNATURA -->
    Nombre: <input type="text" name="<%= Parameters.NAME_SUB %>" required>
    
    <!-- ID DE LA TITULACIÓN LIGADA -->
    <select name="<%= Parameters.ID_TIT %>" required>
    	<option value="">-- Titulación a asignar</option>
    	
    	<%
    		if(titulations != null) {
    			for(Titulation t : titulations.values()) {    				
    				%>
    					<option value="<%= t.getId() %>">
    						<%= t.getId() %> - <%= t.getNombre() %>
    					</option>
    				<%
    			}
    		}
    	%>
    </select>
    <br><br>
    
    Creditos: <input type="number" name="<%= Parameters.CREDITS_SUB %>" min="1" max="10" required>
	<br><br>

    <input type="submit" value="Añadir asignatura">
</form>

<!-- VOLVER AL INDEX -->
<form action="<%= request.getContextPath() %>/crud/index.jsp">
    <input type="submit" value="Volver a la página principal">
</form>

<h1>Lista de asignaturas</h1>
<dad2:listsub/>
</body>
</html>