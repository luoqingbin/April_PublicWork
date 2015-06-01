package com.databasetask.database;

import java.util.ArrayList;
import java.util.List;

import com.databasetask.activity.MainActivity;
import com.databasetask.constant.Constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	private Context context;
	private String name;
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
		this.name = name;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ��������  
		db.beginTransaction();
		try{
			// ����Word��
			db.execSQL(Constant.Database.CREATE_TABLE_SQL);
			// ��ӵ�������
			String [] words = MainActivity.words;
			for (int i = 0; i< words.length; i++) {
				db.insert(Constant.Database.TABLE_NAME, null, setContentValues(words[i]));
			}
			// �ύ����
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// ��������
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/**
	 * ����Text����ContentValues
	 * @param wordText
	 * @return
	 */
	public ContentValues setContentValues(String wordText){
		ContentValues cv = new ContentValues();
		cv.put(Constant.Database.WORD_TEXT_NAME, wordText);
		return cv;
	}
	
	/**
	 * �������е���
	 * @return
	 */
	public List<String> displayAll(){
		List<String> list = new ArrayList<String>();
		Cursor cursor = getReadableDatabase().query(Constant.Database.TABLE_NAME, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getString(cursor.getColumnIndex("_text")));
		}
		return list;
	}
	
	/**
	 * ɾ�����ݿ�
	 */
	public void deleteDatabase(){
		context.deleteDatabase(name);
	}
	
}
