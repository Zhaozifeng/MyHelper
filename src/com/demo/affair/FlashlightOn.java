package com.demo.affair;

import java.util.List;

import com.demo.myhelper.R;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class FlashlightOn extends Activity {
	
	public Camera	camera;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flashlight_on);
		camera = Camera.open();
	}
	
	//打开闪光灯
	public void openLight(){
		if(camera==null)
			return;
		//获取camera设置参数类
		Parameters parameters = camera.getParameters();
		if(parameters==null)
			return;
		List<String> flashModes = parameters.getSupportedFlashModes();
		String flashMode = parameters.getFlashMode();
		if(!Parameters.FLASH_MODE_TORCH.equals(flashMode)){
			if(flashMode.contains(Parameters.FLASH_MODE_TORCH)){
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
			}
		}
	}
	
	

}

















