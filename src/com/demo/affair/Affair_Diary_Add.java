package com.demo.affair;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
	private ImageView  imgOK;
	private Button 	   btnAddEmotion;
	private Button     btnOtherTime;
	private EditText   edtName;
	private EditText   edtTime;
	private EditText   edtContent;
	
	public String title;
	public String emotion;
	public String time;
	public String content;
	
	public int click_id;
	public int emotion_id;
	public String title_and_time[];
	
	private static final String[] emotions={"心情极佳","平平常常","孤单落寞","痛苦悲伤"};
	private ArrayAdapter<String> adapter;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);							//设置标题每个Activity都需要
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.diary_add);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialUI();
		getClickId();
		showOldDiary();
		setListener();
	}
	
	public void getClickId(){
		Intent intent = this.getIntent();
		click_id = intent.getIntExtra(Affair_Diary.CHOOSE_ITEM_ID, -1);
		title_and_time = intent.getStringArrayExtra(Affair_Diary.CHOOSE_ITEM_CONDITION);
	}
	
	public void showOldDiary(){
		if(click_id!=-1){										    //非-1就是查看日记			
			SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
			Cursor c = db.query(MainDatabase.DIARY_TABLE_NAME, null, "title=? and time=?", title_and_time,
					null, null, null, null);						//注意这里的逻辑关系	
			c.moveToFirst();
			edtTime.setText(c.getString(1));			
			edtName.setText(c.getString(2));
			spEmotion.setSelection(c.getInt(4));
			edtContent.setText(c.getString(5));
		}
		
	}

	public void initialUI(){
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		Utools useforTime = new Utools();							//设置获取当前时间的工具类
		useforTime.setDate();
		imgLeft = (ImageView)findViewById(R.id.custom_title_rollback);
		tvTitle = (TextView)findViewById(R.id.title_name);
		//btnCommit = (Button)findViewById(R.id.custom_title_menu_btn);
		imgOK   = (ImageView)findViewById(R.id.custom_title_menu);
		imgOK.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_ok2));
		btnOtherTime = (Button)findViewById(R.id.btn_time_edt);
		spEmotion = (Spinner)findViewById(R.id.spinner_emotion);
		edtName   = (EditText)findViewById(R.id.edt_diary_name);
		edtTime   = (EditText)findViewById(R.id.edt_diary_time);
		edtContent= (EditText)findViewById(R.id.edt_diary_content);
		edtTime.setText(useforTime.getDate());
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,emotions);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spEmotion.setAdapter(adapter);		
	}
			
	void setListener(){
				
		imgLeft.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(Affair_Diary_Add.this,Affair_Diary.class);
				startActivity(intent);
				finish();				
			}		
		});		
				
		btnOtherTime.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				LayoutInflater lf = LayoutInflater.from(Affair_Diary_Add.this);
				View timeview = lf.inflate(R.layout.time_picker, null);
				final DatePicker dp = (DatePicker)timeview.findViewById(R.id.time_picker);				
				Builder build = new Builder(Affair_Diary_Add.this);		
				build
				.setTitle(getResources().getString(R.string.diary_choose_time))
				.setView(timeview)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						String year = dp.getYear()+"";
						String month = dp.getMonth()+1+"";
						String day = dp.getDayOfMonth()+"";
						String date = year+"-"+month+"-"+day;
						edtTime.setText(date);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub						
					}					
				})
				;
				build.create().show();
			}
			
		});
		
		imgOK.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {									//获取编辑框内容以及存进数据库				
				final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();				
				Builder builder =  new Builder(Affair_Diary_Add.this);
				builder
				.setTitle(R.string.diary_commit)
				.setMessage(R.string.diary_add_message)
				.setPositiveButton(R.string.diary_add_yes, new DialogInterface.OnClickListener() {	 //按下确定后获取控件的数据		
					public void onClick(DialogInterface dialog, int which) {
						title   = edtName.getText().toString();
						emotion = spEmotion.getSelectedItem().toString();
						emotion_id = spEmotion.getSelectedItemPosition();
						time    = edtTime.getText().toString();
						content = edtContent.getText().toString();
						ContentValues cv = new ContentValues();
						//cv.put("_id", 1);
						cv.put("time",time);
						cv.put("title", title);
						cv.put("emotion", emotion);
						cv.put("content", content);	
						cv.put("emotion_id", emotion_id);
						sql.insert(MainDatabase.DIARY_TABLE_NAME, null, cv);
						Intent intent = new Intent(Affair_Diary_Add.this,Affair_Diary.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
