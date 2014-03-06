package com.demo.myhelper;

import android.app.Application;

public class GlobalApp extends Application {
	
	public static GlobalApp    self;
	
	//屏幕长宽
	public int ScreenWidth;
	public int ScreenHeight;
	
	//基本数据
	public String	 	user_name;
	public int			user_age;
	public String		user_sex;
		
	//私人定制类变量数据
	public float 	steplenght;
	public int		weight;
	public int 		Sensitivity;
	public float	settingCalorie;
	
	
	//设置屏幕长宽
	public void setScreen(int w,int h){
		this.ScreenWidth  = w;
		this.ScreenHeight = h;
	}
	
	
	
	//定义全局变量
	public static void setGlobalInstance(GlobalApp global){
		self = global;
	}	
	public static GlobalApp getInstance(){
		return self;
	}
	
	//用户名
	public void setUserName(String name){
		this.user_name = name;
	}
	public String getUserName(){
		return this.user_name;
	}
	
	
	

}

















