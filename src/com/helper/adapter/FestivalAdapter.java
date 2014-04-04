package com.helper.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FestivalAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	 * 节日对象类
	 */
	public static class FestivalModel{
		
		public String 	name;
		public int  	month;
		public int 		day;
		public String  	introduction;
		
		public FestivalModel(String name,int month,int day,String picname,String in){
			
			this.name = name;
			this.month = month;
			this.day = day;
			this.introduction = in;			
		}
				
	}

}






















