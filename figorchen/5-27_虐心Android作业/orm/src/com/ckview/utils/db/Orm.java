package com.ckview.utils.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Orm {
	/**
	 * @return the nameMap
	 */
	public HashMap<String, String> getNameMap() {
		return nameMap;
	}
	/**
	 * @param nameMap the nameMap to set
	 */
	public void setNameMap(HashMap<String, String> nameMap) {
		this.nameMap = nameMap;
	}
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}
	public Orm() {
		super();
		this.key = new Key();
		this.items = new ArrayList<Item>();
		this.nameMap = new HashMap<String, String>();
		// TODO Auto-generated constructor stub
	}
	public Orm(Key key, List<Item> items, String tableName) {
		super();
		this.key = key;
		this.items = items;
		this.tableName = tableName;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	private Key key;
	private List<Item> items;
	private String tableName;
	private HashMap<String, String> nameMap;
}
