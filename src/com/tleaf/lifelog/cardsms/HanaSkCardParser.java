package com.tleaf.lifelog.cardsms;


import java.util.Date;
import java.util.StringTokenizer;

import android.content.Context;
import android.widget.Toast;

import com.tleaf.lifelog.model.CardSms;
import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.util.Mylog;

public class HanaSkCardParser implements Parser{

	private Context mContext;

	public HanaSkCardParser() {
	}

	public HanaSkCardParser(Context context) {
		mContext = context;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void parse(String company, Sms sms) {
		CardSms cardSms = new CardSms();
		cardSms.setAddress(sms.getAddress());
		cardSms.setDate(sms.getDate());
		cardSms.setCardCompany(company);

		try {
			StringTokenizer tokenizer = new StringTokenizer(sms.getBody());
			tokenizer.nextToken();
			tokenizer.nextToken();

			Date spendTime = new Date();

			String day = tokenizer.nextToken();
			StringTokenizer dayTokenizer = new StringTokenizer(day, "/");
			int month = Integer.parseInt(dayTokenizer.nextToken());
			int date = Integer.parseInt(dayTokenizer.nextToken());
			spendTime.setMonth(month);
			spendTime.setDate(date);

			String time = tokenizer.nextToken();
			StringTokenizer timeTokenizer = new StringTokenizer(time, ":");
			int hour = Integer.parseInt(timeTokenizer.nextToken());
			int minute = Integer.parseInt(timeTokenizer.nextToken());
			spendTime.setHours(hour);
			spendTime.setMinutes(minute);
			cardSms.setSpendTime(spendTime);

			String money = tokenizer.nextToken();
			money = money.trim();
			money = money.replace(",", "");
			money = money.replace("원", "");
			cardSms.setSpendMoney(money);

			cardSms.setSpendStore(tokenizer.nextToken());

			Mylog.i("파싱결과", cardSms.toString());
			Toast.makeText(mContext, "lifelog cardsms\n" + cardSms.toString(), Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


/*	Calendar calendar = Calendars.forTime(message.getRecieved());
transaction = new RTransaction();
transaction.setCard("하나SK체크카드");
transaction.setYear(Integer.toString(calendar.get(Calendar.YEAR)));
transaction.setMonth(Integer.toString(calendar.get(Calendar.MONTH)) + 1);
transaction.setDate(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
transaction.setTimemillis(message.getRecieved());
transaction.setCurrency(RCurrency.KRW);
transaction.setStore(store);
transaction.setAmount(Integer.parseInt(amount));
transaction.setSmsBody(message.getBody());*/


/*
 * <pre>
 * 하나SK체크카드(3*3*)이*행님 03/20 10:16 일시불/ 2,000원/승인/ 브레댄코 역삼점
 * </pre>
 */

/*public class HanaSkCheckCardSmsParser implements SmsParser {
	private RTransaction transaction; 
	@Override
	public RTransaction parse(RSmsMessage message) {
		}
 */