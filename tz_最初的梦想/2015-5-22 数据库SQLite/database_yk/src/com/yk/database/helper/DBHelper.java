package com.yk.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 数据库操作辅佐类
 * @author yk
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//新建或打开数据库
		//db=this.openOrCreateDatabase("yk_data.db", Context.MODE_PRIVATE, null);
		//编写创建表的sql语句
		String createtablesql="create table student(_id integer primary key autoincrement,name varchar(20),age integer)";
		//创建学生表
		db.execSQL(createtablesql);
		//插入数据
		String insertSql="insert into student values(null,?,?)";
		db.execSQL(insertSql,new Object[]{"张三",20});
		db.execSQL(insertSql,new Object[]{"李四",30});
		db.execSQL(insertSql,new Object[]{"王五",40});
		db.execSQL(insertSql,new Object[]{"赵六",50});
		Log.i("yk","创建数据库成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
