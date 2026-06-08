package edu.ucam.domain;

import edu.ucam.config.UserTypes;

public class Teacher extends User {
	public Teacher(String username, String password) {
		super(username, password, UserTypes.TEACHER);
	}
}