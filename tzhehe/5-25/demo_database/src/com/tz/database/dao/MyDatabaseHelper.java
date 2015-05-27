package com.tz.database.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tz.database.demo.Classes;
import com.tz.database.demo.Student;

public class MyDatabaseHelper  extends SQLiteOpenHelper{
	
	private Context context;
	private String name;
	public MyDatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.name=name;
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//���￪��
		db.beginTransaction();
		try {
			CreateSQLUtil stuCSU=new CreateSQLUtil(db);
			stuCSU.createDatabase(Student.class);
			stuCSU.createDatabase(Classes.class);
			stuCSU.save(new Student("����1", 1,1));
			stuCSU.save(new Student("����2", 1,2));
			stuCSU.save(new Student("����3", 1,2));
			stuCSU.save(new Student("����11", 0,3));
			stuCSU.save(new Student("����12", 0,2));
			stuCSU.save(new Student("����13", 0,2));
			stuCSU.save(new Student("����21", 1,3));
			stuCSU.save(new Student("����22", 1,2));
			stuCSU.save(new Student("����23", 0,2));
			stuCSU.save(new Student("����31", 1,3));
			stuCSU.save(new Student("����32", 1,2));
			stuCSU.save(new Student("����33", 1,2));
			
			stuCSU.save(new Classes("1��", "2015-4-1"));
			stuCSU.save(new Classes("2��","2015-5-1"));
			stuCSU.save(new Classes("3��","2015-6-1"));
			//��������ɹ�
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//��������
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("info","�汾�Ŵ�"+oldVersion+"������"+newVersion);
	}
	
	public void deteleDabase(){
		context.deleteDatabase(name);
		Log.i("info","ɾ��"+name+"��");
	}

	
	
}
