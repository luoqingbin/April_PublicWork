package com.task.sqlite.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * ��¼���ű����Ϣ
 * @author lingjl
 *
 */
public class Orm {
	private String beanName;   //��Ӧ��ʵ�������������
	private String tableName;  //��Ӧ�����ݱ������
	private Key key;    //���ű������
	
	private List<Item> itemList=new ArrayList<Item>();    //�ֶεļ���itemList

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
