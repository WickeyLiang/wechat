package com.wickey.course.message.resp;
/**
 * 音乐model
 * @author fatboyliang
 * @date 2015-01-30
 */
public class Music {
	
	private String Title;
	
	private String Description;
	//音乐链接
	private String MusicUrl;
	//高质量音乐链接，WIFI环境优先使用该链接
	private String HQMusicUrl;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getMusicUrl() {
		return MusicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		MusicUrl = musicUrl;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

}
