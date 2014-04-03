package com.demo.myhelper;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import com.helper.adapter.*;
import com.helper.adapter.BirthListAdapter.BirthdayPersonModel;
import com.helper.adapter.SituationAdapter.SituationModel;

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
	
	//储存生日联系人
	public ArrayList <BirthdayPersonModel>	BirhPeopleList;
	public BirthdayPersonModel				selectBirthItem;
	
	//储存情景模式联系人
	public SituationModel		selectSituItem;
	public ArrayList <SituationModel>	SituationList;
	
	
	
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

















