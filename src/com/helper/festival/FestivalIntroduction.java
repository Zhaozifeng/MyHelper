package com.helper.festival;

import java.util.Calendar;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.FestivalAdapter.FestivalModel;
import com.helper.situation.SituationRecieve;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//介绍节日类
public class FestivalIntroduction extends Activity {
	
	public Button			titleBtn; 
	public ImageView		curImg;
	public TextView			curTv;
	public FestivalModel 	curItem;
	public boolean 			isAlarm;
	public Calendar         calendar;
	public PendingIntent	pi;
	public AlarmManager		am;
	
	public static String NAME_PARAMS = "festival_name";
	public static String MONTH_PARAMS = "month_params";
	public static String DAY_PARAMS = "day_params";

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.single_festival_layout);		
		initUI();
	}
	
	
	
	
	public void initUI(){
		
		curItem = GlobalApp.getInstance().selectFestival;
		Utools.customTitle2(this, curItem.name);		
		titleBtn = (Button)findViewById(R.id.custom_title_menu_btn3);
		titleBtn.setPadding(8, 8, 8, 8);
		isAlarm = isInDb();
		if(isAlarm)
			titleBtn.setText("取消提醒");
		else
			titleBtn.setText("设置提醒");
				
		curImg	 = (ImageView)findViewById(R.id.single_festival_img);
		curTv    = (TextView)findViewById(R.id.festival_ind_tv);
		int imgId = getResources().getIdentifier(curItem.picname, "drawable", this.getPackageName());
		curImg.setBackgroundResource(imgId);
		curTv.setText(curItem.introduction);
		
		setServer();				
		titleBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if(isAlarm){
					isAlarm = false;
					editTable(isAlarm);
					titleBtn.setText("设置提醒");
					am.cancel(pi);
				}
				else{
					isAlarm = true;
					editTable(isAlarm);
					titleBtn.setText("取消提醒");
					am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
				}
			}			
		});		
	}
	
	//设定服务
	public void setServer(){
		Intent intent = new Intent(this,FestivalRecieve.class);
		intent.putExtra(NAME_PARAMS, curItem.name);
		intent.putExtra(MONTH_PARAMS, curItem.month);
		intent.putExtra(DAY_PARAMS, curItem.day);
		pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.MONTH, curItem.month-1);
		calendar.set(Calendar.DAY_OF_MONTH, curItem.day);
		calendar.set(Calendar.HOUR_OF_DAY,10);
	}
	
	
	//检查数据库是否存在
	public boolean isInDb(){
		String condition = "name=? and month=? and day=?";
		String[] values = {curItem.name,curItem.month+"",curItem.day+""};
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		Cursor c = sql.query(MainDatabase.FESTIVAL_TABLE_NAME, null, condition, values, null, null, null);
		c.moveToFirst();		
		if(c.getInt(4)==0)
			return false;
		else
			return true;		
	}
	
	//修改数据表
	public void editTable(boolean isalarm){
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		String condition = "name=? and month=? and day=?";
		String[] values = {curItem.name,curItem.month+"",curItem.day+""};
		ContentValues cv = new ContentValues();
		if(isalarm)
			cv.put("alarm", 1);		
		else
			cv.put("alarm", 0);
		sql.update(MainDatabase.FESTIVAL_TABLE_NAME,cv, condition, values);
	}
	
}



















