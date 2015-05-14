package com.tz_tian.slidingmenu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.tz_tian.slidingmenu.util.ScreenUtils;

public class SlidingMenu extends HorizontalScrollView{




	private boolean isopen; 
	private int mScreenWidth; //屏幕宽度
	private boolean once;
	private ViewGroup mLeftMenu; 
	private ViewGroup mContent;
	private ViewGroup mRightMenu;
	private int  leftPadding = 20;
	/**
	 * 左侧菜单宽度
	 */
	private int mLeftMenuWidth;
	/**
	 * 右侧菜单宽度
	 */
	private int mRightMenuWidth;

	public SlidingMenu(Context context) {
		super(context);
	} 

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScreenWidth = ScreenUtils.getScreenWidth(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX(); // 划出去的距离
			if(scrollX > mLeftMenuWidth / 2){
				smoothScrollTo(mLeftMenuWidth, 0);
				isopen = true;
			}else{
				smoothScrollTo(0, 0);
				isopen = false;
			}
			return true;
		case MotionEvent.ACTION_MOVE:
			int scrollX1 = getScrollX();
			Log.i("INFO", scrollX1 + "+++++++++++++++");
			Log.i("INFO", ">>>>>>>>>>" + mLeftMenuWidth);
			if(scrollX1 > mLeftMenuWidth){
				Log.i("INFO", scrollX1 + ">>>>>>>>>>" + mLeftMenuWidth);
				return true;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			this.scrollTo(mLeftMenuWidth, 0);
			once = true;
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mLeftMenuWidth;
		float leftScale = 1 - 0.3f * scale;
		float rightScale = 0.8f + scale * 0.2f;

		ViewHelper.setScaleX(mLeftMenu, leftScale);
		ViewHelper.setScaleY(mLeftMenu, leftScale);
		ViewHelper.setAlpha(mLeftMenu, 0.6f + 0.4f * (1 - scale));
		ViewHelper.setTranslationX(mLeftMenu, mLeftMenuWidth * scale * 0.7f);

		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!once){
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mLeftMenu = (ViewGroup)wrapper.getChildAt(0);
			mContent = (ViewGroup)wrapper.getChildAt(1);
			mRightMenu = (ViewGroup) wrapper.getChildAt(2);

			mLeftMenuWidth = (int) (mScreenWidth * 0.8f) - leftPadding;
			mRightMenuWidth = (int) (mScreenWidth * 0.2f) - 10;
			mLeftMenu.getLayoutParams().width = mLeftMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;
			mRightMenu.getLayoutParams().width = mRightMenuWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}






}
