package com.tleaf.lifelog.cardsms;

import com.tleaf.lifelog.model.Sms;


public interface Parser {
	public void parse(String company, Sms sms);
}
