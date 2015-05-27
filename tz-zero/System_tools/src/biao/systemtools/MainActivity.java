package biao.systemtools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private EditText phonenumber, phonecontent;
	private Button sendsms, callphone, printlog;
	private String initcontent, initnumber;

	/**
	 * initmethod
	 * 
	 * @author Zero
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	/**
	 * init view
	 * 
	 * @author Zero
	 */
	private void init() {
		// find view
		phonenumber = (EditText) findViewById(R.id.phonenumber);
		phonecontent = (EditText) findViewById(R.id.phonecontent);
		sendsms = (Button) findViewById(R.id.sendsms);
		callphone = (Button) findViewById(R.id.callphone);
		printlog = (Button) findViewById(R.id.printlog);
		// set listener
		sendsms.setOnClickListener(this);
		callphone.setOnClickListener(this);
		printlog.setOnClickListener(this);

	}

	public void onClick(View v) {
		// get init data
		initnumber = phonenumber.getText().toString().trim();
		initcontent = phonecontent.getText().toString();
		// judge button
		switch (v.getId()) {
		case R.id.sendsms:
			sendsms();
			break;
		case R.id.callphone:
			callphone();
			break;
		case R.id.printlog:
			try {
				printlog();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * sendsms method
	 * @author Zero
	 */
	private void sendsms() {
		if (judge()) {
			startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
					+ initnumber)).putExtra("sms_body", initcontent));
		}
	}

	/**
	 * callphone method
	 * @author Zero
	 */
	private void callphone() {
		if (!initnumber.equals("")) {
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ initnumber)));
		} else {
			Toast.makeText(this, "请输入电话号码", 1000).show();
		}
	}
/**
 * printlog method
 * @throws IOException
 * @author Zero
 */
	private void printlog() throws IOException {
		Log.i("INFO", "happy一下");
		StringBuffer buffer = new StringBuffer();
		ArrayList<String> cmdline = new ArrayList<String>();
		cmdline.add("logcat");
		cmdline.add("-d");
		cmdline.add("-s");
		cmdline.add("INFO");
		Process exec = Runtime.getRuntime().exec(
				cmdline.toArray(new String[cmdline.size()]));
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(exec.getInputStream()));
		String temp;
		while ((temp = bufferedReader.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		Toast.makeText(this, buffer.toString(), Toast.LENGTH_LONG).show();
		bufferedReader.close();
	}

	/**
	 * judge method
	 * 
	 * @return
	 * @author Zero
	 */
	private boolean judge() {
		boolean phonenumber = initnumber.equals("");
		boolean phonecontent = initcontent.equals("");
		if (phonenumber && phonecontent || phonenumber || phonecontent) {
			if (phonenumber && phonecontent) {
				Toast.makeText(MainActivity.this, "请输入电话号码和短信内容", 1000).show();
			} else if (phonecontent) {
				Toast.makeText(MainActivity.this, "请输入短信内容", 1000).show();
			} else if (phonenumber) {
				Toast.makeText(MainActivity.this, "请输入电话号码", 1000).show();
			}
			return false;
		} else {
			return true;
		}
	}
}