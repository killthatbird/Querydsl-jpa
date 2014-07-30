package com.tja.domain;

import javax.persistence.Embeddable;


@Embeddable
public class EmailAddress {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
