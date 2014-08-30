package com.tleaf.lifelog.cardsms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.util.Mylog;

public class ParseSms {

	class CardCompany {
		String  cardCompName;
		String  cardCompPhone;

		public CardCompany(String cardCompName, String cardCompPhone) {
			this.cardCompName = cardCompName;
			this.cardCompPhone = cardCompPhone;
		}
	}

	private final String SHINHAN_CARD = "SHINHAN_CARD";
	private final String SHINHAN_CARD_PHONE = "01024504006";
	private final String HANASK_CARD = "HANASK_CARD";
	private final String HANASK_CARD_PHONE = "01026749561";


	private ArrayList<CardCompany> cardCompList;
	private Context mContext;
	
	public ParseSms() {
		init();
	}

	public ParseSms(Context context) {
		mContext = context;
		init();
	}
	
	public Map<String, String> isCardCompany(String phone) {

		Map<String, String> map = new HashMap<String, String>();
		for(int i=0; i<cardCompList.size(); i++) {
			if(phone.equals(cardCompList.get(i).cardCompPhone)) {
				map.put("result", "true");
				map.put("cardCompName", cardCompList.get(i).cardCompName);
				return map;	
			}
		}
		map.put("result", "false");
		return map;
	}

	public void parseCardSms(String company, Sms sms) {
		Parser parser = null;
		switch(company) {
		case SHINHAN_CARD:
			parser = new ShinHanCardParser(mContext);
			break;
		case HANASK_CARD:
			break;
			
		}
		parser.parse(company, sms);
		Mylog.i("parseCardSms", company);
		Mylog.i("parseCardSms body", sms.getBody());
	}

	public void init() {
		cardCompList = new ArrayList<CardCompany>();
		cardCompList.add(new CardCompany(SHINHAN_CARD, SHINHAN_CARD_PHONE));
		cardCompList.add(new CardCompany(HANASK_CARD, HANASK_CARD_PHONE));

	}
}
