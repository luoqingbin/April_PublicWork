package com.task.sqlite.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import android.text.TextUtils;
import android.util.Xml;

/**
 * 解析具体的xml文件的类
 * @author lingjl
 *
 */
public class TempleteConfig {
	public static Map<String,Orm> ormMaps=new HashMap<String, Orm>();
	public static Map<String,String> methodMaps=new HashMap<String, String>();
	static{
		methodMaps.put("java.lang.String", "getString");
		methodMaps.put("java.lang.Integer", "getInt");
		methodMaps.put("java.lang.Long", "getLong");
		methodMaps.put("java.lang.Short", "getShort");
		methodMaps.put("java.lang.Double", "getDouble");
		methodMaps.put("java.lang.Float", "getFloat");
	}
	/**
	 * 得到数据表的完整信息
	 * @param is   输入流
	 * @return   返回Orm  包含数据表所有字段的完整信息的实体类
	 */
	public Orm getOrm(InputStream is){
		Orm orm=null;
		Key key=null;
		Item item=null;
		List<Item> itemList=new ArrayList<Item>();
		XmlPullParser parser=Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int eventType=parser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					orm=new Orm();
					
					break;
				case XmlPullParser.START_TAG:
					if(TextUtils.equals(parser.getName(),"orm")){
						orm.setBeanName(parser.getAttributeValue(null, "beanName"));
						orm.setTableName(parser.getAttributeValue(null, "tableName"));
					}else if(TextUtils.equals(parser.getName(),"key")){
						key=new Key();
						key.setColumnName(parser.getAttributeValue(null, "columnName"));
						key.setProperty(parser.getAttributeValue(null, "property"));
						key.setType(parser.getAttributeValue(null, "type"));
						key.setIdentity(parser.getAttributeValue(null, "identity"));
						orm.setKey(key);
					}else if(TextUtils.equals(parser.getName(),"item")){
						item=new Item();
						item.setColumnName(parser.getAttributeValue(null, "columnName"));
						item.setProperty(parser.getAttributeValue(null, "property"));
						item.setType(parser.getAttributeValue(null, "type"));
						itemList.add(item);
					}
					break;
				case XmlPullParser.END_TAG:
					if(TextUtils.equals(parser.getName(),"item")){
						item=null;
					}else if(TextUtils.equals(parser.getName(),"orm")){
						orm.setItemList(itemList);
					}
					break;
				default:
					break;
				}
				eventType=parser.next();
			}
			ormMaps.put(Class.forName(orm.getBeanName()).getSimpleName()+".orm.xml", orm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orm;
	}
}
