package com.tz.bean;

public class Music {
   private String title;//歌名
   private String artist;//艺术家
   private String album;//专辑
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
public String getAlbum() {
	return album;
}
public void setAlbum(String album) {
	this.album = album;
}
@Override
public String toString() {
	return "Music [title=" + title + ", artist=" + artist + ", album=" + album
			+ "]";
}

   
}
