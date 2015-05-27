package com.tz.sqlite;

import java.util.List;

import com.tz.sqlite.baseDao.StudentDao;
import com.tz.sqlite.model.Student;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MianActivity extends Activity implements OnClickListener, OnItemClickListener {
	private EditText name;
	private RadioGroup rg;
	private RadioButton male,female;
	private ListView ls;
	private Button add;
	private StudentDao dao;
	private List<Student> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }
    
    private void initView(){
    	name=(EditText) findViewById(R.id.name);
    	rg=(RadioGroup) findViewById(R.id.rg);
    	ls=(ListView) findViewById(R.id.ls);
    	add=(Button) findViewById(R.id.add);
    	male=(RadioButton) findViewById(R.id.male);
    	female=(RadioButton) findViewById(R.id.female);
    	SQLiteDatabase database=this.openOrCreateDatabase("student.db", Context.MODE_PRIVATE, null);
    	dao=new StudentDao(database);
    	add.setOnClickListener(this);
    	try {
			dao.createDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	setAdapter();
    	ls.setOnItemClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Student student=new Student();
		if("".equals(name.getText().toString())){
			return;
		}
		student.set_name(name.getText().toString());
		int sex=0;
		RadioButton button= (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());
		switch (button.getId()) {
		case R.id.male:
			sex=1;
			break;
		case R.id.female:
			sex=-1;
			break;
		default:
			break;
		}
		try {
			student.set_sex(sex);
			dao.save(student);
			setAdapter();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//修改list
	public void setAdapter(){
		list = dao.qurryAll();
		MyAdapter adapter=new MyAdapter(this, list);
		ls.setAdapter(adapter);
	}
	
	private class MyAdapter extends BaseAdapter{
		
		private List<Student> list;
		private Context context;
		public MyAdapter(Context context ,List<Student> list){
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
			Student student=list.get(position);
			String sex=student.get_sex()==1?"男":"女";
			view.setText("id:"+student.get_id()+"  name:"+student.get_name()+" sex:"+sex);
			return view;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		View view =  LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
		final PopupWindow window = new PopupWindow(this);//创建popupWindow
		final Student student=list.get(arg2);
		//加载删除按钮
		Button btn= (Button) view.findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("info", student.get_id()+"");
				dao.delete( student.get_id());
				setAdapter();
				window.dismiss();
			}
		});
		

		window.setContentView(view);
		window.setWidth(100);
		window.setHeight(65);
		int [] location = new int[2];
		arg1.getLocationInWindow(location);
		//第一必须设置背景,第二设置可聚焦
		window.setFocusable(true);
		window.showAtLocation(arg1, Gravity.LEFT|Gravity.TOP, 0, location[1]+arg1.getHeight());
	}
    
    
    
}