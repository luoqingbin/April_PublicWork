package com.tz.database.bean;

import java.util.Random;

public class Product {
    private int proId;
    private String proName;
    private float proPrice;
	public int getProId() {
		return proId;
	}
	public void setProId(int proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public float getProPrice() {
		return proPrice;
	}
	public void setProPrice(float proPrice) {
		this.proPrice = proPrice;
	}
	public Product() {
		super();
		this.proName="proName"+new Random().nextInt(10000);
		this.proPrice=new Random().nextFloat();
	}
	@Override
	public String toString() {
		return "Product [proId=" + proId + ", proName=" + proName
				+ ", proPrice=" + proPrice + "]";
	}
}
