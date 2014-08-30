package com.tleaf.lifelog.model;

import java.util.Date;

public class CardSms  extends Sms{
	
	private String cardCompany;
	private String spendStore;
	private Date spendTime;
	private String spendMoney;
	
	
	public String getSpendStore() {
		return spendStore;
	}
	public void setSpendStore(String spendStore) {
		this.spendStore = spendStore;
	}
	
	
	public Date getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(Date spendTime) {
		this.spendTime = spendTime;
	}
	
	public String getSpendMoney() {
		return spendMoney;
	}
	public void setSpendMoney(String spendMoney) {
		this.spendMoney = spendMoney;
	}
	public String getCardCompany() {
		return cardCompany;
	}
	public void setCardCompany(String cardCompany) {
		this.cardCompany = cardCompany;
	}

	@Override
	public String toString() {
		return 	"cardCompany" + cardCompany + "\n" +  
				"spendStore" + spendStore + "\n" + 
				"spendTime" + spendTime +  "\n" + 
				"spendMoney" + spendMoney;
	}
}

