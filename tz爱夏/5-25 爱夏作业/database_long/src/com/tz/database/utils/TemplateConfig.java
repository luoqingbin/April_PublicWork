package com.tz.database.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.tz.database.bean.Item;
import com.tz.database.bean.Key;
import com.tz.database.bean.Orm;

public class TemplateConfig {
	//将解析返回的Orm类放到键值对里存储
	public static Map<String,Orm> mapping=new HashMap<String,Orm>();
	// 解析config文件
	public static Map<String,String> methodMapping=new HashMap<String,String>();
	static{
		methodMapping.put("java.lang.Integer", "getInt");
		methodMapping.put("java.lang.String", "getString");
		methodMapping.put("java.lang.Float", "getFloat");
		methodMapping.put("java.lang.Short", "getShort");
		methodMapping.put("java.lang.Double", "getDouble");
		methodMapping.put("java.lang.Long", "getLong");
	}
	public static Orm parserXml(InputStream is) throws Exception {
		Orm orm = null;
		Item item = null;
		Log.i("INFO", "开始调用了parserXml方法");
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(is, "UTF-8");
		int type = pullParser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				orm = new Orm();
				Log.i("INFO", "文档开始解析");
				break;
			case XmlPullParser.START_TAG:
				if (pullParser.getName().equals("orm")) {
					orm.setTableName(pullParser.getAttributeValue(null,
							"tableName"));
					orm.setBeanName(pullParser.getAttributeValue(null,
							"beanName"));
					orm.setDaoName(pullParser.getAttributeValue(null, "daoName"));
					Log.i("INFO", "ORM开始解析");
				} else if (pullParser.getName().equals("key")) {
					Log.i("INFO", "key开始解析");
					Key key = new Key();
					key.setColumn(pullParser.getAttributeValue(null, "column"));
					key.setProperty(pullParser.getAttributeValue(null,
							"property"));
					key.setType(pullParser.getAttributeValue(null, "type"));
					key.setIdentity(pullParser.getAttributeValue(null,
							"identity"));
					orm.setKey(key);
				} else if (pullParser.getName().equals("item")) {
					item = new Item();
					item.setColumn(pullParser.getAttributeValue(null, "column"));
					item.setProperty(pullParser.getAttributeValue(null,
							"property"));
					item.setType(pullParser.getAttributeValue(null, "type"));
					orm.getItems().add(item);
				}
				break;
			case XmlPullParser.END_TAG:
				if (pullParser.getName().equals("item")) {
					item = null;
				}
				break;

			default:
				break;
			}
			type = pullParser.next();
		}
		return orm;
	}
}
