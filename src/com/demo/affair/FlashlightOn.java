package com.demo.affair;

import java.util.List;

import com.demo.myhelper.R;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class FlashlightOn extends Activity {
	
	public LinearLayout mainLinearlayout;	
	public Camera		camera;
	public Parameters	parameters;	
	public List<String> flashModes;
	public boolean 		isLighting	=	false;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flashlight_on);
		initCamera();
		
		//点击屏幕开关闪光灯
		mainLinearlayout = (LinearLayout)findViewById(R.id.flashlight_on_layout);
		mainLinearlayout.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if(isLighting){
					mainLinearlayout.setBackgroundResource(R.drawable.flash_off_background);
					closeFlashlight();
					isLighting = false;
				}
				else{
					mainLinearlayout.setBackgroundResource(R.drawable.flash_on_background);
					openLight();
					isLighting = true;
				}				
			}			
		});		
	}
	
	
	//初始化camera
	public void initCamera(){
		camera = Camera.open();
		if(camera==null)
			return;
		parameters = camera.getParameters();		
		if(parameters==null)
			return;					
		flashModes = parameters.getSupportedFlashModes();
		openLight();
	}
	
	//重写销毁函数释放camera
	public void onDestroy(){
		closeFlashlight();
		camera.release();
		super.onDestroy();
	}
	
	//打开闪光灯
	public void openLight(){		
		String flashMode = parameters.getFlashMode();
		if(!Parameters.FLASH_MODE_TORCH.equals(flashMode)){
			if(flashModes.contains(Parameters.FLASH_MODE_TORCH)){
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
				isLighting = true;
			}
		}
	}
	
	//关闭闪光灯
	public void closeFlashlight(){
		String flashMode = parameters.getFlashMode();
		if(!Parameters.FLASH_MODE_OFF.equals(flashMode)){
			if(flashModes.contains(Parameters.FLASH_MODE_OFF)){
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(parameters);
				isLighting = false;
			}
		}
	}
}

















