package com.demo.affair;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Diary extends Activity {
	
	TextView 	tvTitle;
	ImageView	imgLeft;
	ImageView	imgAdd;
	ListView    lsDiary;
	Cursor      curDiary;
	
	public static String DIARY_TABLE_TITLE = "title";
	public static String DIARY_TABLE_TIME  = "time";
	public static String CHOOSE_ITEM_ID    = "click_id";
	public static String DIARY_TABLE_NAME = "Diary" ;
	
	public static int totalCursor;
	


	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_diary);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();	
		makeList();
	}
	
	
	public void makeList(){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		//curDiary = db.query(MainDatabase.DIARY_TABLE_NAME, new String[]{DIARY_TABLE_TITLE,DIARY_TABLE_TIME}, null, null, null, null, null);
		String selector[]={"_id",DIARY_TABLE_TITLE,DIARY_TABLE_TIME};
		curDiary = db.query
		(MainDatabase.DIARY_TABLE_NAME, null, null, null, null, null,  "_id desc", null);
		curDiary.moveToFirst();
		totalCursor = curDiary.getCount();
		if(curDiary.getCount()>0){
			String from[]=new String[]{DIARY_TABLE_TITLE,DIARY_TABLE_TIME};
			int to[]=new int[]{R.id.tv_diary_title,R.id.tv_diary_time};
			SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
					R.layout.diary_row, curDiary, from, to);			
			lsDiary.setAdapter(notes);
			lsDiary.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent(Affair_Diary.this,Affair_Diary_Add.class);
					intent.putExtra(CHOOSE_ITEM_ID, arg2);
					startActivity(intent);
				}				
			});			
		}
	}
		
	public void initialTitle(){
		tvTitle    = (TextView)findViewById(R.id.title_name);
		imgLeft    = (ImageView)findViewById(R.id.custom_title_rollback);
		imgAdd 	   = (ImageView)findViewById(R.id.custom_title_menu);
		lsDiary    = (ListView)findViewById(R.id.ls_diary);
		
		tvTitle.setText(R.string.diary_title);
		imgLeft.setBackgroundResource(R.drawable.btn_left);
		imgAdd.setBackgroundResource(R.drawable.btn_add);
		
		imgLeft.setOnClickListener(new OnClickListener(){				//返回上一页按钮
			@Override
			public void onClick(View arg0) {
				finish();				
			}		
		});		
		
		imgAdd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Affair_Diary.this,Affair_Diary_Add.class);
				startActivity(intent);
				finish();
			}	
		});
		
	}
	
	
}






























