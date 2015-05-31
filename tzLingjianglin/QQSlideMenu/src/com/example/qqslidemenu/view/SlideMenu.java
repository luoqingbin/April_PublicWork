package com.example.qqslidemenu.view;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlideMenu extends HorizontalScrollView {
	private int rightOffset;
	private int mMenuWidth;
	private DisplayMetrics metric;
	private ViewGroup menu;
	private ViewGroup main;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			int scrollDis = (Integer) msg.obj;
			SlideMenu.this.smoothScrollTo(scrollDis, 0);
		};
	};


	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		metric = new DisplayMetrics();
		WindowManager manager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metric);
		rightOffset=metric.widthPixels/4;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LinearLayout father=(LinearLayout) getChildAt(0);
		menu = (ViewGroup) father.getChildAt(0);
		main = (ViewGroup) father.getChildAt(1);
		mMenuWidth=metric.widthPixels-rightOffset;
		main.getLayoutParams().width=metric.widthPixels;
		
		menu.getLayoutParams().width=mMenuWidth;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);  //�ȵ��ø���ķ���  ���źÿؼ���λ��  �������Լ��Ĵ���ı�λ��
		if(changed){
			this.scrollTo(mMenuWidth, 0);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			 //�ж��Ѿ�����ȥ�ľ��룬����������������ʾ�˵�����������
			Message msg = handler.obtainMessage();
			int scrollX=0;
			int x=this.getScrollX();
			System.out.println("x=="+x);
			if(x<mMenuWidth-metric.widthPixels/2){
				scrollX=0;
			}else{
				scrollX=mMenuWidth;
			}
			
			msg.obj = scrollX;
			
			msg.sendToTarget();
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);   //��ΪHorizontalScrollView�Լ�д��onTouchEvent�ķ��������¼����Դ���
	//	return false;
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//�����������ļ����¼�
				//������
		float scale = (float)l/mMenuWidth;//��0����1
		float leftScale =(float) (1.0f - 0.3*scale); //���Ŵ�1��С��0.7
		ViewHelper.setScaleX(menu, leftScale);//��menu��1����С��0.7
		ViewHelper.setScaleY(menu, leftScale);
		ViewHelper.setAlpha(menu, (float)(1.0f-0.8*scale));//��menu͸���ȴ�1����0.2
		ViewHelper.setTranslationX(menu, l*0.7f);//���ֲ�������Ƴ�ȥ
		
		//�����������
		float rightScale = 0.8f +scale*0.2f;
		ViewHelper.setScaleX(main, rightScale);//��0.8���Ŵ�1.0�������һ�����
		ViewHelper.setScaleY(main, rightScale);
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
}
