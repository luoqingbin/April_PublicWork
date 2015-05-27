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
	private String columnName;//Ҫ��ʾ������

	public MyCursorAdapter(Context context, Cursor c, boolean autoRequery,String columnName) {
		super(context, c, autoRequery);
		this.context = context;
		this.columnName = columnName;
		inflater = LayoutInflater.from(context);//�õ�LayoutInflater����1
//		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//�õ�LayoutInflater����2
	}
	
	/**
	 * cursor�п�����null,��null����һ�����ַ���
	 */
	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor == null?"":cursor.getString(cursor.getColumnIndex(columnName));
	}

	/**
	 * �õ���ͼ
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = inflater.inflate(R.layout.auto_list, null);
		return view;
	}

	/**
	 * �����ݰ󶨵���ͼ
	 */
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndex(columnName));
		TextView tv = (TextView) view;
		tv.setText(name);
	}

}
