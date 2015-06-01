package biao.uidemo;

import biao.uidemo.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Second extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Ui());
	}

	/**
	 * UI layout
	 * 
	 * @return outerlayout
	 * @author Zero
	 */
	public LinearLayout Ui() {
		LinearLayout outerlayout = new LinearLayout(this);
		outerlayout.setOrientation(LinearLayout.VERTICAL);
		outerlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		outerlayout.setBackgroundColor(Color.WHITE);
		LinearLayout onlayout = new LinearLayout(this);
		onlayout.setOrientation(LinearLayout.HORIZONTAL);
		onlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		EditText leftEditText = new EditText(this);
		leftEditText.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		Button rightButton = new Button(this);
		rightButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		rightButton.setText("查看上一个布局");
		rightButton.setOnClickListener(this);
		LinearLayout inlayout = new LinearLayout(this);
		inlayout.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		ImageView bottomimg = new ImageView(this);
		bottomimg.setImageResource(R.drawable.ic_launcher);
		inlayout.setGravity(Gravity.CENTER);
		inlayout.addView(bottomimg);
		onlayout.addView(leftEditText);
		onlayout.addView(rightButton);
		outerlayout.addView(onlayout);
		outerlayout.addView(inlayout);
		return outerlayout;
	}

	public void onClick(View v) {
		startActivity(new Intent().setClass(this, MainActivity.class));
	}
}
