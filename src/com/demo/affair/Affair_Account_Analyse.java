package com.demo.affair;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Account_Analyse extends Activity {
	
	private TextView tvTitle;
	private ImageView imgBack;
	private ImageView imgMenu;
	private LinearLayout mainLayout;
	private TextView  tvAnalyseTitle;
	private TextView  tvAnalyseItems;
	private TextView  tvAnalyseTotal;
	
	private Cursor myCursor;
	
	private int popupWindow_id;
	private int curMonth;
	private int curYear;
	private double totalIncome=0;																	//记录收入总额
	private int    incomes = 0;																		//统计收入数据条数
	private double totalOutcome=0;																	//记录支出总额
	private int	   outcomes = 0;																	//统计支出数据条数
	
	private int screenWidth  = 0;                          											//获取屏幕的宽度													
	private int screenHeight = 0;																	//获取屏幕的长度
	
	public Double[] IncomeEachValue = new Double[6] ;											    	//定义收入类型数组
	public Double[] OutcomeEachValue= new Double[6];													//定义支出类型数组
	
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);											//设置自定义标题栏
		setContentView(R.layout.account_affair_analyse);	
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);		
		customTitle();
		getCursor();
		countFee(myCursor);
		initialTable();
	}
	
	public void customTitle(){																		//获取相关信息
		
		tvTitle 	   = (TextView)findViewById(R.id.title_name);
		tvAnalyseTitle = (TextView)findViewById(R.id.tv_analyse_title);
		tvAnalyseItems = (TextView)findViewById(R.id.tv_analyse_items);
		tvAnalyseTotal = (TextView)findViewById(R.id.tv_analyse_total);
		
		imgBack = (ImageView)findViewById(R.id.custom_title_rollback);
		imgMenu = (ImageView)findViewById(R.id.custom_title_menu);
		mainLayout = (LinearLayout)findViewById(R.id.analyse_layout);
		imgMenu.setVisibility(View.INVISIBLE);
		imgBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();				
			}			
		});
		Intent intent = this.getIntent();
		popupWindow_id = intent.getIntExtra(Affair_Account_Amonth.WHICH_ANALSE, -1);
		curMonth = intent.getIntExtra(Affair_Account_Amonth.MONTH_PARAMS, -1);
		curYear = intent.getIntExtra(Affair_Account_Amonth.YEAR_PARAMS, -1);
		tvTitle.setText(curMonth+"月份"+Affair_Account_Amonth.menus[popupWindow_id]);		
		
		DisplayMetrics dm = new DisplayMetrics();													//获取屏幕长宽
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth  = dm.widthPixels;                         										//获取屏幕的宽度													
		screenHeight = dm.heightPixels;
		
		for(int i=0;i<Affair_Account_Add.OUTCOME_SORT.length;i++){									//初始化数组为0
			IncomeEachValue[i]=0.0;
			OutcomeEachValue[i]=0.0;
		}
		
	}
	
	public void getCursor(){
		SQLiteDatabase db = MyHelper_MainActivity.HelperSQLite.getReadableDatabase();
		String selection = "month_int=? and year_int=?";
		String selectionArgs[] = {curMonth+"",curYear+""};
		myCursor = db.query
		(MainDatabase.ECONOMY_TABLE_NAME, null, selection, selectionArgs, null, null,  "_id desc", null);		//倒序排列最新的排在最前面
		myCursor.moveToFirst();
		
	}
	
	public void countFee(Cursor c){																//统计金额
		int curNameid;
		int CursorLenght = c.getCount();
		for(int i=0;i<CursorLenght;i++){
			curNameid = getSortIds(c.getString(2),c.getString(1));								//获取类型下标
			if(curNameid!=-1){																	//返回非-1即是下标
				if(c.getString(1).equals("支出")){
					outcomes++;
					totalOutcome = totalOutcome+c.getDouble(3);									//支出总额统计
					OutcomeEachValue[curNameid]=OutcomeEachValue[curNameid]+c.getDouble(3);			//统计各支出类型的数值
				}
				else{
					incomes++;
					totalIncome = totalIncome+c.getDouble(3);									//收入总额统计
					IncomeEachValue[curNameid] = IncomeEachValue[curNameid]+c.getDouble(3);			//统计各收入类型的数值
				}
			}
			c.moveToNext();																		//移到下一条
		}
	}
	
	public int getSortIds(String name,String kind){															//牺牲时间换空间
		if(kind.equals("支出")){																				//判断账本类型
			for(int i=0;i<Affair_Account_Add.OUTCOME_SORT.length;i++){
				if(name.equals(Affair_Account_Add.OUTCOME_SORT[i])){
					return i;																				//返回支出下标
				}}
			return -1;																						//返回-1支出列表不能找到			
		}
		else{
			for(int i=0;i<Affair_Account_Add.INCOME_SORT.length;i++){
				if(name.equals(Affair_Account_Add.INCOME_SORT[i])){											//在收入列表查找										
					return i;
				}}																					//找不到返回-1
			return -1;
		}		
	}

	public void initialTable(){																	//判断划出何种列表类型
		switch(popupWindow_id){
		case 1:
			paintOutcomeTable();																//构造支出分析图
			break;
		case 2:
			paintIncomeTable();																	//构造收入分析图
			break;
		case 3:
			paintOut_In_Table();																//构造收入支出对比图
			break;		
		}	
	}
	
	public void paintOutcomeTable(){															//画支出图
		
		paintItems("支出");	
	}
	
	public void paintIncomeTable(){
		
	}
	
	public void paintOut_In_Table(){
		
	}
	
	public void paintItems(String which){												//画出各项比例	
		mainLayout.removeAllViews();
		String temp_sort[];
		Double each_values[];
		double totalValue;
		if(which.equals("支出")){															//判断是支出时赋初始值
			tvAnalyseItems.setText(which+this.getResources().getString(R.string.economy_analyse_items)+""+outcomes);
			tvAnalyseTotal.setText(which+this.getResources().getString(R.string.economy_analyse_total)+""+totalOutcome);
			temp_sort = Affair_Account_Add.OUTCOME_SORT;
			each_values = OutcomeEachValue;
			totalValue = totalOutcome;
		}
		else{																			//判断是收入时赋初始值
			tvAnalyseItems.setText(which+this.getResources().getString(R.string.economy_analyse_items)+""+incomes);
			tvAnalyseTotal.setText(which+this.getResources().getString(R.string.economy_analyse_total)+""+totalIncome);
			temp_sort = Affair_Account_Add.INCOME_SORT;
			each_values = IncomeEachValue;
			totalValue = totalIncome;
		}
		for(int i=0;i<temp_sort.length;i++){
			LinearLayout ll = new LinearLayout(this);
			ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,200));
			ll.setOrientation(LinearLayout.HORIZONTAL);
			ll.setGravity(Gravity.CENTER_VERTICAL);
			TextView tv_name = new TextView(this);
			tv_name.setText(temp_sort[i]+" :");
			tv_name.setTextSize(25);
			tv_name.setLayoutParams(new LinearLayout.LayoutParams(150,LayoutParams.WRAP_CONTENT));
			ll.addView(tv_name);
			
			LinearLayout ll2 = new LinearLayout(this);
			double rate = each_values[i]/totalValue;
			double value = (screenWidth-150)*rate;
			int colorWidth = (int)value;
			ll2.setLayoutParams(new LinearLayout.LayoutParams(colorWidth,100));
			ll2.setBackgroundColor(this.getResources().getColor(R.color.red));
			ll.addView(ll2);
			ll.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.btn_push_style));
			ll.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Affair_Account_Analyse.this,Affair_Account_Scanf.class);					
					startActivity(i);
				}				
			});			
			mainLayout.addView(ll);
		}
	}

}


























