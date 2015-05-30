package com.ckview.utils.db.bean;

public class Word {
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Word [_id=" + _id + ", word=" + word + "]";
	}
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
	private int _id;
	private String word;
	public Word(String word) {
		super();
		this.word = word;
	}
	public Word() {
		super();
		// TODO Auto-generated constructor stub
	}
}
