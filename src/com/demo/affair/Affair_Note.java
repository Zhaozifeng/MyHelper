package com.demo.affair;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

import com.demo.myhelper.R;

public class Affair_Note extends Activity {	

	private TextView 	tv_title;
	private ImageView   image_left;
	private ImageView   image_commit;
	private EditText    edt_content;
	private TimePicker  mTimePicker;
	private DatePicker  mDatePicker;
	private Calendar	mCalendar;
	
	public static  int 		mYear   = 0;
	public static  int 		mMonth  = 0;
	public static  int 		mDay    = 0;
	public static  int		mHour   = 0;
	public static  int 		mMinute = 0;
	
	
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
			while(true){
				currentTime = myDateFormat.format(new Date());
				Log.e("getCurrentTimeThread", currentTime);
				if(currentTime.equals(settingTime)){
					mHandler.sendEmptyMessage(1);
					Log.e("getCurrentTimeThread", "true");
				}						
			}				
		}			
	});	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialTitle();	
	}
		
	void showNotice(){
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		Toast.makeText(Affair_Note.this, "够时间了，亲" ,Toast.LENGTH_LONG).show();
		vibrator.vibrate(5000);
		//getCurrentTimeThread.destroy();
		
	}
	
	
	
	void initialTitle(){														//初始化标题栏
		tv_title    = (TextView)findViewById(R.id.title_name);
		image_left  = (ImageView)findViewById(R.id.custom_title_rollback);
		image_commit= (ImageView)findViewById(R.id.custom_title_menu);
		edt_content = (EditText)findViewById(R.id.et_note_content);
		
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
		mMonth 	  = mCalendar.get(Calendar.MONTH)+1;
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
		
		
		//edt_content.setText(myDateFormat.format(new Date()));
			
		image_commit.setOnClickListener(new View.OnClickListener(){				//提交按钮
			public void onClick(View v){
				
				Builder mbuilder = new AlertDialog.Builder(Affair_Note.this)    //弹出对话框，注意这里一定要引入活动名
				.setTitle(R.string.note_dialog_title)
				.setMessage(R.string.note_dialog_message)
				.setPositiveButton(R.string.note_dialog_commit, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						mYear   	= mDatePicker.getYear();
						mMonth  	= mDatePicker.getMonth();
						mDay    	= mDatePicker.getDayOfMonth();
						mHour       = mTimePicker.getCurrentHour();
						mMinute     = mTimePicker.getCurrentMinute();
						settingTime = mYear+"/"+mMonth+"/"+mDay+" "+mHour+":"+mMinute;	
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
