package com.demo.affair;
import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;
import com.demo.tools.Utools;
import com.helper.adapter.BirthListAdapter;
import com.helper.adapter.CommonAdapter;
import com.helper.birthday.BirthAdd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Affair_Birthday extends Activity {
	
	public ImageView	imgBack;
	public ImageView	imgMenu;
	public TextView		title;
	public ListView		birthList;
	public ListView		menuList;
	public PopupWindow	menuWindow;
	
	public String[]	menus	={"添加","查询","设置"};
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);		
		setContentView(R.layout.activity_affair_birthday);
		Utools.customTitle(Affair_Birthday.this, getResources().getString(R.string.birthday));
		initUI(); 
	}
	
	
	public void initUI(){
		imgMenu		= (ImageView)findViewById(R.id.custom_title_menu);
		imgMenu.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				menuWindow.showAsDropDown(v);			
			}		
		});
		
		birthList	= (ListView)findViewById(R.id.birth_lv);
		birthList.setAdapter(new BirthListAdapter(Affair_Birthday.this));	
		
		//菜单那列表
		LayoutInflater lf = LayoutInflater.from(Affair_Birthday.this);
		View view = lf.inflate(R.layout.account_menu_list2, null);
		menuList = (ListView)view.findViewById(R.id.account_menu_title_list);
		menuList.setAdapter(new CommonAdapter(menus,Affair_Birthday.this));
		menuList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch(arg2){
				case 0:
					Intent intent1 = new Intent(Affair_Birthday.this,BirthAdd.class);
					startActivity(intent1);
					break;
				}
			}			
		});
		
		//要计算popup长宽
		int popWid 		= (int)(GlobalApp.getInstance().ScreenWidth*0.4);
		int popHeight	= (int)(GlobalApp.getInstance().ScreenHeight*0.6);	
				
		menuWindow = new PopupWindow(view, popWid, popHeight,true);
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
	}


	
}






















