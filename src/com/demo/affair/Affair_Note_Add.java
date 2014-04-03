package com.demo.affair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Note_Add extends Activity {	

	private TextView 		tv_title;
	private TextView		tv_curTime;
	private TextView		tv_curDate;
	private ImageView   	image_left;
	private ImageView   	image_commit;
	private EditText    	edt_content;

	private CheckBox		mSoundCheck;
	private CheckBox        mVibrateCheck;
	private Button			btnSetDate;
	private Button			btnSetTime;
	
	private Calendar 		calendar;
	
	public 	int				year;
	public 	int 			month;
	public 	int				day;
	public 	int 			hour;
	public 	int 			minute;
	
	public  static String PARAMS_VIBRATE = "params_vibrate";
	public  static String PARAMS_SOUND   = "params_sound";
	public  static String PARAMS_CONTENT = "params_content"; 	
	//返回列表刷新参数
	public  static String   RELESH_LIST = "reflesh_list";
	
	//NotificationManager
	public NotificationManager	notificationManager;
	
	
	//主函数
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note2);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialTitle();	
		setListener();
	}
		
	//初始化标题栏
	public void initialTitle(){	
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		tv_title    	= (TextView)findViewById(R.id.title_name);
		tv_curTime		= (TextView)findViewById(R.id.tv_note_time);
		tv_curDate		= (TextView)findViewById(R.id.tv_note_date);
		image_left  	= (ImageView)findViewById(R.id.custom_title_rollback);
		image_commit	= (ImageView)findViewById(R.id.custom_title_menu);
		edt_content 	= (EditText)findViewById(R.id.et_note_content);
		mSoundCheck 	= (CheckBox)findViewById(R.id.checkbox_note_sound);
		mVibrateCheck 	= (CheckBox)findViewById(R.id.checkbox_note_shake);		
		btnSetTime 		= (Button)findViewById(R.id.btn_note_time);
		btnSetDate		= (Button)findViewById(R.id.btn_note_date);		
		mSoundCheck.setChecked(true);
		mVibrateCheck.setChecked(true);				
		tv_title.setText(R.string.note);	
		//获取nitification
		notificationManager	= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		
				
		//设置当前时间
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());		
		year  = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH)+1;
		day   = calendar.get(Calendar.DAY_OF_MONTH);
		hour  = calendar.get(Calendar.HOUR_OF_DAY);
		minute= calendar.get(Calendar.MINUTE);
		tv_curDate.setText(year+"年"+month+"月"+day+"日");
		tv_curTime.setText(hour+" : "+minute);
				
		//返回按钮
		image_left.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {				
				finish();				
			}			
		});
		
		//提交按钮
		image_commit.setBackgroundResource(R.drawable.btn_ok2);
		image_commit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//判断是否震动或者响铃，1是有设置，0是没有设置
				int isVibrate;
				int isSound; 				
				if(mVibrateCheck.isChecked())
					isVibrate = 1;
				else
					isVibrate = 0;
				
				if(mSoundCheck.isChecked())
					isSound = 1;
				else
					isSound = 0;										
				String content    = edt_content.getText().toString();
				
				Intent intent = new Intent(Affair_Note_Add.this,Affair_Note_Alarm.class);	
				intent.putExtra(PARAMS_VIBRATE, isVibrate);
				intent.putExtra(PARAMS_SOUND, isSound);
				intent.putExtra(PARAMS_CONTENT, content);
				PendingIntent pi = PendingIntent.getBroadcast(Affair_Note_Add.this, 0, intent, 0);
				AlarmManager  am = (AlarmManager)getSystemService(ALARM_SERVICE);
				am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
				
				
				
				final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
				final ContentValues cv = new ContentValues();								
								
				//传递给contentView
				cv.put("content", content);
				cv.put("year", calendar.get(Calendar.YEAR));
				cv.put("month", calendar.get(Calendar.MONTH));
				cv.put("day", calendar.get(Calendar.DAY_OF_MONTH));
				cv.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
				cv.put("minute", calendar.get(Calendar.MINUTE));
				cv.put("second", Calendar.SECOND);
				cv.put("rang",isSound);
				cv.put("vibrate", isVibrate);
				//1为该备忘录已启动，0是没有启动
				cv.put("is_action", "已激活");
				
				sql.insert(MainDatabase.NOTE_TABLE_NAME, null, cv);
																				
				Builder builder = new Builder(Affair_Note_Add.this);
				builder
				.setTitle("操作提示")
				.setMessage("提交成功")
				.setPositiveButton("好的", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Affair_Note_Add.this,Affair_Note_List.class);
						intent.putExtra(RELESH_LIST, true);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//新建一个notification
						Intent notiIntent;
						notiIntent = new Intent(Affair_Note_Add.this,
								MyHelper_MainActivity.class);
						//notiIntent.putExtra(RELESH_LIST, true);
						notiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						Notification n = new Notification(R.drawable.noti_label,
								Affair_Note_Add.this.getString(R.string.note_noti_tips),System.currentTimeMillis());
						n.flags = Notification.FLAG_AUTO_CANCEL;
						PendingIntent notiPending = PendingIntent.getActivity
								(Affair_Note_Add.this, 0, notiIntent, 0);		
						
						n.setLatestEventInfo(Affair_Note_Add.this, 
								Affair_Note_Add.this.getString(R.string.note_noti_title), 
								Affair_Note_Add.this.getString(R.string.noti_message), 
								notiPending);
						notificationManager.notify(0,n);						
						startActivity(intent);
						finish();						
					}
				});
				builder.create().show();
			}			
		});		
	}
	
	//设置监听
	public void setListener(){
		
		//时间窗口
		btnSetTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TimePickerDialog tp = new TimePickerDialog(Affair_Note_Add.this, new OnTimeSetListener(){
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set(Calendar.MINUTE, minute);	
						tv_curTime.setText(hourOfDay+" : "+minute);
					}					
				},hour, minute, true);
				tp.show();
			}			
		});
		
		//日期窗口
		btnSetDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				DatePickerDialog dp = new DatePickerDialog(Affair_Note_Add.this,new OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2,
							int arg3) {					
						calendar.set(arg1, arg2, arg3);	
						arg2++;
						tv_curDate.setText(arg1+"年"+arg2+"月"+arg3+"日");
					}					
				},year,month-1,day);	
				dp.show();
			}			
		});
		
	}
	
}





















