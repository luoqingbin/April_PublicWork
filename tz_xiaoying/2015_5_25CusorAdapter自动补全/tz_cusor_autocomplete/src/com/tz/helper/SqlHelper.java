package com.tz.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	public SqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//开启事务
		db.beginTransaction();
		try {
			String sql = "create table words(_id integer primary key autoincrement,word vachar(50))";
			db.execSQL(sql);
			ContentValues content=new ContentValues();
			//第一个
			content.put("word","arror");
			db.insert("words",null, content);
			content.clear();
			//第二个
			content.put("word","hello");
			db.insert("words",null, content);
			content.clear();
			//第三个
			content.put("word","Money");
			db.insert("words",null, content);
			content.clear();
			//第四个
			content.put("word","Sundy");
			db.insert("words",null, content);
			content.clear();
			//第四个
			content.put("word","sunshine");
			db.insert("words",null, content);
			content.clear();
			//第五个
			content.clear();
			content.put("word","Apirl");
			db.insert("words",null, content);
			//第五个
			content.clear();
			content.put("word","hi");
			db.insert("words",null, content);
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			db.endTransaction();
		} 

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
