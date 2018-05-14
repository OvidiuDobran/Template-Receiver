package com.mainserver.app;

public class Problem {
	private int id;
	private String date;
	private User user;
	private String description;
	private String longitude;
	private String latitude;
	private Status status;
	private Receiver receiver;
	private static int idGenerated=0;;

	public Problem(String date, User user, String description, String longitude, String latitude, Status status) {
		this.status = status;
		this.setDate(date);
		this.user = user;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.receiver=null;
	}
	
	public Problem(int id, String date, User user, String description, String longitude, String latitude, Status status) {
		setId(id);
		this.status = status;
		this.setDate(date);
		this.user = user;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.receiver=null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String string) {
		this.date = string;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

}
