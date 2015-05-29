package com.ckview.utils.db.bean;

public class Test {
	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}
	/**
	 * @return the word
	 */
	public String getWord() {
		return word;
	}
	/**
	 * @return the age
	 */
	public short getAge() {
		return age;
	}
	/**
	 * @return the nan
	 */
	public boolean isNan() {
		return nan;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(short age) {
		this.age = age;
	}
	/**
	 * @param nan the nan to set
	 */
	public void setNan(boolean nan) {
		this.nan = nan;
	}
	private int _id;
	private String word;
	private short age;
	private boolean nan;
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Test(int _id, String word, short age, boolean nan) {
		super();
		this._id = _id;
		this.word = word;
		this.age = age;
		this.nan = nan;
	}
}
