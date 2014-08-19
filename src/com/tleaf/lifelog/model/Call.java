package com.tleaf.lifelog.model;

import java.util.Date;

import android.provider.CallLog;

public class Call extends Document{

	private String name;
	private String number;
	private String type;
	private long date;
	private int duration;


	public Call() {

	}

	public Call(String name, String number, String type, long date, int duration) {
		this.name = name;
		this.number = number;
		this.type = type;
		this.date = date;		
		this.duration = duration;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
	
}




