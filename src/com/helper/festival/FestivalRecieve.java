package com.helper.festival;

import java.util.Calendar;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.helper.adapter.SituationAdapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FestivalRecieve extends BroadcastReceiver {
	
	public String name;
	public int month;
	public int day;

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Calendar calendar = Calendar.getInstance();
		int curM = calendar.get(Calendar.MONTH);
		int curD = calendar.get(Calendar.DAY_OF_MONTH);
		if(isToday(arg1))
			alarmNotification(arg0,arg1);	
		else
			Toast.makeText(arg0, curM+","+curD, 8000).show();
	}
	
	
	
	
	public boolean isToday(Intent intent){
		name = intent.getStringExtra(FestivalIntroduction.NAME_PARAMS);
		month = intent.getIntExtra(FestivalIntroduction.MONTH_PARAMS, -1);
		day = intent.getIntExtra(FestivalIntroduction.DAY_PARAMS, -1);
		Calendar calendar = Calendar.getInstance();
		int curM = calendar.get(Calendar.MONTH)+1;
		int curD = calendar.get(Calendar.DAY_OF_MONTH);
		if(month==curM&&curD==day)
			return true;
		else
			return false;
	}
	
	/*
	 * notification提醒
	 */
	public void alarmNotification(Context arg0,Intent intent){		
		String title = "节日提醒";
		NotificationManager mn = (NotificationManager)arg0.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n  = new Notification(R.drawable.holiday,title,System.currentTimeMillis());
		n.defaults = Notification.DEFAULT_ALL;
		n.flags = Notification.FLAG_AUTO_CANCEL;
		Intent it = new Intent(arg0,MyHelper_MainActivity.class);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(arg0, 0, it, 0);		
		n.setLatestEventInfo(arg0, "节日提醒", "今天是"+month+"月"+day+"日"+name, pi);	
		mn.notify(0, n);
	}

}
