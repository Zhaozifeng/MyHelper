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

public class Affair_Note extends Activity {	

	private TextView 		tv_title;
	private ImageView   	image_left;
	private ImageView   	image_commit;
	private EditText    	edt_content;

	private CheckBox		mSoundCheck;
	private CheckBox        mVibrateCheck;
	private Button			btnSetDate;
	private Button			btnSetTime;
	
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
		tv_title    = (TextView)findViewById(R.id.title_name);
		image_left  = (ImageView)findViewById(R.id.custom_title_rollback);
		image_commit= (ImageView)findViewById(R.id.custom_title_menu);
		edt_content = (EditText)findViewById(R.id.et_note_content);
		mSoundCheck = (CheckBox)findViewById(R.id.checkbox_note_sound);
		mVibrateCheck = (CheckBox)findViewById(R.id.checkbox_note_shake);
		
		mSoundCheck.setChecked(true);
		mVibrateCheck.setChecked(true);
				
		image_commit.setBackgroundResource(R.drawable.btn_commit);
		tv_title.setText(R.string.note);		
		image_left.setOnClickListener(new OnClickListener(){					   //返回按钮
			public void onClick(View v){
				finish();
			}
		});
	}
	
	public void setListener(){														//设置监听
		btnSetTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				TimePickerDialog timePick = new Time				
			}			
		});		
	}

}





















