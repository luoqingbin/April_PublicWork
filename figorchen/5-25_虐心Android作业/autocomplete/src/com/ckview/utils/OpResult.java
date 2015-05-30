package com.ckview.utils;

import java.util.HashMap;

import com.ckview.utils.log.BaseLog;

public class OpResult extends HashMap {
	public static HashMap result;
	private static final int SUCCESS = 0;
	private static final int FAIL = 1;
	private static final int SEMI_SUCCESS = 2;
	
	public OpResult(){
		if(result == null){
			this.result = new HashMap();
		}
	}
	
	/**
	 * return a OpResult witch is success
	 * @return
	 */
	public static OpResult success(){
		OpResult instance = new OpResult();
		result.put("status", OpResult.SUCCESS);
		return instance;
	}
	
	/**
	 * return a OpResult witch is success and include data
	 * @return
	 */
	public static OpResult success(Object data){
		OpResult instance = new OpResult();
		result.put("status", OpResult.SUCCESS);
		result.put("data", data);
		return instance;
	}
	
	/**
	 * return a OpResult witch is fail and put error message into
	 * @return
	 */
	public static OpResult fail(String errMsg){
		OpResult instance = new OpResult();
		result.put("status", OpResult.FAIL);
		result.put("errMsg", errMsg);
		return instance;
	}
	
	public void setSuccess(){
		this.result.put("status", OpResult.SUCCESS);
	}
	public void setFail(String errMsg){
		this.result.put("status", OpResult.FAIL);
		this.result.put("errMsg", errMsg);
	}
	public void setSemiSuccess(){
		this.result.put("status", OpResult.SEMI_SUCCESS);
	}
	public void setMessage(String msg){
		this.result.put("message", msg);
	}
	public String getErrMsg(){
		String str = null;
		str = (String) this.result.get("errMsg");
		if(str == null){
			str = "get opresult errmsg fail";
			
		}
		return str;
	}
	public String getMessage(){
		String str = null;
		str = (String) this.result.get("message");
		if(str == null) {
			str =  "get opresult message fail";
		}
		return str;
	}
	/**
	 * If is success/fail return true/false
	 * @return
	 */
	public boolean isSuccess(){
		try {
			int status = (Integer) this.result.get("status");
			if(status == OpResult.SUCCESS){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			BaseLog.warn("warn", "get opresult status fail: "+e);
			return false;
		}
	}
	
	/**
	 * If is semi success/fail return true/false
	 * @return
	 */
	public boolean isSemiSuccess(){
		try {
			int status = (Integer) this.result.get("status");
			if(status == OpResult.SEMI_SUCCESS){
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			BaseLog.warn("warn", "get opresult status fail: "+e);
			return false;
		}
	}
	
	/**
	 * return data if there are data
	 * @return
	 */
	public Object getData(){
		try {
			Object obj = this.result.get("data");
			return obj;
		} catch (Exception e) {
			BaseLog.warn("warn", "get opresult data fail: "+e);
			return null;
		}
	}
	
	/**
	 * Put data into hash map
	 * @return
	 */
	public void setData(Object obj){
		this.result.put("data", obj);
	}
}
