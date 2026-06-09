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
import edu.ucam.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserModify extends Action{

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.adminFilter(request);
		
		if(logged) {
			String username = request.getParameter(Parameters.USERNAME);
			String password = request.getParameter(Parameters.PASSWORD);
			
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			
			try {
				if(username != null && password != null) {
					Hashtable <String, User> users = (Hashtable <String, User>) request.getServletContext().getAttribute(Attributes.USUARIOS);
					userModify(users, username, password, request, conexion);
					request.getRequestDispatcher("/crud/secured/adminIndex.jsp").forward(request, response);
				} else {
					request.setAttribute(Attributes.ERROR_MSG, "Credenciales incorrectas!");
				}
			} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/index.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
	
	
	// BUSCA USUARIO Y LA ELIMINA
	private void userModify(Hashtable <String, User> users, String username, String password, HttpServletRequest request, Connection conexion) {
		if(users.containsKey(username)) {
			User u = users.get(username);
			
			if(u.getUsername().equals("admin") && u.getPassword().equals("admin") && u.getType().equals(UserTypes.ADMIN)) {
				request.setAttribute(Attributes.ERROR_MSG, "Este administrador no se puede modificar!");
			} else {
				users.get(username).setPassword(password);
				
				try {
					// INSERTAR EN LA BASE DE DATOS
					try (PreparedStatement psUpdateUser = conexion.prepareStatement
							("UPDATE Users SET password = ? WHERE username = ?")) {
						psUpdateUser.setString(1, password);
						psUpdateUser.setString(2, username);
						
						psUpdateUser.executeUpdate();
					}
				} catch(SQLException ex) {
					request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
				}
			}
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "No se ha encontrado el usuario");
		}
	}
}
