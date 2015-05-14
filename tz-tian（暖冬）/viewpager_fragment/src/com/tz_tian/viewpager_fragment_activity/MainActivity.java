package com.tz_tian.viewpager_fragment_activity;

import java.util.ArrayList;
import java.util.List;

import com.tz_tian.viewpager_fragment.R;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener, OnPageChangeListener{

	private HorizontalScrollView hsv;
	private RadioGroup rg;
	private RadioButton china,korea,nKorea,japan,usa,uk;
	private View v;
	private ViewPager viewPager;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private MyAdapter adapter;
	private int fromX = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initview();
		initFragment();
		initColor(0);
	}



	private void initview() {
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		rg = (RadioGroup) findViewById(R.id.rg);
		china = (RadioButton) findViewById(R.id.china);
		korea = (RadioButton) findViewById(R.id.korea);
		nKorea = (RadioButton) findViewById(R.id.nkorea);
		japan = (RadioButton) findViewById(R.id.japan);
		usa = (RadioButton) findViewById(R.id.usa);
		uk = (RadioButton) findViewById(R.id.uk);
		v = (View) findViewById(R.id.v);
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		rg.setOnCheckedChangeListener(this);

	}

	private void initFragment() {
		fragments.clear();
		int count = rg.getChildCount();
		for(int i = 0; i < count; i++){
			Bundle bundle = new Bundle();
			bundle.putInt("id", i);
			MyFragment fragment = new MyFragment();
			fragment.setArguments(bundle);
			fragments.add(fragment);
		}
		adapter = new MyAdapter(getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		int currentItem = 0;
		switch (arg1) {
		case R.id.china:
			currentItem = 0;
			break;

		case R.id.korea:
			currentItem = 1;
			break;

		case R.id.nkorea:
			currentItem = 2;
			break;

		case R.id.japan:
			currentItem = 3;
			break;

		case R.id.usa:
			currentItem = 4;
			break;

		case R.id.uk:
			currentItem = 5;
			break;

		default:
			break;
		}
		viewPager.setCurrentItem(currentItem);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int arg2) {
		//需要监听的主要事件
		//1计算对应选项居中时，hsv滑动的位置
		int total = (int) ((position+positionOffset)*china.getWidth());
		Log.i("INFO", "rb_position:"+position);
		int green = (viewPager.getWidth()-china.getWidth())/2;
		int dx = total - green;//计算出要滑出去的距离
		hsv.scrollTo(dx, 0);
		lineScroll(position, positionOffset);
	}

	@Override
	public void onPageSelected(int arg0) {
		initColor(arg0);
	}

	private void initColor(int arg0) {

		switch (arg0) {
		case 0:
			china.setTextColor(Color.parseColor("#ff0000"));
			korea.setTextColor(Color.parseColor("#000000"));
			nKorea.setTextColor(Color.parseColor("#000000"));
			japan.setTextColor(Color.parseColor("#000000"));
			usa.setTextColor(Color.parseColor("#000000"));
			uk.setTextColor(Color.parseColor("#000000"));
			break;
		case 1:
			china.setTextColor(Color.parseColor("#000000"));
			korea.setTextColor(Color.parseColor("#ff0000"));
			nKorea.setTextColor(Color.parseColor("#000000"));
			japan.setTextColor(Color.parseColor("#000000"));
			usa.setTextColor(Color.parseColor("#000000"));
			uk.setTextColor(Color.parseColor("#000000"));
			break;
		case 2:
			china.setTextColor(Color.parseColor("#000000"));
			korea.setTextColor(Color.parseColor("#000000"));
			nKorea.setTextColor(Color.parseColor("#ff0000"));
			japan.setTextColor(Color.parseColor("#000000"));
			usa.setTextColor(Color.parseColor("#000000"));
			uk.setTextColor(Color.parseColor("#000000"));
			break;
		case 3:
			china.setTextColor(Color.parseColor("#000000"));
			korea.setTextColor(Color.parseColor("#000000"));
			nKorea.setTextColor(Color.parseColor("#000000"));
			japan.setTextColor(Color.parseColor("#ff0000"));
			usa.setTextColor(Color.parseColor("#000000"));
			uk.setTextColor(Color.parseColor("#000000"));
			break;
		case 4:
			china.setTextColor(Color.parseColor("#000000"));
			korea.setTextColor(Color.parseColor("#000000"));
			nKorea.setTextColor(Color.parseColor("#000000"));
			japan.setTextColor(Color.parseColor("#000000"));
			usa.setTextColor(Color.parseColor("#ff0000"));
			uk.setTextColor(Color.parseColor("#000000"));
			break;
		case 5:
			china.setTextColor(Color.parseColor("#000000"));
			korea.setTextColor(Color.parseColor("#000000"));
			nKorea.setTextColor(Color.parseColor("#000000"));
			japan.setTextColor(Color.parseColor("#000000"));
			usa.setTextColor(Color.parseColor("#000000"));
			uk.setTextColor(Color.parseColor("#ff0000"));
			break;
		default:
			break;
		}
	}



	public void lineScroll(int position,float positionOffSet){
		//拿到被选中按钮在屏幕中的位置
		//		Toast.makeText(this, "position:"+position, 1000).show();
		Log.i("INFO", "line_position:"+position);
		RadioButton button = (RadioButton) rg.getChildAt(position);
		int [] location = new int[2];
		button.getLocationInWindow(location);
		//开始做位移滑动
		TranslateAnimation animation = new TranslateAnimation(
				fromX,
				location[0]+positionOffSet*china.getWidth(), 
				0, 
				0);
		animation.setDuration(50);//动画持续事件
		animation.setFillAfter(true);
		fromX = (int) (location[0]+positionOffSet*china.getWidth());
		v.startAnimation(animation);//线开始动画
	}
}
