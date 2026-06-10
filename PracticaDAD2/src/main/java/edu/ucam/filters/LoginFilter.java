package edu.ucam.filters;

import java.io.IOException;

import edu.ucam.config.Attributes;
import edu.ucam.domain.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/crud/*", "/Control"})
public class LoginFilter extends HttpFilter implements Filter{
	private static final long serialVersionUID = 1L;
	/**
	 * LOGIN FILTER
	 * IMPIDE ACCEDER A /crud/* SI NO HAY UN USUARIO LOGUEADO
	 */

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// OBTENER USUARIO DE SESIÓN
			User user = (User) request.getSession().getAttribute(Attributes.LOGGED_USER);

			// SI HAY USUARIO -> PERMITIR ACCESO
			if (user != null) {
				chain.doFilter(request, response);
				return;
			}
		} catch(Exception ex) {
			System.out.println("LoginFilter -> " + ex.getMessage());
		}
		
		// SI NO HAY USUARIO -> REDIRIGIR AL LOGIN
		request.getSession().setAttribute(Attributes.ERROR_MSG, "Usuario no logueado!");
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}
}
