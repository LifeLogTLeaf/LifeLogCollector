package com.tleaf.lifelog.model;

public class Sms extends Lifelog {
	private String address;
	private long date;
	private String body;

	public Sms() {

	}

	public Sms(String address, long date, String body) {
		this.address = address;
		this.date = date;
		this.body = body;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
