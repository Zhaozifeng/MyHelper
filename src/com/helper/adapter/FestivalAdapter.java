package com.helper.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import com.demo.myhelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FestivalAdapter extends BaseAdapter {

	public ArrayList <FestivalModel> list;
	public Context		mcontext;
	public int			month;
	public int 			curDayOffset;
	
		
	public FestivalAdapter(Context c,ArrayList <FestivalModel> l){
		this.mcontext = c;
		this.list = l;
		Calendar calendar = Calendar.getInstance();
		month = calendar.get(Calendar.MONTH);
		curDayOffset = calendar.get(Calendar.DAY_OF_YEAR);
		
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = LayoutInflater.from(mcontext);
		View view = inflater.inflate(R.layout.festival_list_item, null);
		TextView name 	= (TextView)view.findViewById(R.id.festival_name_tv);
		TextView offset = (TextView)view.findViewById(R.id.festival_offset_tv);
		ImageView festivalImg = (ImageView)view.findViewById(R.id.festival_item_img);
		
		FestivalModel fest = list.get(arg0);
		name.setText(fest.name);
		
		//计算节日的offset
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, fest.month-1);
		c.set(Calendar.DAY_OF_MONTH, fest.day);
		int festivalOffset = c.get(Calendar.DAY_OF_YEAR);
		int left;
		if(festivalOffset>curDayOffset){
			left = festivalOffset - curDayOffset;
			offset.setText("还有"+left+"天");
		}
		else{
			offset.setText("嘻嘻，该节日已经过啦");
		}
		
		//配置图片
		int id = mcontext.getResources().getIdentifier(fest.picname, "drawable", mcontext.getPackageName());
		festivalImg.setBackgroundResource(id);
		view.setTag(fest);
	
		return view;
	}
	
	
	/*
	 * 节日对象类
	 */
	public static class FestivalModel{
		
		public String 	name;
		public int  	month;
		public int 		day;
		public String  	introduction;
		public String   picname;
		
		public FestivalModel(String name,int month,int day,String picname,String in){
			
			this.name = name;
			this.month = month;
			this.day = day;
			this.introduction = in;		
			this.picname = picname;
		}
				
	}

}






















