package com.databasetask.constant;

public class Constant {
	public static class Database{
		/** 数据库名 */
		public static final String DATABASE_NAME = "Word.db";
		/** 表名 */
		public static final String TABLE_NAME = "word";
		/** Word表的Text条目名 */
		public static final String WORD_TEXT_NAME = "_text";
		/** 数据库版本 */
		public static final int VERSION = 1;
		/** 创建Word表 */
		public  static final String CREATE_TABLE_SQL ="create table if not exists word(" +
				"_id integer primary key autoincrement," +
				"_text varchar(50) not null)";
		/** 查询Word表的所有数据 */
		public static final String DISPLAY_ALL_STUDENT = "select * from word";
	}

}
