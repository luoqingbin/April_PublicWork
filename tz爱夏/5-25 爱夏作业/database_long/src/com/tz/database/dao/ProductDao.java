package com.tz.database.dao;

import com.tz.database.bean.Product;
import com.tz.database.bean.Student;
import com.tz.database.utils.MyDatabaseHelper;

public class ProductDao extends BaseDao<Product> {

	public ProductDao(MyDatabaseHelper helper) {
		super(Product.class,helper);
	}

}
