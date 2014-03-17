package com.helper.birthday;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.R;
import com.demo.tools.Utools;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;


public class BirthAdd extends Activity {
	
	ImageView imgMenu;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.birth_add_layout);
		Utools.customTitle(BirthAdd.this, getResources().getString(R.string.birthday_indiviual));
		initUI();
	}
	
	public void initUI(){
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setVisibility(View.INVISIBLE);
	}

}
