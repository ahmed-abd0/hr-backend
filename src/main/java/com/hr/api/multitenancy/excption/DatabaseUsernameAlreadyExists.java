package com.hr.api.multitenancy.excption;

import lombok.Getter;

@Getter
public class DatabaseUsernameAlreadyExists extends RuntimeException  {

	private String fieldName;
	
	private String username;
	
	public DatabaseUsernameAlreadyExists(String fieldName, String username) {
        super("Database username already exists: " + username);
    	this.fieldName = fieldName;
		this.username = username;
    }

    public DatabaseUsernameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
