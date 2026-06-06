<%@ page import="java.util.Hashtable, edu.ucam.config.*, edu.ucam.domain.Subject, edu.ucam.domain.Titulation" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modificar asignatura</title>
</head>
<body>
<%
	String idSub = request.getParameter(Parameters.ID_SUB);

	Hashtable<String, Subject> subjects =
   		(Hashtable<String, Subject>) application.getAttribute(Attributes.ASIGNATURAS);

	Hashtable<String, Titulation> titulations =
    	(Hashtable<String, Titulation>) application.getAttribute(Attributes.TITULACIONES);

	Subject subject = subjects.get(idSub);
%>

<h1>Modificar asignatura</h1>

<form action="<%= request.getContextPath() %>/Control" method="post">

	<!-- Acción -->
    <input type="hidden" name="<%= Parameters.ACTION_ID %>" value="<%= ActionID.MODIFYSUB %>">

	<!-- id -->
    ID: <input type="text" name="<%= Parameters.ID_SUB %>" value="<%= subject.getId() %>" readonly>
    <br><br>

	<!-- Nombre -->
    Nombre: <input type="text" name="<%= Parameters.NAME_SUB %>" value="<%= subject.getNombre() %>"required>
    
    <!-- ID DE LA TITULACIÓN LIGADA -->
    <select name="<%= Parameters.ID_TIT %>" required>
        <%
        if(titulations != null){
            for(Titulation t : titulations.values()){
                String selected = "";

                if(t.getId().equals(subject.getIdTit())){
                    selected = "selected";
                }
        %>
                <option value="<%= t.getId() %>" <%= selected %>>
                    <%= t.getId() %> - <%= t.getNombre() %>
                </option>
        <%
            }
        }
        %>
    </select>
    <br><br>
    
    Créditos: <input type="number" name="<%= Parameters.CREDITS_SUB %>" value="<%= subject.getCredits() %>" min="1" required>
    <br><br>

    <input type="submit" value="Guardar cambios">
</form>

<a href="<%= request.getContextPath() %>/crud/subIndex.jsp">Volver</a>

<% 
	String error = (String) request.getAttribute(edu.ucam.config.Attributes.ERROR_MSG);
        
    if(error != null) {
        %><p style="color:red;">Error: <%= error %></p><%
    }
%>

</body>
</html>