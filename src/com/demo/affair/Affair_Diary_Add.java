package com.demo.affair;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

public class Affair_Diary_Add extends Activity{
	
	private TextView   tvTitle;
	private ImageView  imgLeft;
	private Spinner    spEmotion;
	private Button     btnCommit;
	private Button 	   btnAddEmotion;
	private Button     btnOtherTime;
	private EditText   edtName;
	private EditText   edtTime;
	private EditText   edtContent;
	
	public String title;
	public String emotion;
	public String time;
	public String content;
	
	private static final String[] emotions={"心情极佳","平平常常","孤单落寞","痛苦悲伤"};
	private ArrayAdapter<String> adapter;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);							//设置标题每个Activity都需要
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.diary_add);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout2);
		initialUI();
	}
	
	
	void initialUI(){
		Utools useforTime = new Utools();							//设置获取当前时间的工具类
		useforTime.setDate();
		imgLeft = (ImageView)findViewById(R.id.custom_title_rollback2);
		tvTitle = (TextView)findViewById(R.id.title_name2);
		btnCommit = (Button)findViewById(R.id.custom_title_menu_btn);
		spEmotion = (Spinner)findViewById(R.id.spinner_emotion);
		edtName   = (EditText)findViewById(R.id.edt_diary_name);
		edtTime   = (EditText)findViewById(R.id.edt_diary_time);
		edtContent= (EditText)findViewById(R.id.edt_diary_content);
		edtTime.setText(useforTime.getDate());		
		imgLeft.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(Affair_Diary_Add.this,Affair_Diary.class);
				startActivity(intent);
				finish();				
			}		
		});		
		
		btnCommit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {									//获取编辑框内容以及存进数据库
				title   = edtName.getText().toString();
				emotion = spEmotion.getSelectedItem().toString();
				Utools u = new Utools();
				u.setDate();
				time    = u.getDate();
				content = edtContent.getText().toString();
				ContentValues cv = new ContentValues();
				//cv.put("_id", 1);
				cv.put("time",time);
				cv.put("title", title);
				cv.put("emotion", emotion);
				cv.put("content", content);				
				SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
				sql.insert(MainDatabase.DIARY_TABLE_NAME, null, cv);
				Builder builder =  new Builder(Affair_Diary_Add.this);
				builder
				.setTitle(R.string.diary_commit)
				.setMessage(R.string.diary_add_message)
				.setPositiveButton(R.string.diary_add_yes, new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Affair_Diary_Add.this,Affair_Diary.class);
						startActivity(intent);
						finish();					
					}
				})
				.setNegativeButton(R.string.diry_add_no, new DialogInterface.OnClickListener() {			
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				builder.create().show();
			}			
		});
		
		tvTitle.setText(R.string.diary_add_title);		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,emotions);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spEmotion.setAdapter(adapter);
		
	}

}
