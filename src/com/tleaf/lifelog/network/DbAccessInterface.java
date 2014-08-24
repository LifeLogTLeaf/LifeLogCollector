package com.tleaf.lifelog.network;

import com.tleaf.lifelog.model.Lifelog;

public interface DbAccessInterface {
	public void getData(DbAccessOption option);
	public void postData(DbAccessOption option, Lifelog document);
	
}
