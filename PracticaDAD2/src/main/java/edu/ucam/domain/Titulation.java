package edu.ucam.domain;

public class Titulation {
	private String id, nombre;

	public Titulation(String id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}
}
