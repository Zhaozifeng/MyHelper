package com.demo.affair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Diary_Add extends Activity{
	
	private TextView   tvTitle;
	private ImageView  imgLeft;
	private Spinner    spEmotion;
	private Button     btnCommit;
	private Button 	   btnAddEmotion;
	private Button     btnOtherTime;
	
	private static final String[] emotions={"心情极佳","平平常常","孤单落寞","痛苦悲伤"};
	private ArrayAdapter<String> adapter;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);							//设置标题每个Activity都需要
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.diary_add);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout2);
		initialUI();
	}
	
	
	void initialUI(){
		imgLeft = (ImageView)findViewById(R.id.custom_title_rollback2);
		tvTitle = (TextView)findViewById(R.id.title_name2);
		btnCommit = (Button)findViewById(R.id.custom_title_menu_btn);
		spEmotion = (Spinner)findViewById(R.id.spinner_emotion);
		
		imgLeft.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();				
			}		
		});		
		tvTitle.setText(R.string.diary_add_title);		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,emotions);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spEmotion.setAdapter(adapter);
		
	}

}
