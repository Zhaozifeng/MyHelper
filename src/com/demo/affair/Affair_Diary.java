package com.demo.affair;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Diary extends Activity {
	
	TextView 	tvTitle;
	ImageView	imgLeft;
	ImageView	imgSetting;
	
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_diary);
		initialTitle();	
	}
	
	public void initialTitle(){
		tvTitle = (TextView)findViewById(R.id.custom_title);
		imgLeft = (ImageView)findViewById(R.id.custom_title_rollback);
		//imgSetting = (ImageView)findViewById()
		tvTitle.setText(R.string.diary_title);
		//imgLeft.setBackground(getResources().getDrawable(R.drawable.btn_back));
		//imgLeft.setBackground(getResources().getDrawable(R.drawable.))
		imgSetting = (ImageView)findViewById(R.id.custom_title_rollback);
	}
	
	
}






























