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
		//ÊÂÎï¿ªÆô
		db.beginTransaction();
		try {
			CreateSQLUtil stuCSU=new CreateSQLUtil(db);
			stuCSU.createDatabase(Student.class);
			stuCSU.createDatabase(Classes.class);
			stuCSU.save(new Student("²âÊÔ1", 1,1));
			stuCSU.save(new Student("²âÊÔ2", 1,2));
			stuCSU.save(new Student("²âÊÔ3", 1,2));
			stuCSU.save(new Student("²âÊÔ11", 0,3));
			stuCSU.save(new Student("²âÊÔ12", 0,2));
			stuCSU.save(new Student("²âÊÔ13", 0,2));
			stuCSU.save(new Student("²âÊÔ21", 1,3));
			stuCSU.save(new Student("²âÊÔ22", 1,2));
			stuCSU.save(new Student("²âÊÔ23", 0,2));
			stuCSU.save(new Student("²âÊÔ31", 1,3));
			stuCSU.save(new Student("²âÊÔ32", 1,2));
			stuCSU.save(new Student("²âÊÔ33", 1,2));
			
			stuCSU.save(new Classes("1°à", "2015-4-1"));
			stuCSU.save(new Classes("2°à","2015-5-1"));
			stuCSU.save(new Classes("3°à","2015-6-1"));
			//ÉèÖÃÊÂÎñ³É¹¦
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			//½áÊøÊÂÎñ
			db.endTransaction();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("info","°æ±¾ºÅ´Ó"+oldVersion+"Éı¼¶µ½"+newVersion);
	}
	
	public void deteleDabase(){
		context.deleteDatabase(name);
		Log.i("info","É¾³ı"+name+"¿â");
	}

	
	
}
