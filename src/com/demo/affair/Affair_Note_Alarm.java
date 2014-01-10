package com.demo.affair;

import com.demo.myhelper.MyHelper_Recall;
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
	@Override
	public void onReceive(Context arg0, Intent arg1) {	
		//跳转备忘录到时间
		showNoteDialog(arg0);
			
	}
	
	
	//显示弹出框
	public void showNoteDialog(Context context){
		Intent intent = new Intent(context,MyHelper_Recall.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);

	}
}













