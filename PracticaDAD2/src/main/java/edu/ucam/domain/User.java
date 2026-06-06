package edu.ucam.domain;

public abstract class User {
	private String username, password, type;

	
	// CONSTRUCTOR
	public User(String username, String password, String type) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
	}
	
	
	// GETTERS & SETTERS
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}
}
