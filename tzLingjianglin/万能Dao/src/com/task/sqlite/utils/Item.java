package com.task.sqlite.utils;
/**
 * 记录表的字段的实体类
 * @author lingjl
 *
 */
public class Item {
	//<item columnName="name" property="name" type="java.lang.String"></item>
	private String columnName;
	private String property;
	private String type;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
