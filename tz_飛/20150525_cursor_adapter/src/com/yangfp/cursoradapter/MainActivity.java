package com.yangfp.cursoradapter;

import com.yangfp.cursoradapter.adapter.MyCursorAdapter;
import com.yangfp.cursoradapter.utils.MySQLiteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity implements TextWatcher {
	private AutoCompleteTextView actv;
	private SQLiteDatabase db;
	private Cursor cursor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MySQLiteHelper helper = new MySQLiteHelper(
        		this, 
        		"dictionary.db", 
        		null, 
        		1);
        db = helper.getWritableDatabase();
        
        actv = (AutoCompleteTextView) findViewById(R.id.actv);
        actv.setThreshold(1);//输入1个单词开始匹配
        actv.addTextChangedListener(this);
    }

    /**
     * 值改变前调用
     */
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		Log.i("INFO", "beforeTextChanged:   "+"s:"+s+"start:"+start+"count:"+count+"after:"+after);
	}

	/**
	 * 值改变中调用
	 */
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Log.i("INFO", "onTextChanged:   "+"s:"+s+"start:"+start+"before:"+before+"count:"+count);
		
		String text = actv.getText().toString().trim();
		if(!"".equals(text)){
			cursor = db.rawQuery("select * from words where word like ?", new String[]{text+"%"});
			
			MyCursorAdapter cursorAdapter = new MyCursorAdapter(this, cursor,true, "word");
			actv.setAdapter(cursorAdapter);
		}
	}

	/**
	 * 值改变后调用
	 */
	public void afterTextChanged(Editable s) {
		Log.i("INFO", "afterTextChanged:   "+"s:"+s);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(cursor!=null){
			cursor.close();//关闭游标
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(db!=null){
			db.close();//关闭数据库连接
		}
	}
}