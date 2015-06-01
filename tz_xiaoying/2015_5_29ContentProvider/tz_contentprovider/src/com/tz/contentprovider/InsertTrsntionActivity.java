package com.tz.contentprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tz.bean.ContactBean;

public class InsertTrsntionActivity extends Activity implements OnClickListener{
	private String TAG="InsertTrsntionActivity";
	private TextView insert,select;
	private AsyncQueryHandler asyncQuery;
	private List<ContactBean> list;

	private Map<Integer, ContactBean> contactIdMap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insert);
		initView();
		setListener();
	}


	private void initView() {
		// TODO Auto-generated method stub
		insert=(TextView) findViewById(R.id.insert);
		select=(TextView) findViewById(R.id.select);
	}
	private void setListener() {
		// TODO Auto-generated method stub
		insert.setOnClickListener(this);
		select.setOnClickListener(this);
		asyncQuery = new MyAsyncQueryHandler(getContentResolver());
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.insert:
			insertTrans();
			break;
		case R.id.select:
			Query();
			break;

		default:
			Log.i(TAG, "误操作");
			break;
		}
	}
	public void insertTrans(){
		try {
			ContentResolver cr=this.getContentResolver();
			// ContentProvider操作的数组
					ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
			//插入raw_contacts——id为自增
			Uri uri = Uri.parse("content://" + ContactsContract.AUTHORITY
					+ "/raw_contacts");
			ContentValues values=new ContentValues();
			ContentProviderOperation opt1=ContentProviderOperation.newInsert(uri).withValues(values).build();
			operations.add(opt1);
			//插入data
			Uri uri1 = Uri.parse("content://" + ContactsContract.AUTHORITY
					+ "/data");
			ContentProviderOperation opt2=ContentProviderOperation.newInsert(uri1)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1","arror")
					.withValue("mimetype", "vnd.android.cursor.item/name")
					.build();
			operations.add(opt2);
			// 插邮箱
			ContentProviderOperation op3 = ContentProviderOperation
					.newInsert(uri1)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1", "arror@google.com")
					.withValue("mimetype", "vnd.android.cursor.item/email_v2")
					.build();

			operations.add(op3);
			// 插电话
			ContentProviderOperation op4 = ContentProviderOperation
					.newInsert(uri1)
					.withValueBackReference("raw_contact_id", 0)
					.withValue("data1", "1838383833")
					.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
					.build();
			operations.add(op4);
			cr.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),"插入有误", 0).show();
		}
	
		
	}
	public void Query(){
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人的Uri
		String[] projection = { 
				ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1,
				"sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
		}; // 查询的列
		asyncQuery.startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc"); // 按照sort_key升序查询
	}
	/**
	 * 数据库异步查询类AsyncQueryHandler
	 * 
	 * @author administrator
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);
		}

		/**
		 * 查询结束的回调函数
		 */
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				
				contactIdMap = new HashMap<Integer, ContactBean>();
				
				list = new ArrayList<ContactBean>();
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					int contactId = cursor.getInt(4);
					Long photoId = cursor.getLong(5);
					String lookUpKey = cursor.getString(6);

					if (contactIdMap.containsKey(contactId)) {
						
					}else{
						
						ContactBean cb = new ContactBean();
						cb.setDisplayName(name);
//					if (number.startsWith("+86")) {// 去除多余的中国地区号码标志，对这个程序没有影响。
//						cb.setPhoneNum(number.substring(3));
//					} else {
						cb.setPhoneNum(number);
//					}
						cb.setSortKey(sortKey);
						cb.setContactId(contactId);
						cb.setPhotoId(photoId);
						cb.setLookUpKey(lookUpKey);
						list.add(cb);
						
						contactIdMap.put(contactId, cb);
						
					}
				}
				if (list.size() > 0) {
					for (ContactBean b:list) {
						Log.i(TAG, list.toString());
					}
				}
			}
		}

	}

}
