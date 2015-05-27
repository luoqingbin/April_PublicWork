package com.example.cursoradapter.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public MySQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL("create table words(_id integer primary key autoincrement,word varchar(30))");
			this.addWords(db, "adapter");
			this.addWords(db, "android");
			this.addWords(db, "buffer");
			this.addWords(db, "bind");
			this.addWords(db, "cursor");
			this.addWords(db, "care");
			this.addWords(db, "come");
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	
	public void addWords(SQLiteDatabase db,String word){
		ContentValues values=new ContentValues();
		values.put("word", word);
		db.insert("words", null, values);
	}

}
