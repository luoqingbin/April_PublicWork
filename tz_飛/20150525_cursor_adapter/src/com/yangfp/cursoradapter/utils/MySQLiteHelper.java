package com.yangfp.cursoradapter.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{

	public MySQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//需要创建表
		db.beginTransaction();
		try{
			db.execSQL("create table words(_id integer primary key autoincrement" +
					",word varchar(20))");
			ContentValues values = new ContentValues();
			values.put("word", "c");
			db.insert("words", null, values);
			
			//第二个
			values.clear();
			values.put("word", "clear");
			db.insert("words", null, values);
			//第三个
			values.clear();
			values.put("word", "cab");
			db.insert("words", null, values);
			//第四个
			values.clear();
			values.put("word", "dab");
			db.insert("words", null, values);
			//第五个
			values.clear();
			values.put("word", "daft");
			db.insert("words", null, values);
			db.setTransactionSuccessful();
			Log.i("INFO", "db create success");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
