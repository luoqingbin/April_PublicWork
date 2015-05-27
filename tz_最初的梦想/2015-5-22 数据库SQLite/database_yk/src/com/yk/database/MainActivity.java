package com.yk.database;

import java.util.List;

import com.yk.database.entity.Student;
import com.yk.database.helper.DBHelper;
import com.yk.database.service.StudentService;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 数据库操作
 * 
 * @author yk
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private Button btn_create_database, btn_addstu, btn_deletestu, btn_updatestu, btn_querystu, btn_querystus;
	// 数据库辅助类
	private DBHelper dbHelper;
	// 数据库
	private SQLiteDatabase db;
	//学生业务类
	private StudentService studentService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		initViews();
	}

	private void findViews() {
		btn_create_database = (Button) findViewById(R.id.btn_create_database);
		btn_addstu = (Button) findViewById(R.id.btn_addstu);
		btn_deletestu = (Button) findViewById(R.id.btn_deletestu);
		btn_updatestu = (Button) findViewById(R.id.btn_updatestu);
		btn_querystu = (Button) findViewById(R.id.btn_querystu);
		btn_querystus = (Button) findViewById(R.id.btn_querystus);
	}

	private void initViews() {
		btn_create_database.setOnClickListener(this);
		btn_addstu.setOnClickListener(this);
		btn_deletestu.setOnClickListener(this);
		btn_updatestu.setOnClickListener(this);
		btn_querystu.setOnClickListener(this);
		btn_querystus.setOnClickListener(this);
		
	}

	@Override
	protected void onDestroy() {
		// 关闭数据库
		dbHelper.close();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_create_database:
			dbHelper = new DBHelper(this, "yk_data.db", null, 1);
			db = dbHelper.getWritableDatabase();
			studentService=new StudentService(db);
			break;
		case R.id.btn_addstu:
			Student stu=new Student("夏夏",22);
			studentService.addStudent(stu);
			break;
		case R.id.btn_deletestu:
			studentService.deleteStudent(2);
			break;
		case R.id.btn_updatestu:
			Student stu1=new Student(1,"小明",25);
			studentService.updateStudent(stu1);
			break;
		case R.id.btn_querystu:
			Student stu2=studentService.queryStudentById(3);
			toast(stu2.getId()+stu2.getName()+stu2.getAge());
			break;
		case R.id.btn_querystus:
			List<Student> stus=studentService.queryAllStudents();
			String msg="";
			for (int i = 0; i < stus.size(); i++) {
				msg+=stus.get(i).getId()+stus.get(i).getName()+stus.get(i).getAge();
			}
			toast(msg);
			break;

		default:
			break;
		}
	}
	
	public void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
