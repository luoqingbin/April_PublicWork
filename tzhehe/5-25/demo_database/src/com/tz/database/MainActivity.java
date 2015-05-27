package com.tz.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.tz.database.dao.CreateSQLUtil;
import com.tz.database.dao.MyDatabaseHelper;
import com.tz.database.demo.Classes;
import com.tz.database.demo.Student;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	
	private EditText className;
	private Button add;
	private ListView ls;
	private MyDatabaseHelper helper;
	private SQLiteDatabase db;
	CreateSQLUtil createSQLUtil;
	private List<Classes> list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		helper = new MyDatabaseHelper(this, "tz_vip.db", null,
				1);
		db = helper.getWritableDatabase();
		createSQLUtil=new CreateSQLUtil(db);
		helper.deteleDabase();
		try {
			initView();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 初始化控件
	 * @throws Exception
	 */
	private void initView() throws Exception {
		className=(EditText) findViewById(R.id.className);
		add=(Button) findViewById(R.id.add);
		ls=(ListView) findViewById(R.id.ls);
		add.setOnClickListener(this);
		ls.setOnItemClickListener(this);
		setAdapter();
		
		
	}
	
	
	/**
	 * 查询所有的班级  并设置list
	 * @throws Exception
	 */
		public void setAdapter() throws Exception{
			list = createSQLUtil.quryAll(Classes.class);
			ClassesAdapter classesAdapter=new ClassesAdapter(this, list);
			ls.setAdapter(classesAdapter);
		}
		
		@Override
		public void onClick(View v) {
			if(!"".equals(className.getText().toString())){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
				Classes classes=new Classes(className.getText().toString(), df.format(new Date()));
				try {
					createSQLUtil.save(classes);
					setAdapter();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Classes classes= list.get(arg2);
			Intent intent=new Intent(this,StudentActivity.class);
			intent.putExtra("_id", classes.get_id());
			intent.putExtra("_name", classes.get_name());
			startActivity(intent);
		}
		
		
		private class ClassesAdapter extends BaseAdapter{
			
			private List<Classes> list;
			private Context context;
			public ClassesAdapter(Context context ,List<Classes> list){
				this.context=context;
				this.list=list;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return list.get(position);
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView view=new TextView(context);
				Classes classes=list.get(position);
				view.setText("班级id:"+classes.get_id()+"  班级名字:"+classes.get_name()+"  创建时间:"+classes.get_createdate());
				return view;
			}
		}




	
	
}