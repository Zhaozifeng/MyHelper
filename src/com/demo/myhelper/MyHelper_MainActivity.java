package com.demo.myhelper;

import com.demo.affair.AffairMain;
import com.demo.health.HealthMain;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MyHelper_MainActivity extends TabActivity {
	
	private TabHost 	mTabhost;
	public ImageView	imgMenu;
	public ImageView	imgBack;
	public TextView		title;
	
	public static MainDatabase HelperSQLite; 
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_my_helper_main);	
		Utools.customTitle(this, "我的生活小管家");
		initialDataBase();
		initialUI();	
	}
		
	
	void initialDataBase(){
		HelperSQLite = new MainDatabase(MyHelper_MainActivity.this,1); 
	}
	
	MainDatabase getHelperSQLite(){
		return HelperSQLite;
	}
	
	
	void initialUI(){												     //初始化主界面
		imgMenu = (ImageView)findViewById(R.id.custom_title_rollback);
		imgMenu.setVisibility(View.INVISIBLE);
		imgBack = (ImageView)findViewById(R.id.custom_title_menu);
		imgBack.setVisibility(View.INVISIBLE);
		
		mTabhost = getTabHost();	
		Intent intentToAffair = new Intent(MyHelper_MainActivity.this,AffairMain.class);
		mTabhost.addTab(mTabhost.newTabSpec("affair_helper")
						.setIndicator(makeTabView("事务达人"))
						.setContent(intentToAffair));	
		
		Intent intentToHealth = new Intent(MyHelper_MainActivity.this,HealthMain.class);
		mTabhost.addTab(mTabhost.newTabSpec("health_helper")
						.setIndicator(makeTabView("健康管家"))
						.setContent(intentToHealth));	
		
		/*Intent intentToSetting = new Intent(MyHelper_MainActivity.this,MyHelper_Setting.class);
		mTabhost.addTab(mTabhost.newTabSpec("main_setting")
						.setIndicator(makeTabView("设置"))
						.setContent(intentToSetting));	*/
		mTabhost.setCurrentTab(0);										//设置当前停留的标签
	}
	

	public View makeTabView(String title){
		LayoutInflater inflater = LayoutInflater.from(MyHelper_MainActivity.this);
		View view = inflater.inflate(R.layout.tab_menu_layout, null);
		TextView titleTv = (TextView)view.findViewById(R.id.tab_tv);
		titleTv.setText(title);
		return view;
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





















