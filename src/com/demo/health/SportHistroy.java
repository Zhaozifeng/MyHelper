package com.demo.health;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.demo.myhelper.R;
import com.demo.tools.Utools;

public class SportHistroy extends Activity {
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.sport_histroy);
		Utools.customTitle(this, "历史记录");
	}
	
	

}
