package com.ckview.utils.db;

public class Item {
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the preproty
	 */
	public String getPreproty() {
		return preproty;
	}
	/**
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}
	/**
	 * @return the notNull
	 */
	public boolean isNotNull() {
		return notNull;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param preproty the preproty to set
	 */
	public void setPreproty(String preproty) {
		this.preproty = preproty;
	}
	/**
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	/**
	 * @param notNull the notNull to set
	 */
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	private String name;
	private String preproty;
	private boolean unique;
	private boolean notNull;
	private String defaultValue;
	private int size;
	private String type;
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Item(String name, String preproty, boolean unique, boolean notNull,
			String defaultValue, int size, String type) {
		super();
		this.name = name;
		this.preproty = preproty;
		this.unique = unique;
		this.notNull = notNull;
		this.defaultValue = defaultValue;
		this.size = size;
		this.type = type;
	}
}
