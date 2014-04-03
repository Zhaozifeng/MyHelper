package com.helper.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.helper.situation.SituationRecieve;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SituationAdapter extends BaseAdapter {

	private Cursor 		cursor;
	private Context		mcontext;
	private int			nums;
	private ArrayList<SituationModel>	list;
	
	public static String NAME_PARAMS   = "name";
	public static String HOUR_PARAMS   = "hour";
	public static String MINUTE_PARAMS = "minute";
	
	public SituationAdapter(Context context,Cursor cursor){
		list = new ArrayList<SituationModel>();
		this.cursor   = cursor;
		this.mcontext = context;
		nums = cursor.getCount();
		makeItem();
	}
	
	@Override
	public int getCount() {
		return nums;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(mcontext);
		View view = inflater.inflate(R.layout.situation_item_layout, null);
		TextView name = (TextView)view.findViewById(R.id.situ_name_tv);
		TextView time = (TextView)view.findViewById(R.id.situ_time_tv);
		TextView vibrate = (TextView)view.findViewById(R.id.situ_vibrate_tv);
		TextView volumn  = (TextView)view.findViewById(R.id.situ_vol_tv);
		TextView light   = (TextView)view.findViewById(R.id.situ_light_tv);
		CheckBox openCheck = (CheckBox)view.findViewById(R.id.situ_begin_ck);
				
		final SituationModel item = list.get(arg0);		
		time.setText("预定开始时间 "+item.hour+":"+item.minute);
		name.setText(item.name);	
		
		if(item.volumn==0)
			volumn.setText("预设静音状态");
		else
			volumn.setText("铃声大小: "+item.volumn);
		if(item.isvibrate==0)
			vibrate.setText("没有震动效果");
		else
			vibrate.setText("有震动效果");
		light.setText("预设亮度"+item.light);
		
		if(item.iopen==0)
			openCheck.setChecked(false);
		else
			openCheck.setChecked(true);
		openCheck.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			String messageOpen = item.hour+":"+item.minute+"自动为您开启"+item.name+"模式";
			String messageClose = "您关闭了"+item.name+"模式";
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked){			
					Toast.makeText(mcontext, messageOpen, Toast.LENGTH_SHORT).show();
					item.iopen = 1;
				}
				else{
					Toast.makeText(mcontext, messageClose, Toast.LENGTH_SHORT).show();
					item.iopen = 0;
				}		
				saveIsOpen(item);
				startService(item);
			}			
		});				
		view.setTag(item);		
		return view;
	}
	
	
	/*
	 * 开启后台服务
	 */
	public void startService(SituationModel item){
		if(item.iopen==1/*&&isToday(item)*/){
			Intent intent = new Intent(mcontext,SituationRecieve.class);
			intent.putExtra(NAME_PARAMS, item.name);
			intent.putExtra(HOUR_PARAMS, item.hour);
			intent.putExtra(MINUTE_PARAMS, item.minute);
			PendingIntent pi = PendingIntent.getBroadcast(mcontext, 0, intent, 0);
			AlarmManager am = (AlarmManager)mcontext.getSystemService(Context.ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, item.hour);
			calendar.set(Calendar.MINUTE, item.minute);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
			
		}
	}
	
	public boolean isToday(SituationModel item){
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		c.setTimeInMillis(System.currentTimeMillis());
		if(item.hour==hour&&item.minute==minute)
			return true;
		else
			return false;
		
	}
	
	/*
	 * 保存是否开启状态
	 */
	public void saveIsOpen(SituationModel item){
		SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		String condition = "hour=? and minute=? and name=?";
		String[] values = {item.hour+"",item.minute+"",item.name};
		ContentValues cv = new ContentValues();
		cv.put("isopen", item.iopen);
		int rec = sql.update(MainDatabase.MODE_TABLE_NAME, cv, condition, values);
		if(rec==0)
			Toast.makeText(mcontext, "保存失败", Toast.LENGTH_SHORT).show();
	}
	

	public void makeItem(){
		int sum = cursor.getCount();
		for(int i=0;i<sum;i++){
			SituationModel situ = new SituationModel();
			String namestr = cursor.getString(1);			
			situ.name = namestr;			
			int hour = cursor.getInt(2);
			int minute = cursor.getInt(3);
			situ.hour = hour;
			situ.minute = minute;					
			int vol = cursor.getInt(5);		
			situ.volumn = vol;					
			int vir = cursor.getInt(4);		
			situ.isvibrate = vir;		
			float lightvalue = cursor.getFloat(6);
			situ.light = lightvalue;
			list.add(situ);
			situ.iopen = cursor.getInt(7);
			cursor.moveToNext();
		}	
	}
	
	//情景模式对象
	public static class SituationModel{
		
		public String	name;
		public int		hour;
		public int		minute;
		public int		isvibrate;
		public int		volumn;
		public float	light;
		public int		iopen;		
	}

}

















