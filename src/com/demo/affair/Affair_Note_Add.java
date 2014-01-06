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
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
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

import com.demo.myhelper.R;

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
	public	int				second;
	
	
	//主函数
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note2);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialTitle();	
		setListener();
	}
		
	public void initialTitle(){														//初始化标题栏
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
				
		image_commit.setBackgroundResource(R.drawable.btn_commit);
		tv_title.setText(R.string.note);		
		
		calendar = Calendar.getInstance();                                         //获取当前时间      
		calendar.setTimeInMillis(System.currentTimeMillis());
		year	= calendar.get(Calendar.YEAR);
		month	= calendar.get(Calendar.MONTH)+1;
		day		= calendar.get(Calendar.DAY_OF_MONTH);
		hour 	= calendar.get(Calendar.HOUR_OF_DAY);
		minute  = calendar.get(Calendar.MINUTE);
		tv_curDate.setText(year+"年"+month+"月"+day+"日");
		tv_curTime.setText(hour+" : "+minute);		
	}
	
	
	public void setListener(){	
		
		image_commit.setOnClickListener(new OnClickListener(){					   //确定提交
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Affair_Note_Add.this,Affair_Note_Alarm.class);
				PendingIntent pending = PendingIntent.getBroadcast(Affair_Note_Add.this, 0, intent, 0);
				AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
				
				am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
				Builder b = new Builder(Affair_Note_Add.this);
				b
				.setMessage("添加成功")
				.setPositiveButton("好的", null);
				b.create().show();
			}			
		});				
		image_left.setOnClickListener(new OnClickListener(){					   //返回按钮
			public void onClick(View v){
				finish();
			}
		});
		btnSetTime.setOnClickListener(new OnClickListener(){					 //时间按钮设置监听
			@Override
			public void onClick(View v) {
				
				TimePickerDialog timePick = new TimePickerDialog(Affair_Note_Add.this,new OnTimeSetListener(){
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						tv_curTime.setText(hourOfDay+" : "+minute);	
						calendar.setTimeInMillis(System.currentTimeMillis());
						calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set(Calendar.MINUTE, minute);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
					}					
				}, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), true);
				timePick.show();
			}			
		});		
		btnSetDate.setOnClickListener(new OnClickListener(){					    //日期按钮设置监听
			@Override
			public void onClick(View v) {
				DatePickerDialog datePicker = new DatePickerDialog(Affair_Note_Add.this,new OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker arg0, int arg1, int arg2,
							int arg3) {
						calendar.set(arg1, arg2, arg3);
						arg2++;
						tv_curDate.setText(arg1+"年"+arg2+"月"+arg3+"日");								
					}					
				},year,month-1,day);
				datePicker.show();
			}			
		});
	}
	
	

}





















