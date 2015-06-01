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
     * ͨ��contentResolver����ContentProvider����ѯ��ϵ��
     */
    public void searchContacts(View v){
//      ��ϰ��Ŀ������ѯͨѶ¼ʱ����ʱ�Ĵ�������:
//    	1.Uri·��д��
//    	2.��Ȩ��δ��
//    	3.�������α꣬���ô���
//    	4.��ȡ���ֶ�д��
//    	5.�绰������ͨѶ¼���ַ������͵ģ���bean�����Integer������
    	
    	List<Contacts> contactses = new ArrayList<Contacts>();
    	
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentResolver cr = this.getContentResolver();// ���ݽ�����
    	Cursor cursor = cr.query(uri, null, null, null, null);
    	while (cursor.moveToNext()) {
    		// ˵���ҵ�һ����ϵ��
    		Contacts contacts = new Contacts();
    		int _id = cursor.getInt(cursor.getColumnIndex("_id"));
    		Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts/"+_id+"/data");
    		Cursor cursorData = cr.query(dataUri, null, null, null, null);
    		while (cursorData.moveToNext()) {
    			String data1 = cursorData.getString(cursorData.getColumnIndex("data1"));
    			String mimetype = cursorData.getString(cursorData.getColumnIndex("mimetype"));
    			if(mimetype.equals(StructuredName.CONTENT_ITEM_TYPE)){//�൱��mimetypeId.equals("vnd.android.cursor.item/name")
    				// ���������ǵ绰
    				contacts.setName(data1);
    			}else if (mimetype.equals(Phone.CONTENT_ITEM_TYPE)){//vnd.android.cursor.item/phone_v2
    				// ��������������
    				contacts.setPhoneNumber(data1);
    			}else if (mimetype.equals(Email.CONTENT_ITEM_TYPE)){//vnd.android.cursor.item/email_v2
    				// ��������������
    				contacts.setEmail(data1);
    			}
			}
    		contactses.add(contacts);// ÿѭ��һ�����һ����ϵ��
		}
    	
    	for (Contacts con : contactses) {
			Log.i("INFO", con.toString());
		}
    }
    
    /**
     * ͨ��contentResolver����һ����ϵ��
     */
    public void insertContacts(View view){
    	
//    	��ϰʱ���д���
//    	1��cr.insert(uri, values);��values���ܴ�null���ǿ���ֻ��һ��new ContentValues()
//    	2:raw_contact_idд��_id��������
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentResolver cr = this.getContentResolver();
    	ContentValues values = new ContentValues();
    	Uri insert = cr.insert(uri, values);
    	
    	long id = ContentUris.parseId(insert);// �������ݿ��������_id
    	
    	Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/data");// ��data���в�������
    	// ������������
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "����");
    	values.put("mimetype", "vnd.android.cursor.item/name");//StructuredName.CONTENT_ITEM_TYPE
    	cr.insert(dataUri, values);
    	
    	// ����绰����
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "13552706251");
    	values.put("mimetype", "vnd.android.cursor.item/phone_v2");//Phone.CONTENT_ITEM_TYPE
    	cr.insert(dataUri, values);
    	
    	// ����email����
    	values.clear();
    	values.put("raw_contact_id", id);
    	values.put("data1", "1@1.com");
    	values.put("mimetype", "vnd.android.cursor.item/email_v2");//vnd.android.cursor.item/email_v2
    	cr.insert(dataUri, values);
    	
    }
    
    /**
     * ͨ��Զ���������һ����ϵ��
     */
    public void insertContactsByTranscation(View v) {
    	ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
    	
    	Uri uri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/raw_contacts");
    	ContentValues values = new ContentValues();
    	ContentProviderOperation cpo1  = ContentProviderOperation.newInsert(uri).withValues(values).build();
    	
    	Uri dataUri = Uri.parse("content://"+ContactsContract.AUTHORITY+"/data");// ��data���в�������
    	
    	ContentProviderOperation cpo2 = 
    			ContentProviderOperation.newInsert(dataUri).withValueBackReference("raw_contact_id", 0)//0�����һ������ķ��ص���������_id
    			.withValue("data1", "�����11").withValue("mimetype", "vnd.android.cursor.item/name").build();
    	
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
    		//һ�����ύ
			cr.applyBatch(ContactsContract.AUTHORITY, operations);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}
    	
	}
    
}