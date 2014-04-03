package com.helper.situation;

import java.util.Calendar;

import com.demo.affair.Affair_Note_Recall;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;
import com.helper.adapter.SituationAdapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

public class SituationRecieve extends BroadcastReceiver {
	
	public Context mcontext;
	public Cursor  cursor;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		this.mcontext = arg0;			
		searchDb(arg0,arg1);		
		//Utools.setVibrator(arg0, 1000, 1);
	}
	
	
	/*
	 * 判断是否今天响铃
	 */
	public boolean isReceive(Intent arg1){
		int hour = arg1.getIntExtra(SituationAdapter.HOUR_PARAMS, -1);
		int minute = arg1.getIntExtra(SituationAdapter.MINUTE_PARAMS, -1);
		Calendar calendar = Calendar.getInstance();
		int h = calendar.get(Calendar.HOUR_OF_DAY);
		int m = calendar.get(Calendar.MINUTE);
		if(hour==h&&m==minute)
			return true;
		else
			return false;
		
	}
	
	
	/*
	 * notification提醒
	 */
	public void alarmNotification(Context arg0,Intent intent){
		String modename = intent.getStringExtra(SituationAdapter.NAME_PARAMS);
		String modetitle = "小助手为您改变模式啦";
		NotificationManager mn = (NotificationManager)arg0.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n  = new Notification(R.drawable.modeset,modetitle,System.currentTimeMillis());
		n.defaults = Notification.DEFAULT_ALL;
		Intent it = new Intent(arg0,MyHelper_MainActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(arg0, 0, it, 0);		
		n.setLatestEventInfo(arg0, "通知", "小助手提醒您，您设置的"+modename+"模式已生效，点击这里查看", pi);	
		mn.notify(0, n);
		setParams();
	}
	
	
	/*
	 * 设置参数
	 */
	public void setParams(){
		if(cursor.getCount()!=0){
			AudioManager am = (AudioManager)mcontext.getSystemService(Context.AUDIO_SERVICE);
			int volumn      = cursor.getInt(5);
			int isvibrate   = cursor.getInt(4);
			am.setStreamVolume(AudioManager.STREAM_RING, volumn, 0);
			am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volumn, 0);
			am.setStreamVolume(AudioManager.STREAM_SYSTEM, volumn, 0);
			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			if(isvibrate==1)				
				am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
			else
				am.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);						
		}
	}	
	
	/*
	 * 显示设置的模式
	 */
	public void searchDb(Context arg0,Intent intent){
		int hour = intent.getIntExtra(SituationAdapter.HOUR_PARAMS, -1);
		int minute = intent.getIntExtra(SituationAdapter.MINUTE_PARAMS, -1);
		String name = intent.getStringExtra(SituationAdapter.NAME_PARAMS);
		if(hour==-1){
			Toast.makeText(arg0, "切换失败", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(arg0, "切换成功", Toast.LENGTH_SHORT).show();
			//这里需要重新初始化数据库
			MainDatabase HelperSQLite = new MainDatabase(arg0,1);
			SQLiteDatabase db         = HelperSQLite.getReadableDatabase();
			String condition = "name=? and hour=? and minute=?";
			String values[]  = {name,hour+"",minute+""};
			cursor = db.query
			(MainDatabase.MODE_TABLE_NAME, null, condition, values, null, null, null);
			cursor.moveToFirst();
			if(cursor.getCount()!=0){
				int isopen = cursor.getInt(7);
				if(isopen!=0)
					alarmNotification(arg0,intent);
			}
			else
				Toast.makeText(arg0, "切换成功", Toast.LENGTH_SHORT).show();			
		}
	}
	
}








