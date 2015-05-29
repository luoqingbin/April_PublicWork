package com.task.sqlite.utils;

public class Key extends Item {
	//<key columnName="_id" property="_id" type="java.lang.Integer" identity="true"></key>
	private String  identity;   //是否自增长

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
