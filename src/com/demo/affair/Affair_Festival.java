package com.demo.affair;

import com.demo.myhelper.R;
import com.demo.tools.Utools;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class Affair_Festival extends Activity {
	
	public ImageView menuImg;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_festival);
		Utools.customTitle(Affair_Festival.this, "节日港");
	}
	
	

}
