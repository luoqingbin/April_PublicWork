package com.tz.database.demo;

public class Classes {
	private int _id;
	private String _name;
	private String _createdate;
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
	public String get_createdate() {
		return _createdate;
	}
	public void set_createdate(String _createdate) {
		this._createdate = _createdate;
	}
	public Classes(String _name, String _createdate) {
		super();
		this._name = _name;
		this._createdate = _createdate;
	}
	public Classes() {
		super();
	}
	
	
	
	
	
	

}
