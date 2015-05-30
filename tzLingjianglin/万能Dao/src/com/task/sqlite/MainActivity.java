package com.task.sqlite;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.task.sqlite.bean.Student;
import com.task.sqlite.dao.BaseDao;
import com.task.sqlite.db.MySQLiteHelper;
import com.task.sqlite.utils.Orm;
import com.task.sqlite.utils.TempleteConfig;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void insert(View v){
		try {
			InputStream is=getAssets().open("Student.orm.xml");
			TempleteConfig config=new TempleteConfig();
			config.getOrm(is);
			Orm orm=TempleteConfig.ormMaps.get("Student.orm.xml");
			MySQLiteHelper helper=new MySQLiteHelper(this, "tz_vip.db", null, 1);
			BaseDao<Student> dao=new BaseDao<Student>(helper,Student.class);
			Student student=new Student(0, "Áè½­ÁÖ1", "ÄÐ1", "138719509011");
			dao.insert(student);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void query(View v){
		try {
			InputStream is=getAssets().open("Student.orm.xml");
			TempleteConfig config=new TempleteConfig();
			config.getOrm(is);
			Orm orm=TempleteConfig.ormMaps.get("Student.orm.xml");
			MySQLiteHelper helper=new MySQLiteHelper(this, "tz_vip.db", null, 1);
			BaseDao<Student> dao=new BaseDao<Student>(helper,Student.class);
			List<Student> list=dao.getAll();
			for(Student s:list){
				System.out.println("name=="+s.getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
