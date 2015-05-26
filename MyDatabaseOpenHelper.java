package com.lwh.search.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper{

	public MyDatabaseOpenHelper(Context context) {
		super(context, "mydatabase.db",null,1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE keyword(name varchar(20)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("ALERT TABLE keyword ADD count integer NULL");
	}
}
