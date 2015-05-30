package com.tz.autocomplete;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tz.autocomplete.util.MyDatabaseHelper;

public class MainActivity extends Activity implements TextWatcher {
	private AutoCompleteTextView autoComplete;
	private MyDatabaseHelper myHelper;
	private SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        autoComplete=(AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoComplete.setThreshold(1); //从第一个字母开始补齐
        myHelper=new MyDatabaseHelper(this, "coutrys.db", null, 1);
        db=myHelper.getWritableDatabase();
        autoComplete.addTextChangedListener(this);
        
    }
     
    private class MyCursorAdapter extends CursorAdapter{

		public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
		}
        
		 @Override
		public CharSequence convertToString(Cursor cursor) {
			return cursor==null?"":cursor.getString(cursor.getColumnIndex("name"));
		}
		 
		//决定长什么样子
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view=LayoutInflater.from(context).inflate(R.layout.cursor, null);
			return view;
		}
        
		//绑定数据
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			 TextView textView=(TextView)view;
			 textView.setText(cursor.getString(cursor.getColumnIndex("name")));
		}
    	
    }
    
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
	}
	public void afterTextChanged(Editable s) {
		 //输入改变后回调
		String text=autoComplete.getText().toString();
		if(!text.equals("")){
			String sql="select * from country where name like ?";
			Cursor rawQuery = db.rawQuery(sql, new String[]{"%"+text+"%"});
			MyCursorAdapter adapter=new MyCursorAdapter(this, rawQuery, true);
			autoComplete.setAdapter(adapter);
		}
	}
}