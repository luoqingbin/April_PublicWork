package com.ckview.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.ckview.utils.OpResult;
import com.ckview.utils.log.BaseLog;

public class ParseTableXML {
	
	public static HashMap<String, Orm> tables = new HashMap<String, Orm>();;
	
	/**
	 * 解析assets/path目录下所有以.orm.xml结尾的文件，转换成orm对象并保存在tables的HashMap中，键为表名，值为orm对象
	 * @param context
	 * @param path assets目录下的文件夹路径
	 */
	public static void praseXML(Context context, String path){
		try {
			String[] files = context.getAssets().list(path);
			for (String file : files) {
				if(file.endsWith(".orm.xml")){
					//找到以 ".orm.xml" 结尾的文件，打开并解析
					InputStream is = context.getAssets().open(path+"/"+file);
					XmlPullParser parser = Xml.newPullParser();
					parser.setInput(is, "UTF-8");
					int type = parser.getEventType();
					Orm orm = null;
					while(type != XmlPullParser.END_DOCUMENT){
						
						switch (type) {
						case XmlPullParser.START_DOCUMENT:
							orm = new Orm();
							break;
						case XmlPullParser.START_TAG:
							if(parser.getName().equals("TableInfo")){
								orm.setTableName(parser.getAttributeValue(null, "name"));
							}else if(parser.getName().equals("Key")){
								if(parser.getAttributeValue(null, "name") != null){
									orm.getKey().setName(parser.getAttributeValue(null, "name"));
								}
								if(parser.getAttributeValue(null, "preproty") != null){
									orm.getKey().setPreproty(parser.getAttributeValue(null, "preproty"));
									orm.getNameMap().put(parser.getAttributeValue(null, "preproty").toLowerCase(), parser.getAttributeValue(null, "name"));
								}
								if(parser.getAttributeValue(null, "size") != null){
									orm.getKey().setSize(Integer.valueOf(parser.getAttributeValue(null, "size")));
								}
								if(parser.getAttributeValue(null, "identity") != null){
									orm.getKey().setIdentity(Boolean.valueOf(parser.getAttributeValue(null, "identity")));
								}
								if(parser.getAttributeValue(null, "default") != null){
									orm.getKey().setDefaultValue(parser.getAttributeValue(null, "default"));
								}
								if(parser.getAttributeValue(null, "type") != null){
									orm.getKey().setType(parser.getAttributeValue(null, "type"));
								}
								if(parser.getAttributeValue(null, "unique") != null){
									orm.getKey().setUnique(Boolean.valueOf(parser.getAttributeValue(null, "unique")));
								}
								if(parser.getAttributeValue(null, "notnull") != null){
									orm.getKey().setNotNull(Boolean.valueOf(parser.getAttributeValue(null, "notnull")));
								}
							}else if(parser.getName().equals("Item")){
								Item item = new Item();
								if(parser.getAttributeValue(null, "name") != null){
									item.setName(parser.getAttributeValue(null, "name"));
								}
								if(parser.getAttributeValue(null, "preproty") != null){
									item.setPreproty(parser.getAttributeValue(null, "preproty"));
									orm.getNameMap().put(parser.getAttributeValue(null, "preproty").toLowerCase(), parser.getAttributeValue(null, "name"));
								}
								if(parser.getAttributeValue(null, "size") != null){
									item.setSize(Integer.valueOf(parser.getAttributeValue(null, "size")));
								}
								if(parser.getAttributeValue(null, "default") != null){
									item.setDefaultValue(parser.getAttributeValue(null, "default"));
								}
								if(parser.getAttributeValue(null, "type") != null){
									item.setType(parser.getAttributeValue(null, "type"));
								}
								if(parser.getAttributeValue(null, "unique") != null){
									item.setUnique(Boolean.valueOf(parser.getAttributeValue(null, "unique")));
								}
								if(parser.getAttributeValue(null, "notnull") != null){
									item.setNotNull(Boolean.valueOf(parser.getAttributeValue(null, "notnull")));
								}
								orm.getItems().add(item);
							}
							break;

						default:
							break;
						}
						type = parser.next();
					}
					tables.put(orm.getTableName(), orm);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getCreateTableSql(){
		if(tables.size() < 1){
			return "";
		}
		String sql = "";
		
		//遍历拿出tables中的orm对象
		for (String tableName : tables.keySet()) {
			Orm orm = tables.get(tableName);
			Key key = orm.getKey();
			List<Item> items = orm.getItems();
			//根据orm对象解析出sql语句
			String ormSql = "CREATE TABLE ["+tableName+"] (";
			//主键名字不为null，说明主键配置存在，拼接创建主键字符串
			if(key.getName() != null){
				ormSql += "["+key.getName()+"] ";
				if(key.getType().equalsIgnoreCase("INTEGER") || key.getType().equalsIgnoreCase("SHORT") ||key.getType().equalsIgnoreCase("LONG")){
					ormSql += key.getType()+" PRIMARY KEY ";
				}else{
					ormSql += key.getType()+"("+key.getSize()+") PRIMARY KEY ";
				}
				if(key.isIdentity()){
					ormSql += "AUTOINCREMENT";
				}
			}
			//遍历每一个item，生成sql
			for (Item item : items) {
				ormSql += ", ["+item.getName()+"] ";
				if(item.getType().equalsIgnoreCase("INTEGER") || item.getType().equalsIgnoreCase("SHORT") ||item.getType().equalsIgnoreCase("LONG")){
					ormSql += item.getType()+" ";
				}else{
					ormSql += item.getType()+"("+item.getSize()+") ";
				}
				if(item.isUnique()){
					ormSql += "UNIQUE ";
				}
				if(item.isNotNull()){
					ormSql += "NOT NULL ";
				}
				if(item.getDefaultValue() != null && !item.getDefaultValue().equals("")){
					ormSql += "DEFAULT "+item.getDefaultValue();
				}
			}
			//单条创建表语句添加到整体
			sql += ormSql + ");";
		}
		BaseLog.debug("ck", sql);
		return sql;
	}
}
