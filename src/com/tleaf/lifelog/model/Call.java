package com.tleaf.lifelog.model;

import java.util.Map;

public class Call extends Lifelog{
	private String name;
	private String date;
	private String number;
	//2014.08.20 by young
	// 타입 속성은 라이프로그 전체에 걸쳐 쓰이는데 이거 이름 변경이 필요할것 같은데..
	// 지금 데이터 모델링 명세서에는 타입은 라이프로그가 어떤 타입인가를 지정해주는건데.
	// 이 타입은 모야 ?.
	//private String type; 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	/*
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	*/
	@Override
	public void setMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("name", this.name);
		map.put("date", this.date);
		map.put("number", this.number);
		map.put("longitude", super.getLongitude());
		map.put("latitude", super.getLatitude());
		map.put("locationTime", super.getLocationTime());
		map.put("type", super.getType());
		map.put("logTime", super.getLogTime());
	}

	
//	int nameidx = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
//	int dateidx = cursor.getColumnIndex(CallLog.Calls.DATE);
//	int numidx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
//	int duridx = cursor.getColumnIndex(CallLog.Calls.DURATION);
	// int typeidx = cursor.getColumnIndex(CallLog.Calls.TYPE);
}
