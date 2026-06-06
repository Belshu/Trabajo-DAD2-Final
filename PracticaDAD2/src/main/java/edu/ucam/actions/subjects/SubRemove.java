package edu.ucam.actions.subjects;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import edu.ucam.actions.Action;
import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.domain.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SubRemove extends Action{

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request);
		
		if(logged) {
			String idSub = request.getParameter(Parameters.ID_SUB);
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			
			try {
				if(idSub != null) {
					Hashtable <String, Subject> subjects = (Hashtable <String, Subject>) request.getServletContext().getAttribute(Attributes.ASIGNATURAS);
					removeSub(subjects, idSub, request, conexion);
				}
			} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/subIndex.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
	
	@SuppressWarnings("unused")
	private void removeSub(Hashtable <String, Subject> subjects, String id, HttpServletRequest request, Connection conexion) {
		if(subjects.containsKey(id)) {
			
			// INSERTAR EN LA BASE DE DATOS
			try {
				try (PreparedStatement psDeleteSub = conexion.prepareStatement
						("DELETE FROM Subjects WHERE id = ?")) {
					psDeleteSub.setString(1, id);
					if(psDeleteSub.executeUpdate() > 0) {
						subjects.remove(id);
						
					}
				}
			} catch(SQLException ex) {
				request.getSession().setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "No se ha encontrado la titulación");
		}
	}
}
