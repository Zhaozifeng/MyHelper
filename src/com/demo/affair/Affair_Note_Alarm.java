package com.demo.affair;

import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Affair_Note_Alarm extends BroadcastReceiver {
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Toast.makeText(arg0, "时间到了！", 8000).show();	
		Builder builder = new Builder(arg0);
		builder
		.setTitle("your clock")
		.setMessage("ha ha")
		.create().show();
	}
}
