package com.example.tz_cusor_autocomplete;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;

import com.tz.adapter.CusorCompleteAdapter;
import com.tz.helper.SqlHelper;

public class MainActivity extends Activity implements TextWatcher {

	private AutoCompleteTextView act;
	private SqlHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();

	}

	private void initView() {
		act = (AutoCompleteTextView) findViewById(R.id.act);
	    act.setThreshold(1);//从第一个单词开始补齐
		act.addTextChangedListener(this);
	}

	private void initData() {
		db = new SqlHelper(this, "tz.db", null, 1);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if (!"".equals(s.toString())) {
			String sql = "select * from words where word like ?";
			Cursor c = db.getReadableDatabase().rawQuery(sql,
					new String[] { s.toString() + "%" });
			CusorCompleteAdapter mAdapter = new CusorCompleteAdapter(MainActivity.this, c,
					true);
			act.setAdapter(mAdapter);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
