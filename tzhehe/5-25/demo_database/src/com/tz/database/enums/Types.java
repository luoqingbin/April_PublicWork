package com.tz.database.enums;

/**
 * ����һ��ö������
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
