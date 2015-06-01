package com.ckview.utils.db.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ckview.utils.BeanUtils;
import com.ckview.utils.OpResult;
import com.ckview.utils.log.BaseLog;

/**
 * It is a abstract class, user must write getBeanClass and getTableName when extend it.
 * @author Administrator
 * @param <T>
 * @param <T>
 *
 */
public abstract class AbstractDAO<T> {
	
	/**
	 * @return the db
	 */
	public SQLiteDatabase getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	private SQLiteDatabase db;
	
	public AbstractDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbstractDAO(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	/**
	 * get bean class
	 * @return bean class
	 */
	protected abstract Class<?> getBeanClass();
	
	/**
	 * get table name
	 * @return table
	 */
	protected abstract String getTableName();
	
	/**
	 * Check the object.class is equal to DAO.getBeanClass or not
	 * If they a equal return null,else return a OpResult that is fail
	 * @param obj
	 * @return
	 */
	private OpResult checkObject(T bean){
		OpResult result = null;
		if(!bean.getClass().getName().equals(this.getBeanClass().getName())){
			result = OpResult.fail("faild to insert because this object is not a "+this.getBeanClass().getSimpleName()+
					" instance");
			result.setData(bean);
			return result;
		}
		return result;
	}
	
	/**
	 * 通过一个List插入多个Bean到表中。
	 * 如果不分插入成功则返回一个标记为semiSuccess的OpResult类，具体异常信息可通过message获取，如果完全插入失败则返回失败状态的OpResult
	 * @param records
	 * @return OpResult may be semi success
	 */
	public OpResult insert(List<T> beans){
		OpResult opResult = new OpResult();
		for (T bean : beans) {
			OpResult or = insert(bean);
			if(!or.isSuccess()){
				if(opResult.isSuccess()){
					// There are some insert have done success in collection, set status to semi success
					opResult.setSemiSuccess();
					// Take message add into end of message
					opResult.setMessage((String)or.get("errMsg"));
				}else if(opResult.isSemiSuccess()){
					try {
						// Add message
						String message = (String)opResult.get("message");
						opResult.setMessage(message + "," + (String)or.get("errMsg"));
					} catch (Exception e) {
						opResult.setMessage((String)or.get("errMsg"));
					}
				}else{
					try {
						// Add message
						String errMsg = (String)opResult.get("errMsg");
						opResult.setMessage(errMsg + "," + (String)or.get("errMsg"));
					} catch (Exception e) {
						opResult.setMessage((String)or.get("errMsg"));
					}
				}
			}else {
				opResult.setSuccess();
			}
		}
		return opResult;
	}
	
	/**
	 * 通过Bean插入一条数据。
	 * 如果插入成功将返回一个在data中包含新插入行数的OpResult类，如果插入失败将返回一个在errMsg中包含插入异常的OpResult类,并且在data中返回插入失败的Bean对象
	 * @param obj 表对应的Bean
	 * @return
	 */
	public OpResult insert(T bean){
		OpResult result = checkObject(bean);
		if(result != null){
			return result;
		}
		
		// Transform object to ContentValues
		ContentValues contentValues = BeanUtils.transformBeanToContentValues(getBeanClass(), bean);
		
		try {
			long row = db.insert(this.getTableName(), null, contentValues);
			result = OpResult.success();
			result.setData(row);
		} catch (Exception e) {
			BaseLog.error("error", "Faild to insert "+this.getBeanClass().getName()+
					" into "+this.getTableName() + "Exception:" + e);
			result = OpResult.fail("Faild to insert "+this.getBeanClass().getName()+
					" into "+this.getTableName());
			result.setData(bean);
		}
		return result;
	}
	
	/**
	 * 按条件删除记录
	 * @param whereClause 条件（可以是带 ? 参数的那种，跟后面的 whereArgs对应上就行）
	 * @param whereArgs 参数
	 * @return
	 */
	public OpResult delete(String whereClause, String[] whereArgs){
		OpResult result;
		try {
			int count = db.delete(getTableName(), whereClause, whereArgs);
			result = OpResult.success();
			result.setData(count);
		} catch (Exception e) {
			BaseLog.error("error", "Faild to delete from "+this.getTableName() + "Exception:" + e);
			result = OpResult.fail("Faild to delete from "+this.getTableName());
		}
		return result;
	}
	
	/**
	 * 删除符合Map描述的记录
	 * 如果插入成功将返回一个在data中包含受影响行数的OpResult类，如果插入失败将返回一个在errMsg中包含插入异常的OpResult类,并且在data中返回删除失败的Map对象
	 * @param map
	 * @return
	 */
	public OpResult deleteDataByMap(HashMap<String, Object> map){
		OpResult result;
		
		// Transform map to whereClause and whereArgs
		String whereClause = "";
		List<String> whereArgs = new ArrayList<String>();
		for (String key : map.keySet()) {
			whereClause += key+"=? and ";
			if(map.get(key) != null){
				whereArgs.add(String.valueOf(map.get(key)));
			}
		}
		whereClause = whereClause.substring(0, whereClause.length()-4);
		BaseLog.debug("debug", "whereClause:"+whereClause);
		BaseLog.debug("debug", "whereArgs:"+whereArgs);
		
		result = delete(whereClause, whereArgs.toArray(new String[0]));
		return result;
	}
	
	/**
	 * 利用Map更新数据
	 * @param map 要更新的字段-值集合
	 * @param whereClause 条件（可以是带 ? 参数的那种，跟后面的 whereArgs对应上就行）
	 * @param whereArgs 参数
	 * @return
	 */
	public OpResult update(HashMap<String, Object> map, String whereClause, String[] whereArgs){
		OpResult result;
		
		ContentValues values = new ContentValues();
		for (String key : map.keySet()) {
			values.put(key, String.valueOf(map.get("key")));
		}
		
		try {
			int count = db.update(getTableName(), values, whereClause, whereArgs);
			result = OpResult.success();
			result.setData(count);
		} catch (Exception e) {
			BaseLog.error("error", "Faild to update to "+this.getTableName() + "Exception:" + e);
			result = OpResult.fail("Faild to delete to "+this.getTableName());
			result.setData(map);
		}
		return result;
	}
	
	/**
	 * 通过map更新数据
	 * @param map 要更新的字段-值集合
	 * @param term 更新满足的whereClause-whereArgs集合
	 * @return
	 */
	public OpResult update(HashMap<String, Object> map, HashMap<String, Object> term){
		OpResult result;
		
		// Transform map to whereClause and whereArgs
		String whereClause = "";
		List<String> whereArgs = new ArrayList<String>();
		for (String key : term.keySet()) {
			whereClause += key+"=? and ";
			if(term.get(key) != null){
				whereArgs.add(String.valueOf(term.get(key)));
			}
		}
		whereClause = whereClause.substring(0, whereClause.length()-4);
		result = update(map, whereClause, whereArgs.toArray(new String[0]));
		return result;
	}
	
	/**
	 * 通过Bean更新数据
	 * @param obj 要更新对应的Bean
	 * @param whereClause 条件（可以是带 ? 参数的那种，跟后面的 whereArgs对应上就行）
	 * @param whereArgs 参数
	 * @return
	 */
	public OpResult update(T bean, String whereClause, String[] whereArgs){
		OpResult result = checkObject(bean);
		if(result != null){
			return result;
		}
		
		ContentValues values = BeanUtils.transformBeanToContentValues(getBeanClass(), bean);
		try {
			int count = db.update(getTableName(), values, whereClause, whereArgs);
			result = OpResult.success();
			result.setData(count);
		} catch (Exception e) {
			BaseLog.error("error", "Faild to update to "+this.getTableName() + "Exception:" + e);
			result = OpResult.fail("Faild to delete to "+this.getTableName());
			result.setData(bean);
		}
		return result;
	}
	
	/**
	 * 根据bean和map更新数据
	 * @param bean 要更新对应的Bean
	 * @param map 要更新的字段-值集合
	 * @return
	 */
	public OpResult update(T bean, HashMap<String, Object> map){
		OpResult result = checkObject(bean);
		if(result != null){
			return result;
		}
		
		ContentValues values = BeanUtils.transformBeanToContentValues(getBeanClass(), bean);
		// Transform map to whereClause and whereArgs
		String whereClause = "";
		List<String> whereArgs = new ArrayList<String>();
		for (String key : map.keySet()) {
			whereClause += key+"=? and ";
			if(map.get(key) != null){
				whereArgs.add(String.valueOf(map.get(key)));
			}
		}
		whereClause = whereClause.substring(0, whereClause.length()-4);
		
		try {
			int count = db.update(getTableName(), values, whereClause, whereArgs.toArray(new String[0]));
			result = OpResult.success();
			result.setData(count);
		} catch (Exception e) {
			BaseLog.error("error", "Faild to update to "+this.getTableName() + "Exception:" + e);
			result = OpResult.fail("Faild to delete to "+this.getTableName());
			result.setData(bean);
		}
		return result;
	}
	
	/**
	 * 根据条件返回表数据
	 * @param columns 要返回的字段名，为null表示返回全部
	 * @param selection 筛选条件列表（可以是带 ? 参数的那种，跟后面的 selectionArgs对应上就行），为null表示无条件
	 * @param selectionArgs 条件参数
	 * @param groupBy
	 * @param having
	 * @param orderBy 排序方式
	 * @param limit 返回最大条数
	 * @return
	 */
	public List<T> select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		Cursor cursor = db.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
		List<T> result = new ArrayList<T>();
		while(cursor.moveToNext()){
			result.add((T) BeanUtils.getBean(getBeanClass(), cursor));
		}
		cursor.close();
		return result;
	}
	
	public List<T> selectAll(String[] columns){
		List<T> result = select(columns, null, null, null, null, null, null);
		return result;
	}
	
	public Cursor selectForCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
		return db.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
	}
}
