package com.ckview.orm.activity;

import java.util.ArrayList;
import java.util.List;

import com.ckview.orm.R;
import com.ckview.utils.InitializeActivity;
import com.ckview.utils.db.DbHelper;
import com.ckview.utils.db.ParseTableXML;
import com.ckview.utils.db.bean.Test;
import com.ckview.utils.db.dao.TestDAO;
import com.ckview.utils.log.BaseLog;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		InitializeActivity.initView(this);
		button1.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		ParseTableXML.praseXML(this, "orm");
		DbHelper helper = new DbHelper(this, "test.db", null, 1);
		SQLiteDatabase db = helper.getWritableDatabase();
		TestDAO dao = new TestDAO(db);
		
		//测试插入数据
		List<Test> testBeans = new ArrayList<Test>();
		testBeans.add(new Test(0, "qwe", (short) 15, true));
		testBeans.add(new Test(0, "qdsawe", (short) 15, true));
		testBeans.add(new Test(0, "qwfge", (short) 11, false));
		testBeans.add(new Test(0, "qetwe", (short) 12, true));
		testBeans.add(new Test(0, "qdswe", (short) 14, false));
		dao.insert(testBeans);
		
		//测试查询数据
		testBeans = null;
		testBeans = dao.selectAll(null);
		for (Test test : testBeans) {
			BaseLog.debug("ck", test.toString());
		}
		
		//测试修改数据
		Test updateBean = new Test(100, "fdkjghjfdhgjdf", (short) 99, false);
		dao.update(updateBean, "fage=?", new String[]{"12"});
		
		//测试查询数据
		testBeans = null;
		testBeans = dao.selectAll(null);
		for (Test test : testBeans) {
			BaseLog.debug("ck", test.toString());
		}
	}
}
