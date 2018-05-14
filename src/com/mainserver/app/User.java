package com.mainserver.app;

public class User {
	private String email;
	private String password;
	private int id;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public User(int id, String email, String password) {
		this.setId(id);
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
