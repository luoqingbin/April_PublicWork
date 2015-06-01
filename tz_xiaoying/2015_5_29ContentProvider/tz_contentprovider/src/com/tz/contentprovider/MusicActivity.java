package com.tz.contentprovider;

import java.util.ArrayList;
import java.util.List;

import com.tz.bean.Music;
import com.tz.adapter.*;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

public class MusicActivity extends ListActivity {
	private List<Music> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		list=new ArrayList<Music>();
		Music music=null;
		Cursor mAudioCursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null,// 字段　没有字段　就是查询所有信息　相当于SQL语句中的　“ * ”
				null, // 查询条件
				null, // 条件的对应?的参数
				null);// 排序方式
		while (mAudioCursor.moveToNext()) {
			music=new Music();
			music.setTitle(mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)));
			music.setArtist(mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)));
			music.setAlbum(mAudioCursor.getString(mAudioCursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
		    list.add(music);
		}
		mAudioCursor.close();
		MusicAdapter mAdapter=new MusicAdapter(this,list); 
		setListAdapter(mAdapter);
	}
}
