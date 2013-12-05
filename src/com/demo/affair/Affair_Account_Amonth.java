package com.demo.affair;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Account_Amonth extends Activity {
	
	private ImageView titleBack;
	private ImageView titleMenu;
	private TextView  tvMonth;
	private PopupWindow puWindow = null;
	private ListView  menuList;
	private ListView  amonthList;
	private int totalCursor;
	
	private Cursor curDiary;
	
	
	public  String	  titleMonth; 
	public  int curMonth;
	public  int curYear;
	
	public  static String menus[]={"添加","支出统计图","收入统计图","支出收入对比图","经济分析建议","设置"};
		
	public 	static String TABLE_DATE = "date";
	public  static String TABLE_CONTENT = "content";
	public  static String TABLE_KIND = "kind";
	public 	static String TABLE_FEE = "fee";
		
	public String from[] = {TABLE_CONTENT,TABLE_FEE,TABLE_DATE,TABLE_KIND};	
	public int to[] = {R.id.tv_account_row_content,R.id.tv_account_row_fee,R.id.tv_account_row_date
			,R.id.tv_account_row_kind};
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.account_month);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		getYearAndMonth();
		initialTitle();
		showList();																//展示当前月份收支情况
	}
	
	public void getYearAndMonth(){														//获取点击的月份和年份数
		Intent intent = this.getIntent();
		//titleMonth = intent.getStringExtra(Affair_Account.MONTH);
		curMonth = intent.getIntExtra(Affair_Account.MONTH, -1);
		curYear  = intent.getIntExtra(Affair_Account.YEAR, -1);
		titleMonth = curMonth+"月";
	}
	
	
	
	public void initialTitle(){
		titleBack = (ImageView)findViewById(R.id.custom_title_rollback);
		titleMenu = (ImageView)findViewById(R.id.custom_title_menu);
		tvMonth   = (TextView)findViewById(R.id.title_name);
		amonthList = (ListView)findViewById(R.id.ls_account_amonth);
		tvMonth.setText(titleMonth+"收支记录");
		initPopup();
		titleBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();				
			}			
		});
		titleMenu.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {												    //按原来按钮消去弹出框
				puWindow.showAsDropDown(v);	
				puWindow.setOutsideTouchable(true);
			}			
		});
	}
	
	public void initPopup(){
		LayoutInflater layoutInflater = LayoutInflater.from(this); 							//初始化弹出小列表
        View popupWindow = layoutInflater.inflate(R.layout.account_menu_list2, null);        
        menuList = (ListView)popupWindow.findViewById(R.id.account_menu_title_list);
        menuList.setAdapter(new MenuAdapter(Affair_Account_Amonth.this));
        menuList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Affair_Account_Amonth.this,Affair_Account_Add.class);
				intent.putExtra(Affair_Account.MONTH, curMonth);
				intent.putExtra(Affair_Account.YEAR, curYear);
				startActivity(intent);
			}     	
        });
        puWindow = new PopupWindow(popupWindow, 300, 650,true);
        puWindow.setBackgroundDrawable(new BitmapDrawable());
	}
	
	public void showList(){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		String selection = "month_int=?";
		String selectionArgs[] = {curMonth+""};
		curDiary = db.query
		(MainDatabase.ECONOMY_TABLE_NAME, null, selection, selectionArgs, null, null,  "_id desc", null);		//倒序排列最新的排在最前面
		curDiary.moveToFirst();																					//自动递增的id要下表_id
		totalCursor = curDiary.getCount();
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(Affair_Account_Amonth.this,
				R.layout.account_row, curDiary, from, to);
		amonthList.setAdapter(cursorAdapter);
		amonthList.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Vibrator v = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);								//震动一下
				v.vibrate(100);
				Builder builder = new Builder(Affair_Account_Amonth.this);
				builder
				.setTitle("操作提示")
				.setIcon(R.drawable.delete)
				.setMessage("您是否将要删除该记录？")
				.setPositiveButton("删除", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub						
					}
				})
				.setNegativeButton("保留", new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub						
					}
				});
				builder.create().show();
				return false;
			}			
		});
		
	}
	
	
	
	private class MenuAdapter extends BaseAdapter{
		
		private Context mContext;
		
		public MenuAdapter(Context mContext){
			this.mContext = mContext;
		}
		@Override
		public int getCount() {			
			return menus.length;
		}
		
		@Override
		public Object getItem(int arg0) {
			String item = menus[arg0];
			return item;
		}

		@Override
		public long getItemId(int position) {			
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout l = new LinearLayout(mContext);
			l.setGravity(Gravity.CENTER);
			TextView tv = new TextView(mContext);
			tv.setTextSize(20);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 100));
			tv.setText(menus[position]);
			l.addView(tv);
			return l;
		}
		
	}
	
	
	
}



















