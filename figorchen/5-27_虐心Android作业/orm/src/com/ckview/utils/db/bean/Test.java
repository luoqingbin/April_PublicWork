package com.ckview.utils.db.bean;

public class Test {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Test [id=" + id + ", word=" + word + ", age=" + age + ", nan="
				+ nan + "]";
	}
	/**
	 * @return the id
	 */
	public int getid() {
		return id;
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
	 * @param id the id to set
	 */
	public void setid(int id) {
		this.id = id;
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
	private int id;
	private String word;
	private short age;
	private boolean nan;
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Test(int id, String word, short age, boolean nan) {
		super();
		this.id = id;
		this.word = word;
		this.age = age;
		this.nan = nan;
	}
}
