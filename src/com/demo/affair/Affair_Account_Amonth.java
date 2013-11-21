package com.demo.affair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Account_Amonth extends Activity {
	
	private ImageView titleBack;
	private ImageView titleMenu;
	private TextView  tvMonth;
	private PopupWindow puWindow = null;
	private ListView  menuList;
	
	
	public  String	  curMonth; 
	public  static String menus[]={"添加","支出统计图","收入统计图","支出收入对比图","经济分析建议","设置"};
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.account_month);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		getMonth();
		initialTitle();
	}
	
	public void getMonth(){														//获取点击的月份数
		Intent intent = this.getIntent();
		curMonth = intent.getStringExtra(Affair_Account.MONTH);
	}
	
	
	
	public void initialTitle(){
		titleBack = (ImageView)findViewById(R.id.custom_title_rollback);
		titleMenu = (ImageView)findViewById(R.id.custom_title_menu);
		tvMonth   = (TextView)findViewById(R.id.title_name);
		tvMonth.setText(curMonth+"收支记录");
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
				startActivity(intent);
			}     	
        });
        puWindow = new PopupWindow(popupWindow, 300, 650,true);
        puWindow.setBackgroundDrawable(new BitmapDrawable());
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



















