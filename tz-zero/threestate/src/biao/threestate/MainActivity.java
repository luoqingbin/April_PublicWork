package biao.threestate;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import biao.adapter.MyAdapter;
import biao.been.Heros;

public class MainActivity extends Activity implements OnItemClickListener {
	private ListView lv;
	private List<Heros> data;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initData();
		initView();
	}

	/**
	 * initDate
	 * 
	 * @author Zero
	 */
	private void initData() {
		data = new ArrayList<Heros>();
		int faceimg [] = new int []{
				R.drawable.face1,
				R.drawable.face2,
				R.drawable.face3,
				R.drawable.face4,
				R.drawable.face5,
				R.drawable.face6,
				R.drawable.face7,
				R.drawable.face8
		};
		String name [] = new String []{
			"’≈∑…",
			"≤‹≤Ÿ",
			"ÀÔ»®",
			"¬¿≤º",
			"÷Ó∏¡¡",
			"ıı≤ı",
			"πÿ”",
			"¡ı±∏",
		};
		for (int i = 0; i < 8; i++) {
			Heros heros = new Heros();
			heros.setName(name[i]);
			heros.setFaceimg(faceimg[i]);
			heros.setNumber("66666666666");
			data.add(heros);
		}
	}

	/**
	 * initView
	 * 
	 * @author Zero
	 */
	private void initView() {
		lv = (ListView) findViewById(R.id.lv);
		MyAdapter adapter = new MyAdapter(this, data);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView name = (TextView) view.findViewById(R.id.name);
		Toast.makeText(this, name.getText(), 1000).show();
	}
}