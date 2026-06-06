package edu.ucam.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;

import edu.ucam.config.Attributes;
import edu.ucam.config.Parameters;
import edu.ucam.config.UserTypes;
import edu.ucam.domain.Admin;
import edu.ucam.domain.Student;
import edu.ucam.domain.User;

/**
 * LOGIN SERVLET
 * COMPRUEBA LAS CREDENCIALES DEL USUARIO Y LO REDIRIGE SEGÚN SU TIPO (ADMIN / STUDENT)
 */

@WebServlet("/RegistryServlet")
public class RegistryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public RegistryServlet() {
        super();
     
    }

	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// OBTENER PARÁMETROS DEL FORMULARIO
			String username = request.getParameter(Parameters.USERNAME);
			String password = request.getParameter(Parameters.PASSWORD);
			String type = request.getParameter(Parameters.USERTYPE); // ADMIN o STUDENT

			// OBTENER LISTA DE USUARIOS DEL CONTEXTO
			Hashtable<String, User> usuarios =
					(Hashtable<String, User>) request.getServletContext().getAttribute(Attributes.USUARIOS);

			// COMPROBAR SI EL USUARIO YA EXISTE
			if (usuarios.containsKey(username)) {
				request.setAttribute(Attributes.ERROR_MSG, "El usuario ya existe");
				request.getRequestDispatcher("registry.jsp").forward(request, response);
				return;
			}
			
			// CREAR NUEVO USUARIO SEGÚN EL TIPO
			User newUser;
			
			if (type.equals(UserTypes.ADMIN)) {
				newUser = new Admin(username, password);
			} else {
				newUser = new Student(username, password);
			}
			
			// AÑADIR USUARIO A LA HASHTABLE
			usuarios.put(username, newUser);
			
			// METERLO A LA BBDD
			Connection conexion = (Connection) request.getServletContext().getAttribute(Attributes.CONEXION);
			PreparedStatement ps = conexion.prepareStatement("INSERT INTO Users (username, password, type)"
					+ " VALUES ('" + newUser.getUsername() + "','" + newUser.getPassword() + "','" + newUser.getType() + "')");
			
			int files = ps.executeUpdate();
			
			if(files > 0) {
				// REDIRIGIR AL LOGIN
				response.sendRedirect("login.jsp");
			} else {
				request.setAttribute(Attributes.ERROR_MSG, "No se pudo registrar el usuario.");
	            request.getRequestDispatcher("registry.jsp").forward(request, response);
			}
		} catch(Exception ex) {
			System.out.println("LoginServlet -> " + ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
