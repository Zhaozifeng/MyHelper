package com.demo.affair;
import android.app.Activity;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

public class Affair_Diary extends Activity {
	
	TextView 	tvTitle;
	ImageView	imgLeft;
	ImageView	imgAdd;
	ListView    lsDiary;
	Cursor      curDiary;
	
	public static String DIARY_TABLE_TITLE = "title";
	public static String DIARY_TABLE_TIME  = "time";
	public static String CHOOSE_ITEM_ID    = "click_id";
	public static String CHOOSE_ITEM_CONDITION = "condition";
	public static String DIARY_TABLE_NAME  = "Diary" ;
	
	public static int totalCursor;
	
	String from[]=new String[]{DIARY_TABLE_TITLE,DIARY_TABLE_TIME};
	int to[]=new int[]{R.id.tv_diary_title,R.id.tv_diary_time};

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_diary);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();	
		initialList();
	}
	
	public void initialList(){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		String selector[]={"_id",DIARY_TABLE_TITLE,DIARY_TABLE_TIME};
		curDiary = db.query
		(MainDatabase.DIARY_TABLE_NAME, null, null, null, null, null,  "_id desc", null);		//倒序排列最新的排在最前面
		curDiary.moveToFirst();
		totalCursor = curDiary.getCount();
		if(curDiary.getCount()>0){							
			setList(curDiary);
		}
	}
	
	public void setList(Cursor c){
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.diary_row, curDiary, from, to);
		lsDiary.setAdapter(notes);		
		lsDiary.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				convertParams(arg1,arg2);				
			}				
		});		
		
		lsDiary.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				TextView tv_title = (TextView)arg1.findViewById(R.id.tv_diary_title);				
				TextView tv_time = (TextView)arg1.findViewById(R.id.tv_diary_time);
				
				final String time_string = tv_time.getText().toString();
				final String title_string = tv_title.getText().toString();
				final View view    = arg1;
				final int  clicked = arg2;
				
				Vibrator v = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
				v.vibrate(100);
				
				Builder build = new Builder(Affair_Diary.this);
				String[] items = new String[]{"编辑","收藏","删除"};	
				build
				.setTitle("编辑操作")
				.setItems(items, new DialogInterface.OnClickListener() {						
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:convertParams(view,clicked);break;
						case 1:break;
						case 2:showMakeSureDialog(title_string,time_string);  break;							
						}
					}
				});					
				build.create().show();
				return false;
			}				
		});
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
	
	public void del(String title,String time){												//删除日记
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		String condition[] = {title,time};		
		db.delete(MainDatabase.DIARY_TABLE_NAME, "title=? and time=?", condition);			//为避免误删日记，双重条件判断是否
		curDiary = db.query
		(MainDatabase.DIARY_TABLE_NAME, null, null, null, null, null,  "_id desc", null);
		curDiary.moveToFirst();
		totalCursor = curDiary.getCount();
		Builder build = new Builder(Affair_Diary.this);
		build
		.setTitle(getResources().getString(R.string.note_dialog_title))
		.setMessage(getResources().getString(R.string.diary_delete_sucess))
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
				setList(curDiary);				
			}
		});
		build.create().show();	
		setList(curDiary);
	}
	
	public void convertParams(View v,int clicked){									//传递参数函数并跳转
		TextView tv_title = (TextView)v.findViewById(R.id.tv_diary_title);
		String title = tv_title.getText().toString();
		TextView tv_time = (TextView)v.findViewById(R.id.tv_diary_time);
		String time = tv_time.getText().toString();
		String condition[]={title,time};											//传递参数标题和时间
		Intent intent = new Intent(Affair_Diary.this,Affair_Diary_Add.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(CHOOSE_ITEM_ID, clicked);
		intent.putExtra(CHOOSE_ITEM_CONDITION, condition);
		startActivity(intent);
		finish();
	}

	void showMakeSureDialog(String title,String time){
		final String string_title = title;
		final String string_time  = time;
		Builder builder = new Builder(Affair_Diary.this);
		builder
		.setTitle("删除提示")
		.setMessage("是否确定删除该日记？")
		.setPositiveButton("删除", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				del(string_title,string_time);				
			}
		})
		.setNegativeButton("保留", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}
		});
		builder.create().show();
	}
	
	
}






























