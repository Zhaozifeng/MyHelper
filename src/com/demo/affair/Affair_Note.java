package com.demo.affair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.demo.myhelper.R;

public class Affair_Note extends Activity {	

	private TextView 		tv_title;
	private ImageView   	image_left;
	private ImageView   	image_commit;
	private EditText    	edt_content;
	private TimePicker  	mTimePicker;
	private DatePicker 		mDatePicker;
	private Calendar		mCalendar;
	private CheckBox		mSoundCheck;
	private CheckBox        mVibrateCheck;
	private MediaPlayer   	mPlay;
	private Vibrator		vibrator;
	
	
	public static  int 		mYear   = 0;
	public static  int 		mMonth  = 0;
	public static  int 		mDay    = 0;
	public static  int		mHour   = 0;
	public static  int 		mMinute = 0;
	public static  boolean  isAlarmed = false;										//判断是否已经警示
	public static  boolean  isSound   = true;										//是否响铃
	public static  boolean  isVibrate = true;										//是否震动
	
	
	private static String 		mContent    =null;
	private static String 		currentTime =null;
	private static String       settingTime =null;
	
	SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	
	
	Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==1){				
				showNotice();
			}
		}
	};
	
	Thread getCurrentTimeThread = new Thread(new Runnable(){
		public void run() {
			while(!isAlarmed){
				currentTime = myDateFormat.format(new Date());
				Log.e("getCurrentTimeThread", currentTime);
				if(currentTime.equals(settingTime)){
					isAlarmed = true;								//避免多次弹出对话框
					mHandler.sendEmptyMessage(1);
					Log.e("getCurrentTimeThread", "true");
				}						
			}				
		}			
	});	
	
	//主函数
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialTitle();	
	}
		
	void showNotice(){
		
		if(isSound){
		mPlay = MediaPlayer.create(Affair_Note.this, R.raw.silent_cry);					//想起固定铃声
		mPlay.start();}
		if(isVibrate){
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);						//震动
		vibrator.vibrate(5000);}
		
		Builder builder = new AlertDialog.Builder(Affair_Note.this)
		.setTitle(R.string.note_dialog_timeup_title)
		.setMessage(mContent)
		.setPositiveButton(getResources().getString(R.string.note_dialog_known), 
				new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(isSound)
					mPlay.release();														//释放播放
				if(isVibrate)
					vibrator.cancel();
			}
		});
		builder.create().show();
	}
	
	
	
	void initialTitle(){														//初始化标题栏
		tv_title    = (TextView)findViewById(R.id.title_name);
		image_left  = (ImageView)findViewById(R.id.custom_title_rollback);
		image_commit= (ImageView)findViewById(R.id.custom_title_menu);
		edt_content = (EditText)findViewById(R.id.et_note_content);
		mSoundCheck = (CheckBox)findViewById(R.id.checkbox_note_sound);
		mVibrateCheck = (CheckBox)findViewById(R.id.checkbox_note_shake);
		
		mSoundCheck.setChecked(true);
		mVibrateCheck.setChecked(true);
		
		 
		
		
		mTimePicker = (TimePicker)findViewById(R.id.timepicker_note);
		mDatePicker = (DatePicker)findViewById(R.id.datepicker_note);
				
		image_commit.setBackgroundResource(R.drawable.btn_commit);
		tv_title.setText(R.string.note);		
		image_left.setOnClickListener(new OnClickListener(){					//返回按钮
			public void onClick(View v){
				finish();
			}
		});
		
		mCalendar = Calendar.getInstance();										//获取年月并监听
		mYear 	  = mCalendar.get(Calendar.YEAR);
		mMonth 	  = mCalendar.get(Calendar.MONTH);
		mDay 	  = mCalendar.get(Calendar.DAY_OF_MONTH);
		
		mDatePicker.init(mYear, mMonth, mDay, new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker arg0, int year, int month_of_year,
					int day_of_month) {
				Affair_Note.this.mYear   = year;
				Affair_Note.this.mMonth  = month_of_year;
				Affair_Note.this.mDay    = day_of_month;		
			}		
		});			
		
		mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener(){
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				Affair_Note.this.mHour      = hourOfDay;
				Affair_Note.this.mMinute    = minute;				
			}			
		});				
		
		
		image_commit.setOnClickListener(new View.OnClickListener(){				//提交按钮
			public void onClick(View v){				
				Builder mbuilder = new AlertDialog.Builder(Affair_Note.this)    //弹出对话框，注意这里一定要引入活动名
				.setTitle(R.string.note_dialog_title)
				.setMessage(R.string.note_dialog_message)
				.setPositiveButton(R.string.note_dialog_commit, new DialogInterface.OnClickListener(){
					String str_month;
					String str_day;
					String str_hour;
					String str_minute;

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						isAlarmed   = false;
						mContent    = edt_content.getText().toString();			//获取提醒内容
						mYear   	= mDatePicker.getYear();
						mMonth  	= mDatePicker.getMonth()+1;
						mDay    	= mDatePicker.getDayOfMonth();
						mHour       = mTimePicker.getCurrentHour();
						mMinute     = mTimePicker.getCurrentMinute();
						
						if(mMonth<10)											//设置判别个位数处理
							 str_month = "0"+mMonth;
						else
							 str_month = ""+mMonth;
						
						if(mDay<10)
							str_day = "0"+mDay;
						else
							str_day = ""+mDay;
						
						if(mHour<10)
							str_hour = "0"+mHour;
						else
							str_hour = ""+mHour;
						
						if(mMinute<10)
							str_minute = ":0"+mMinute;
						else
							str_minute = ":"+mMinute;
											
						settingTime = mYear+"/"+str_month+"/"+str_day+" "+str_hour+str_minute;						
						isSound   = mSoundCheck.isChecked();									//获取是否响声
						isVibrate = mVibrateCheck.isChecked();									//获取是否震动
						getCurrentTimeThread.start();
					}					
				}).
				setNegativeButton(R.string.note_dialog_cancel, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});	
				mbuilder.create().show();
			}
		});

	}
	
}
