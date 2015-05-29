package com.ckview.utils.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ckview.utils.db.bean.Word;

public class WordDAO extends AbstractDAO<Word> {

	@Override
	protected Class<?> getBeanClass() {
		return Word.class;
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "words";
	}

	public WordDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WordDAO(SQLiteDatabase db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

}
