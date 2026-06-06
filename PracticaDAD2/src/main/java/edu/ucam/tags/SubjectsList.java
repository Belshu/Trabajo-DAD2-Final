 package edu.ucam.tags;

import java.util.Hashtable;

import edu.ucam.config.ActionID;
import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.domain.Subject;
import edu.ucam.domain.Teacher;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

public class SubjectsList extends BodyTagSupport{
	private static final long serialVersionUID = 1L;

	// HACER LOGICA PARA IMPRIMIR ASIGNATURAS CON SUS OPCIONES DE MODIFICAR Y ELIMINAR 
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		try {
			Hashtable <String, Subject> subjects = 
					(Hashtable <String, Subject>) pageContext.getServletContext().getAttribute(Attributes.ASIGNATURAS);
			
			String ctx = pageContext.getServletContext().getContextPath();
			
			if(subjects != null) {
				if(subjects.size() > 0) {
					for(Subject s : subjects.values()) {
						pageContext.getOut().println("<p>ID: " + s.getId() + " | NOMBRE: " + s.getNombre() + "</p>" +
								" <a href='" + ctx + "/crud/modifiers/subModify.jsp?" + Parameters.ID_SUB + "=" + s.getId() + "'>Modificar</a>" +
								" | <a href='" + ctx + "/Control?" + Parameters.ACTION_ID + "=" + ActionID.REMOVESUB +
								"&" + Parameters.ID_SUB + "=" + s.getId() + "'>Eliminar</a></p>");
					}
				} else {
					pageContext.getOut().println("<p>Lista vacía!</p>");
				}
			} else {
				pageContext.getOut().println("<p>Lista no disponible!</p>");
			}
		} catch(Exception ex) {
			System.out.println("SubjectsList -> " + ex.getMessage());
		}
		
		return SKIP_BODY;
	}
}