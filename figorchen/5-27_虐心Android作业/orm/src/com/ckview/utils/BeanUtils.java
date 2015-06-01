package com.ckview.utils;

import java.lang.reflect.Field;
import java.util.HashMap;


import android.content.ContentValues;
import android.database.Cursor;

public class BeanUtils {
	/**
	 * return field value
	 * @param o the object that include field
	 * @param field
	 * @return
	 */
	@SuppressWarnings("finally")
	public static Object getValueByField(Object o, Field field){
		Object result = null;
		try {  
			field.setAccessible(true);
			result =  field.get(o);
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IllegalArgumentException e) {  
	        e.printStackTrace();  
	    } catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}
	
	/**
	 * 将一个Bean转换成ContentValues
	 * @param <T>
	 * @param tableName
	 * @param clazz table Bean class
	 * @param obj
	 * @return
	 */
	public static <T> ContentValues transformBeanToContentValues(Class<T> clazz, Object obj, HashMap<String, String>nameMap){
		
		ContentValues maps = new ContentValues();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			maps.put(nameMap.get(field.getName().toLowerCase()), String.valueOf( BeanUtils.getValueByField(obj, field)));
		}
		
		return maps;
	}
	
	/**
	 * 将一个Bean转换成HashMap
	 * @param <T>
	 * @param tableName
	 * @param clazz table Bean class
	 * @param obj
	 * @return
	 */
	public static <T> HashMap<String, Object> transformBeanToHashMap(Class<T> clazz, Object obj, HashMap<String, String>nameMap){
		
		HashMap<String, Object> maps = new HashMap<String, Object>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			maps.put(nameMap.get(field.getName().toLowerCase()), String.valueOf( BeanUtils.getValueByField(obj, field)));
		}
		
		return maps;
	}
	
	public static <T> T getBean(Class<T> clazz, Cursor cursor, HashMap<String, String>nameMap){
		Field[] fields = clazz.getDeclaredFields();
		T bean = null;
		try {
			bean = (T) clazz.newInstance();
			for (Field field : fields) {
				field.setAccessible(true);
				String typeName = field.getType().getSimpleName();
				String fieldName = nameMap.get(field.getName().toLowerCase());
				if(typeName.equals("String")){
					field.set(bean, cursor.getString(cursor.getColumnIndex(fieldName)));
				}else if(typeName.equals("int")){
					field.set(bean, cursor.getInt(cursor.getColumnIndex(fieldName)));
				}else if(typeName.equals("float")){
					field.set(bean, cursor.getFloat(cursor.getColumnIndex(fieldName)));
				}else if(typeName.equals("double")){
					field.set(bean, cursor.getDouble(cursor.getColumnIndex(fieldName)));
				}else if(typeName.equals("boolean")){
					field.set(bean, Boolean.valueOf(cursor.getString(cursor.getColumnIndex(fieldName))));
				}else if(typeName.equals("long")){
					field.set(bean, cursor.getLong(cursor.getColumnIndex(fieldName)));
				}else if(typeName.equals("short")){
					field.set(bean, cursor.getShort(cursor.getColumnIndex(fieldName)));
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
}
