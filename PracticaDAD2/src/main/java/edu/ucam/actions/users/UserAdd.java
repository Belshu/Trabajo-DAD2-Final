package edu.ucam.actions.users;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import edu.ucam.actions.Action;
import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.config.UserTypes;
import edu.ucam.domain.Admin;
import edu.ucam.domain.Student;
import edu.ucam.domain.Titulation;
import edu.ucam.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAdd extends Action {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean logged = super.adminFilter(request);
		if(logged) {
			String username = request.getParameter(Parameters.USERNAME);
			String password = request.getParameter(Parameters.PASSWORD);
			String type = request.getParameter(Parameters.USERTYPE);
			
			try {
				Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
				
				// SI ES NUEVO USUARIO -> REDIRIGE A adminIndex.jsp
				// SI ES CUALQUIER OTRO DATO U ERRORES -> index.jsp
				if(username != null && password != null && type != null){
					Hashtable <String, User> users = (Hashtable <String, User>) request.getServletContext().getAttribute(Attributes.USUARIOS);
					newUser(users, username, password, type, request, conexion);
				} else {
					request.setAttribute(Attributes.ERROR_MSG, "Campos no válidos!");
				}
	;		} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
				System.out.println("execute" + ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/secured/adminIndex.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
	
	
	// CREAR NUEVO USUARIO Y AÑADIRLO A LA HASHTABLE
	private void newUser(Hashtable <String, User> users, String username, String password, String type, HttpServletRequest request, Connection conexion) {
		if(users.contains(username)) {
			request.setAttribute(Attributes.ERROR_MSG, "El usuario ya existe");
			return;
		}
		
		User user = null;
		
		if(type.equals(UserTypes.ADMIN)) {
			user = new Admin(username, password);
		} else if (type.equals(UserTypes.STUDENT)) {
			user = new Student(username, password);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "Tipo de usuario no válido");
			return;
		}
		users.put(username, user);
		
		
		// INSERTAR EN LA BASE DE DATOS
		try {
			try (PreparedStatement psInsertUser = conexion.prepareStatement
					("INSERT INTO Users (username, password, type) VALUES (?, ?, ?)")) {
				psInsertUser.setString(1, username);
				psInsertUser.setString(2, password);
				psInsertUser.setString(3, type);
				
				psInsertUser.executeUpdate();
			}
		} catch(SQLException ex) {
			request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
		}
	}
}
