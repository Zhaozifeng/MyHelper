package com.demo.affair;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

//此类用于电筒
public class Affair_Flashlight extends Activity {
	
	public LinearLayout		mainLinearLayout;
	public Dialog			listDialog;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_affair_flashlight);
		
		mainLinearLayout = (LinearLayout)findViewById(R.id.flash_layout);		
		mainLinearLayout.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				showListDialog();
				Toast.makeText(Affair_Flashlight.this, "这里会弹出对话框", 8888).show();				
			}			
		});
	}
	
	//展示对话框
	public void showListDialog(){
		listDialog = new Dialog(Affair_Flashlight.this);
		listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		listDialog.setContentView(R.layout.flash_list_layout);
		Window dialogWindow = listDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.alpha = 0.9f;
		lp.width = (int)(GlobalApp.getInstance().ScreenWidth*0.8);
		lp.height= (int)(GlobalApp.getInstance().ScreenHeight*0.5);
		dialogWindow.setAttributes(lp);	
		
		listDialog.show();
	}
	
	

}











