package com.task.contentprovider_media.adapter;

import java.util.List;

import com.task.contentprovider_media.R;
import com.task.contentprovider_media.bean.Music;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MySongListAdapter extends BaseAdapter {

	private Context context;
	private List<Music> musiclist;
	public MySongListAdapter(Context context,List<Music> musiclist) {
		super();
		this.context = context;
		this.musiclist=musiclist;
	}

	@Override
	public int getCount() {
		return musiclist==null?0:musiclist.size();
	}

	@Override
	public Object getItem(int position) {

		return musiclist.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Music music=musiclist.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.listitem, null);
			holder.title = (TextView) convertView.findViewById(R.id.itemTitle);
			holder.artist = (TextView) convertView.findViewById(R.id.itemArtist);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(music.getTitle());
		holder.artist.setText(music.getArtist());

		return convertView;
	}

	class ViewHolder {

		public TextView title;
		public TextView artist;
	}
}
