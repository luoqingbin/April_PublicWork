package com.tz.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tz.bean.Music;
import com.tz.contentprovider.R;

public class MusicAdapter extends BaseAdapter {
    private List<Music> list;
    private LayoutInflater mInflater;
	public MusicAdapter(Context context,List<Music> list) {
		super();
		this.list = list;
		mInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		ViewHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.music_item, null);
			holder=new ViewHolder();
			holder.title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.artist=(TextView) convertView.findViewById(R.id.tv_artist);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Music m=list.get(position);
		holder.title.setText(m.getTitle());
		holder.artist.setText(m.getArtist());
		return convertView;
	}
	public class ViewHolder{
		TextView title;
		TextView artist;
	}

}
