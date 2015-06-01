package com.tz.contentprovider;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private String TAG = "MainActivity";
	private TextView insertTrans, showMusic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		insertTrans = (TextView) findViewById(R.id.insert_trans);
		showMusic = (TextView) findViewById(R.id.show_music);
	}

	private void setListener() {
		// TODO Auto-generated method stub
		insertTrans.setOnClickListener(this);
		showMusic.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insert_trans:
            Intent intent1=new Intent(this,InsertTrsntionActivity.class);
            startActivity(intent1);
			break;
		case R.id.show_music:
			Intent intent = new Intent(this, MusicActivity.class);
			startActivity(intent);
			break;
		default:
			Log.i(TAG, "误操作");
			break;
		}
	}

}
