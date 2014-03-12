package com.demo.affair;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//此类用于电筒
public class Affair_Flashlight extends Activity {
	
	public static String FLASH_GLITTER 		= "flash_glitter";
	public static String CAMERA_GLITTER		= "camera_glitter";
	
	//选项内容
	public static String[] LIST = {"闪光灯","正屏幕","炫彩屏","信号灯"}; 
	
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
		lp.width = (int)(GlobalApp.getInstance().ScreenWidth*0.9);
		lp.height= (int)(GlobalApp.getInstance().ScreenHeight*0.55);
		
		dialogWindow.setAttributes(lp);	
		ListView listview = (ListView)listDialog.findViewById(R.id.flash_list);
		listview.setAdapter(new MyAdapter());		
		listDialog.show();
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0:
					startActivity(new Intent(Affair_Flashlight.this,FlashlightOn.class));
					break;
				case 1:
					startActivity(new Intent(Affair_Flashlight.this,FlashScreen.class));
					break;
				case 2:
					Intent intent = new Intent(Affair_Flashlight.this,FlashScreen.class);
					intent.putExtra(Affair_Flashlight.FLASH_GLITTER, true);
					startActivity(intent);
					break;
				case 3:
					Intent intent2 = new Intent(Affair_Flashlight.this,FlashlightOn.class);
					intent2.putExtra(Affair_Flashlight.CAMERA_GLITTER, true);
					startActivity(intent2);
					break;
				}
			}			
		});
	}
	
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return LIST.length;
		}

		@Override
		public Object getItem(int arg0) {
			return LIST[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater.from(Affair_Flashlight.this);
			View view = inflater.inflate(R.layout.list_item, null);
			TextView tv = (TextView)view.findViewById(R.id.flash_list_tv);
			tv.setText(LIST[arg0]);
			return view;
		}
		
	}
	

}











