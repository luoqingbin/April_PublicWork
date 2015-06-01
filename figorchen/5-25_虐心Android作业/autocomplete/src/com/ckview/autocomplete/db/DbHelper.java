package com.ckview.autocomplete.db;

import java.util.ArrayList;
import java.util.List;

import com.ckview.utils.db.bean.Word;
import com.ckview.utils.db.dao.WordDAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL("create table words(word char(40) not null, _id integer primary key autoincrement)");
			List<Word> words = new ArrayList<Word>();
			words.add(new Word("access"));
			words.add(new Word("absolut"));
			words.add(new Word("advertise"));
			words.add(new Word("adc"));
			words.add(new Word("alibaba"));
			words.add(new Word("A"));
			WordDAO dao = new WordDAO(db);
			dao.insert(words);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
