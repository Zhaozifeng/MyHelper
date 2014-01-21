package com.demo.affair;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Account_Scanf extends Activity {	
	
	private TextView tvTitle;
	private ImageView imgBack;
	private ImageView imgMenu;
	private ViewPager viewPage;
	private Cursor	  curCursor;
	private Cursor    analyseCursor;
	private List<View> views;
	
	public int curYear;
	public int curMonth;
	public int curItemId;																		//记录当前页面id
	public String showContent;																	//当前要展示的卡片的内容
	public String showSort;																		//从分析activity跳转过来的数据
	public int    showYear;
	
	public boolean isAnalyse;																	//判断是否分析传来的页面
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);										//设置自定义标题栏
		setContentView(R.layout.account_affair_scanf);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);				
		customTitle();																			//设置标题栏
			
	}
	
	public void searchDB(){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		String selection = "year_int=? and month_int=?";
		String selectionArgs[] = {curYear+"",curMonth+""};
		curCursor = db.query
		(MainDatabase.ECONOMY_TABLE_NAME, null, selection, selectionArgs, null, null,  "_id desc", null);		//倒序排列最新的排在最前面
		curCursor.moveToFirst();																				//自动递增的id要下表_id
	}
	
	public void searchDb2(){																					//分析返回的搜索游标
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		String selection = "year_int=? and sort=?";
		String selectionArgs[] = {showYear+"",showSort};
		analyseCursor = db.query
		(MainDatabase.ECONOMY_TABLE_NAME, null, selection, selectionArgs, null, null,  "_id desc", null);		//倒序排列最新的排在最前面
		analyseCursor.moveToFirst();
	}
	
	public void showPager(Cursor c){		
		views = new ArrayList<View>();
		LayoutInflater inflate = this.getLayoutInflater();
		for(int i=0;i<c.getCount();i++){
			View view = inflate.inflate(R.layout.account_affair_scanf_page, null);
			
			TextView date = (TextView)view.findViewById(R.id.date_show);							//获取时间
			date.setText(c.getString(6).toString());
						
			TextView sort  = (TextView)view.findViewById(R.id.sort_show);							//获取类型
			sort.setText(c.getString(2).toString());
			
			TextView fee  = (TextView)view.findViewById(R.id.fee_show);								//获取金额
			fee.setText(c.getString(3).toString()+"元");
			
			TextView content  = (TextView)view.findViewById(R.id.content_show);						//获取内容
			content.setText(c.getString(8).toString());
			
			TextView kind = (TextView)view.findViewById(R.id.kind_show);							//获取方式
			if(c.getString(1).toString().equals("收入")){
				kind.setTextColor(this.getResources().getColor(R.color.forestgreen));				//设置收入为绿色
			}
			else{
				kind.setTextColor(this.getResources().getColor(R.color.red));						//支出为红色
			}			
			kind.setText(c.getString(1).toString());
			views.add(view);
			/*if(c.getString(8).toString().equals(showContent)){										//搜索按下的id
				curItemId = i;																		//记录当前
			}*/
			c.moveToNext();	
			}																						//走下一个游标
			c.moveToFirst();
			viewPage.setAdapter(new MyPagerAdapter(Affair_Account_Scanf.this,views));
			viewPage.setCurrentItem(curItemId);
		
		
	}
	
	
	public void getParams(){
		Intent intent = this.getIntent();
		curYear 		= intent.getIntExtra(Affair_Account_Amonth.YEAR_PARAMS, -1);
		curMonth 		= intent.getIntExtra(Affair_Account_Amonth.MONTH_PARAMS, -1);
		showContent		= intent.getStringExtra(Affair_Account_Amonth.CONTENT_PARAMS);
		curItemId       = intent.getIntExtra(Affair_Account_Amonth.WHICH_ITEM, -1);
		showSort = intent.getStringExtra(Affair_Account_Analyse.SORT_ITEM);						//接受分析数据传来的
		showYear = intent.getIntExtra(Affair_Account_Analyse.YEAR_PARAMS, -1);		
		isAnalyse       = intent.getBooleanExtra(Affair_Account_Analyse.IS_ANALYSE, false); 
	}
	
	public void customTitle(){
		getParams();																			//获取相关信息
		tvTitle = (TextView)findViewById(R.id.title_name);
		imgBack = (ImageView)findViewById(R.id.custom_title_rollback);
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		viewPage = (ViewPager)findViewById(R.id.account_pager);
		imgBack.setVisibility(View.INVISIBLE);
		imgMenu.setVisibility(View.INVISIBLE);
		if(isAnalyse){
			tvTitle.setText(showSort+"类型记录");
			searchDb2();
			showPager(analyseCursor);
		}
		else{
		tvTitle.setText(curMonth+"月份账本");
		searchDB();
		showPager(curCursor);
		}
		Intent intent = this.getIntent();
		
	}
	
	private class MyPagerAdapter extends PagerAdapter{											//自定义划屏适配器

		private Cursor cursor;
		private Context context;
		private List<View> views;
		
		MyPagerAdapter(Context context,List<View> views){
			this.context = context;
			this.views = views;
		}
		
		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {			
			return arg0==(View)arg1;
		}		
		
		public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(views.get(position));
       }
		
		public Object instantiateItem(View arg0, int arg1) { 
			((ViewPager) arg0).addView(views.get(arg1)); 
			return views.get(arg1); 
		} 
	}
}
























