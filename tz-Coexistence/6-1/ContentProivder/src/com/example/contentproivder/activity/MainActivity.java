package com.example.contentproivder.activity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener {
	Button get_music;
	ListView listview;
	List<String> listMusicName;
	private static final String MUSIC_MIN_DURATION = "30000";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		get_music = (Button) findViewById(R.id.get_music);
		listview = (ListView) findViewById(R.id.listview);
		get_music.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		listMusicName = new ArrayList<String>();
		getMediaPlayers();
		//getContacts();
	}
	
	/**
	 * ��ȡSD���µ����������ļ���
	 */
	public void getMediaPlayers(){
		// ��ȡ�ⲿ�����ļ���URI
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		// ��ȡ���ݽ�����ʵ��
		ContentResolver cr = getContentResolver();
		// ����ʱ����С�������ļ�
		Cursor cursor = cr.query(uri,null,MediaStore.Audio.Media.DURATION+">?",new String[]{MUSIC_MIN_DURATION},null);
		
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//������
			listMusicName.add(name);
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				listMusicName);
		listview.setAdapter(adapter);
	}
	
	/**
	 * ��ȡͨѶ¼������ϵ��
	 */
	public void getContacts(){
		Uri uri = Uri.parse(ContactsContract.AUTHORITY_URI+"/data");
		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(uri, null, null, null, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("data1"));
			listMusicName.add(name);
		}
		//listMusicName.remove(listMusicName.size()-1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				listMusicName);
		listview.setAdapter(adapter);
	}

}
