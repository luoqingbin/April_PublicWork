package com.ckview.utils.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.ckview.utils.db.bean.Test;

public class TestDAO extends AbstractDAO<Test> {

	@Override
	protected Class<?> getBeanClass() {
		// TODO Auto-generated method stub
		return Test.class;
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "test";
	}

	public TestDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestDAO(SQLiteDatabase db) {
		super(db);
		// TODO Auto-generated constructor stub
	}

}
