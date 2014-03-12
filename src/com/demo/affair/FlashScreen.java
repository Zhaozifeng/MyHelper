package com.demo.affair;

import com.demo.myhelper.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class FlashScreen extends Activity  {
	
	public ViewPager	pager;
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
		
		pager	= (ViewPager)findViewById(R.id.flash_screen_vp);
		pager.setAdapter(new MyPageAdapter());
	}
	
	
	
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














