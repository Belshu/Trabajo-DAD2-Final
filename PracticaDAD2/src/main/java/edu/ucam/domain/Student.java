package edu.ucam.domain;

import edu.ucam.config.UserTypes;

public class Student extends User{
	
	public Student(String username, String password) {
		super(username, password, UserTypes.STUDENT);
	}
}
