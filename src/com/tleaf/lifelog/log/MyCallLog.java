package com.tleaf.lifelog.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.tleaf.lifelog.model.Call;

public class MyCallLog {
	private Context mContext;

	public MyCallLog() {
	}


	public MyCallLog(Context context) {
		mContext = context;
	}

	public ArrayList<Call> collectCall() {
		
		ArrayList<Call> arr = new ArrayList<Call>();

		ContentResolver cr = mContext.getContentResolver();
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,null,null,null,
				CallLog.Calls.DATE + " DESC");

		int nameidx = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateidx = cursor.getColumnIndex(CallLog.Calls.DATE);
		int numidx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int duridx = cursor.getColumnIndex(CallLog.Calls.DURATION);
		int typeidx = cursor.getColumnIndex(CallLog.Calls.TYPE);
		while (cursor.moveToNext()) {

			Call call = new Call();
			call.setName(cursor.getString(nameidx));
			call.setNumber(cursor.getString(numidx));

			int type = cursor.getInt(typeidx);
			String stype;
			switch (type) {
			case CallLog.Calls.INCOMING_TYPE:
				stype = "수신";
				break;
			case CallLog.Calls.OUTGOING_TYPE:
				stype = "발신";
				break;
			case CallLog.Calls.MISSED_TYPE:
				stype = "부재중";
				break;
			case 14:
				stype = "문자보냄";
				break;
			case 13:
				stype = "문자받음";
				break;
			default:
				stype = "기타:" + type;
				break;
			}
			call.setCallType(stype);
			call.setDate(cursor.getLong(dateidx));
			call.setDuration(cursor.getInt(duridx));

			arr.add(call);
		}
		cursor.close();
		
		return arr;
	}
}









//StringBuilder result = new StringBuilder();
//SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
//result.append("총 기록 개수 : " + cursor.getCount() + "개\n");
//int count = 0;



//String sdate = formatter.format(new Date(date));
//result.append(sdate + ",");


//result.append(duration + "초\n");

// 최대 100개까지만
//if (count++ == 100) {
//	break;
//}

