package edu.ucam.tags;

import java.util.Hashtable;

import edu.ucam.config.ActionID;
import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.domain.Titulation;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

public class TitulationsList extends BodyTagSupport{
	private static final long serialVersionUID = 1L;

	// HACER LOGICA PARA IMPRIMIR TITULACIONES CON SUS OPCIONES DE MODIFICAR Y ELIMINAR 
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		try {
			Hashtable <String, Titulation> titulations = (Hashtable <String, Titulation>)
					pageContext.getServletContext().getAttribute(Attributes.TITULACIONES);
			
			String ctx = pageContext.getServletContext().getContextPath();
			
			if(titulations != null) {
				if(titulations.size() > 0) {
					
					for(Titulation t : titulations.values()) {
						pageContext.getOut().println("<p>ID: " + t.getId() + " | NOMBRE: " + t.getNombre() + "</p>" +
								" <a href='" + ctx + "/crud/modifiers/titModify.jsp?" + Parameters.ID_TIT + "=" + t.getId() + "'>Modificar</a>" +
								" | <a href='" + ctx + "/Control?" + Parameters.ACTION_ID + "=" + ActionID.REMOVETIT +
								"&" + Parameters.ID_TIT + "=" + t.getId() + "'>Eliminar</a></p>");
					}
				} else {
					pageContext.getOut().println("<p>Lista vacía!</p>");
				}
			} else {
				pageContext.getOut().println("<p>Lista no disponible!</p>");
			}
		} catch(Exception ex) {
			System.out.println("ListTitulations -> " + ex.getMessage());
		}
		
		return SKIP_BODY;
	}
}
