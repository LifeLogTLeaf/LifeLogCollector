package com.tleaf.lifelog.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.tleaf.lifelog.model.Location;

public class Util {

	public final static String BOOKMARK = "bookmark";
	public final static String CALL = "call";
	public final static String PHOTO = "photo";
	public final static String SMS = "sms";
	public final static String POSITON = "position";

	
	private final static String RECEIVE ="수신";
	private final static String SEND ="발신";
	
	private final static String TAG = "UTIL";

	public static String formatLongTime(Long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(date);
        return currentDate;
	}
	
	/*public static long getCurrentTime() {
		long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(date);
        return currentDate;
	}


	public static Time convertStringToTime(String stringIime) {

	}


	public static Time convertLongToTime(long longIime) {
		String[] day = {"일", "월", "화", "수", "목", "금", "토"};
		Time time = new Time(Time.getCurrentTimezone());
		time.set(longIime);

		Mylog.i(TAG, "year "+time.year);
		Mylog.i(TAG, "month "+time.month);
		Mylog.i(TAG, "day "+time.monthDay);
		Mylog.i(TAG, "요일 "+ day[time.weekDay]);

		Mylog.i(TAG, "hour"+time.hour);
		Mylog.i(TAG, "minute"+time.minute);
		Mylog.i(TAG, "second"+time.second);
		return time;

	}
	 */	
	public static Location getCurrentPostion() {
		return new Location();
	}

	public static long getCurrentTime() {
		long time = System.currentTimeMillis();
		return time;
	}
	
/*	public static Date getCurrentDate() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		return date;
	}
*/
	//utilDate -> sqlDate
	/*public static java.sql.Date convertUtilToSqlDate(Date date) {
		return new java.sql.Date(0);
		
	}
*/	
	
	public static String convertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
	
	
	//서버에서 받아온 시간을 Date로 바꿈
	public static Date convertStringToDate(String stringDate) {
		Date date = new Date(stringDate);
		return date;
	}
	
	
}
