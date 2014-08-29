package com.tleaf.lifelog.network;

public class DbTaskFactory {

	public static DbAccessInterface createTask(String type){
		DbAccessInterface db = null;
		if(type.equals("serverTask")){
			db = new ServerTask();
		}else if(type.equals("CouchDBLiteTask")){
			db = new CouchDBLiteTask();
		}else if(type.equals("CouchDBTask")){
			
		}
		return db;
	}
}
