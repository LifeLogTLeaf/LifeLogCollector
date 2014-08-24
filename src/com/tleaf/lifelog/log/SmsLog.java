package com.tleaf.lifelog.log;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.text.format.Time;

import com.tleaf.lifelog.model.Sms;
import com.tleaf.lifelog.util.Mylog;
import com.tleaf.lifelog.util.Util;
//import android.provider.Telephony.Sms;
import com.tleaf.lifelog.util.Preference;

public class SmsLog {

	private Context mContext;
	private long baseTime;
	
	public SmsLog() {
	}


	public SmsLog(Context context) {
		mContext = context;
	}

	public ArrayList<Sms> collectSms() {
	
		Mylog.i("baseTime 초기값", ""+baseTime);

		if(baseTime == 0) {
			Preference pref = new Preference(mContext);
			baseTime = pref.getLongPref("installTime");
			Mylog.i("baseTime 초기화", Util.formatLongTime(baseTime));
		}
		
		ArrayList<Sms> arr = new ArrayList<Sms>();

		ContentResolver cr = mContext.getContentResolver();
//		Cursor cursor = cr.query(Uri.parse("content://sms/inbox"),null,null,null,null);

		Cursor cursor = cr.query(Uri.parse("content://sms"), null, updateTime(), null, "date"+ " DESC");



		int nameidx = cursor.getColumnIndex("address");
		int dateidx = cursor.getColumnIndex("date");
		int bodyidx = cursor.getColumnIndex("body");

		//		StringBuilder result = new StringBuilder();
		//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
		//		result.append("총 기록 개수 : " + cursor.getCount() + "개\n");
		//		int count = 0;
		if(cursor.getCount() != 0) {
			cursor.moveToPosition(-1);
			while (cursor.moveToNext()) {
				Sms sms = new Sms();
				sms.setAddress(cursor.getString(nameidx));
				sms.setDate(cursor.getLong(dateidx));
				sms.setBody(cursor.getString(bodyidx));
				//			String sdate = formatter.format(new Date(date));
				//			result.append(sdate + ",");
				//			result.append(body + "\n\n");

				arr.add(sms);
				// 최대 100개까지만
				//			if (count++ == 100) {
				//				break;
				//			}

			}
		}
		cursor.close();
		return arr;
	}

	public String updateTime() {

		String where = android.provider.Telephony.Sms.DATE + " >= " + baseTime;
		baseTime += 5;
		
		// Today Message
/*		Time time = new Time(Time.getCurrentTimezone());
		time.setToNow();
		time.monthDay--;
		time.hour = time.minute = time.second = 0;
		time.normalize(false);
		String where = CallLog.Calls.DATE + " > " + time.toMillis(false);
*/		return where;
	}
}
