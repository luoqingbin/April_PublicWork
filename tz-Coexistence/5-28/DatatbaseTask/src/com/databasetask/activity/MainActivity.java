package com.databasetask.activity;

import java.util.List;

import com.databasetask.R;
import com.databasetask.constant.Constant;
import com.databasetask.database.DatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity implements TextWatcher{
	AutoCompleteTextView autoText;
	DatabaseHelper databaseHelper;
	/** 需要模糊查询的单词 */
	public static final String[] words = new String []{
		"apple","attr","about",
		"base","back","baby",
		"fuck",
		"what"
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		databaseHelper = new DatabaseHelper(this, Constant.Database.DATABASE_NAME, null, 1);
		autoText = (AutoCompleteTextView) findViewById(R.id.autoText);
		// 设置从输入的第一个字符开始自动补齐
		autoText.setThreshold(1);
		// 添加文本更改监听器
		autoText.addTextChangedListener(this);
		// 条件查询
/*		Cursor cursor = databaseHelper.getReadableDatabase().query(
				Constant.Database.TABLE_NAME, null, "_id = ?", new String[]{Integer.toString(1)},
				null, null, null);*/
		// 模糊查询	
/*		Cursor cursor = databaseHelper.getReadableDatabase().query(Constant.Database.TABLE_NAME, null, 
                " _text like ?", new String[] {"a%"},null, null, null);*/
/*		Cursor cursor = databaseHelper.getReadableDatabase().query(Constant.Database.TABLE_NAME, null, 
                " _text like ?", new String[] {"a%"},null, null, null);
		while(cursor.moveToNext()){
			Log.i("Log", "_id = "+cursor.getInt(cursor.getColumnIndex("_id")));
			Log.i("Log", "_text = "+cursor.getString(cursor.getColumnIndex("_text")));
		}*/
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		String text = autoText.getText().toString().trim();
		// 判空
		if (!TextUtils.equals("", text)) {
			// 根据用户输入的内容来模糊查询
			Cursor cursor = databaseHelper.getReadableDatabase().query(Constant.Database.TABLE_NAME, 
					null, " _text like ?", new String[] {text+"%"},null, null, null);
			autoText.setAdapter(new MyAdapter(this, cursor, true));
		}
	}


	@Override
	public void afterTextChanged(Editable s) {

	}
	
	class MyAdapter extends CursorAdapter{

		public MyAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
		}
		
		// 防止返回的内容为对象地址
		@Override
		public CharSequence convertToString(Cursor cursor) {
			// TODO Auto-generated method stub
			// 如果等于null 就返回空
			return cursor == null?"":cursor.getString(cursor.getColumnIndex("_text"));
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = getLayoutInflater().inflate(R.layout.auto_text_list, null);
			return view;
		}

		// 给视图绑定数据
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView text = (TextView) view.findViewById(R.id.auto_text_item);
			String result = cursor.getString(cursor.getColumnIndex("_text"));
			text.setText(result);
		}
		
	}
}
