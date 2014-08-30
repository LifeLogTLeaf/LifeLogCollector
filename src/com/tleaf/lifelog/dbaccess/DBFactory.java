package com.tleaf.lifelog.dbaccess;

import com.tleaf.lifelog.dbaccess.db.CouchDB;
import com.tleaf.lifelog.dbaccess.db.DBInterface;
import com.tleaf.lifelog.dbaccess.db.ServerDB;
import com.tleaf.lifelog.dbaccess.db.TouchDB;


public class DBFactory {
	
	//issue : 잘못됫 값을 넣으면 exception을 발생시키는 예외처리 넣어야한다.
	public static DBInterface createDB(String dest) {
		if (dest.equals("server")) {
			return new ServerDB();
		} else if (dest.equals("couch")) {
			return new CouchDB();
		} else if (dest.equals("touch")) {
			return new TouchDB();
		}
		return null;
	}
}
