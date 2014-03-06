package com.demo.health;

import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Health_Step_Set extends Activity {
	
	//默认值
	public static float STEP_LENGHT = 1.5f;
	public static float WEIGHT		= 65;
	public static float CALORIE		= 1000;
	public static int	SENSITY		= 5;
	public static int	MAX_SENSITY	= 10;
	public static String SELF_SET 	= "self_set";
	public static String IS_ALARM	= "is_alarm";
	
	public boolean		isAlarm		= false;
	
	//标题组件
	public TextView 	tvTitle;
	public ImageView	imgBack;
	public ImageView	imgSave;
	
	//设置组件
	public RadioButton	radioRun;
	public RadioButton	radioWalk;
	public SeekBar		seekSensity;
	public EditText		edtStepLenght;
	public EditText		edtWeight;
	public CheckBox		levelCheckBox;
	public EditText		edtCalorie;
	public Button		reBtn;
		
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.health_step_set_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title_layout);
		setTitle();
		initUI();
	}
	
	/*
	 * 设置标题栏
	 */
	public void setTitle(){
		tvTitle = (TextView)findViewById(R.id.title_name);
		tvTitle.setText(R.string.health_step_set);
		imgBack = (ImageView)findViewById(R.id.custom_title_rollback);
		imgSave = (ImageView)findViewById(R.id.custom_title_menu);
		imgSave.setBackgroundDrawable(getResources().getDrawable(R.drawable.saved));
		imgBack.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				finish();				
			}			
		});		
	}
	
	/*
	 * 初始化组件
	 */
	public void initUI(){
		radioRun		= (RadioButton)findViewById(R.id.health_set_walk_radio);		
		radioWalk		= (RadioButton)findViewById(R.id.health_set_run_radio);		
		seekSensity		= (SeekBar)findViewById(R.id.health_set_seekbar);
		edtStepLenght   = (EditText)findViewById(R.id.healthset_steplenght_edt);
		edtWeight       = (EditText)findViewById(R.id.healthset_stepweight_edt);
		levelCheckBox   = (CheckBox)findViewById(R.id.healthset_check);
		edtCalorie      = (EditText)findViewById(R.id.healthset_consume_edt);	
		reBtn			= (Button)findViewById(R.id.health_reset_btn);
		//适应屏幕
		int width = (int) (GlobalApp.getInstance().ScreenWidth*0.8);
		seekSensity.setLayoutParams(new LinearLayout.LayoutParams(width,LayoutParams.WRAP_CONTENT));
		seekSensity.setMax(10);
		//设置默认值
		setDefaultValue();		
		reBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				setDefaultValue();				
			}			
		});
		levelCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				isAlarm = arg1;	
				if(arg1)
					edtCalorie.setEnabled(true);									
				else
					edtCalorie.setEnabled(false);
			}			
		});
		//保存按钮
		imgSave.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Health_Step_Set.this,Health_Step.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra(SELF_SET, true);
				intent.putExtra(IS_ALARM, isAlarm);
				saveParams();
				startActivity(intent);
				Toast.makeText(Health_Step_Set.this, 
						getResources().getString(R.string.health_set_finish), 10000).show();
				finish();
			}			
		});
		
	}
	
	/*
	 * 保存参数到全局变量中
	 */
	public void saveParams(){
		GlobalApp.getInstance().Sensitivity = MAX_SENSITY-seekSensity.getProgress();
		String str = "";
		str = edtStepLenght.getText().toString();
		GlobalApp.getInstance().steplenght	= Float.parseFloat(str);
		str = edtCalorie.getText().toString();
		GlobalApp.getInstance().settingCalorie = Float.parseFloat(str);
	}
	
	/*
	 * 设置默认值函数
	 */
	public void setDefaultValue(){
		radioWalk.setChecked(true);
		edtStepLenght.setText(STEP_LENGHT+"");
		edtWeight.setText(WEIGHT+"");
		edtCalorie.setText(CALORIE+"");
		seekSensity.setProgress(SENSITY);
		levelCheckBox.setChecked(false);
		edtCalorie.setEnabled(false);		
	}

}























