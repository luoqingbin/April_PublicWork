package biao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import biao.been.Heros;
import biao.threestate.R;

/**
 * Adapter
 * 
 * @author Zero
 *
 */
public class MyAdapter extends BaseAdapter {
	private List<Heros> data;
	private LayoutInflater inflater;

	/**
	 * deliver the paramete
	 * 
	 * @param context
	 *            £ºcontext
	 * @param data
	 *            £º data
	 * @author Zero
	 */
	public MyAdapter(Context context, List<Heros> data) {
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.list, null);
		Heros heros = data.get(position);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView number = (TextView) convertView.findViewById(R.id.number);
		ImageView faceimg = (ImageView) convertView.findViewById(R.id.faceimg);
		name.setText(heros.getName());
		number.setText(heros.getNumber());
		faceimg.setImageResource(heros.getFaceimg());
		return convertView;
	}
}
