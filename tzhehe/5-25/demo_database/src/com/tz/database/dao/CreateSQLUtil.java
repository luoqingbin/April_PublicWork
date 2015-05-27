package com.tz.database.dao;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tz.database.demo.Classes;
import com.tz.database.enums.Types;

import android.R.integer;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 * 此方法很多都没有过细的处理，int，String等类型都没有进行处理。后面慢慢改
 * 实体类的属性首字母大写没有处理，现在都按下划线处理吧
 * @author gx
 *
 * @param <T>
 */
public class CreateSQLUtil {
	private  SQLiteDatabase db;
	public CreateSQLUtil( SQLiteDatabase db){
		this.db=db;
	}
	
	/**
	 * 
	 * @param c
	 * @param db
	 * @throws Exception
	 */
	public  void  createDatabase(Class c) throws Exception{
		Field[] f = c.getDeclaredFields();
		String sb="";
		for (Field field : f) {
			if(!field.getName().equals("_id")){
				sb+=field.getName()+"  "+getType(field.getType().getSimpleName())+" ,";
			}
		}
		//id是固定的，添加的时候就不进行处理了
		String sql="create table "+getTableName(c)+"(_id integer primary key autoincrement , "+lastString(sb)+")";
		db.execSQL(sql);
		Log.i("info", "执行createTable语句:"+sql);
	}
	
	/**
	 * 添加的方法
	 * @param t
	 * @throws Exception
	 */
	public  void save(Object t) throws Exception{
		Field[] f = t.getClass().getDeclaredFields();
		String value="";
		String key="";
		for (int i = 0; i < f.length; i++) {
			String name=f[i].getName();
				//最后的一个值不要加逗号
					key+=name+",";
					if(name.equals("_id")){
						value+= null+",";
					}else{
						value+= "'"+getter(t, name)+"',";
					}
		}
		
		String sql="insert into "+getTableName(t.getClass())+"("+lastString(key)+") values ("+lastString(value)+")";
		db.execSQL(sql);
		Log.i("info", "执行inser语句:"+sql);
	}
	
	
	/**
	 * 修改的方法
	 * @param t
	 * @throws Exception
	 */
	public  void update(Object t) throws Exception{
		Field[] f = t.getClass().getDeclaredFields();
		String sb="";
		String id="";
		for (Field field : f) {
			if(!field.getName().equals("_id")){
				//不是id的值修改，拼装
				sb+=field.getName()+"='"+getter(t, field.getName())+"',";
			}else{
				//id的值单独拿出来
				id=getter(t, field.getName())+"";
			}
		}
		String sql="update "+getTableName(t.getClass())+" set "+lastString(sb)+" where _id='"+id+"'";
		Log.i("info", "执行update语句:"+sql);
	}
	
	
	
	 /**
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * */
	  public   Object getter(Object obj, String att) {
		  Object b=null;
	        try {
	            Method method = obj.getClass().getMethod("get" + att);
	             b=  method.invoke(obj);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	       return  b;
	    }
    /**
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * @param value
     *            设置的值
     * @param type
     *            参数的属性
     * */
    public  void setter(Object obj, String att, Object value,
            Class<?> type) {
    	Object o=null;
        try {
            Method method = obj.getClass().getMethod("set" + att, type);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   
    /**
     * 得到table的表字
     * @param t
     * @return
     */
    public  String getTableName(Class c){
    	return c.getSimpleName();
    }
    
    /**
     * 根据字段的类型返回数据库的类型
     * @param type
     * @return
     */
    public  String getType(String type){
    	String sqlType="";
	 switch (Types.toTypes(type)) {
	case INT:
		sqlType= " integer ";
		break;
	case STRING:
		sqlType= " varchar(20) ";
		break;
	default:
		sqlType= " data ";
		break;
	}
	 return sqlType;
	 
  }
  
    /**
     * 判断表是否存在
     * @param c
     * @param db
     * @return
     */
    public  Boolean tabbleIsExist(Class c){
    	String sql=" SELECT count(*) as count FROM sqlite_master WHERE type='table' AND name=?";  
    	Cursor cursor = db.rawQuery(sql, new String[]{getTableName(c)});
    	int count=0;
    	while (cursor.moveToNext()) {
    		count= cursor.getInt(cursor.getColumnIndex("count"));
		}
    	Log.i("info","表名：("+getTableName(c)+")为"+count);
    	return count>0?true:false;
    	
    }
    
    /**
     * 添加的方法
     * @param t
     * @param db
     */
    public  void saveClass(Object t){
    	ContentValues values = new ContentValues();
    	values.clear();
    	Field[] f = t.getClass().getDeclaredFields();
		for (Field field : f) {
			if(!field.getName().equals("_id")){
				values.put(field.getName(), String.valueOf(getter(t, field.getName())));
			}
		}
		db.insert(getTableName(t.getClass()), null, values);
		Log.i("info","saveClass添加表名("+getTableName(t.getClass())+")成功!");
    }
  	
    
    
    /**
     * 
     * 查询所有
     * @throws InstantiationException 
     * @throws IllegalAccessException
     * {@注}这个方法和下面的方法大概就一样的，没有进行处理 
     */
    public  <T>  List<T> quryAll(Class c) throws Exception{
    	List<T> list=new ArrayList<T>();
		Field[] names=c.getDeclaredFields();
    	String sql = "select * from "+getTableName(c)+" where 1=1";
		Cursor cursor = db.rawQuery(sql, new String[]{} );
		while (cursor.moveToNext()) {
			T t2=(T) c.newInstance();
			for (Field field : names) {
				Class typeClass= field.getType();
				String simpleName= typeClass.getSimpleName();
				Object value="";
				//根据类型判断要cursor调用那个方法。
				switch (Types.toTypes(simpleName)) {
				case INT:
					value=cursor.getInt(cursor.getColumnIndex(field.getName()));
					break;
				case STRING:
					value=cursor.getString(cursor.getColumnIndex(field.getName()));
					break;
				default:
					break;
				}
				setter(t2, field.getName(), value, field.getType());
			}
			list.add(t2);
		}
		cursor.close();
		return list;
    }
    
    
    
    
    /**
     * 按传进的参数查询
     * @throws Exception
     *  {@注}这个方法和上面的方法大概就一样的，没有进行处理  
     */
    public <T> List<T> quryAllField(T t) throws Exception{
    	List<T> list=new ArrayList<T>();
		Field[] names=t.getClass().getDeclaredFields();
		
    	String sql = "select * from "+getTableName(t.getClass())+" where 1=1";
    	for (Field field : names) {
			Object  o= getter(t, field.getName());
			String simpleName= field.getType().getSimpleName();
			//0或者空 不能进入
			switch (Types.toTypes(simpleName)) {
			case INT:
				if(Integer.valueOf(o+"")!=0){
					sql+=" and  "+field.getName()+"='"+o+"'";
				}
				break;
			case STRING:
				if(String.valueOf(o+"").equals("")){
					sql+=" and  "+field.getName()+"='"+o+"'";
				}
				break;
			default:
				break;
			}
			
			
		}
    	Log.i("sql", sql);
		Cursor cursor = db.rawQuery(sql, new String[]{} );
		while (cursor.moveToNext()) {
			T t2=(T) t.getClass().newInstance();
			for (Field field : names) {
				Class typeClass= field.getType();
				String simpleName= typeClass.getSimpleName();
				Object value="";
				//根据类型判断要cursor调用那个方法。
				switch (Types.toTypes(simpleName)) {
				case INT:
					value=cursor.getInt(cursor.getColumnIndex(field.getName()));
					break;
				case STRING:
					value=cursor.getString(cursor.getColumnIndex(field.getName()));
					break;
				default:
					break;
				}
				setter(t2, field.getName(), value, field.getType());
			}
			list.add(t2);
		}
		cursor.close();
		return list;
    }
    
    //根据_id 删除这个表的某个字段
    public <T>  void delete(T t) throws Exception{
    	String sql="delete from "+getTableName(t.getClass())+" where _id="+ getter(t, "_id");
    	db.execSQL(sql);
    	Log.i("info",  "删除表("+getTableName(t.getClass())+")字段_id为："+getter(t, "_id")+"的值");
    }
    
    
    //处理最后一个逗号的问题
    private String lastString(String  s){
    	return s.substring(0, s.length()-1);
    }

    
    /**
     * 调用静态的方法
     * @param t
     * @return
     */
    
   public static  <T extends Classes >  List<T> swap(T t){
	   List<T> list=new ArrayList<T>();
	   list.add(t);
	   return list;
   }
   
   
}
