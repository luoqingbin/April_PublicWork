package biao.uidemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {
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
		outerlayout.setBackgroundColor(Color.WHITE);
		outerlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		EditText leftEditText = new EditText(this);
		leftEditText.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
		Button rightButton = new Button(this);
		rightButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		rightButton.setText("查看下一个布局");
		rightButton.setOnClickListener(this);
		outerlayout.addView(leftEditText);
		outerlayout.addView(rightButton);
		return outerlayout;
	}

	public void onClick(View v) {
		startActivity(new Intent().setClass(this, Second.class));
	}
}