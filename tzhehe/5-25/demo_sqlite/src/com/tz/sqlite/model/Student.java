package com.tz.sqlite.model;





public class Student {
	private int _id;
	private String _name;
	private int _sex;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}

	public Student(int _id, String _name, int sex) {
		super();
		this._id = _id;
		this._name = _name;
		this._sex = sex;
	}
	public Student() {
		super();
	}
	public int get_sex() {
		return _sex;
	}
	public void set_sex(int _sex) {
		this._sex = _sex;
	}
	
	
	
}
