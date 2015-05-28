package com.tz.adapter;


import com.example.tz_cusor_autocomplete.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CusorCompleteAdapter extends CursorAdapter{

	public CusorCompleteAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
	}


	@Override
	public CharSequence convertToString(Cursor cursor) {
		// TODO Auto-generated method stub
		return cursor==null?"":cursor.getString(cursor.getColumnIndex("word"));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_item, null); 
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		TextView v=(TextView) view;
		v.setText(cursor.getString(cursor.getColumnIndex("word")));
	}

}
