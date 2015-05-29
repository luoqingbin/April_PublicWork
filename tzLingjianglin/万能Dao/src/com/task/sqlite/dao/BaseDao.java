package com.task.sqlite.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.task.sqlite.db.MySQLiteHelper;
import com.task.sqlite.utils.Item;
import com.task.sqlite.utils.Key;
import com.task.sqlite.utils.Orm;
import com.task.sqlite.utils.TempleteConfig;

public class BaseDao <T>{
	private MySQLiteHelper helper;
	
	private Class cls;   //T对应的实体类的class
	public BaseDao(MySQLiteHelper helper,Class cls) {
		super();
		this.helper = helper;
		this.cls=cls;
	}


	/**
	 * 对象的插入
	 * @param t
	 * @return
	 */
	public void insert(T t){
		SQLiteDatabase db=helper.getWritableDatabase();
		Orm orm=TempleteConfig.ormMaps.get(t.getClass().getSimpleName()+".orm.xml");
		if(orm==null) return;
		Key key=orm.getKey();
		if(key==null) return;
		List<Item> list=orm.getItemList();
		if(list==null || list.size() <=0) return;
		ContentValues values=new ContentValues();
		//如果主键不是自增长
		if(!"true".equals(key.getIdentity())){
			//values.put(key, value);
			try {
				String keyName=key.getColumnName();  //得到字段的名称
				Field keyField=t.getClass().getDeclaredField(key.getProperty());
				keyField.setAccessible(true);
				Object obj=keyField.get(t);
				Method keyMethod=values.getClass().getDeclaredMethod("put",  new Class[]{String.class,Class.forName(key.getType())});
				keyMethod.invoke(values, new Object[]{keyName,obj});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(list!=null && list.size()>0){
			for(Item item:list){
				try {
					String itemName=item.getColumnName();  //得到字段的名称
					Field itemField=t.getClass().getDeclaredField(item.getProperty());
					itemField.setAccessible(true);
					Object obj=itemField.get(t);
					Method itemMethod=values.getClass().getDeclaredMethod("put", new Class[]{String.class,Class.forName(item.getType())});
					itemMethod.invoke(values, new Object[]{itemName,obj});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		db.insert(orm.getTableName(), null, values);
	}
	/**
	 * 得到所有的记录集
	 * @return
	 */
	public List<T> getAll(){
		List<T> list=new ArrayList<T>();
		SQLiteDatabase db=helper.getReadableDatabase();
		Orm orm=TempleteConfig.ormMaps.get(cls.getSimpleName()+".orm.xml");
		List<Item> items=orm.getItemList();
		Cursor cursor=null;
		try {
			T t=(T) cls.newInstance();
			cursor=db.query(orm.getTableName(), null, null, null, null, null, null);
			while(cursor.moveToNext()){
				//cursor.getString(cursor.getColumnIndex(columnName))
				Key key=orm.getKey();
				int keyIndex=cursor.getColumnIndex(key.getColumnName());
				Method keyMethod=cursor.getClass().getMethod(TempleteConfig.methodMaps.get(key.getType()), int.class);
				Object keyvalue=keyMethod.invoke(cursor, keyIndex);
				Field keyField=t.getClass().getDeclaredField(key.getProperty());
				keyField.setAccessible(true);
				keyField.set(t, keyvalue);
				for(Item item:items){
					int itemIndex=cursor.getColumnIndex(item.getColumnName());
					Method itemMethod=cursor.getClass().getMethod(TempleteConfig.methodMaps.get(item.getType()), int.class);
					Object itemvalue=itemMethod.invoke(cursor, itemIndex);
					Field itemField=t.getClass().getDeclaredField(item.getProperty());
					itemField.setAccessible(true);
					itemField.set(t, itemvalue);
				}
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(cursor!=null){
				cursor.close();
			}
			if(db!=null){
				db.close();
			}
		}
		return list;
	}
}
