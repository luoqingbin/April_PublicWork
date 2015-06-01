package com.yangfp.contentresolver;

import java.util.ArrayList;
import java.util.List;

import com.yangfp.contentresolver.bean.Contacts;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * 通过contentResolver访问ContentProvider来查询联系人
     */
    public void searchContacts(View v){
//      练习项目案例查询通讯录时运行时的错误问题:
//    	1.Uri路径写错
//    	2.读权限未加
//    	3.有两个游标，调用错误
//    	4.获取的字段写错
//    	5.电话号码在通讯录是字符串类型的，而bean定义成Integer类型了
    	
    	List<Contacts> contactses = new ArrayList<Contacts>();
    	
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentResolver cr = this.getContentResolver();// 内容接受者
    	Cursor cursor = cr.query(uri, null, null, null, null);
    	while (cursor.moveToNext()) {
    		// 说明找到一个联系人
    		Contacts contacts = new Contacts();
    		int _id = cursor.getInt(cursor.getColumnIndex("_id"));
    		Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts/"+_id+"/data");
    		Cursor cursorData = cr.query(dataUri, null, null, null, null);
    		while (cursorData.moveToNext()) {
    			String data1 = cursorData.getString(cursorData.getColumnIndex("data1"));
    			String mimetype = cursorData.getString(cursorData.getColumnIndex("mimetype"));
    			if(mimetype.equals(StructuredName.CONTENT_ITEM_TYPE)){//相当于mimetypeId.equals("vnd.android.cursor.item/name")
    				// 这条属性是电话
    				contacts.setName(data1);
    			}else if (mimetype.equals(Phone.CONTENT_ITEM_TYPE)){//vnd.android.cursor.item/phone_v2
    				// 这条属性是邮箱
    				contacts.setPhoneNumber(data1);
    			}else if (mimetype.equals(Email.CONTENT_ITEM_TYPE)){//vnd.android.cursor.item/email_v2
    				// 这条属性是名字
    				contacts.setEmail(data1);
    			}
			}
    		contactses.add(contacts);// 每循环一次添加一个联系人
		}
    	
    	for (Contacts con : contactses) {
			Log.i("INFO", con.toString());
		}
    }
    
    /**
     * 通过contentResolver插入一个联系人
     */
    public void insertContacts(View view){
    	
//    	练习时运行错误：
//    	1：cr.insert(uri, values);的values不能传null但是可以只传一个new ContentValues()
//    	2:raw_contact_id写成_id，意义搞错
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentResolver cr = this.getContentResolver();
    	ContentValues values = new ContentValues();
    	Uri insert = cr.insert(uri, values);
    	
    	long id = ContentUris.parseId(insert);// 插入数据库后自增的_id
    	
    	Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/data");// 往data表中插入数据
    	// 插入名字属性
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "马化腾");
    	values.put("mimetype", "vnd.android.cursor.item/name");//StructuredName.CONTENT_ITEM_TYPE
    	cr.insert(dataUri, values);
    	
    	// 插入电话属性
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "13552706251");
    	values.put("mimetype", "vnd.android.cursor.item/phone_v2");//Phone.CONTENT_ITEM_TYPE
    	cr.insert(dataUri, values);
    	
    	// 插入email属性
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "1@1.com");
    	values.put("mimetype", "vnd.android.cursor.item/email_v2");//vnd.android.cursor.item/email_v2
    	cr.insert(dataUri, values);
    	
    }
    
    /**
     * 通过远程事务插入一个联系人
     */
    public void insertContactsByTranscation(View v) {
    	ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
    	
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentValues values = new ContentValues();
    	ContentProviderOperation cpo1  = ContentProviderOperation.newInsert(uri).withValues(values).build();
    	
    	Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/data");// 往data表中插入数据
    	
    	ContentProviderOperation cpo2 = 
    			ContentProviderOperation.newInsert(dataUri).withValueBackReference("raw_contact_id", 0)//0代表第一个插入的返回的自增长的_id
    			.withValue("data1", "李彦宏11").withValue("mimetype", "vnd.android.cursor.item/name").build();
    	
    	ContentProviderOperation cpo3 = 
    			ContentProviderOperation.newInsert(dataUri).withValueBackReference("raw_contact_id", 0)
    			.withValue("data1", "13552706259").withValue("mimetype", "vnd.android.cursor.item/phone_v2").build();
    	
    	ContentProviderOperation cpo4 = 
    			ContentProviderOperation.newInsert(dataUri).withValueBackReference("raw_contact_id", 0)
    			.withValue("data1", "123@qq.com").withValue("mimetype", "vnd.android.cursor.item/email_v2").build();
    	
    	operations.add(cpo1);
    	operations.add(cpo2);
    	operations.add(cpo3);
    	operations.add(cpo4);
    	ContentResolver cr = this.getContentResolver();
    	try {
    		//一次性提交
			cr.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
    	
	}
    
}