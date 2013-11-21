package com.demo.tools;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

public class Utools extends Activity {
	
	private 	String mYear;
	private 	String mMonth;
	private   	String mDay;
	private		String mDayOfWeek;
	private     String mTime;
	private 	Calendar mCalendar;
	private 	Context  mContext;

	public Vibrator vibrator;
	
	
	public void Utools(Context c){
		mContext = c;
	}
	
	public void setDate(){											//设置时间字符串
		mCalendar = Calendar.getInstance();
		mYear  = ""+mCalendar.get(Calendar.YEAR);
		mMonth = ""+mCalendar.get(Calendar.MONTH);
		mDay   = ""+mCalendar.get(Calendar.DAY_OF_MONTH);
		mDayOfWeek = ""+mCalendar.get(Calendar.DAY_OF_WEEK);		
	}
	
	public String getDate(){										//返回字符串
		return mYear+"-"+mMonth+"-"+mDay;
	}
	
	
	public void setVibrator(long second){
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(second);
	}

}























