package com.yangfp.cursoradapter.adaoter;

import com.yangfp.cursoradapter.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter{
	private Context context;
	private LayoutInflater inflater;
	private String columnName;//要显示的列名

	public MyCursorAdapter(Context context, Cursor c, boolean autoRequery,String columnName) {
		super(context, c, autoRequery);
		this.context = context;
		this.columnName = columnName;
		inflater = LayoutInflater.from(context);//得到LayoutInflater方法1
//		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//得到LayoutInflater方法2
	}
	
	/**
	 * cursor有可能是null,是null返回一个空字符串
	 */
	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor == null?"":cursor.getString(cursor.getColumnIndex(columnName));
	}

	/**
	 * 得到视图
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.auto_list, null);
		return view;
	}

	/**
	 * 将数据绑定到视图
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndex(columnName));
		TextView tv = (TextView) view;
		tv.setText(name);
	}

}
