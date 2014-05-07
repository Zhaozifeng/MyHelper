package com.demo.myhelper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MyHelper_Welcome extends Activity{
	
	public  static String           	LOGIN   = "IS_FIRST_LOGIN";
	private boolean						isLogin = false;
	private ViewPager 					mPager;
	private ArrayList<View>       		ViewsList;  
	
	private int ViewCounts		= 0;
	private int ImageIds[]      = {R.drawable.p1,R.drawable.p2,		//预存图片
								   R.drawable.p3,R.drawable.p4};
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 		//实现全屏效果
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_helper_welcome);			
		isEnter();
	}
			
	
	public void isEnter(){				
		SharedPreferences setting = getPreferences(Activity.MODE_PRIVATE);		//获取登录信息
		isLogin = setting.getBoolean(LOGIN, false);
		
		//设置全局变量，保存屏幕尺寸
		DisplayMetrics dm     = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);                                   
		GlobalApp myapp = new GlobalApp();
		myapp.setScreen(dm.widthPixels, dm.heightPixels);
		GlobalApp.setGlobalInstance(myapp);
				
		if(isLogin == false){													//判定是否首次登陆
			initialUI();														//初始化界面
			mPager.setAdapter(new MyPagerAdapter(ViewsList));
		}
		else{
			Intent intent = new Intent(MyHelper_Welcome.this,MyHelper_MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);			
			startActivity(intent);		
			finish();
		}
	}
	
	public void initialUI(){
		mPager = (ViewPager)findViewById(R.id.vp_myhelper_welcome);
		ViewCounts = ImageIds.length;
		ViewsList  = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(MyHelper_Welcome.this);
		for(int i=0;i<ViewCounts;i++){			
			View view = inflater.inflate(R.layout.welcome_img_layout, null);
			RelativeLayout bg = (RelativeLayout)view.findViewById(R.id.welcome_gb_rly);
			bg.setBackgroundResource(ImageIds[i]);
			
			if(i==ViewCounts-1){												//最后一页添加进入程序按钮
				Button login = (Button)view.findViewById(R.id.welcome_btn_login);
				login.setVisibility(View.VISIBLE);
				login.setOnClickListener(new ButtonListener());
			}
			ViewsList.add(bg);
		}		
	}
	
	/*
	 * 最后一页按钮监听
	 */	
	private class ButtonListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MyHelper_Welcome.this,MyHelper_MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			isLogin = true;
			SharedPreferences uiState = getPreferences(Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = uiState.edit();
			editor.putBoolean(LOGIN, isLogin);
			editor.commit();
			startActivity(intent);		
			finish();
		}		
	}
	
	private class MyPagerAdapter extends PagerAdapter{
		
		private ArrayList<View> ViewsList;				
		public MyPagerAdapter(ArrayList<View> ViewsList){
			this.ViewsList = ViewsList;
		}
		@Override
		public int getCount() {
			return ViewsList.size();
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==(View)arg1;
		}		
		@Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(ViewsList.get(position));
            return ViewsList.get(position);
        }	
	}	
}



















