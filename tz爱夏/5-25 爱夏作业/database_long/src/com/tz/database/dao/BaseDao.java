package com.tz.database.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tz.database.bean.Item;
import com.tz.database.bean.Key;
import com.tz.database.bean.Orm;
import com.tz.database.utils.MyDatabaseHelper;
import com.tz.database.utils.TemplateConfig;

/**
 * 
 * @author jackie 万能DAO可以操作任何表 插入数据 查询数据 任何表的值对象 泛型T
 * @param <T>
 */
public class BaseDao<T> {
	private MyDatabaseHelper helper;
	private Class templateCls;

	public BaseDao(Class templateCls, MyDatabaseHelper helper) {
		this.templateCls = templateCls;
		this.helper = helper;
	}

	public long insert(T t) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			ClassNotFoundException {
		Orm orm = null;
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		Class cls = t.getClass();
		orm = TemplateConfig.mapping.get(cls.getSimpleName() + ".org.xml");
		// 获得主键
		Key key = orm.getKey();
		Log.i("INFO", key.getColumn());
		Log.i("INFO", key.getProperty());
		Log.i("INFO", key.getIdentity());
		// 通过反射实现put方法
		if (!key.getIdentity().equals("true")) {
			// 如果主键不是自增长，就需要手动添加主键
			// 反射获得主键的值
			Field field = cls.getDeclaredField(key.getProperty());
			field.setAccessible(true);
			// 获取主键值对象
			Object value = field.get(t);
			// 获取put方法中第一个参数
			Class clsValues = values.getClass();
			Method valuesMethod = clsValues.getDeclaredMethod("put",
					new Class[] { String.class, Class.forName(key.getType()) });
			valuesMethod
					.invoke(values, new Object[] { key.getColumn(), value });
		}
		List<Item> items = orm.getItems();
		// 循环将items的值加到键值对values中
		for (Item item : items) {
			// 反射获得item的值
			Field field = cls.getDeclaredField(item.getProperty());
			field.setAccessible(true);
			// 获取item值对象
			Object value = field.get(t);
			// 获取put方法中第一个参数
			Class clsValues = values.getClass();
			Method valuesMethod = clsValues
					.getDeclaredMethod(
							"put",
							new Class[] { String.class,
									Class.forName(item.getType()) });
			valuesMethod.invoke(values,
					new Object[] { item.getColumn(), value });
		}
		long id = db.insert(orm.getTableName(), null, values);
		db.close();
		return id;
	}

	public List<T> getAll() {
		List<T> lists = new ArrayList<T>();
		try {
			SQLiteDatabase db = helper.getWritableDatabase();
			Orm orm = TemplateConfig.mapping.get(templateCls.getSimpleName()
					+ ".org.xml");
			Cursor cursor = db.query(orm.getTableName(), null, null, null,
					null, null, null);
			Key key = orm.getKey();
			List<Item> items = orm.getItems();
			// 获得cursor的对象
			Class cursorCls = cursor.getClass();
			while (cursor.moveToNext()) {
				// 将templateCls实例化
				T t = (T) templateCls.newInstance();
				// 获取主键的索引
				int index = cursor.getColumnIndex(key.getColumn());
				// 根据主键的type获取主键的方法
				String methodName = TemplateConfig.methodMapping.get(key
						.getType());
				// 反射获取cursor的get方法
				Method cursorMethod = cursorCls
						.getMethod(methodName, int.class);
				// 返回主键的值
				Object value = cursorMethod.invoke(cursor, index);
				// 获取templateCls的对应的值对象，并赋值
				Field keyField = templateCls
						.getDeclaredField(key.getProperty());
				keyField.setAccessible(true);
				keyField.set(t, value);
				for (Item item : items) {
					// 获取主键的索引
					int itemIndex = cursor.getColumnIndex(item.getColumn());
					// 根据主键的type获取主键的方法
					String itemMethodName = TemplateConfig.methodMapping
							.get(item.getType());
					// 反射获取cursor的get方法
					Method itemCursorMethod = cursorCls.getMethod(
							itemMethodName, int.class);
					// 返回主键的值
					Object itemValue = itemCursorMethod.invoke(cursor,
							itemIndex);
					// 获取templateCls的对应的值对象，并赋值
					Field itemField = templateCls.getDeclaredField(item
							.getProperty());
					itemField.setAccessible(true);
					itemField.set(t, itemValue);
				}
				lists.add(t);
			}
			db.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lists;
	}

}
