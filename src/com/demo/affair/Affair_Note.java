package com.demo.affair;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Note extends Activity {	

	private TextView 	tv_title;
	private ImageView   btn_left;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		tv_title = (TextView)findViewById(R.id.title_name);
		btn_left = (ImageView)findViewById(R.id.custom_title_rollback);
		tv_title.setText(R.string.note);		
		btn_left.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				finish();
			}
		});
			
		
	}
	
	

}
