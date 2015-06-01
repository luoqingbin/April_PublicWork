package com.tz.database.bean;

import java.util.Random;

public class Student {
	private int stuId;
	private String stuname;
	private String stuclassname;

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getStuclassname() {
		return stuclassname;
	}

	public void setStuclassname(String stuclassname) {
		this.stuclassname = stuclassname;
	}

	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuname=" + stuname
				+ ", stuclassname=" + stuclassname + "]";
	}

	public Student(int stuId, String stuname, String stuclassname) {
		super();
		this.stuId = stuId;
		this.stuname = stuname;
		this.stuclassname = stuclassname;
	}

	public Student() {
		super();
		this.stuclassname="StuClass"+new Random().nextInt(10000);
		this.stuname="StuName"+new Random().nextInt(10000);
	}

}
