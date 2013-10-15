package com.demo.affair;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;

public class Affair_Note extends Activity {	

	private TextView 	tv_title;
	private ImageView   image_left;
	private ImageView   image_commit;
	 
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);			
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialTitle();
		
			
		
	}
		
	
	void initialTitle(){														//初始化标题栏
		tv_title    = (TextView)findViewById(R.id.title_name);
		image_left  = (ImageView)findViewById(R.id.custom_title_rollback);
		image_commit= (ImageView)findViewById(R.id.custom_title_menu);
		
		image_commit.setBackgroundResource(R.drawable.btn_commit);
		tv_title.setText(R.string.note);		
		image_left.setOnClickListener(new OnClickListener(){					//返回按钮
			public void onClick(View v){
				finish();
			}
		});
		
		image_commit.setOnClickListener(new View.OnClickListener(){				//提交按钮
			public void onClick(View v){
				
				Builder mbuilder = new AlertDialog.Builder(Affair_Note.this)    //弹出对话框，注意这里一定要引入活动名
				.setTitle(R.string.note_dialog_title)
				.setMessage(R.string.note_dialog_message)
				.setPositiveButton(R.string.note_dialog_commit, new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();						
					}					
				}).
				setNegativeButton(R.string.note_dialog_cancel, new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});	
				mbuilder.create().show();
			}
		});
	}
	
}
