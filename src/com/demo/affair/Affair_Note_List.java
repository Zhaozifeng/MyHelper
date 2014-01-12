package com.demo.affair;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;
import com.demo.tools.Utools;

public class Affair_Note_List extends Activity {
	
	private TextView  tvTitle;
	private ImageView rollBack;
	private ImageView imgMenu;
	private ListView  menuList;
	private ListView  noteList;
	private PopupMenu menu;
	private PopupWindow  menuWindow = null;
	
	private Cursor    	   cursor;
	private SQLiteDatabase mainDB;
	
	public int screenWidth;
	public int screenHeight;
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note_list);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();	
		getCursor();
		setListener();																	//设定列表监听
	}
	
	//获取数据库中Note表
	public void getCursor(){
		mainDB = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		cursor = mainDB.query(MainDatabase.NOTE_TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		noteList.setAdapter(new NoteAdapter(Affair_Note_List.this,cursor));
	}
	
	public void setListener(){
		//菜单列表监听
		menuList.setOnItemClickListener(new OnItemClickListener(){						
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {	
				menuWindow.dismiss();
				switch(arg2){				
				case 0:	
					//新建备忘录			
					Intent intent = new Intent(Affair_Note_List.this,Affair_Note_Add.class);					
					startActivity(intent);
					break;
				case 1:	
					break;
				case 2:
					break;
				}
			}			
		});
		
		//备忘录列表长按监听
		noteList.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//调用震动函数
				//由于是倒着展示，所以下标有所改变
				final int position = cursor.getCount()-arg2-1;
				Utools.setVibrator(Affair_Note_List.this, 100,0);
				Builder builder = new Builder(Affair_Note_List.this);
				builder
				.setIcon(R.drawable.delete)
				.setTitle("操作提示")
				.setMessage("你是否删除该提醒?")
				.setPositiveButton("保留", null)
				.setNegativeButton("删除", new DialogInterface.OnClickListener() {					
					public void onClick(DialogInterface dialog, int which) {
						Utools.setVibrator(Affair_Note_List.this, 100, 1);
						deleteItem(position);							
					}
				});			
				builder.create().show();				
				return false;
			}			
		});
	
	}
	
	
	//删除项目
	public void deleteItem(int position){
		cursor.moveToPosition(position);
		String strYear  	= cursor.getInt(1)+"";
		String strMonth 	= cursor.getInt(2)+"";
		String strDay   	= cursor.getInt(3)+"";
		String strHour  	= cursor.getInt(4)+"";
		String strMinute	= cursor.getInt(5)+"";
		String strContent	= cursor.getString(7);
		
		//定义搜索范围
		String condition	= "year=? and month=? and day=? and hour=? "
				+ "and minute=? and content=?";
		//定义搜索条件
		String values[]     = {strYear,strMonth,strDay,strHour,strMinute,strContent};
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		db.delete(MainDatabase.NOTE_TABLE_NAME, condition, values);
		getCursor();
	}
		
	//初始化标题栏
	public void initialTitle(){
		
		DisplayMetrics dm = new DisplayMetrics();									  //获取屏幕长宽
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		//实例备忘录列表
		noteList = (ListView)findViewById(R.id.ls_affair_note);		
		noteList.setDivider(this.getResources().getDrawable(R.color.ivory));
		noteList.setDividerHeight(3);
		//设置标题栏
		LayoutInflater lf = LayoutInflater.from(Affair_Note_List.this);
		View view = lf.inflate(R.layout.account_menu_list2, null);
		menuList = (ListView)view.findViewById(R.id.account_menu_title_list);
		menuList.setAdapter(new MenuAdapter(Affair_Note_List.this));
		tvTitle  = (TextView)findViewById(R.id.title_name);
		tvTitle.setText("备忘录列表");
		rollBack = (ImageView)findViewById(R.id.custom_title_rollback);
		rollBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();			
			}			
		});
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				menuWindow.showAsDropDown(v);
			}			
		});		
		menuWindow = new PopupWindow(view, 300, 650,true);
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
	}
	
	//定义菜单列表适配类
	private class MenuAdapter extends BaseAdapter{
		
		public String[] menus = {"新建","生日达人","我的节日","设置"};
		private Context mcontext;
		
		public MenuAdapter(Context context){
			mcontext = context;
		}
		@Override
		public int getCount() {
			return menus.length;
		}
		@Override
		public Object getItem(int arg0) {			
			return menus[arg0];
		}
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LinearLayout l = new LinearLayout(mcontext);
			l.setGravity(Gravity.CENTER);
			TextView tv = new TextView(mcontext);
			tv.setTextSize(20);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 100));
			tv.setText(menus[arg0]);
			l.addView(tv);
			return l;
		}
		
	}
	
	//定义备忘录列表适配类
	private class NoteAdapter extends BaseAdapter{
		
		private Cursor  mcursor;
		private Context context;
		private int		size;
		
		private String  OVER_TIME  = "已过期";
		private String  ON_ACTION  = "已激活";
		private String  OFF_ACTION = "已取消";
		
		public NoteAdapter(Context context, Cursor c){
			this.mcursor = c;
			this.context = context;
			mcursor.moveToLast();
			size = c.getCount();
		}

		@Override
		public int getCount() {
			return size;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			mcursor.moveToPosition(size-1-position);
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.affair_note_list_item, null);
			
			//显示日期
			TextView date = (TextView)view.findViewById(R.id.tv_note_item);
			int year  	  = mcursor.getInt(1);
			int month 	  = mcursor.getInt(2)+1;
			int day   	  = mcursor.getInt(3);
			int hour  	  = mcursor.getInt(4);
			int minute	  = mcursor.getInt(5);
			date.setText("日期 : "+year+"."+month+"."+day+"  "+hour+" : "+minute);
			
			//显示内容
			TextView content = (TextView)view.findViewById(R.id.tv_note_content);
			content.setText(mcursor.getString(7));
			
			//显示状态
			TextView status  = (TextView)view.findViewById(R.id.tv_note_status);
			if(isOverTime(mcursor).equals(OVER_TIME)){
				status.setTextColor(context.getResources().getColor(R.color.red));
				status.setText(OVER_TIME);
			}
			else{
				status.setTextColor(context.getResources().getColor(R.color.darkseagreen));
				status.setText(mcursor.getString(10));
			}			
			
			
			//显示声音
			ImageView sound  = (ImageView)view.findViewById(R.id.img_note_sound);
			if(mcursor.getInt(8)==0){
				sound.setVisibility(View.INVISIBLE);
			}
			//显示震动
			ImageView vibrate  = (ImageView)view.findViewById(R.id.img_note_vibrate);
			if(mcursor.getInt(9)==0){
				vibrate.setVisibility(View.INVISIBLE);
			}
			
			return view;
		}
		
		//判断是否过期函数
		public String isOverTime(Cursor c){ 
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			int year  	= cal.get(Calendar.YEAR);
			int month 	= cal.get(Calendar.MONTH);
			int day   	= cal.get(Calendar.DAY_OF_MONTH);
			int hour  	= cal.get(Calendar.HOUR_OF_DAY);
			int minute	= cal.get(Calendar.MINUTE);
			
			if(mcursor.getString(10).equals(ON_ACTION)){
				if(c.getInt(1)<year){
					return OVER_TIME;
				}
				else if(c.getInt(1)==year){
					if(c.getInt(2)<month){
						return OVER_TIME;
					}
					else if(c.getInt(2)==month){
						if(c.getInt(3)<day){
							return OVER_TIME;
						}
						else if(c.getInt(3)==day){
							if(c.getInt(4)<hour){
								return OVER_TIME;
							}
							else if(c.getInt(4)==hour){
								if(c.getInt(5)<minute){
									return OVER_TIME;
								}
							}
						}
					}
				}
			}
			return mcursor.getString(7);
		}
		
	}
	

}























