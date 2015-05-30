package com.tz.autocomplete.util;

import android.content.ContentValues;
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

	@Override
	public void onCreate(SQLiteDatabase db) {
         db.beginTransaction();
         try{
        	 //创建表
        	 db.execSQL("create table country(_id integer primary key autoincrement,name varchar(20))");
             //插入数据
        	 ContentValues values=new ContentValues();
        	 values.put("name", "Cape Verde");
        	 db.insert("country", null, values);
        	 
        	 values.clear();
        	 values.put("name", "Cuba");
        	 db.insert("country", null, values);
        	 
        	 values.clear();
        	 values.put("name", "Congo");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "Colombia");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "Costa Rica");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "Canada");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "Cambodia");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "China");
        	 db.insert("country", null, values);
        	 values.clear();
        	 values.put("name", "Cameroo");
        	 db.insert("country", null, values);
        	 db.setTransactionSuccessful();
        	 Log.i("INFO", "第一次创建数据库成功");
         }catch(Exception e){
        	 e.printStackTrace();
         }finally{
        	 db.endTransaction();
         }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}

}
