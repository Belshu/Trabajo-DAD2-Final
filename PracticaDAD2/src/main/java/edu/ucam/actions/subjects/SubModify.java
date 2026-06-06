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

public class SubModify extends Action{

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request);
		
		if(logged) {
			String id = request.getParameter(Parameters.ID_SUB);
			String name = request.getParameter(Parameters.NAME_SUB);
			int credits = 0;
			try {
				credits = Integer.parseInt(request.getParameter(Parameters.CREDITS_SUB));
			} catch(NumberFormatException ex) {
				request.setAttribute(Attributes.ERROR_MSG, "CREDITOS INCORRECTOS!");
				response.sendRedirect(request.getContextPath() + "/subIndex.jsp");
				return;
			}
			
			String idTit = request.getParameter(Parameters.ID_TIT);
			
			try {
				
				if(id != null && name != null && idTit != null && credits > 0) {
					Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
					Hashtable <String, Subject> subjects = 
							(Hashtable <String, Subject>) request.getServletContext().getAttribute(Attributes.ASIGNATURAS);
					
					Subject newSubject = new Subject(id, idTit, name, credits);
					subModify(newSubject, subjects, request, conexion);
				}
				
				request.getRequestDispatcher("/crud/subIndex.jsp").forward(request, response);
			} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/subIndex.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
	
	private void subModify(Subject subject, Hashtable<String, Subject> subjects, HttpServletRequest request,
			Connection conexion) {
		if(subjects.containsKey(subject.getId())) {
			
			// INSERTAR EN LA BASE DE DATOS
			try {
				try (PreparedStatement psUpdateSub = conexion
						.prepareStatement("UPDATE Subjects SET tit_id = ?, nombre = ?, creditos = ? WHERE id = ?")) {
					psUpdateSub.setString(1, subject.getIdTit());
					psUpdateSub.setString(2, subject.getNombre());
					psUpdateSub.setInt(3, subject.getCredits());
					psUpdateSub.setString(4, subject.getId());

					if (psUpdateSub.executeUpdate() > 0) {
						subjects.replace(subject.getId(), subject);
					}
				}
			} catch (SQLException ex) {
				request.getSession().setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
		}
	}
}
