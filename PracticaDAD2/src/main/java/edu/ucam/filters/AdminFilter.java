package edu.ucam.filters;

import java.io.IOException;

import edu.ucam.config.Attributes;
import edu.ucam.config.UserTypes;
import edu.ucam.domain.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/crud/secured/*")
public class AdminFilter extends HttpFilter implements Filter{
	private static final long serialVersionUID = 1L;
	
	/**
	 * ADMIN FILTER
	 * SOLO PERMITE ACCESO A /crud/secured/* A USUARIOS ADMIN
	 */

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// OBTENER USUARIO DE SESIÓN
			User user = (User) request.getSession().getAttribute(Attributes.LOGGED_USER);

			// COMPROBAR SI ES ADMIN
			if (user != null && user.getType().equals(UserTypes.ADMIN)) {
				System.out.println("AdminFilter -> Administrador '" + user.getUsername() + "' accediendo a zona segura");
				chain.doFilter(request, response);
				return;
			}

			System.out.println("AdminFilter -> Acceso denegado");

		} catch (Exception ex) {
			System.out.println("AdminFilter -> " + ex.getMessage());
		}

		// SI NO ES ADMIN -> REDIRIGIR A INDEX GENERAL
		request.getRequestDispatcher("/crud/index.jsp").forward(request, response);
	}
}
