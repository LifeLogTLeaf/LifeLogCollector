package com.tleaf.lifelog.model;

import java.util.Map;

public class Call extends Lifelog{
	private String name;
	private String number;
	private String callType;
	private long date;
	private int duration;

	public Call() {

	}

	public Call(String name, String number, String callType, long date, int duration) {
		this.name = name;
		this.number = number;
		this.callType = callType;
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
	
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
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


	
//	int nameidx = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
//	int dateidx = cursor.getColumnIndex(CallLog.Calls.DATE);
//	int numidx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
//	int duridx = cursor.getColumnIndex(CallLog.Calls.DURATION);
	// int typeidx = cursor.getColumnIndex(CallLog.Calls.TYPE);
}
