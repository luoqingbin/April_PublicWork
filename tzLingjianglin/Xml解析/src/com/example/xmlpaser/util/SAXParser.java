package com.example.xmlpaser.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;

import com.example.xmlpaser.bean.Student;

public class SAXParser {
	
	private List<Student> list;
	private Student student;
	private StringBuilder builder;
	
	public List<Student> getStudentList(Context context,String fileName){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();  //ȡ��SAXParserFactoryʵ��  
	        javax.xml.parsers.SAXParser parser = factory.newSAXParser();                  //��factory��ȡSAXParserʵ��  
	        MyHanlder handler = new MyHanlder();                        //ʵ�����Զ���Handler  
	        InputStream inputStream = context.getAssets().open(fileName);
	        parser.parse(inputStream, handler); 
		} catch (Exception e) {
			e.printStackTrace();
		}                                 //�����Զ���Handler�������������  
        return list;
	}
	
	private class MyHanlder extends DefaultHandler{

		@Override
		public void startDocument() throws SAXException {
			
			super.startDocument();
			System.out.println("startDocument");
			list=new ArrayList<Student>();
			builder=new StringBuilder();
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
			System.out.println("endDocument");
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			System.out.println("startElement");
			if (localName.equals("student")) {  
				student = new Student();
            }  
            builder.setLength(0);   //���ַ���������Ϊ0 �Ա����¿�ʼ��ȡԪ���ڵ��ַ��ڵ�  
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			System.out.println("endElement");
			 if (localName.equals("name")) {  
	                student.setName(builder.toString());  
	            } else if (localName.equals("age")) {  
	            	student.setAge(builder.toString());  
	            } else if (localName.equals("sex")) {  
	            	student.setSex(builder.toString());  
	            }else if(localName.equals("student")){
	            	list.add(student);
	            }
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			builder.append(ch, start, length);  //����ȡ���ַ�����׷�ӵ�builder�� 
		}
	}
}
