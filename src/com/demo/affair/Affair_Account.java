package com.demo.affair;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.demo.myhelper.R;

public class Affair_Account extends Activity {
	
	private ImageView  menu;
	private TextView   title;
	private ImageView  back;
	private Spinner    yearSpinner;
	private Spinner    classSpinner;
	private GridView   monthsGrid;
	
	
	public Adapter classAdapter;
	public ArrayAdapter yearAdapter;
	public String CLASS_SORT[]={"视图","列表"}; 
	public String YEARS[]={"2013","2014","2015"};
	public List<String>   monthLists;
	
	public static String MONTH = "month";								//传递参数名字
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_affair_account);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		initialTitle();
		setClicked();
	}
	
	public void initialTitle(){
		back = (ImageView)findViewById(R.id.custom_title_rollback);
		title = (TextView)findViewById(R.id.title_name);
		menu = (ImageView)findViewById(R.id.custom_title_menu);
		yearSpinner = (Spinner)findViewById(R.id.account_yearSpinner);
		classSpinner= (Spinner)findViewById(R.id.account_classSpinner);
		monthsGrid  = (GridView)findViewById(R.id.account_grid);		
		menu.setVisibility(View.INVISIBLE);		
		back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();				
			}			
		});		
		title.setText(getResources().getString(R.string.economy_title));
		monthLists = new ArrayList<String>();
		for(int i=1;i<=12;i++){												//输入月份
			monthLists.add(i+"月");
		}
	}
	
	public void setClicked(){
		classAdapter = new ArrayAdapter<String>(Affair_Account.this,
				android.R.layout.simple_spinner_item,CLASS_SORT);
		((ArrayAdapter<String>)classAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		classSpinner.setAdapter((SpinnerAdapter)classAdapter);
		yearAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,YEARS);
		yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(yearAdapter);
		monthsGrid.setAdapter(new GridAdapter(Affair_Account.this,monthLists));
		
		monthsGrid.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String monthParams = monthLists.get(arg2);
				Intent intent = new Intent(Affair_Account.this,Affair_Account_Amonth.class);
				intent.putExtra(MONTH, monthParams);								//传递月份参数
				startActivity(intent);
			}			
		});
		
		
	}
	
	private class GridAdapter extends BaseAdapter{
		
		private List<String> list;
		private Context context;
		
		public GridAdapter(Context context,List<String> list){
			this.context = context;
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			String month = (String) list.get(position);
			return month;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout l = new LinearLayout(context);
			l.setGravity(Gravity.CENTER);
			TextView tv = new TextView(context);
			tv.setGravity(Gravity.CENTER);
			tv.setText(list.get(position));
			tv.setTextSize(30);
			return tv;
		}
		
	}
	

}























