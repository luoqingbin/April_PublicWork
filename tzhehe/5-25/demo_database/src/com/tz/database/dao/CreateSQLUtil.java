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
 * �˷����ܶ඼û�й�ϸ�Ĵ���int��String�����Ͷ�û�н��д�������������
 * ʵ�������������ĸ��дû�д������ڶ����»��ߴ����
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
		//id�ǹ̶��ģ���ӵ�ʱ��Ͳ����д�����
		String sql="create table "+getTableName(c)+"(_id integer primary key autoincrement , "+lastString(sb)+")";
		db.execSQL(sql);
		Log.i("info", "ִ��createTable���:"+sql);
	}
	
	/**
	 * ��ӵķ���
	 * @param t
	 * @throws Exception
	 */
	public  void save(Object t) throws Exception{
		Field[] f = t.getClass().getDeclaredFields();
		String value="";
		String key="";
		for (int i = 0; i < f.length; i++) {
			String name=f[i].getName();
				//����һ��ֵ��Ҫ�Ӷ���
					key+=name+",";
					if(name.equals("_id")){
						value+= null+",";
					}else{
						value+= "'"+getter(t, name)+"',";
					}
		}
		
		String sql="insert into "+getTableName(t.getClass())+"("+lastString(key)+") values ("+lastString(value)+")";
		db.execSQL(sql);
		Log.i("info", "ִ��inser���:"+sql);
	}
	
	
	/**
	 * �޸ĵķ���
	 * @param t
	 * @throws Exception
	 */
	public  void update(Object t) throws Exception{
		Field[] f = t.getClass().getDeclaredFields();
		String sb="";
		String id="";
		for (Field field : f) {
			if(!field.getName().equals("_id")){
				//����id��ֵ�޸ģ�ƴװ
				sb+=field.getName()+"='"+getter(t, field.getName())+"',";
			}else{
				//id��ֵ�����ó���
				id=getter(t, field.getName())+"";
			}
		}
		String sql="update "+getTableName(t.getClass())+" set "+lastString(sb)+" where _id='"+id+"'";
		Log.i("info", "ִ��update���:"+sql);
	}
	
	
	
	 /**
     * @param obj
     *            �����Ķ���
     * @param att
     *            ����������
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
     *            �����Ķ���
     * @param att
     *            ����������
     * @param value
     *            ���õ�ֵ
     * @param type
     *            ����������
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
     * �õ�table�ı���
     * @param t
     * @return
     */
    public  String getTableName(Class c){
    	return c.getSimpleName();
    }
    
    /**
     * �����ֶε����ͷ������ݿ������
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
     * �жϱ��Ƿ����
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
    	Log.i("info","������("+getTableName(c)+")Ϊ"+count);
    	return count>0?true:false;
    	
    }
    
    /**
     * ��ӵķ���
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
		Log.i("info","saveClass��ӱ���("+getTableName(t.getClass())+")�ɹ�!");
    }
  	
    
    
    /**
     * 
     * ��ѯ����
     * @throws InstantiationException 
     * @throws IllegalAccessException
     * {@ע}�������������ķ�����ž�һ���ģ�û�н��д��� 
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
				//���������ж�Ҫcursor�����Ǹ�������
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
     * �������Ĳ�����ѯ
     * @throws Exception
     *  {@ע}�������������ķ�����ž�һ���ģ�û�н��д���  
     */
    public <T> List<T> quryAllField(T t) throws Exception{
    	List<T> list=new ArrayList<T>();
		Field[] names=t.getClass().getDeclaredFields();
		
    	String sql = "select * from "+getTableName(t.getClass())+" where 1=1";
    	for (Field field : names) {
			Object  o= getter(t, field.getName());
			String simpleName= field.getType().getSimpleName();
			//0���߿� ���ܽ���
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
				//���������ж�Ҫcursor�����Ǹ�������
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
    
    //����_id ɾ��������ĳ���ֶ�
    public <T>  void delete(T t) throws Exception{
    	String sql="delete from "+getTableName(t.getClass())+" where _id="+ getter(t, "_id");
    	db.execSQL(sql);
    	Log.i("info",  "ɾ����("+getTableName(t.getClass())+")�ֶ�_idΪ��"+getter(t, "_id")+"��ֵ");
    }
    
    
    //�������һ�����ŵ�����
    private String lastString(String  s){
    	return s.substring(0, s.length()-1);
    }

    
    /**
     * ���þ�̬�ķ���
     * @param t
     * @return
     */
    
   public static  <T extends Classes >  List<T> swap(T t){
	   List<T> list=new ArrayList<T>();
	   list.add(t);
	   return list;
   }
   
   
}
