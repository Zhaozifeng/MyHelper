package com.demo.affair;

import java.util.Calendar;

import com.demo.myhelper.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class Demo extends Activity {
	private Button b;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);
		b = (Button)findViewById(R.id.myBtn);
		b.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final Calendar c = Calendar.getInstance();
				TimePickerDialog tp = new TimePickerDialog(Demo.this, new OnTimeSetListener(){
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						c.set(Calendar.HOUR_OF_DAY, hourOfDay);
						c.set(Calendar.MINUTE, minute);
						b.setText(hourOfDay+":"+minute);
						Intent intent = new Intent(Demo.this,DemoCall.class);
						PendingIntent pi = PendingIntent.getBroadcast(Demo.this, 0, intent, 0);
						AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
						am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
						
												
					}					
				}, 0, 0, true);
				tp.show();
			}			
		});
	}

}
