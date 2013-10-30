package com.demo.affair;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.myhelper.R;
import com.demo.object.Obejct_Diary;

public class Affair_Diary extends FinalActivity {
	
	TextView 	tvTitle;
	ImageView	imgLeft;
	ImageView	imgSetting;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_diary);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		FinalDb db = FinalDb.create(Affair_Diary.this,"系统数据库");
		Obejct_Diary u =new Obejct_Diary();
		u.setTime("2013-10-30");
		u.setEmotion("bad");
		u.setTitle("why not");
		u.setContent("i must fix it");
		db.save(u);	
		List<Obejct_Diary> list= db.findAll(Obejct_Diary.class);
		initialTitle();	
	}
	
	public void initialTitle(){
		tvTitle    = (TextView)findViewById(R.id.title_name);
		imgLeft    = (ImageView)findViewById(R.id.custom_title_rollback);
		imgSetting = (ImageView)findViewById(R.id.custom_title_menu);
		
		tvTitle.setText(R.string.diary_title);
		imgLeft.setBackgroundResource(R.drawable.btn_left);
		imgSetting.setBackgroundResource(R.drawable.btn_setting);
		
		imgLeft.setOnClickListener(new OnClickListener(){				//返回上一页按钮
			@Override
			public void onClick(View arg0) {
				finish();				
			}		
		});		
	}
	
	
}






























