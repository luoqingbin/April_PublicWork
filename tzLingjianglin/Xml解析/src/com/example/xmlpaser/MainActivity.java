package com.example.xmlpaser;

import java.util.ArrayList;
import java.util.List;

import com.example.xmlpaser.bean.Student;
import com.example.xmlpaser.util.DomParser;
import com.example.xmlpaser.util.PullParser;
import com.example.xmlpaser.util.SAXParser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private Button pull,sax,dom;
	private List<Student> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pull=(Button) findViewById(R.id.pull);
		sax=(Button) findViewById(R.id.sax);
		dom=(Button) findViewById(R.id.dom);
		pull.setOnClickListener(this);
		sax.setOnClickListener(this);
		dom.setOnClickListener(this);
		list=new ArrayList<Student>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pull:
			PullParser pp=new PullParser();
			list.clear();
			list.addAll(pp.getStudentList(this, "students.xml"));
			break;
		case R.id.sax:
			SAXParser sp=new SAXParser();
			list.clear();
			list.addAll(sp.getStudentList(this, "students.xml"));
			break;
		case R.id.dom:
			DomParser dp=new DomParser();
			list.clear();
			list.addAll(dp.getStudentList(this, "students.xml"));
			break;
		default:
			break;
		}
		for(Student s:list){
			System.out.println("name=="+s.getName());
			System.out.println("age=="+s.getAge());
			System.out.println("sex=="+s.getSex());
		}
		
	}

}
