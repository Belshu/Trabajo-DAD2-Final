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

public class SubAssignTeacher extends Action {
	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request); 
		
		if (logged) {
			String idSub = request.getParameter(Parameters.ID_SUB); 
			String profUsername = request.getParameter(Parameters.USERNAME); 
			
			// Si el profesor viene vacío ("-- Selecciona Profesor --"), lo interpretamos como desasignar (NULL)
			if (profUsername != null && profUsername.trim().isEmpty()) {
				profUsername = null;
			}
			
			// Obtenemos la conexión clásica que habéis dado en clase
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			
			try {
				if (idSub != null) {
					Hashtable<String, Subject> subjects = (Hashtable<String, Subject>) request.getServletContext().getAttribute(Attributes.ASIGNATURAS);
					
					// Llamamos al método para actualizar BD y memoria
					assignTeacher(idSub, profUsername, subjects, request, conexion);
					
				} else {
					request.setAttribute(Attributes.ERROR_MSG, "Faltan parámetros obligatorios (ID Asignatura).");
				}
			} catch (Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			
			// Redirigimos de vuelta a la página de asignaturas
			if (!response.isCommitted()) {
				request.getRequestDispatcher("/crud/subIndex.jsp").forward(request, response);
			}
		} else {
			request.getSession().setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	private void assignTeacher(String idSub, String profUsername, Hashtable<String, Subject> subjects, HttpServletRequest request, Connection conexion) {
		// Comprobación de que la asignatura existe en el mapa
		if (subjects == null || !subjects.containsKey(idSub)) {
			request.setAttribute(Attributes.ERROR_MSG, "La asignatura especificada no existe en la memoria.");
			return;
		}

		// Actualizar en la base de datos
		try {
			try (PreparedStatement psAssign = conexion.prepareStatement(
					"UPDATE Subjects SET prof_username = ? WHERE id = ?")) {
				
				// Permitimos NULL por si queremos quitar al profesor
				if (profUsername != null) {
					psAssign.setString(1, profUsername);
				} else {
					psAssign.setNull(1, java.sql.Types.VARCHAR);
				}
				
				psAssign.setString(2, idSub);

				if (psAssign.executeUpdate() > 0) {
					// Actualizamos la memoria
					subjects.get(idSub).setProfUsername(profUsername);
					System.out.println("SubAssignTeacher -> ¡Profesor asignado con éxito!");
				} else {
					request.setAttribute(Attributes.ERROR_MSG, "No se encontró la asignatura en la base de datos.");
				}
			}
		} catch (SQLException ex) {
			request.setAttribute(Attributes.ERROR_MSG, "Error SQL: " + ex.getMessage());
		}
	}
}