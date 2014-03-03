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
		timeStart = System.currentTimeMillis();
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
			Intent intent = new Intent();
			intent.putExtra("isok", true);
			intent.setAction("com.demo.health.StepCountService");
			sendBroadcast(intent);
			}
		timeFinish = System.currentTimeMillis();
		if(timeFinish-timeStart-TIME_BETWEEN>=0){
			speedStep  = timeBetweenSteps/(TIME_BETWEEN/1000.0);
			speedMeter = timeBetweenSteps*STEP_LENGTH/(TIME_BETWEEN/1000.0);
			timeBetweenSteps = 0;
			timeStart = timeFinish;	
			
		}
		float consume = stepCount*STEP_CONSUME;
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

}















