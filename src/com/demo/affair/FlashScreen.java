package com.demo.affair;

import java.util.Timer;
import java.util.TimerTask;

import com.demo.myhelper.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;


public class FlashScreen extends Activity  {
	
	public ViewPager		pager;
	public LinearLayout		mainLinear;
	public boolean			isGlitter;
	public int				colId	=	0;
	
	public int colorlist[]	= {R.color.white,R.color.red,R.color.gold,R.color.brown,
			R.color.orange,R.color.yellow,R.color.powderblue,R.color.lightgreen,R.color.pink};
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.flashscreen);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
				
		//系统设置时1为最亮，0为最暗，-1为根据系统自己定义
		lp.screenBrightness		=	1;
		getWindow().setAttributes(lp);		
		pager		= (ViewPager)findViewById(R.id.flash_screen_vp);
		mainLinear	= (LinearLayout)findViewById(R.id.flash_screen_layout);
		//判断是否闪烁选项
		isGlitter	=	getIntent().getBooleanExtra(Affair_Flashlight.FLASH_GLITTER, false);
		if(isGlitter){
			pager.setVisibility(View.INVISIBLE);
			startGlitter();
		}
		else{
			pager.setAdapter(new MyPageAdapter());
			//提示克左右切换
			Toast.makeText(FlashScreen.this, 
					this.getResources().getString(R.string.flash_scroll_tips), 10000).show();
		}		
	}
		
	public void onDestroy(){
		super.onDestroy();
		timer.cancel();
	}	
	
	/*
	 * 接受信息修改背景颜色
	 */	
	public Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(colId==colorlist.length-1)
				colId=0;
			mainLinear.setBackgroundColor
			(FlashScreen.this.getResources().getColor(colorlist[colId++]));
		}
	};
	//重复任务
	TimerTask timetask = new TimerTask(){
		public void run() {
			handler.sendEmptyMessage(0);			
		}		
	};			
	/*
	 * 计时器
	 */
	Timer timer = new Timer(true);	
	public void startGlitter(){
		timer.schedule(timetask, 0,500);
	}
	
	
	//viewpager
	private class MyPageAdapter extends PagerAdapter{
		
		public int getCount() {
			return colorlist.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1){
			
			return arg0==(View)arg1;
		}
		
		public void destroyItem(ViewGroup container, int position, Object object){
			//super.destroyItem(container, position, object);
			container.removeView((View) object);
		}
		
		 public Object instantiateItem(ViewGroup container, final int position){
			LinearLayout layout = new LinearLayout(FlashScreen.this);
			layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
			layout.setBackgroundColor(FlashScreen.this.getResources().getColor(colorlist[position]));
			container.addView(layout);
			return layout;
			 
		 }
	}
	
}














