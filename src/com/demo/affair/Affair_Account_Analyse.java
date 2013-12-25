package com.demo.affair;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Account_Analyse extends Activity {
	
	private TextView tvTitle;
	private ImageView imgBack;
	private ImageView imgMenu;
	private LinearLayout mainLayout;
	
	private Cursor myCursor;
	
	private int popupWindow_id;
	private int curMonth;
	private int curYear;
	private int totalIncome;																	//记录收入总额
	private int totalOutcome;																	//记录支出总额
	
	public Double[] IncomeNameIds=new Double[6]; 													//定义收入类型数组
	public Double[] OutcomeNameIds=new Double[6];													//定义支出类型数组
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);										//设置自定义标题栏
		setContentView(R.layout.account_affair_analyse);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);		
		customTitle();
	}
	
	public void customTitle(){																	//获取相关信息
		
		tvTitle = (TextView)findViewById(R.id.title_name);
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
			curNameid = getSortIds(c.getString(2),c.getString(1));
			if(curNameid!=-1){
				if(c.getString(1).equals("支出")){
					OutcomeNameIds[curNameid]=OutcomeNameIds[curNameid]+c.getDouble(3);
				}
				else{
					IncomeNameIds[curNameid] = IncomeNameIds[curNameid]+c.getDouble(3);
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
				}
			}
			return -1;																						//返回-1支出列表不能找到			
		}
		else{
			for(int i=0;i<Affair_Account_Add.INCOME_SORT.length;i++){
				if(name.equals(Affair_Account_Add.INCOME_SORT[i])){											//在收入列表查找										
					return i;
				}
			}
			return -1;																						//找不到返回-1
		}		
	}
	
	
	public void initialTable(){																	//判断划出何种列表类型
		switch(popupWindow_id){
		case 1:
			outcomeTable();																		//构造支出分析图
			break;
		case 2:
			incomeTable();																		//构造收入分析图
			break;
		case 3:
			out_in_Table();																		//构造收入支出对比图
			break;		
		}	
	}
	
	public void outcomeTable(){
		mainLayout.removeAllViews();
		int curNameid;
		for(int i=0;i<myCursor.getCount();i++){
			curNameid = 
			NameIds[]
		}
		
		
	}
	
	public void incomeTable(){
		
	}
	
	public void out_in_Table(){
		
	}

}


























