package com.demo.affair;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Diary extends Activity {
	
	TextView 	tvTitle;
	ImageView	imgLeft;
	ImageView	imgAdd;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_diary);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();	
	}
	
	public void initialTitle(){
		tvTitle    = (TextView)findViewById(R.id.title_name);
		imgLeft    = (ImageView)findViewById(R.id.custom_title_rollback);
		imgAdd 	   = (ImageView)findViewById(R.id.custom_title_menu);
		
		tvTitle.setText(R.string.diary_title);
		imgLeft.setBackgroundResource(R.drawable.btn_left);
		imgAdd.setBackgroundResource(R.drawable.btn_add);
		
		imgLeft.setOnClickListener(new OnClickListener(){				//返回上一页按钮
			@Override
			public void onClick(View arg0) {
				finish();				
			}		
		});		
		
		imgAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Affair_Diary.this,Affair_Diary_Add.class);
				startActivity(intent);
			}	
		});
		
	}
	
	
}






























