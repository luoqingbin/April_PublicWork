package com.yangfp.database;

import java.util.List;

import com.yangfp.database.bean.Student;
import com.yangfp.database.service.StudentService;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	private EditText name_add,_id,name_update;
	private Button insert,update,search,delete_table;
	
	private SQLiteDatabase db;
	private StudentService studentService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initView();
        initDatabaseAndTable();
        studentService = new StudentService();
    }
    
	/**
	 * create database and student table
	 */
	private void initDatabaseAndTable() {
		db = this.openOrCreateDatabase("student.db", this.MODE_PRIVATE, null);//创建数据库连接
		String createTableSql = "create table student(_id integer primary key autoincrement,name varchar)";
		try {
			db.execSQL(createTableSql);
			Log.i("INFO", "student表创建成功");
		} catch (SQLException e) {
			Log.i("INFO", "student表已创建");
		}
		
	}

	/**
	 * init view and set listener for button
	 */
	private void initView() {
		name_add = (EditText) findViewById(R.id.name_add);
		_id = (EditText) findViewById(R.id.id);
		name_update = (EditText) findViewById(R.id.name_update);
		insert = (Button) findViewById(R.id.insert);
		update = (Button) findViewById(R.id.update);
		search = (Button) findViewById(R.id.search);
		delete_table = (Button) findViewById(R.id.delete_table);
		
		insert.setOnClickListener(this);
		update.setOnClickListener(this);
		search.setOnClickListener(this);
		delete_table.setOnClickListener(this);
	}

	/**
	 * trigger insert,update,search,delete
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.insert:
			String name = name_add.getText().toString();
			studentService.insertStudent(db,name);
			break;
		case R.id.update:
			String id = _id.getText().toString();
			String nameUpdate = name_update.getText().toString();
			studentService.updateStudentById(db,id,nameUpdate);
			break;
		case R.id.search:
			List<Student> students = studentService.searchAllStudents(db);
			for (Student student : students) {
				Log.i("INFO", student.toString());
			}
			break;	
		case R.id.delete_table:
			studentService.deleteTable(db);
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		//activity生命周期结束时关闭数据库连接
		if(db!=null){
			db.close();
		}
	}
    
}