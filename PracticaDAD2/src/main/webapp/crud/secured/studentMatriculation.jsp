<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.ucam.config.*, edu.ucam.domain.User, edu.ucam.domain.Subject" %>
<%@ page import="java.util.Hashtable, java.util.ArrayList" %>
<%
    // 1. Validamos si hay sesión
    User usuarioLogueado = (User) session.getAttribute(Attributes.LOGGED_USER); 
    
    if (usuarioLogueado == null) {
        request.setAttribute(Attributes.ERROR_MSG, "Acceso denegado. Por favor, inicia sesión.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
    }
    
    // 2. Validamos si es Administrador
    if (!UserTypes.ADMIN.equals(usuarioLogueado.getType())) {
        request.setAttribute(Attributes.ERROR_MSG, "No tienes permisos de administrador para ver esta página.");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
        return;
    }
%>
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