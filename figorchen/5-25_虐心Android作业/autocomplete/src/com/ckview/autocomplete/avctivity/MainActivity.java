package com.ckview.autocomplete.avctivity;

import java.util.List;

import com.ckview.autocomplete.R;
import com.ckview.autocomplete.db.DbHelper;
import com.ckview.utils.InitializeActivity;
import com.ckview.utils.db.ParseTableXML;
import com.ckview.utils.db.bean.Word;
import com.ckview.utils.db.dao.WordDAO;
import com.ckview.utils.log.BaseLog;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements TextWatcher, OnFocusChangeListener {
	private AutoCompleteTextView tv;
	WordDAO dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		InitializeActivity.initView(this);
		DbHelper helper = new DbHelper(this, "words.db", null, 1);
		SQLiteDatabase db = helper.getReadableDatabase();
		dao = new WordDAO(db);
		List<Word> words = dao.selectAll(null);
		for (Word word : words) {
			BaseLog.info("ck", word.toString());
		}
		tv.setThreshold(1);
		tv.addTextChangedListener(this);
		tv.setOnFocusChangeListener(this);
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String text = tv.getText().toString().trim();
		Cursor cursor = dao.selectForCursor(null, "word like ?", new String[]{text+"%"}, null, null, null, "0,5");
		List<Word> words =  dao.select(null, "word like ?", new String[]{text+"%"}, null, null, null, "0,5");
		for (Word word : words) {
			BaseLog.info("ck", word.toString());
		}
		MyAdapter adapter = new MyAdapter(this, cursor, true);
		tv.setAdapter(adapter);
	}
	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
	private class MyAdapter extends CursorAdapter{

		public MyAdapter(Context context, Cursor cursor, boolean autoRequery) {
			super(context, cursor, autoRequery);
			// TODO Auto-generated constructor stub
		}

		@Override
		public CharSequence convertToString(Cursor cursor) {
			// TODO Auto-generated method stub
			return cursor == null?"":cursor.getString(cursor.getColumnIndex("word"));
		}
		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv_auto = (TextView)view;
			String text = cursor.getString(cursor.getColumnIndex("word"));
			tv_auto.setText(text);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
			TextView tv_auto = new TextView(context);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			tv_auto.setLayoutParams(params);
			return tv_auto;
		}

		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if(hasFocus){
			String text = tv.getText().toString().trim();
			Cursor cursor = dao.selectForCursor(null, "word like ?", new String[]{text+"%"}, null, null, null, "0,5");
			MyAdapter adapter = new MyAdapter(this, cursor, true); 
			tv.setAdapter(adapter);
		}
	}
}
