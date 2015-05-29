package com.task.sqlite.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录整张表的信息
 * @author lingjl
 *
 */
public class Orm {
	private String beanName;   //对应的实体类的完整名称
	private String tableName;  //对应的数据表的名称
	private Key key;    //整张表的主键
	
	private List<Item> itemList=new ArrayList<Item>();    //字段的集合itemList

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
