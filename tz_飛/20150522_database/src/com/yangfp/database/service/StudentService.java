package com.yangfp.database.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yangfp.database.bean.Student;

public class StudentService {
	
	/**
	 * 插入一个学生信息
	 * @param db 数据库连接
	 * @param name 学生姓名
	 */
	public void insertStudent(SQLiteDatabase db,String name) {
		String insertSql = "insert into student values(null,?)";
		db.execSQL(insertSql, new String[]{name});
	}
	
	/**
	 * 查询所有的学生信息
	 * @param db 数据库连接
	 * @return 学生对象集合
	 */
	public List<Student> searchAllStudents(SQLiteDatabase db){
		List<Student> students = new ArrayList<Student>();
		Cursor cursor = db.rawQuery("select * from student", null);
		while(cursor.moveToNext()){
			Student student = new Student();
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			student.setId(id);
			student.setName(name);
			students.add(student);
		}
		cursor.close();
		return students;
	}
	
	/**
	 * 根据id修改学生姓名
	 * @param db 数据库连接
	 * @param id 学生id
	 * @param name 学生姓名
	 */
	public void updateStudentById(SQLiteDatabase db, String id, String name) {
		String updateSql = "update student set name=? where _id=?";
		db.execSQL(updateSql, new String[]{name,id});
	}

	/**
	 * 删除学生表
	 * @param db 数据库连接
	 */
	public void deleteTable(SQLiteDatabase db) {
		String dropSql = "drop table student";
		db.execSQL(dropSql);
	}

}
