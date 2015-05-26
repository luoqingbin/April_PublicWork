package com.lwh.search.service;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lwh.search.database.MyDatabaseOpenHelper;

public class SearchService {
	
	private MyDatabaseOpenHelper helper;
	
	public SearchService(Context context){
		this.helper = new MyDatabaseOpenHelper(context);
	}
	/**
	 * 查找
	 * @param context
	 * @param name
	 */
	public ArrayList<String> findAll(){
		ArrayList<String> keywords = new ArrayList<String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor =  db.rawQuery("select * from keyword",null);
		while(cursor.moveToNext()){
			keywords.add(cursor.getString(0));
		}
		cursor.close();
		db.close();
		return keywords;
	}
	/**
	 * 保存
	 * @param context
	 * @param name 搜索的关键字
	 */
	public void save(String name){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("insert into keyword values(?)",new String[]{name});
		db.close();
	}
}
