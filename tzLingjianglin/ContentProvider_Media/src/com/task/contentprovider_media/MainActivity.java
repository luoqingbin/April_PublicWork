package com.task.contentprovider_media;

import java.util.ArrayList;
import java.util.List;

import com.task.contentprovider_media.adapter.MySongListAdapter;
import com.task.contentprovider_media.bean.Music;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private List<Music> musicList;
	private MySongListAdapter adapter;
	private ListView music_listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		music_listview=(ListView)findViewById(R.id.music_list);
		musicList=new ArrayList<Music>();
		init();
		adapter=new MySongListAdapter(this, musicList);
		music_listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void init(){
		Cursor cur = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.ARTIST,
						MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DATA }, null, null, null);
		
		try {
			if (cur != null) {

				while (cur.moveToNext()) {
					Music m = new Music();
					m.setTitle(cur.getString(0));
					m.setDuration(cur.getString(1));
					m.setArtist(cur.getString(2));
					m.setId(cur.getString(3));
					m.setPath(cur.getString(4));
					musicList.add(m);
				}
			}
		} catch (Exception e) {
		} finally {
			if (cur != null)
				cur.close();
		}
	}

}
