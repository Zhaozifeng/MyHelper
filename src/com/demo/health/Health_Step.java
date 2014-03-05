package com.demo.health;

import java.util.Calendar;

import com.demo.myhelper.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Health_Step extends Activity implements SensorEventListener {
	
	public static int STEP_NOTI_ID = 1;
	public static String STEP_NUMBER 	= "step_number";
	public static String STEP_SPEED1 	= "speed1";
	public static String STEP_SPEED2 	= "speed2";
	public static String CONSUME_KARO 	= "consume";
	public static String STEP_BETWEEN_STEP	= "step_between_step";
	public static String IS_START	  	= "is_start";
	
	public SharedPreferences sp;
	public SharedPreferences.Editor	editor;
	private TextView  tvTitle;
	private ImageView imgBack;
	private ImageView imgMenu;
	private TextView  tvStepCount;
	private TextView  tvStepSpeedStep;
	private TextView  tvStepSpeedMeter;
	private TextView  tvStepConsume;
	private Button 	  btnSet;
	private Button    btnStart;
	private Button    btnStop;
	private Button    btnCancel;
	private Button    btnRenew;
	private Button    btnExit;
	
	//传感器管理类
	private SensorManager	sensorManager;	
	private Calendar        mCalendar;
	
	//默认的设置标准
	public double  LENGTH 	    = 5;							//三角勾股定理长度
	public long    TIME_BETWEEN = 5000;						    //时间间隔
	public float   STEP_LENGTH  = 1.5f;							//步长
	public int     CHECK_TIMES  = 100000000;					//检测长时间没有步行	
	public float   STEP_CONSUME = 0.07f;						//每走一步消耗卡路里
		
	public float   firstX = 0;
	public float   firstY = 0;
	public float   firstZ = 0;
	public int     stepCount = 0;
	public boolean isStart   = false;
	public long    timeStart;
	public long    timeFinish;
	public int     timeBetweenSteps=0;
	public float   consume = 0;
	public double  speedStep;
	public double  speedMeter;
	public int     checkTimes = 0;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_health_step);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initUI();		
		setListened();
	}
	
	
	/*
	 * 销毁activity时关闭传感器
	 */
	public void onDestroy(){
		NotificationManager mn = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		mn.cancel(STEP_NOTI_ID);
		stopSensor();
		super.onDestroy();		
	}	
	
	public void initUI(){
		tvTitle = (TextView)findViewById(R.id.title_name);
		tvTitle.setText(R.string.health_main_step);
		imgBack	= (ImageView)findViewById(R.id.custom_title_rollback);
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setVisibility(View.INVISIBLE);
		tvStepCount  = (TextView)findViewById(R.id.tv_health_steps);
		tvStepSpeedStep  = (TextView)findViewById(R.id.tv_health_speed);
		tvStepSpeedMeter = (TextView)findViewById(R.id.tv_health_speed2);
		tvStepConsume= (TextView)findViewById(R.id.tv_health_consume);
		btnSet    = (Button)findViewById(R.id.btn_health_set);
		btnStart  = (Button)findViewById(R.id.btn_health_start);
		btnStop   = (Button)findViewById(R.id.btn_health_stop);
		btnCancel = (Button)findViewById(R.id.btn_health_cancel);
		btnRenew  = (Button)findViewById(R.id.btn_health_renew);
		btnExit   = (Button)findViewById(R.id.btn_health_exit);
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mCalendar = Calendar.getInstance();		
		//获取保存的信息
		sp 		= getSharedPreferences("stepRecord",MODE_PRIVATE);
		editor	= sp.edit();
		isStart = sp.getBoolean(IS_START, false);
		if(isStart)
			startFromNotification();		
	}
	
	
	/**
	 * notification启动，显示已经记录的数据
	 */
	public void startFromNotification(){
		stepCount = sp.getInt(STEP_NUMBER, -1);
		speedStep = sp.getFloat(STEP_SPEED1, -1);
		speedMeter= sp.getFloat(STEP_SPEED2, -1);
		consume   = sp.getFloat(CONSUME_KARO, -1);
		timeBetweenSteps = sp.getInt(STEP_BETWEEN_STEP,-1);		
		speedStep = speedStep/1.00;
		speedMeter= speedMeter/1.00;
		tvStepCount.setText(stepCount+"步");		
		tvStepSpeedStep.setText(speedStep+"");		
		tvStepSpeedMeter.setText(speedMeter+"");
		tvStepConsume.setText(consume+"卡路里");		
		btnStart.setEnabled(false);
		btnStop.setEnabled(true);
		btnCancel.setEnabled(true);
		btnRenew.setEnabled(true);
		startSensor();	
		setNotification();		
	}
	
	/*
	 * 设置后台跑步计算器
	 */
	public void setNotification(){
		NotificationManager mn = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification
				(R.drawable.foot,getResources().getString(R.string.step_notification_tip),System.currentTimeMillis());
		n.flags = Notification.FLAG_NO_CLEAR;
		n.icon  = R.drawable.step_middle;
		Intent intent = new Intent(this,Health_Step.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pt = PendingIntent.getActivity
				(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		n.setLatestEventInfo
		(this, getResources().getString(R.string.step_notification_title),
				"当前为步行模式", pt);
		mn.notify(STEP_NOTI_ID,n);
	}
	
	
	/**
	 * 按钮监听函数
	 */
	public void setListened(){				
		//设置activity
		btnSet.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Health_Step.this,Health_Step_Set.class);
				startActivity(intent);
			}			
		});				
		//启动传感器
		btnStart.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {	
				if(!isStart){
					setNotification();
					Toast.makeText(Health_Step.this, getResources().getString(R.string.step_begin), 
							8000).show();
					//计算时间间隔
					timeStart = System.currentTimeMillis();
					btnStart.setEnabled(false);
					btnStop.setEnabled(true);
					btnCancel.setEnabled(true);
					btnRenew.setEnabled(true);
					isStart = true;
					startSensor();		
					editor.putBoolean(IS_START, true);
					editor.commit();
				}								
			}			
		});
		//停止传感器
		btnCancel.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(isStart){				
					btnStart.setEnabled(true);
					btnCancel.setEnabled(false);
					btnStop.setEnabled(false);					
					btnRenew.setEnabled(false);
					isStart = false;
					stopSensor();
					//取消notification
					NotificationManager mn = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					mn.cancel(STEP_NOTI_ID);
					editor.putBoolean(IS_START, false);
					editor.commit();
				}								
			}			
		});	
		//清零按钮
		btnRenew.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				setZero();				
			}			
		});
		//退出按钮
		btnExit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				stopSensor();
				//取消notification
				NotificationManager mn = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				mn.cancel(STEP_NOTI_ID);
				finish();				
			}			
		});
		//暂停按钮
		btnStop.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				isStart = false;	
				btnStart.setEnabled(true);
			}			
		});
		
		//返回按钮
		imgBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();				
			}			
		});		
	}
	
	//启动传感器
	public void startSensor(){
		sensorManager.registerListener
		(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
	}
	
	//停止传感器
	public void stopSensor(){
		sensorManager.unregisterListener(this);
		setZero();
	}
	
	//清零函数
	public void setZero(){
		tvStepCount.setText("0");
		tvStepSpeedStep.setText("0");
		tvStepSpeedMeter.setText("0");
		tvStepConsume.setText("0");
		stepCount = 0;
	}
	
	//计算是否走了一步数学模型
	public boolean isOneStep(float values[]){
		float x = values[0];
		float y = values[1];
		float z = values[2];
		double temp = Math.pow((double)(x-firstX), 2)+Math.pow((double)(y-firstY), 2)+
		Math.pow((double)(z-firstZ), 2);
		double length = Math.sqrt(temp);		
		firstX = x;
		firstY = y;
		firstZ = z;	
		if((length-LENGTH)>0)
			return true;
		else
			return false;
	}
			
	/**
	 * 传感器继承函数
	 */
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(isStart&&isOneStep(event.values)){
			stepCount++;
			timeBetweenSteps++;
			tvStepCount.setText(stepCount+"步");			
			}
		timeFinish = System.currentTimeMillis();
		if(timeFinish-timeStart-TIME_BETWEEN>=0){
			speedStep  = timeBetweenSteps/(TIME_BETWEEN/1000.0);
			speedMeter = timeBetweenSteps*STEP_LENGTH/(TIME_BETWEEN/1000.0);
			tvStepSpeedStep.setText(""+speedStep+"步/s");
			tvStepSpeedMeter.setText(""+speedMeter+"m/s");
			timeBetweenSteps = 0;
			timeStart = timeFinish;	
		}
		consume = stepCount*STEP_CONSUME;
		tvStepConsume.setText(consume+"卡路里");
		//保存到本地数据上
		editor.putInt(STEP_NUMBER, stepCount);
		editor.putInt(STEP_BETWEEN_STEP,timeBetweenSteps);
		editor.putFloat(STEP_SPEED1, (float) speedStep);
		editor.putFloat(STEP_SPEED2, (float) speedMeter);
		editor.putFloat(CONSUME_KARO, (float) consume);
		editor.commit();		
	}
}


















