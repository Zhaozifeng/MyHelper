package com.demo.affair;

import java.io.IOException;
import java.util.Calendar;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Affair_Note_Recall extends Activity {
	
	private int ScreenWidth;
	private int ScreenHeight;
	
	private Button    btnOk;
	private TextView  tvContent;
	
	public int     issound=1;
	public int     isvibrate=1; 
	public String  content;
	public Cursor  cursor;
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_alarm);
		initialInstance();		
		showAlarm();
		Toast.makeText(Affair_Note_Recall.this, "time up", 3000).show();
	}
	
	public void test(){
		Vibrator  vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(5000);		
	}

	public void initialInstance(){
		
		Calendar c    = Calendar.getInstance();
		String year   = c.get(Calendar.YEAR)+"";
		String month  = c.get(Calendar.MONTH)+"";
		String day    = c.get(Calendar.DAY_OF_MONTH)+"";
		String hour   = c.get(Calendar.HOUR_OF_DAY)+"";
		String minute = c.get(Calendar.MINUTE)+"";
		
		String condition = "year=? and month=? and day=? and hour=? and minute=?";
		String values[]  = {year,month,day,hour,minute};
			
		//这里需要重新初始化数据库
		MainDatabase HelperSQLite = new MainDatabase(Affair_Note_Recall.this,1);
		SQLiteDatabase db         = HelperSQLite.getReadableDatabase();
		cursor                    = db.query(MainDatabase.NOTE_TABLE_NAME, null, condition, values, null, null, null);
		cursor.moveToFirst();
		
		tvContent = (TextView)findViewById(R.id.tv_recall_content);
		btnOk     = (Button)findViewById(R.id.btn_noterecall_ok);	
		
		//设置内容
		tvContent.setText(cursor.getString(7));
		//按下按钮后停止响铃和震动并退出
		btnOk.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(cursor.getInt(8)==1){
					Utools.setMedia(Affair_Note_Recall.this, 0);
				}					
				if(cursor.getInt(9)==1)
					Utools.setVibrator(Affair_Note_Recall.this, 0, 0);
				finish();
			}		
		});
	}
		
	
	public void showAlarm(){		
		DisplayMetrics dm = new DisplayMetrics();
		//获取屏幕大小
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		ScreenWidth  = dm.widthPixels;
		ScreenHeight = dm.heightPixels; 
		
		int dialog_height  = (int) ((int)ScreenHeight*0.5);
		int dialog_content = (int) ((int)dialog_height*0.5);
		//设置高度以便按钮放在底部		
		RelativeLayout rl_dialog  = (RelativeLayout)findViewById(R.id.recall_main_conainer);		
		rl_dialog.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, dialog_height));				
		//设置内容高度以便控制文本居中
		RelativeLayout rl_content = (RelativeLayout)findViewById(R.id.recall_content_container);
		rl_content.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, dialog_height));
		
		
		//获取当前窗口大小
		Window window = getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		//设置窗口的大小及透明度
		layoutParams.width  = (int) ((int)ScreenWidth*1);
		layoutParams.height = (int) ((int)ScreenHeight*0.5);
		layoutParams.alpha  = 0.7f;
		window.setAttributes(layoutParams);		
		if(cursor.getInt(8)==1){
			Utools.setMedia(Affair_Note_Recall.this, 1);
		}
		if(cursor.getInt(9)==1){
			Utools.setVibrator(Affair_Note_Recall.this, 5000, 1);
		}	
	}

}



