package com.tz.database.enums;

/**
 * 定义一个枚举类型
 * @author Administrator
 *
 */
public enum Types 

{ 

INT, STRING,FLOAT,DOUBLE,BOOLEAN,LONG; 


public static Types toTypes(String str) 

{ 

try { 

return valueOf(str.toUpperCase());  

} 

catch (Exception ex) { 

return STRING; 

} 

} 

} 
