package com.demo.affair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class Affair_Note_List extends Activity {
	
	private TextView  tvTitle;
	private ImageView rollBack;
	private ImageView imgMenu;
	private ListView  menuList;
	private ListView  noteList;
	private PopupMenu menu;
	private PopupWindow  menuWindow = null;
	
	private Cursor    cursor;
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
		menuList.setOnItemClickListener(new OnItemClickListener(){						
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {	
				menuWindow.dismiss();
				switch(arg2){				
				case 0:																	//新建备忘录			
					Intent intent = new Intent(Affair_Note_List.this,Affair_Note_Add.class);					
					startActivity(intent);
					break;
				case 1:
					RelativeLayout main = (RelativeLayout)findViewById(R.id.note_list_main);
					RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_can_container);
					rl.setVisibility(View.VISIBLE);
					break;
				case 2:
					break;
				}
			}			
		});
	}
		
	public void initialTitle(){
		
		DisplayMetrics dm = new DisplayMetrics();									  //获取屏幕长宽
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
		//实例备忘录列表
		noteList = (ListView)findViewById(R.id.ls_affair_note);		
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
		
		public String[] menus = {"新建","删除","设置"};
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
		
		public NoteAdapter(Context context, Cursor c){
			this.mcursor = c;
			this.context = context;
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
			RelativeLayout rl = new RelativeLayout(context);
			rl.setLayoutParams(new ListView.LayoutParams(LayoutParams.FILL_PARENT,200));
			
			//添加日期显示
			TextView time = new TextView(context);
			time.setTextSize(20);
			int year  	= mcursor.getInt(1);
			int month 	= mcursor.getInt(2)+1;
			int day   	= mcursor.getInt(3);
			int hour  	= mcursor.getInt(4);
			int minute	= mcursor.getInt(5);
			time.setText("日期 : "+year+"."+month+"."+day+"  "+hour+" : "+minute);
			RelativeLayout.LayoutParams rLayout = new RelativeLayout.LayoutParams
			(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			rLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			rLayout.setMargins(10, 5, 0, 0);
			rl.addView(time,rLayout);
			
			//添加内容显示
			TextView content = new TextView(context);
			content.setTextSize(25);
			content.setText(mcursor.getString(7));
			RelativeLayout.LayoutParams rLayout2 = new RelativeLayout.LayoutParams
			(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			rLayout2.addRule(RelativeLayout.RIGHT_OF,time.getId());
			rl.addView(content,rLayout2);
	
			mcursor.moveToNext();
			return rl;
		}
		
	}
	

}























