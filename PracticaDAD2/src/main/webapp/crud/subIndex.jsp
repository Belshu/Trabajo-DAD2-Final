<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="mytags" prefix="dad2" %>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.Titulation,  edu.ucam.domain.User, edu.ucam.config.Attributes" %>
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
	
	Hashtable<String, edu.ucam.domain.Subject> subjects = 
			(Hashtable<String, edu.ucam.domain.Subject>) application.getAttribute(Attributes.ASIGNATURAS);
	Hashtable<String, User> users = 
			(Hashtable<String, User>) application.getAttribute(Attributes.USUARIOS);
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

<hr> <h2>Asignar Profesor a Asignatura</h2>
<form action="<%= request.getContextPath() %>/Control" method="post">
    
    <input type="hidden" name="<%= Parameters.ACTION_ID %>" value="asignarProfesor">

    Asignatura:
    <select name="<%= Parameters.ID_SUB %>" required>
        <option value="">-- Selecciona Asignatura --</option>
        <%
            if(subjects != null) {
                for(edu.ucam.domain.Subject s : subjects.values()) {
                    // Si ya tiene profesor, lo mostramos al lado de su nombre
                    String infoProf = (s.getProfUsername() != null) ? " (Prof: " + s.getProfUsername() + ")" : " (Sin Profesor)";
        %>
                    <option value="<%= s.getId() %>">
                        <%= s.getId() %> - <%= s.getNombre() %><%= infoProf %>
                    </option>
        <%
                }
            }
        %>
    </select>
    
    <br><br>

    Profesor a asignar:
    <select name="<%= Parameters.USERNAME %>" required>
        <option value="">-- Selecciona Profesor --</option>
        <%
            if(users != null) {
                for(User u : users.values()) {
                    // Filtramos para que únicamente aparezcan los usuarios que son profesores
                    if("TEACHER".equals(u.getType())) {
        %>
                        <option value="<%= u.getUsername() %>">
                            <%= u.getUsername() %>
                        </option>
        <%
                    }
                }
            }
        %>
    </select>
    
    <br><br>
    <input type="submit" value="Vincular Profesor">
</form>

<hr>
<!-- VOLVER AL INDEX -->
<form action="<%= request.getContextPath() %>/crud/index.jsp">
    <input type="submit" value="Volver a la página principal">
</form>

<h1>Lista de asignaturas</h1>
<dad2:listsub/>
</body>
</html>