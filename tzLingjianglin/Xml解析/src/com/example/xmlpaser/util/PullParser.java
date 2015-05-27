package com.example.xmlpaser.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.example.xmlpaser.bean.Student;

import android.content.Context;
import android.util.Xml;

/**
 * pull·½Ê½½âÎöxml
 * @author lingjl
 *
 */
public class PullParser {
	public List<Student> getStudentList(Context context,String fileName){
		List<Student> list=new ArrayList<Student>();
		XmlPullParser parser=Xml.newPullParser();
		try {
			InputStream inputStream = context.getAssets().open(fileName);
			parser.setInput(inputStream, "utf-8");
			int type=parser.getEventType();
			Student s=null;
			while(type!=XmlPullParser.END_DOCUMENT){
				switch (type) {
				case XmlPullParser.START_TAG:
					if("student".endsWith(parser.getName())){
						s=new Student();
					}else if("name".endsWith(parser.getName())){
						String name=parser.nextText();
						s.setName(name);
					}else if("age".endsWith(parser.getName())){
						String age=parser.nextText();
						s.setAge(age);
					}else if("sex".endsWith(parser.getName())){
						String sex=parser.nextText();
						s.setSex(sex);
					}
					break;
				case XmlPullParser.END_TAG:
					if("student".endsWith(parser.getName())){
						list.add(s);
					}
					break;
				default:
					break;
				}
				type=parser.next();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
