package com.demo.tools;

import java.util.Calendar;

import com.demo.myhelper.MyHelper_MainActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

public class Utools extends Activity{
	
	private 	String mYear;
	private 	String mMonth;
	private   	String mDay;
	private		String mDayOfWeek;
	private     String mTime;
	private 	Calendar mCalendar;
	private 	Context  mContext;

	public static Vibrator vibrator;
	
	
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
	
	
	//震动处理
	public static void setVibrator(Context context,long second){
		vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(second);
	}
	
	//删除数据库项目
	public static void deleteTableItem(String tableName,Cursor c,int position){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase(); 
	}

}























