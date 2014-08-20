package com.tleaf.lifelog.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tleaf.lifelog.util.Util;

/**
 * 2014.08.20 by 장영진 
 * 이 클래스는 모든 라이프로그 데이터에서 공통적으로 발생하는 속성을 가지고 있는 클래스입니다. 
 * 이 클래스를 상속받은 라이프로그 관련 클래스는 반드시 getMap함수를 구현해야 합니다. 
 * getMap함수가 존재하는 이유는 내부 데이터베이스인 카우치베이스 라이트에 데이터를 삽입할대 
 * Map 객체를 생성해서 데이터를 넣기 때문입니다. 따라서 라이프로그 객체의 멤버변수의 값들을 하나하나
 * 맵에 넣는것이 이 함수의 역할이 됩니다.
 */
public abstract class Document {
	@JsonProperty("_id")
	protected String id;

	@JsonProperty("_rev")
	protected String rev;

	private long uploadtime;
	private String type;

	public Document() {
		this.uploadtime = Util.getCurrentTime();
	}

	public Document(long uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public long getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(long uploadtime) {
		this.uploadtime = uploadtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract void setMap(Map<String, Object> map);
}
