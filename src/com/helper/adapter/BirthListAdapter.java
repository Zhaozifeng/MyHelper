package com.helper.adapter;

import java.util.Calendar;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BirthListAdapter extends BaseAdapter {
	
	//限定倒数的日子
	public static int 		DAY_LEFT = 30;

	private Context context;
	private Cursor  cursor;
	
	public int year;
	public int month;
	public int day;
	public int offset_day;
	
	
	public BirthListAdapter(Context context,Cursor c){
		this.context = context;
		this.cursor  = c;
		Calendar calendar = Calendar.getInstance();
		year 	= calendar.get(Calendar.YEAR);
		month	= calendar.get(Calendar.MONTH);
		day		= calendar.get(calendar.DAY_OF_MONTH);
		offset_day = calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view	= inflater.inflate(R.layout.birth_list_item, null);
		for(int i=0;i<cursor.getCount();i++){
			if(isComingSoon(cursor)<=DAY_LEFT&&isComingSoon(cursor)>=0){
				
			}
		}
		
		return view;
	}
	
	
	public int isComingSoon(Cursor c){
		int y = c.getInt(5);
		int m = c.getInt(6);
		int d = c.getInt(7);
		Calendar cal = Calendar.getInstance();
		cal.set(y,m,d);
		int birthOffset = cal.get(Calendar.DAY_OF_YEAR);
		int daysBetween = birthOffset-offset_day;
		return daysBetween;
			
	}

}



















