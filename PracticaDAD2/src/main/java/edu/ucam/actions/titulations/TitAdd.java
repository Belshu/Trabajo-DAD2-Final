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

public class TitAdd extends Action{

	@SuppressWarnings("unchecked")
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean logged = super.loginFilter(request);
		if(logged) {
			String idTit = request.getParameter(Parameters.ID_TIT);
			String nameTit = request.getParameter(Parameters.NAME_TIT);
			
			try {
				if(idTit != null && nameTit != null) {
					Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
					Hashtable <String, Titulation> titulations = 
							(Hashtable <String, Titulation>) request.getServletContext().getAttribute(Attributes.TITULACIONES);
					newTitu(titulations, idTit, nameTit, request, conexion);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
			
			if(!response.isCommitted()) request.getRequestDispatcher("/crud/titIndex.jsp").forward(request, response);
		} else {
			request.setAttribute(Attributes.ERROR_MSG, "USUARIO NO LOGUEADO, POR FAVOR INICIA SESION!");
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}
	
	
	// CREAR NUEVA TITULACION Y AÑADIRLO A LA HASHTABLE
		private void newTitu(Hashtable <String, Titulation> titulations, String id, String name, HttpServletRequest request, Connection conexion) {
			if(titulations.containsKey(id)) {
				request.setAttribute(Attributes.ERROR_MSG, "La titulación ya existe");
				return;
			}
			titulations.put(id, new Titulation(id, name));
			
			
			// INSERTAR EN LA BASE DE DATOS
			try {
				
				try (PreparedStatement psInsertTitu = conexion.prepareStatement
						("INSERT INTO Titulations (id, nombre) VALUES (?, ?)")) {
					psInsertTitu.setString(1, id);
					psInsertTitu.setString(2, name);
					
					psInsertTitu.executeUpdate();
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
				request.setAttribute(Attributes.ERROR_MSG, ex.getMessage());
			}
		}
}
