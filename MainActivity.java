package com.lwh.search.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.lwh.search.R;
import com.lwh.search.service.SearchService;

/**
 * 搜索排序
 * @author lwh
 */
public class MainActivity extends Activity {

	private AutoCompleteTextView atv_search;
	private Button btn_search;
	//搜索关键字
	private List<String> keywords;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initData();
        initView();
    }

	private void initData() {
		SearchService service = new SearchService(this);
		keywords = service.findAll();
	}

	private void initView() {
		atv_search = (AutoCompleteTextView) findViewById(R.id.atv_search);
		btn_search = (Button) findViewById(R.id.btn_search);
		atv_search.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,keywords));
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = atv_search.getText().toString();
				SearchService service = new SearchService(MainActivity.this);
				service.save(keyword);
				Toast.makeText(MainActivity.this, "正在搜索...", 1000).show();
			}
		});
	}
}
