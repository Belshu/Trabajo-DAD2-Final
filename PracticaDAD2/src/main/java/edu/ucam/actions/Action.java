package edu.ucam.actions;

import java.io.IOException;

import edu.ucam.config.Attributes;
import edu.ucam.config.UserTypes;
import edu.ucam.domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class Action {
	public abstract void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// FILTRO
	protected boolean loginFilter(HttpServletRequest request) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(Attributes.LOGGED_USER);
		if(user != null) return true;
		
		return false;
	}
	
	// FILTRO ADMIN
	protected boolean adminFilter(HttpServletRequest request) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute(Attributes.LOGGED_USER);
		if(user != null && user.getType().equals(UserTypes.ADMIN)) {
			return true;
		}
		
		return false;
	}
}
