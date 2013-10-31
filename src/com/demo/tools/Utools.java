package com.demo.tools;

import java.util.Calendar;
import java.util.Date;

import android.content.Context;

public class Utools {
	
	private 	String mYear;
	private 	String mMonth;
	private   	String mDay;
	private		String mDayOfWeek;
	private 	Calendar mCalendar;
	private 	Context  mContext;

	
	public void Utools(Context c){
		mContext = c;
	}
	
	public void setDate(){
		mCalendar = Calendar.getInstance();
		mYear  = ""+mCalendar.get(Calendar.YEAR);
		mMonth = ""+mCalendar.get(Calendar.MONTH);
		mDay   = ""+mCalendar.get(Calendar.DAY_OF_MONTH);
		mDayOfWeek = ""+mCalendar.get(Calendar.DAY_OF_WEEK);		
	}
	
	public String getDate(){
		return
	}

}























