package com.tz.sqlite.baseDao;

import java.util.ArrayList;
import java.util.List;

import com.tz.sqlite.model.Student;

import android.R.integer;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StudentDao extends BaseDao {

	public StudentDao(SQLiteDatabase db) {
		super(db);
	}
	
	
	/**
	 * �������ݿ�
	 */
	
	public void createDatabase() throws Exception{
		String sql="create table student(_id integer primary key autoincrement , _name varchar(20),_sex int )";
		db.execSQL(sql);
	}
	
	/**
	 * ����ֶ�
	 */
	public void save(Student s)throws Exception{
		String sql = "insert into student values(null,?,?)";
		db.execSQL(sql,new Object[] { s.get_name(), s.get_sex()});
		
	}
	
	/**
	 * �޸��ֶ�
	 */
	public void update(Student s){
		StringBuffer sb=new StringBuffer();
		sb.append("update student set  _name =? , _sex=? where _id=?  ");
		db.execSQL(sb.toString(),new Object[] { s.get_name(), s.get_sex(),s.get_id()});
	}
	
	
	/**
	 * ɾ���ֶ�
	 */
	public void delete(int id){
		String sql="delete from student where _id=?";
		db.execSQL(sql,new Object[] {id});
	}
	
	
	/**
	 * ��ѯȫ��
	 */
	public List<Student> qurryAll(){
		List<Student> list=new ArrayList<Student>();
		String sql = "select * from student ";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			Student	stu = new Student();
			stu.set_id(Integer.valueOf(cursor.getString(cursor
					.getColumnIndex("_id"))));
			stu.set_name(cursor.getString(cursor.getColumnIndex("_name")));
			stu.set_sex(cursor.getInt(cursor.getColumnIndex("_sex")));
			list.add(stu);
		}
		cursor.close();
		return list;
	}
	
	
	/**
	 * ��ѯȫ��
	 */
	public List<Student> qurryByStudent(Student s){
		List<Student> list=new ArrayList<Student>();
		String sql = "select * from student where 1=1";
		if(s.get_name()!=null){
			sql+=" _name = ?";
		}

		if(s.get_sex()!=0){
			sql+=" _sex = ?";
		}
		
		Cursor cursor = db.rawQuery(sql, new String[]{s.get_name(),s.get_sex()+"" } );
		while (cursor.moveToNext()) {
			Student	stu = new Student();
			stu.set_id(Integer.valueOf(cursor.getString(cursor
					.getColumnIndex("_id"))));
			stu.set_name(cursor.getString(cursor.getColumnIndex("name")));
			stu.set_sex(cursor.getInt(cursor.getColumnIndex("_sex")));
			list.add(stu);
		}
		cursor.close();
		return list;
	}
	
	
	
}
