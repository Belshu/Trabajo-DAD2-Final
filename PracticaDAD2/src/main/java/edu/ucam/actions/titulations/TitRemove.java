package edu.ucam.actions.titulations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;

import edu.ucam.actions.Action;
import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.domain.Titulation;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TitRemove extends Action{

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request);
		
		if(logged) {
			String idTit = request.getParameter(Parameters.ID_TIT);
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			
			try {
				if(idTit != null) {
					Hashtable <String, Titulation> titulations = (Hashtable <String, Titulation>) request.getServletContext().getAttribute(Attributes.TITULACIONES);
					tituRemove(titulations, idTit, request, conexion);
				}
			} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/titIndex.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	
	// BUSCA TITULATIÓN Y LA ELIMINA
		private void tituRemove(Hashtable <String, Titulation> titulations, String id, HttpServletRequest request, Connection conexion) {
			if(titulations.containsKey(id)) {
				titulations.remove(id);
				
				// INSERTAR EN LA BASE DE DATOS
				try {
					try (PreparedStatement psDeleteTitu = conexion.prepareStatement
							("DELETE FROM Titulations WHERE id = ?")) {
						psDeleteTitu.setString(1, id);
						psDeleteTitu.executeUpdate();
					}
				} catch(SQLException ex) {
					request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
				}
			} else {
				request.setAttribute(Attributes.ERROR_MSG, "No se ha encontrado la titulación");
			}
		}
		
}
