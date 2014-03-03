package com.demo.health;

import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class StepCountService extends Service implements SensorEventListener {
	
	//传感器管理类
	private SensorManager	sensorManager;	
	private Calendar        mCalendar;
		
	//默认的设置标准
	public double  LENGTH 	    = 5;							//三角勾股定理长度
	public long    TIME_BETWEEN = 5000;						    //时间间隔
	public float   STEP_LENGTH  = 1.5f;							//步长
	public int     CHECK_TIMES  = 100000000;					//检测长时间没有步行	
	public float   STEP_CONSUME = 0.07f;						//每走一步消耗卡路里
	
	public float  firstX = 0;
	public float  firstY = 0;
	public float  firstZ = 0;
	public int     stepCount = 0;
	public boolean isStart  = false;
	public long  timeStart;
	public long  timeFinish;
	public int   timeBetweenSteps=0;
	public double speedStep;
	public double speedMeter;
	public int   checkTimes = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	}
	
	
	@Override
	public void onStart(Intent intent,int startId){
		super.onStart(intent, startId);
	}
	
	
	public void startCountStep(){
		sensorManager.registerListener
		((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_GAME);
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
		float consume = stepCount*STEP_CONSUME;
		tvStepConsume.setText(consume+"卡路里");
	}

}















