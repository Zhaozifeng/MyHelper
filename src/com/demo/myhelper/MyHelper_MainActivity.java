package com.demo.myhelper;

import com.demo.affair.AffairMain;
import com.demo.health.HealthMain;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
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

}
