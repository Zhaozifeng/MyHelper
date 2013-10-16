package com.demo.myhelper;

import com.demo.affair.AffairMain;
import com.demo.health.HealthMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TabHost;

public class MyHelper_MainActivity extends TabActivity {
	
	private TabHost		mTabHost;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_my_helper_main);	
		initialUI();	
	}
	
	
	void initialUI(){												     //初始化主界面
		mTabHost = getTabHost();	
		Intent intentToAffair = new Intent(MyHelper_MainActivity.this,AffairMain.class);
		mTabHost.addTab(mTabHost.newTabSpec("affair_helper")
						.setIndicator(getResources().getString(R.string.affair_helper))
						.setContent(intentToAffair));	
		
		Intent intentToHealth = new Intent(MyHelper_MainActivity.this,HealthMain.class);
		mTabHost.addTab(mTabHost.newTabSpec("health_helper")
						.setIndicator(getResources().getString(R.string.health_helper))
						.setContent(intentToHealth));	
		
		Intent intentToSetting = new Intent(MyHelper_MainActivity.this,MyHelper_Setting.class);
		mTabHost.addTab(mTabHost.newTabSpec("main_setting")
						.setIndicator(getResources().getString(R.string.main_setting))
						.setContent(intentToSetting));	
		mTabHost.setCurrentTab(0);										//设置当前停留的标签
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_helper__main, menu);
		return true;
	}
	
	
	public boolean dispatchKeyEvent(KeyEvent event){												 //退出对话框			
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount()==0){
			Builder mbuilder = new AlertDialog.Builder(MyHelper_MainActivity.this)
			.setTitle(R.string.app_exit_title)
			.setMessage(R.string.app_exit_message)
			.setPositiveButton(R.string.app_exit_exitbutton, new DialogInterface.OnClickListener() {	//退出按钮			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);					
				}
			})
			.setNegativeButton(R.string.app_exit_cancel, new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub					
				}
			});
			mbuilder.create().show();
			return true;
		}
		return super.dispatchKeyEvent(event);
	}


}





















