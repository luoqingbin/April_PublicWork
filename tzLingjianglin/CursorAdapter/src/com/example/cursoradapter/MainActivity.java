package com.example.cursoradapter;

import com.example.cursoradapter.utils.MySQLiteOpenHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	private AutoCompleteTextView actv;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		actv = (AutoCompleteTextView) findViewById(R.id.word);
		actv.setThreshold(1);
		
		MySQLiteOpenHelper helper=new MySQLiteOpenHelper(this, "word.db", null, 1);
		db=helper.getReadableDatabase();
		actv.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String text=actv.getText().toString().trim();
				if(!TextUtils.isEmpty(text)){
					Cursor cursor=db.rawQuery("select * from words where word like ?", new String[]{text+"%"});
					MyAdapter adapter=new MyAdapter(MainActivity.this, cursor, true);
					actv.setAdapter(adapter);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class MyAdapter extends CursorAdapter{

		public MyAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			// TODO Auto-generated constructor stub
		}

		@Override
		public CharSequence convertToString(Cursor cursor) {
			// TODO Auto-generated method stub
			return cursor==null ? "" :cursor.getString(cursor.getColumnIndex("word"));
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater=LayoutInflater.from(context);
			View view=inflater.inflate(R.layout.list_item, null);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv=(TextView)view;
			tv.setText(cursor.getString(cursor.getColumnIndex("word")));	
		}
		
	}

}
