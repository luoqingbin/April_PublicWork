package com.task.contentprovider_media.bean;

public class Music {
	//����
		private String title;
		//�ݳ���
		private String artist;
		//ID
		private String id;
		//��SDcard�ϵĲ���·��
		private String path;
		//ʱ��
		private String duration;
		
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getArtist() {
			return artist;
		}
		public void setArtist(String artist) {
			this.artist = artist;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
}
