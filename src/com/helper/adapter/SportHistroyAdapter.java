package com.helper.adapter;

import java.util.ArrayList;

import com.demo.myhelper.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SportHistroyAdapter extends BaseAdapter {
	
	public Context mcontext;
	public ArrayList<SportDateModel> list;
	public int 	   month = 0;
	public int     day = 0;
	public float   sumTotal = 0.0f;
	
	public SportHistroyAdapter(Context context, ArrayList<SportDateModel> list){
		this.mcontext = context;
		this.list = list;
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
		View view = inflater.inflate(R.layout.histroy_sport_item, null);
		TextView datetv = (TextView)view.findViewById(R.id.sport_item_date_tv);
		TextView nametv = (TextView)view.findViewById(R.id.sport_item_name_tv);
		TextView kalutv = (TextView)view.findViewById(R.id.sport_item_kalu_tv);
		
		SportDateModel dateitem = list.get(arg0);
		datetv.setText(dateitem.year+"年"+dateitem.month+"月"+dateitem.day+"日");
		nametv.setText(dateitem.name);
		kalutv.setText(dateitem.total+"千卡");
		sumTotal+=dateitem.total;
		view.setTag(dateitem);
		return view;
	}
	
	
	/*
	 * 获取本月总卡路里
	 */
	public static float getSumKalu(ArrayList<SportDateModel> list){
		float sum = 0f;
		for(int i=0;i<list.size();i++)
			sum+=list.get(i).total;
		return sum;
	}
	
	
	public static class SportDateModel{
		
		public int 		year;
		public int 		month;
		public int 		day;
		public String 	name;
		public float    total;
		public int  	minute;
		public int 		random;
		
		public SportDateModel(String n,int y,int m,int day,int min,float total,int random){
			this.name = n;
			this.year = y;
			this.month = m;
			this.day = day;
			this.minute = min;
			this.total = total;
			this.random = random;
		}		
	}

}













