package com.tz.database;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tz.database.bean.Orm;
import com.tz.database.utils.MyDatabaseHelper;
import com.tz.database.utils.TemplateConfig;

public class MainActivity extends Activity {
	private TemplateConfig config;
	private EditText et_bean, version;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		et_bean = (EditText) findViewById(R.id.et_bean);
		version = (EditText) findViewById(R.id.version);
		// 加载配置文件
		try {
			String[] files = getAssets().list("");
			for (String file : files) {
				if (file.endsWith(".org.xml")) {
					TemplateConfig.mapping.put(file,
							TemplateConfig.parserXml(getAssets().open(file)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertData(View view) {
		// 获得用户输入的类名
		String simpleClassName = et_bean.getText().toString().trim();
		Orm orm = TemplateConfig.mapping.get(simpleClassName + ".org.xml");
		if (orm != null) {
			// 说明配置文件不为空
			Class clsDao;
			try {
				clsDao = Class.forName(orm.getDaoName());
				Constructor constructor = clsDao
						.getDeclaredConstructor(new Class[] { MyDatabaseHelper.class });
				Object dao = constructor.newInstance(new MyDatabaseHelper(this,
						"tz_school.db", null, Integer.parseInt(version
								.getText().toString().trim())));
				// 获得insert方法 参数是泛型类
				Method method = clsDao.getMethod("insert", Object.class);
				// 获得对应的值对象
				Class beanCls = Class.forName(orm.getBeanName());
				Object bean = beanCls.newInstance();
				Object result = method.invoke(dao, bean);
				Log.i("NB", result + "编号数据生成");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 说明没有对应的配置文件
			Toast.makeText(this, "未找到相应的映射文件", 1000).show();
		}
	}

	public void queryData(View view) {
		// 获得用户输入的类名
		String simpleClassName = et_bean.getText().toString().trim();
		Orm orm = TemplateConfig.mapping.get(simpleClassName + ".org.xml");
		if (orm != null) {
			Class clsDao;
			try {
				clsDao = Class.forName(orm.getDaoName());
				Constructor constructor = clsDao
						.getDeclaredConstructor(new Class[] { MyDatabaseHelper.class });
				Object dao = constructor.newInstance(new MyDatabaseHelper(this,
						"tz_school.db", null, Integer.parseInt(version
								.getText().toString().trim())));
				Method getAll = clsDao.getMethod("getAll", null);
				Object result = getAll.invoke(dao, null);
				Toast.makeText(this, (List) result + "", Toast.LENGTH_LONG)
						.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Toast.makeText(this, "未找到相应的映射文件", 1000).show();
		}
	}
}
