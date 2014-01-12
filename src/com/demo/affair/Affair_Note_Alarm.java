package com.demo.affair;

import com.demo.myhelper.R;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Affair_Note_Alarm extends BroadcastReceiver {
	
	public static int     issound;
	public static int     isvibrate;
	public static String  content;
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {	
		//获取参数
		//getParams(arg1);
		//跳转备忘录到时间
		showNoteDialog(arg0);			
	}
	
	//判断响应效果函数
	public void getParams(Intent intent){
		issound   = intent.getIntExtra(Affair_Note_Add.PARAMS_SOUND, -1);
		isvibrate = intent.getIntExtra(Affair_Note_Add.PARAMS_VIBRATE, -1);
		content   = intent.getStringExtra(Affair_Note_Add.PARAMS_CONTENT);		
	}
	
	//显示弹出框
	public void showNoteDialog(Context context){
		Intent intent = new Intent(context,Affair_Note_Recall.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}
}













