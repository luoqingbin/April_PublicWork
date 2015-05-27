package com.yangfp.cursoradapter;

import com.yangfp.cursoradapter.adaoter.MyCursorAdapter;
import com.yangfp.cursoradapter.utils.MySQLiteHelper;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        actv.setThreshold(1);//����1�����ʿ�ʼƥ��
        actv.addTextChangedListener(this);
    }

    /**
     * ����ǰ����
     */
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * �����е���
	 */
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ������ɺ����
	 */
	public void afterTextChanged(Editable s) {
		String text = actv.getText().toString().trim();
		if(!"".equals(text)){
			cursor = db.rawQuery("select * from words where word like ?", new String[]{actv.getText()+"%"});
			
			MyCursorAdapter cursorAdapter = new MyCursorAdapter(this, cursor,true, "word");
			actv.setAdapter(cursorAdapter);
		}
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		cursor.close();//�ر��α�
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		db.close();//�ر����ݿ�����
	}
}