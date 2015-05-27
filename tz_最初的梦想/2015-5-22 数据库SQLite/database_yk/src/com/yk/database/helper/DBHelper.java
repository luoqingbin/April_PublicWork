package com.yk.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * ���ݿ����������
 * @author yk
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//�½�������ݿ�
		//db=this.openOrCreateDatabase("yk_data.db", Context.MODE_PRIVATE, null);
		//��д�������sql���
		String createtablesql="create table student(_id integer primary key autoincrement,name varchar(20),age integer)";
		//����ѧ����
		db.execSQL(createtablesql);
		//��������
		String insertSql="insert into student values(null,?,?)";
		db.execSQL(insertSql,new Object[]{"����",20});
		db.execSQL(insertSql,new Object[]{"����",30});
		db.execSQL(insertSql,new Object[]{"����",40});
		db.execSQL(insertSql,new Object[]{"����",50});
		Log.i("yk","�������ݿ�ɹ�");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
