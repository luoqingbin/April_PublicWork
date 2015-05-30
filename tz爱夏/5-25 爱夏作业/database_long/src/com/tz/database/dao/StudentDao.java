package com.tz.database.dao;

import com.tz.database.bean.Student;
import com.tz.database.utils.MyDatabaseHelper;

public class StudentDao extends BaseDao<Student> {

	public StudentDao(MyDatabaseHelper helper) {
		super(Student.class,helper);
	}

}
