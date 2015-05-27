package com.tz.database.demo;


public class Student {
	private int _id;
	private String _name;
	private int _sex;
	private int _cid;
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
	public int get_sex() {
		return _sex;
	}
	public void set_sex(int _sex) {
		this._sex = _sex;
	}
	public int get_cid() {
		return _cid;
	}
	public void set_cid(int _cid) {
		this._cid = _cid;
	}
	public Student(String _name, int _sex, int _cid) {
		super();
		this._name = _name;
		this._sex = _sex;
		this._cid = _cid;
	}
	public Student() {
		super();
	}
	
	
	
	
	
}
