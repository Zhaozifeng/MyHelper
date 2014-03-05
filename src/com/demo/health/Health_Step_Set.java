package com.demo.health;

import com.demo.myhelper.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Health_Step_Set extends Activity {
	
	public TextView 	tvTitle;
	public ImageView	imgBack;
	public ImageView	imgSave;
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.health_step_set_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		setTitle();
		//initUI();
	}
	
	/*
	 * 设置标题栏
	 */
	public void setTitle(){
		tvTitle = (TextView)findViewById(R.id.title_name);
		tvTitle.setText(R.string.health_step_set);
		imgBack = (ImageView)findViewById(R.id.custom_title_rollback);
		imgSave = (ImageView)findViewById(R.id.custom_title_menu);
		imgSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.saved));
		imgBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();				
			}			
		});
		
	}
	
	public void initUI(){
		
		
	}

}
