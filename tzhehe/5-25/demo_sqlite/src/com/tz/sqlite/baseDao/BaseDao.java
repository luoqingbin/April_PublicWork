package com.tz.sqlite.baseDao;

import android.database.sqlite.SQLiteDatabase;

public class BaseDao {
	protected SQLiteDatabase db;
	public BaseDao(SQLiteDatabase db){
		this.db=db;
	}

}
