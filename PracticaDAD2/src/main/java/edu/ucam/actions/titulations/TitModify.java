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

public class TitModify extends Action{

	@SuppressWarnings({ "unchecked"})
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request);
		
		if(logged) {
			String idTit = request.getParameter(Parameters.ID_TIT);
			String nameTit = request.getParameter(Parameters.NAME_TIT);
			
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			
			try {
				
				if(idTit != null && nameTit != null) {
					Hashtable <String, Titulation> titulations = 
							(Hashtable <String, Titulation>) request.getServletContext().getAttribute(Attributes.TITULACIONES);
					tituModify(titulations, idTit, nameTit, request, conexion);
				} else {
					request.setAttribute(Attributes.ERROR_MSG, "Credenciales incorrectas!");
				}
				
				request.getRequestDispatcher("/crud/titIndex.jsp").forward(request, response);
			} catch(Exception ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/index.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	// BUSCA TITULATION Y LA ELIMINA
	private void tituModify (Hashtable<String, Titulation> titulations, String id, String name, HttpServletRequest request, Connection conexion) {
		if(titulations.containsKey(id)) {
			titulations.get(id).setNombre(name);
				
			try {
				// INSERTAR EN LA BASE DE DATOS
				try (PreparedStatement psUpdateTitu = conexion.prepareStatement
						("UPDATE Titulations SET nombre = ? WHERE id = ?")) {
					psUpdateTitu.setString(1, name);
					psUpdateTitu.setString(2, id);
					
					psUpdateTitu.executeUpdate();
				}
			} catch(SQLException ex) {
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "No se ha encontrado la titulación");
		}
	} 
}
