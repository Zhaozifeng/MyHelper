package com.demo.affair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.demo.myhelper.R;

public class Affair_Note_List extends Activity {
	
	private TextView  tvTitle;
	private ImageView rollBack;
	private ImageView imgMenu;
	private ListView  menuList;
	private PopupMenu menu;
	private PopupWindow  menuWindow = null;
	
	public int screenWidth;
	public int screenHeight;
		
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_note_list);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();	
		setListener();																	//设定列表监听
	}
	
	public void setListener(){
		menuList.setOnItemClickListener(new OnItemClickListener(){						
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {	
				menuWindow.dismiss();
				switch(arg2){				
				case 0:																	//新建备忘录			
					Intent intent = new Intent(Affair_Note_List.this,Affair_Note.class);					
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
		
		DisplayMetrics dm = new DisplayMetrics();									//获取屏幕长宽
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		
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
	

}























