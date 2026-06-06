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

public class SubAdd extends Action{

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
			}
			
			String idTit = request.getParameter(Parameters.ID_TIT);
			
			try {
				if(id != null && name != null && idTit != null && credits > 0) {
					Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
					Hashtable <String, Subject> subjects = 
							(Hashtable <String, Subject>) request.getServletContext().getAttribute(Attributes.ASIGNATURAS);
					
					Subject newSubject = new Subject(id, idTit, name, credits);
					newSub(newSubject, subjects, request, conexion);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/subIndex.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}


	private void newSub(Subject subject, Hashtable <String, Subject> subjects, HttpServletRequest request, Connection conexion) {
		if(subjects.containsKey(subject.getId())) {
			request.setAttribute(Attributes.ERROR_MSG, "La asignatura ya existe");
			return;
		}
		
		
		// INSERTAR EN LA BASE DE DATOS
		System.out.println("SubAdd -> Insertando en la base de datos: " + subject.getId() + "|" + subject.getNombre());
		
		try {
			try (PreparedStatement psInsertSub = conexion.prepareStatement
					("INSERT INTO Subjects (id, tit_id, nombre, creditos) VALUES (?, ?, ?, ?)")) {
				psInsertSub.setString(1, subject.getId());
				psInsertSub.setString(2, subject.getIdTit());
				psInsertSub.setString(3, subject.getNombre());
				psInsertSub.setInt(4, subject.getCredits());
				
				if(psInsertSub.executeUpdate() > 0) {
					subjects.put(subject.getId(), subject);
					System.out.println("SubAdd -> Insertado correctamente!");
				}
			}
		} catch(SQLException ex) {
			request.getSession().setAttribute(Attributes.ERROR_MSG, ex.getMessage());
		}
	}
}
