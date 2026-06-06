package edu.ucam.domain;

import java.util.Hashtable;

public class Subject {
	private String id, nombre;
	private int credits;
	private final int maxStudents = 300;
	
	private Hashtable<String, Student> students;
	private String idTit;
	
	public Subject(String id, String idTit, String nombre, int credits) {
		this.id = id;
		this.idTit = idTit;
		this.nombre = nombre;
		this.credits = credits;
		this.students = new Hashtable<>();
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getIdTit() {
		return idTit;
	}
	
	public void setIdTit(String idTit) {
		this.idTit = idTit;
	}
	
	// GESTIÓN DE ESTUDIANTES
	public boolean addStudent(Student student) {
		if(students != null && students.size() < maxStudents) {
			students.put(student.getUsername(), student);
			return true;
		}
		return false;
	}
	
	public boolean removeStudent(Student student) {
		if(students != null && students.containsKey(student.getUsername())) {
			students.remove(student.getUsername());
			return true;
		}
		
		return false;
	}
	
	public Student searchStudent(String username) {
		if(!students.isEmpty()) {
			for(Student s : students.values()) {
				if(s.getUsername().equals(username)) return s;
			}
		}
		
		return null;
	}
	
	public boolean studentSearched(String username) {
		if(!students.isEmpty()) {
			for(Student s : students.values()) {
				if(s.getUsername().equals(username)) return true;
			}
		}
		
		return false;
	}
}
