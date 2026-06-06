<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.User, edu.ucam.domain.Subject" %>
<%@ page import="java.util.Hashtable, java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String username = request.getParameter(Parameters.USERNAME);
	Hashtable<String, User> users = (Hashtable<String, User>) request.getServletContext().getAttribute(Attributes.USUARIOS);

	User user = users.get(username);
%>

<title>Matriculación del estudiante: <%= user.getUsername() %> </title>


</head>
<body>

</body>
</html>