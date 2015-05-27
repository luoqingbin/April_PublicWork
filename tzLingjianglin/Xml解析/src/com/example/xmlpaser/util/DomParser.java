package com.example.xmlpaser.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.content.Context;
import com.example.xmlpaser.bean.Student;

public class DomParser {
	public List<Student> getStudentList(Context context,String fileName){
		List<Student> list = new ArrayList<Student>();  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例  
	    try {
	    	 DocumentBuilder builder = factory.newDocumentBuilder(); //从factory获取DocumentBuilder实例  
	         InputStream inputStream = context.getAssets().open(fileName);
	         Document doc = builder.parse(inputStream);   //解析输入流 得到Document实例  
	         Element rootElement = doc.getDocumentElement();  
	         NodeList items = rootElement.getElementsByTagName("student");  
	         for (int i = 0; i < items.getLength(); i++) {  
	         	Student student = new Student();  
	             Node item = items.item(i);  
	             NodeList properties = item.getChildNodes();  
	             for (int j = 0; j < properties.getLength(); j++) {  
	                 Node property = properties.item(j);  
	                 String nodeName = property.getNodeName();  
	                 if (nodeName.equals("name")) {  
	                 	student.setName(property.getFirstChild().getNodeValue());  
	                 } else if (nodeName.equals("age")) {  
	                 	student.setAge(property.getFirstChild().getNodeValue());  
	                 } else if (nodeName.equals("sex")) {  
	                 	student.setSex(property.getFirstChild().getNodeValue());  
	                 }  
	             }  
	             list.add(student);  
	         }  
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;  
	}
}
