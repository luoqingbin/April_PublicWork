package com.yk.database.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yk.database.entity.Student;

/**
 * 学生业务层
 * @author yk
 *
 */
public class StudentService {
	
	private SQLiteDatabase db;
	
	
	public StudentService(SQLiteDatabase db) {
		super();
		this.db = db;
	}
	/**
	 * 查询所有学生信息
	 * @param db 数据库对象
	 * @return 学生集合
	 */
	public List<Student> queryAllStudents(){
		String sql="select * from student";
		Cursor cursor=db.rawQuery(sql, null);
		List<Student> stus=new ArrayList<Student>();
		Student stu;
		while(cursor.moveToNext()){
			//创建学生对象
			 stu=new Student();
			 int id=cursor.getInt(cursor.getColumnIndex("_id"));
			 stu.setId(id);
			 String name=cursor.getString(cursor.getColumnIndex("name"));
			 stu.setName(name);
			 int age=cursor.getInt(cursor.getColumnIndex("age"));
			 stu.setAge(age);
			 //添加学生对象到集合
			 stus.add(stu);
		}
		cursor.close();
		return stus;
	}
	/**
	 * 通过id查询指定学生
	 * @param db 数据库对象
	 * @param id 学生id
	 * @return 学生对象
	 */
	public Student queryStudentById(int id){
		String sql="select * from student where _id=?";
		Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(id)});
		Student stu=null;
		if(cursor.moveToNext()){
			//创建学生对象
			 stu=new Student();
			 stu.setId(id);
			 String name=cursor.getString(cursor.getColumnIndex("name"));
			 stu.setName(name);
			 int age=cursor.getInt(cursor.getColumnIndex("age"));
			 stu.setAge(age);
		}
		cursor.close();
		return stu;
	}
	/**
	 * 添加学生对象到数据库
	 * @param stu 学生对象
	 * @return 0：插入失败；1：插入成功
	 */
	public int addStudent(Student stu){
		int isSuccess=0;
		String sql="insert into student values(null,?,?)";
		try {
			db.execSQL(sql,new Object[]{stu.getName(),stu.getAge()});
			isSuccess=1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	/**
	 * 更新学生信息
	 * @param stu 学生对象
	 * @return 0：更新失败；1：更新成功
	 */
	public int updateStudent(Student stu){
		int isSuccess=0;
		String sql="update student set name=?,age=? where _id=?";
		try {
			db.execSQL(sql,new Object[]{stu.getName(),stu.getAge(),stu.getId()});
			isSuccess=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	/**
	 * 删除学生信息
	 * @param id 学生id
	 * @return 0：删除失败；1：删除成功
	 */
	public int deleteStudent(int id){
		int isSuccess=0;
		String sql="delete from student where _id=?";
		try {
			db.execSQL(sql,new Integer[]{id});
			isSuccess=1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}
	
	
	
}
