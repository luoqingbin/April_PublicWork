package com.task.contentprovider;

import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void query(View v){
		Uri conetnt_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/raw_contacts");
		ContentResolver resolver=this.getContentResolver();
		Uri data_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/data");
		Cursor cursor=resolver.query(conetnt_uri, new String[]{"_id"}, null, null, null);
		while(cursor.moveToNext()){
			String id=cursor.getString(0);
			System.out.println("id=="+id);
			Cursor cursor2=resolver.query(data_uri, new String[]{"data1","mimetype",}, "raw_contact_id=?", new String[]{id}, null);
			while(cursor2.moveToNext()){
				System.out.println("data1=="+cursor2.getString(0));
				System.out.println("mimetype=="+cursor2.getString(1));
			}
		}
	}
	
	public void insert(View v){
		Uri conetnt_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/raw_contacts");
		ContentResolver resolver=this.getContentResolver();
		Uri data_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/data");
		ContentValues values=new ContentValues();
	//	Uri insert = resolver.insert(conetnt_uri, values);
	//	long id=ContentUris.parseId(insert);
		long id=2;
		values.put("raw_contact_id", 2);
		values.put("data1", "Danny");
		values.put("mimetype", "vnd.android.cursor.item/name");
		resolver.insert(data_uri, values);
		
		// 插入电话属性
		values.clear();
		values.put("raw_contact_id", id);
		values.put("data1", "1383838388");
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		resolver.insert(data_uri, values);

		// 插入email属性
		values.clear();
		values.put("raw_contact_id", id);
		values.put("data1", "ligang@google.com");
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		resolver.insert(data_uri, values);
	}
	
	public void insertByTransaction(View v){
		try {
			ContentResolver resolver=this.getContentResolver();
			ArrayList<ContentProviderOperation> operations=new ArrayList<ContentProviderOperation>();
			Uri conetnt_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/raw_contacts");
			Uri data_uri=Uri.parse("content://" + ContactsContract.AUTHORITY+"/data");
			ContentValues values=new ContentValues();
			ContentProviderOperation operation1=ContentProviderOperation
					.newInsert(conetnt_uri)
					.withValues(values)
					.build();
			
			operations.add(operation1);
			ContentProviderOperation operation2=ContentProviderOperation
					.newInsert(data_uri)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1", "Maybe")
					.withValue("mimetype", "vnd.android.cursor.item/name")
					.build();
			operations.add(operation2);
			// 插邮箱
			ContentProviderOperation operation3 = ContentProviderOperation
					.newInsert(data_uri)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1", "Maybe@google.com")
					.withValue("mimetype", "vnd.android.cursor.item/email_v2")
					.build();

			operations.add(operation3);
			// 插电话
			ContentProviderOperation operation4 = ContentProviderOperation
					.newInsert(data_uri)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1", "1838383833")
					.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
					.build();
			operations.add(operation4);
			
			// 最后一次性提交
			resolver.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
