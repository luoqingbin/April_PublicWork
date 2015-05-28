package com.databasetask.constant;

public class Constant {
	public static class Database{
		/** ���ݿ��� */
		public static final String DATABASE_NAME = "Word.db";
		/** ���� */
		public static final String TABLE_NAME = "word";
		/** Word���Text��Ŀ�� */
		public static final String WORD_TEXT_NAME = "_text";
		/** ���ݿ�汾 */
		public static final int VERSION = 1;
		/** ����Word�� */
		public  static final String CREATE_TABLE_SQL ="create table if not exists word(" +
				"_id integer primary key autoincrement," +
				"_text varchar(50) not null)";
		/** ��ѯWord����������� */
		public static final String DISPLAY_ALL_STUDENT = "select * from word";
	}

}
