package com.task.sqlite.bean;
/**
 * ѧ����ʵ����
 * @author lingjl
 *2015-05-28
 */
public class Student {
	private int _id;         //id
	private String name;   //����
	private String sex;    //�Ա�
	private String phone;  //��ϵ�绰

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
