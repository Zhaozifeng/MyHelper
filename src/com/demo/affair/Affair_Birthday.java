package com.demo.affair;
import com.demo.myhelper.R;
import com.demo.tools.Utools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Affair_Birthday extends Activity {
	
	public ImageView	imgBack;
	public ImageView	imgMenu;
	public TextView		title;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.activity_affair_birthday);
		Utools.customTitle(Affair_Birthday.this, getResources().getString(R.string.birthday));
		initUI(); 
	}
	
	
	public void initUI(){
	imgMenu		= (ImageView)findViewById(R.id.custom_title_menu);
	imgMenu.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View v) {
			Toast.makeText(Affair_Birthday.this, "yes you click", 8888).show();			
		}		
	});
	}
	

	
}






















