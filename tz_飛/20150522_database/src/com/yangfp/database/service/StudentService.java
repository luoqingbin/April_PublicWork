package com.yangfp.database.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yangfp.database.bean.Student;

public class StudentService {
	
	/**
	 * ����һ��ѧ����Ϣ
	 * @param db ���ݿ�����
	 * @param name ѧ������
	 */
	public void insertStudent(SQLiteDatabase db,String name) {
		String insertSql = "insert into student values(null,?)";
		db.execSQL(insertSql, new String[]{name});
	}
	
	/**
	 * ��ѯ���е�ѧ����Ϣ
	 * @param db ���ݿ�����
	 * @return ѧ�����󼯺�
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
	 * ����id�޸�ѧ������
	 * @param db ���ݿ�����
	 * @param id ѧ��id
	 * @param name ѧ������
	 */
	public void updateStudentById(SQLiteDatabase db, String id, String name) {
		String updateSql = "update student set name=? where _id=?";
		db.execSQL(updateSql, new String[]{name,id});
	}

	/**
	 * ɾ��ѧ����
	 * @param db ���ݿ�����
	 */
	public void deleteTable(SQLiteDatabase db) {
		String dropSql = "drop table student";
		db.execSQL(dropSql);
	}

}
