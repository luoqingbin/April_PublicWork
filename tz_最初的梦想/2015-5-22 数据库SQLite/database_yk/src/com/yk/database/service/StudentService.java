package com.yk.database.service;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.yk.database.entity.Student;

/**
 * ѧ��ҵ���
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
	 * ��ѯ����ѧ����Ϣ
	 * @param db ���ݿ����
	 * @return ѧ������
	 */
	public List<Student> queryAllStudents(){
		String sql="select * from student";
		Cursor cursor=db.rawQuery(sql, null);
		List<Student> stus=new ArrayList<Student>();
		Student stu;
		while(cursor.moveToNext()){
			//����ѧ������
			 stu=new Student();
			 int id=cursor.getInt(cursor.getColumnIndex("_id"));
			 stu.setId(id);
			 String name=cursor.getString(cursor.getColumnIndex("name"));
			 stu.setName(name);
			 int age=cursor.getInt(cursor.getColumnIndex("age"));
			 stu.setAge(age);
			 //���ѧ�����󵽼���
			 stus.add(stu);
		}
		cursor.close();
		return stus;
	}
	/**
	 * ͨ��id��ѯָ��ѧ��
	 * @param db ���ݿ����
	 * @param id ѧ��id
	 * @return ѧ������
	 */
	public Student queryStudentById(int id){
		String sql="select * from student where _id=?";
		Cursor cursor=db.rawQuery(sql,new String[]{String.valueOf(id)});
		Student stu=null;
		if(cursor.moveToNext()){
			//����ѧ������
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
	 * ���ѧ���������ݿ�
	 * @param stu ѧ������
	 * @return 0������ʧ�ܣ�1������ɹ�
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
	 * ����ѧ����Ϣ
	 * @param stu ѧ������
	 * @return 0������ʧ�ܣ�1�����³ɹ�
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
	 * ɾ��ѧ����Ϣ
	 * @param id ѧ��id
	 * @return 0��ɾ��ʧ�ܣ�1��ɾ���ɹ�
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
