package edu.ucam.domain;

import edu.ucam.config.UserTypes;

public class Admin extends User{

	public Admin(String username, String password) {
		super(username, password, UserTypes.ADMIN);
	}
}
