package com.ckview.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.util.Xml;

import com.ckview.utils.OpResult;

public class ParseTableXML {
	
	public static HashMap<String, Orm> tables;
	
	public static OpResult praseXML(Context context, String path){
		OpResult result = null;
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
								orm.setTableName(parser.getAttributeValue(null, "TableName"));
							}else if(parser.getName().equals("Key")){
								if(parser.getAttributeValue(null, "name") != null){
									orm.getKey().setName(parser.getAttributeValue(null, "name"));
								}
								if(parser.getAttributeValue(null, "preproty") != null){
									orm.getKey().setPreproty(parser.getAttributeValue(null, "preproty"));
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
		return result;
	}
}
