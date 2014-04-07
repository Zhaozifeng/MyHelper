package com.demo.affair;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.demo.myhelper.MyHelper_MainActivity;
import com.demo.myhelper.R;
import com.demo.object.MainDatabase;

public class Affair_Account_Add extends Activity {
	
	private ImageView titleBack;
	private ImageView titleCommit;
	private TextView  tvTitle;
	private Spinner   mSpinner;
	private Adapter   inAdapter;   
	private Adapter   outAdapter;
	private RadioButton inRadioButton;
	private RadioButton outRadioButton;
	private EditText  edtFee;
	private DatePicker mDpicker;
	private TimePicker mTpicker;
	private EditText  edtContent;
	
	private int curYear;
	private int curMonth;
	
	public static String[] INCOME_SORT = {"工资","定期","资助","分红","中奖","其他"};
	public static String[] OUTCOME_SORT= {"餐饮","用品","买菜","学费","送礼","其他"};
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.account_affair_add);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);	
		initialCommonTitle();
		btnListener();
	}
	
	public void initialCommonTitle(){												//初始化标题栏
		//隐藏键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		titleBack = (ImageView)findViewById(R.id.custom_title_rollback);
		titleCommit = (ImageView)findViewById(R.id.custom_title_menu);
		tvTitle   = (TextView)findViewById(R.id.title_name);
		mSpinner  = (Spinner)findViewById(R.id.account_sp_sort);
		inRadioButton = (RadioButton)findViewById(R.id.radio_income);
		outRadioButton = (RadioButton)findViewById(R.id.radio_outcome);
		edtFee    = (EditText)findViewById(R.id.edtFee);
		edtContent= (EditText)findViewById(R.id.edt_economy_content);
		mDpicker  = (DatePicker)findViewById(R.id.account_datepicker);
		mTpicker  = (TimePicker)findViewById(R.id.account_timepicker);
		mTpicker.setIs24HourView(true);
		getYearAndMonth();															//获取需要添加的年月
		mDpicker.init(curYear, curMonth-1, 1, null);								//注意日期控件的月份数量变化
		Calendar c = Calendar.getInstance();										//获取当前时间设置在timepicker里
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		mTpicker.setCurrentHour(hour);
		mTpicker.setCurrentMinute(minute);
		titleBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();				
			}			
		});
		tvTitle.setText(R.string.economy_add_title);
		titleCommit.setBackgroundResource(R.drawable.btn_ok2);				
		//设置收入下拉表的Adapter
		inAdapter = new ArrayAdapter<String>(Affair_Account_Add.this,android.R.layout.simple_spinner_item,INCOME_SORT);
		((ArrayAdapter<String>)inAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//设置支出下拉表的Adapter
		outAdapter = new ArrayAdapter<String>(Affair_Account_Add.this,android.R.layout.simple_spinner_item,OUTCOME_SORT);
		((ArrayAdapter<String>)outAdapter).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter((SpinnerAdapter) inAdapter);
		inRadioButton.setSelected(true);		
	}
	
	public void btnListener(){
		inRadioButton.setOnClickListener(new OnClickListener(){							//选择收入类型
			@Override
			public void onClick(View v) {
				mSpinner.setAdapter((SpinnerAdapter) inAdapter);	
				inRadioButton.setSelected(true);
				outRadioButton.setSelected(false);
			}			
		});
		
		outRadioButton.setOnClickListener(new OnClickListener(){						//选择支出类型
			@Override
			public void onClick(View v) {
				mSpinner.setAdapter((SpinnerAdapter) outAdapter);		
				inRadioButton.setSelected(false);
				outRadioButton.setSelected(true);
			}			
		});		
		final SQLiteDatabase sql = MyHelper_MainActivity.HelperSQLite.getWritableDatabase();
		final ContentValues cv = new ContentValues();
		
		final Builder build = new Builder(Affair_Account_Add.this);						//设置弹出对话框
		build
		.setTitle("操作提示")
		.setMessage("亲，是否确定提交数据？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				if(inRadioButton.isSelected()){
					cv.put("kind", "收入");												//获取记账类型
				}
				else{
					cv.put("kind", "支出");
				}
				String sort = (String)mSpinner.getSelectedItem();						//费用类型
				cv.put("sort", sort);
				double fee  = Double.parseDouble(edtFee.getText().toString());
				cv.put("fee", fee);
				
				String date_year = mDpicker.getYear()+"";								//获取日期
				String date_month = mDpicker.getMonth()+1+"";
				String date_day = mDpicker.getDayOfMonth()+"";
				String date = date_year+"-"+date_month+"-"+date_day;
				cv.put("year_int", mDpicker.getYear());
				cv.put("month_int", mDpicker.getMonth()+1);
				cv.put("date", date);
				
				String time_hour = mTpicker.getCurrentHour().toString();				//获取时间
				String time_minute = mTpicker.getCurrentMinute().toString();
				String time = time_hour+":"+time_minute;
				cv.put("time", time);
				
				String content = edtContent.getText().toString();						//获取内容
				cv.put("content", content);
				sql.insert(MainDatabase.ECONOMY_TABLE_NAME, null, cv);
				
				Intent intent = new Intent(Affair_Account_Add.this,Affair_Account_Amonth.class);	//返回
				intent.putExtra(Affair_Account.MONTH, mDpicker.getMonth()+1);						//返回月份设置标题
				intent.putExtra(Affair_Account.YEAR, mDpicker.getYear());							//返回年份
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}
		});
		build.create();
		
		titleCommit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				build.show();				
			}			
		});
		
	}
	
	public void getYearAndMonth(){																	//获取年月数值
		Intent intent = this.getIntent();
		curMonth = intent.getIntExtra(Affair_Account.MONTH, -1);
		curYear  = intent.getIntExtra(Affair_Account.YEAR, -1);
	}

}




















