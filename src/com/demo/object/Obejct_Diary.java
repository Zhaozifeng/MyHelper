package com.demo.object;

import java.util.Date;

public class Obejct_Diary {
	
	private int id;
	private String mTime;
	private String mEmotion;
	private String mTitle;
	private String mContent;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTime(){
		return mTime;
	}
	
	public void setTime(String mTime){
		this.mTime = mTime;
	}

	public String getEmotion(){
		return mEmotion;
	}
	
	public void setEmotion(String mEmotion){
		this.mEmotion = mEmotion;
	}
	
	public String getTitle(){
		return mTitle; 
	}
	
	public void setTitle(String mTitle){
		this.mTitle = mTitle;
	}
	
	public String getContent(){
		return this.mContent;
	}
	
	public void setContent(String mContent){
		this.mContent = mContent;
	}
	
}




























