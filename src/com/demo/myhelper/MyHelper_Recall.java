package com.demo.myhelper;
import com.demo.tools.Utools;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

//该类用于服务返回调用程序
public class MyHelper_Recall extends Activity {
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		showNoteTimeup();
	}
		
	public void showNoteTimeup(){
		Utools.setVibrator(MyHelper_Recall.this, 100);
		Toast.makeText(MyHelper_Recall.this, "远程调用", 8000).show();
	}
	
	

}
