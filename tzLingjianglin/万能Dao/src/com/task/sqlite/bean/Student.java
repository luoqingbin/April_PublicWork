package com.task.sqlite.bean;
/**
 * 学生的实体类
 * @author lingjl
 *2015-05-28
 */
public class Student {
	private int _id;         //id
	private String name;   //姓名
	private String sex;    //性别
	private String phone;  //联系电话

	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(int _id,String name, String sex, String phone) {
		super();
		this._id=_id;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Student [_id=" + _id + ", name=" + name + ", sex=" + sex
				+ ", phone=" + phone + "]";
	}
}
