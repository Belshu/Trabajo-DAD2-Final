package edu.ucam.listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.ucam.config.Attributes;
import edu.ucam.domain.Admin;
import edu.ucam.domain.Student;
import edu.ucam.domain.Subject;
import edu.ucam.domain.Titulation;
import edu.ucam.domain.User;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitializeContexts implements ServletContextListener{

	/**
	 * MÁS ADELANTE: GUARDAR EN BBDD LOS HASHTABLE
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("InitializeContexts -> destruyendo contexto...");
		
		
		Hashtable <String, User> usuarios = (Hashtable <String, User>) sce.getServletContext().getAttribute(Attributes.USUARIOS);
		Hashtable <String, Titulation> titulaciones = (Hashtable <String, Titulation>) sce.getServletContext().getAttribute(Attributes.TITULACIONES);
		Hashtable <String, Subject> subjects = (Hashtable <String, Subject>) sce.getServletContext().getAttribute(Attributes.ASIGNATURAS);
		
		Connection conexion = (Connection) sce.getServletContext().getAttribute(Attributes.CONEXION);
		
		try {
			try {
				
				// ACTUALIZAR USUARIOS -----------------------------------------------------------------------------------------
				if(usuarios != null) {
					try (PreparedStatement psDelete = conexion.prepareStatement("DELETE FROM users WHERE username <> 'admin'")) {
                        psDelete.executeUpdate();
                    }
					
					try (PreparedStatement psInsert = conexion.prepareStatement(
							"INSERT INTO users (username, password, type) VALUES (?, ?, ?)")) {
		                for (User u : usuarios.values()) {
		                    psInsert.setString(1, u.getUsername());
		                    psInsert.setString(2, u.getPassword());
		                    psInsert.setString(3, u.getType());
		                    psInsert.addBatch();
		                }
		                psInsert.executeUpdate();
		            }
				}
				
				// ACTUALIZAR TITULACIONES -----------------------------------------------------------------------------------------
				if(titulaciones != null) {
					try (PreparedStatement psDelete = conexion.prepareStatement("DELETE FROM titulations")) {
                        psDelete.executeUpdate();
                    }

                    try (PreparedStatement psInsert = conexion.prepareStatement(
                            "INSERT INTO titulations (id, nombre) VALUES (?, ?)")) {

                        for (Titulation t : titulaciones.values()) {
                            psInsert.setString(1, t.getId());
                            psInsert.setString(2, t.getNombre());
                            psInsert.addBatch();
                        }

                        psInsert.executeUpdate();
                    }
				}
				
				// ACTUALIZAR ASIGNATURAS -----------------------------------------------------------------------------------------
				if(subjects != null) {
					try (PreparedStatement psDelete = conexion.prepareStatement("DELETE FROM Subjects")){
						psDelete.executeUpdate();
					}
					
					try (PreparedStatement psInsert = conexion.prepareStatement(
                            "INSERT INTO subjects (id, tit_id, nombre, credits) VALUES (?, ?, ?, ?)")) {

                        for (Subject s : subjects.values()) {
                            psInsert.setString(1, s.getId());
                            psInsert.setString(2, s.getIdTit());
                            psInsert.setString(3, s.getNombre());
                            psInsert.setInt(4, s.getCredits());
                            psInsert.addBatch();
                        }

                        psInsert.executeUpdate();
                    }
				}
			} catch (SQLException ex) {
	            System.out.println("InitializeContexts -> " + ex.getMessage());
	        }
		} catch(Exception ex) {
			System.out.println("InitializeContexts -> " + ex.getMessage());
		}
	}

	
	/**
	 * INICIALIZACIÓN DE HASHTABLES Y ASIGNACIÓN DE SUS ATRIBUTOS. TAMBIÉN AÑADIDO UN EJEMPLO DE CADA UNO
	 * */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("InitializeContexts -> Iniciando contextos..." + sce.getServletContext().getContextPath());
		
		Hashtable <String, User> usuarios = new Hashtable<>();
		Hashtable <String, Titulation> titulaciones = new Hashtable<>();
		Hashtable <String, Subject> subjects = new Hashtable<>();
		
		/* PONER USUARIOS MEDIANTE CÓDIGO --------------------------------------------------------- 
		Admin admin = new Admin("admin", "admin");
		Student student = new Student("alumno1", "pass");
		Titulation tit = new Titulation("0", "TITULACION 0");
		
		usuarios.put(admin.getUsername(), admin);
		usuarios.put(student.getUsername(), student);
		titulaciones.put(tit.getId(), tit);
		titulaciones.put("1", new Titulation("1", "TITULACION 1"));
		titulaciones.put("2", new Titulation("2", "TITULACION 2"));
		titulaciones.put("3", new Titulation("3", "TITULACION 3"));
		*/
		
		// INICIALIZAR USUARIOS DE BBDD --------------------------------------------------------- 
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/dad2_24420162G_48845233H");
			Connection conexion = ds.getConnection();
			
			
			// EXTRAER DATOS DE USUARIOS --------------------------------------------------------
			PreparedStatement ps = conexion.prepareStatement("SELECT * FROM Users");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {				
				if(rs.getString("type").equals("ADMIN")) {
					usuarios.put(rs.getString("username"), new Admin(rs.getString("username"), rs.getString("password")));
				} else if(rs.getString("type").equals("STUDENT")) {
					usuarios.put(rs.getString("username"), new Student(rs.getString("username"), rs.getString("password")));
				}
			}
			ps.executeBatch();

			
			// EXTRAER DATOS DE TITULACIONES --------------------------------------------------------
			PreparedStatement ps2 = conexion.prepareStatement("SELECT * FROM Titulations");
			rs = ps2.executeQuery();
			
			while(rs.next()) {
				titulaciones.put(rs.getString("id"), new Titulation(rs.getString("id"), rs.getString("nombre")));
			}
			ps2.executeBatch();
			
			
			// EXTRAER DATOS DE ASIGNATURAS --------------------------------------------------------
			PreparedStatement ps3 = conexion.prepareStatement("SELECT * FROM Subjects");
			rs = ps3.executeQuery();
			
			while(rs.next()) {
				subjects.put(rs.getString("id"), 
						new Subject(rs.getString("id"), rs.getString("tit_id"), rs.getString("nombre"), rs.getInt("creditos")));
			}
			ps3.executeBatch();

			sce.getServletContext().setAttribute(Attributes.CONEXION, conexion);
		} catch(NamingException ex) {
			System.out.println("InitializeContext -> " + ex.getMessage());
		} catch(SQLException ex) {
			System.out.println("InitializeContext -> " + ex.getMessage());
		}
		
		sce.getServletContext().setAttribute(Attributes.USUARIOS, usuarios);
		sce.getServletContext().setAttribute(Attributes.TITULACIONES, titulaciones);
		sce.getServletContext().setAttribute(Attributes.ASIGNATURAS, subjects);
	}
}
