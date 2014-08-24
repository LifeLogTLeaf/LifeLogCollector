package com.tleaf.lifelog.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tleaf.lifelog.model.Sms;

public class DataManager {

	private Context mContext = null;
	private DBHelper dbHelper = null;

	public DataManager(Context context) {
		mContext = context;
		dbHelper = new DBHelper(mContext);
	}

//	public boolean insertSms(Sms book) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		String sql = "insert into salebook values (" +
//				"null, " +
//				"'" + book.getIsbn() + "', " +
//				"'" + book.getTitle() + "', " +
//				"'" + book.getAuthor() + "', " +
//				"'" + book.getPublisher() + "', " +
//				"'" + book.getRePrice() + "', " +
//				"'" + book.getImage() + "', " +
//				"'" + book.getRegDate() + "', " +
//				"'" + book.getPrice() + "', " +
//				"'" + book.getRating() + "', " +
//				"'" + book.getUniv() + "', " +
//				"'" + book.getMajor() + "', " +
//				"'" + book.getLecture() + "', " +
//				"'" + book.getProfessor() + "', " +
//				"'" + book.getUsedYear() + "', " +
//				"'" + book.getUsedTerm() + "', " +
//				"'" + book.getDealLocation() + "')";		
//		db.execSQL(sql);
//		dbHelper.close();
//		return true;
//	}


//	public boolean updateSms(int saleBookNo, Sms book) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		String sql = "UPDATE salebook SET " +
//				"isbn = '" + book.getIsbn() + "', " +
//				"title = '" + book.getTitle() + "', " +
//				"author = '" + book.getAuthor() + "', " +
//				"publisher = '" + book.getPublisher() + "', " +
//				"reprice = '" + book.getRePrice() + "', " +
//				"image = '" + book.getImage() + "', " +
//				"regDate = '" + book.getRegDate() + "', " +
//				"price = '" + book.getPrice() + "', " +
//				"rating = '" + book.getRating() + "', " +
//				"univ = '" + book.getUniv() + "', " +
//				"major = '" + book.getMajor() + "', " +
//				"lecture = '" + book.getLecture() + "', " +
//				"professor = '" + book.getProfessor() + "', " +
//				"usedYear = '" + book.getUsedYear() + "', " +
//				"usedTerm = '" + book.getUsedTerm() + "', " +
//				"daelLocation = '" + book.getDealLocation() + "' " +			
//				"where salebookno = " + saleBookNo;
//		db.execSQL(sql);
//		dbHelper.close();
//		return true;
//	}

//	public boolean deleteSms(String rcvIsbn) {
//		Log.e("rcvIsbn", rcvIsbn);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		String sql = "delete from salebook where isbn='" + rcvIsbn + "'";
//		db.execSQL(sql);
//		dbHelper.close();
//		return true;
//	}

	public ArrayList<Sms> getSmsList() {
		Log.e("getSmsList", "");

		ArrayList<Sms> arItem = new ArrayList<Sms>();
		SQLiteDatabase db = dbHelper.getReadableDatabase(); 
		String sql = "select * from salebook";

		Cursor cursor = db.rawQuery(sql, null);

		while(cursor.moveToNext()) {
			Sms sms = new Sms();
//			int salebookno = cursor.getInt(0);
//			String isbn = cursor.getString(1);
//			String title = cursor.getString(2);
//			String author = cursor.getString(3);
//			String publisher = cursor.getString(4);
//			String rePrice = cursor.getString(5);
//			String image = cursor.getString(6);
//			String regDate = cursor.getString(7);
//			String price = cursor.getString(8);
//			String rating = cursor.getString(9);
//			String univ = cursor.getString(10);
//			String major = cursor.getString(11);
//			String lecture = cursor.getString(12);
//			String professor = cursor.getString(13);
//			String usedYear = cursor.getString(14);
//			String usedTerm = cursor.getString(15);
//			String dealLocation = cursor.getString(16);

//			Log.e("cursor.getInt(0)", ""+cursor.getInt(0));
//			sms.setSmsNo(salebookno);
//			sms.setIsbn(isbn);
//			sms.setTitle(title);
//			sms.setAuthor(author);
//			sms.setPublisher(publisher);
//			sms.setRePrice(rePrice);
//			sms.setImage(image);
//			sms.setRegDate(regDate);
//			sms.setPrice(price);
//			sms.setRating(rating);
//			sms.setUniv(univ);
//			sms.setMajor(major);
//			sms.setLecture(lecture);
//			sms.setProfessor(professor);
//			sms.setUsedYear(usedYear);
//			sms.setUsedTerm(usedTerm);
//			sms.setDealLocation(dealLocation);
			arItem.add(sms);
		}

		return arItem;
	}	
	public Sms getSms(String rcvIsbn) {
		Log.e("rcvIsbn", rcvIsbn);

		Sms sms = new Sms();

		SQLiteDatabase db = dbHelper.getReadableDatabase(); 
		String sql = "select * from salebook where isbn='" + rcvIsbn + "'";
		Log.e("rcvIsbn", rcvIsbn);

		Cursor cursor = db.rawQuery(sql, null);
		Log.e("cursor null", ""+cursor);
		while(cursor.moveToNext()) {
//			int salebookno = cursor.getInt(0);
//			String isbn = cursor.getString(1);
//			String title = cursor.getString(2);
//			String author = cursor.getString(3);
//			String publisher = cursor.getString(4);
//			String rePrice = cursor.getString(5);
//			String image = cursor.getString(6);
//			String regDate = cursor.getString(7);
//			String price = cursor.getString(8);
//			String rating = cursor.getString(9);
//			String univ = cursor.getString(10);
//			String major = cursor.getString(11);
//			String lecture = cursor.getString(12);
//			String professor = cursor.getString(13);
//			String usedYear = cursor.getString(14);
//			String usedTerm = cursor.getString(15);
//			String dealLocation = cursor.getString(16);
//
//			sms.setSmsNo(salebookno);
//			sms.setIsbn(isbn);
//			sms.setTitle(title);
//			sms.setAuthor(author);
//			sms.setPublisher(publisher);
//			sms.setRePrice(rePrice);
//			sms.setImage(image);
//			sms.setRegDate(regDate);
//			sms.setPrice(price);
//			sms.setRating(rating);
//			sms.setUniv(univ);
//			sms.setMajor(major);
//			sms.setLecture(lecture);
//			sms.setProfessor(professor);
//			sms.setUsedYear(usedYear);
//			sms.setUsedTerm(usedTerm);
//			sms.setDealLocation(dealLocation);
		}
		return sms;
	}	

	public Sms getSmsByNo(int saleBookNo) {
		Log.e("saleBookNo", ""+saleBookNo);
		Sms sms = new Sms();

		SQLiteDatabase db = dbHelper.getReadableDatabase(); 
		String sql = "select * from salebook where salebookno=" + saleBookNo;

		Cursor cursor = db.rawQuery(sql, null);
		Log.e("cursor null", ""+cursor);
		while(cursor.moveToNext()) {
//			int salebookno = cursor.getInt(0);
//			String isbn = cursor.getString(1);
//			String title = cursor.getString(2);
//			String author = cursor.getString(3);
//			String publisher = cursor.getString(4);
//			String rePrice = cursor.getString(5);
//			String image = cursor.getString(6);
//			String regDate = cursor.getString(7);
//			String price = cursor.getString(8);
//			String rating = cursor.getString(9);
//			String univ = cursor.getString(10);
//			String major = cursor.getString(11);
//			String lecture = cursor.getString(12);
//			String professor = cursor.getString(13);
//			String usedYear = cursor.getString(14);
//			String usedTerm = cursor.getString(15);
//			String dealLocation = cursor.getString(16);
//
//			sms.setSmsNo(salebookno);
//			sms.setIsbn(isbn);
//			sms.setTitle(title);
//			sms.setAuthor(author);
//			sms.setPublisher(publisher);
//			sms.setRePrice(rePrice);
//			sms.setImage(image);
//			sms.setRegDate(regDate);
//			sms.setPrice(price);
//			sms.setRating(rating);
//			sms.setUniv(univ);
//			sms.setMajor(major);
//			sms.setLecture(lecture);
//			sms.setProfessor(professor);
//			sms.setUsedYear(usedYear);
//			sms.setUsedTerm(usedTerm);
//			sms.setDealLocation(dealLocation);
		}
		return sms;
	}	
}