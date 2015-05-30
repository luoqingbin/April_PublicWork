package com.tz.database.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	// 创建表
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try{
		db.execSQL("create table students(_sid integer primary key autoincrement,"
				+ "name varchar(20),classname varchar(20))");
		db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		Log.i("INFO", "第一次创建数据库成功");
	}

	// 数据库版本变化时会调用
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("INFO", "数据库版本更新");
		db.beginTransaction();
		try{
		db.execSQL("create table products(_pid integer primary key autoincrement,"
				+ "productName varchar(20),price float)");
		db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}

}
