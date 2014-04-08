package com.demo.health;
import java.util.ArrayList;
import java.util.List;
import com.demo.myhelper.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HealthMain extends Activity {
		
	private GridView 		mGridView;
	private int 	 		number;
	private List<View> 		mList;
	
	
	
	//健康伴侣图片ID
	public int 	  imgIDs [] 	= {R.drawable.foot,R.drawable.weather,
									R.drawable.addsport,R.drawable.histroy,
									R.drawable.bmi}; 
	public int 	  nameIDs[] 	= {R.string.health_main_step,R.string.health_weather_icon,
									R.string.sport_add,R.string.sport_histroy,R.string.sport_bmi};
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		
		setContentView(R.layout.activity_health_main);
		initUI();
		setListened();
	}

	
	/**
	 * 实例化控件，并初始化界面
	 */
	public  void initUI(){ 
		mGridView = (GridView)findViewById(R.id.gv_health_main);
		mList = new ArrayList<View>();
		for(int i=0;i<imgIDs.length;i++){
			LinearLayout ll   = new LinearLayout(this);
			ll.setGravity(Gravity.CENTER_HORIZONTAL);
			ll.setOrientation(LinearLayout.VERTICAL);
			ImageView    img  = new ImageView(this);
			TextView     tv   = new TextView(this);
			img.setLayoutParams(new LayoutParams(150,150));
			img.setBackgroundResource(imgIDs[i]);
			ll.addView(img);
			tv.setText(nameIDs[i]);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			ll.addView(tv);
			mList.add(ll);
		}
		mGridView.setAdapter(new GridAdapter(mList));
	}
	
	/**
	 *设置监听函数 
	 */
	public void setListened(){
		final Intent intents[] = {
				new Intent(HealthMain.this,Health_Step.class),
				new Intent(HealthMain.this,Health_Weather.class),
				new Intent(HealthMain.this,SportAdd.class),
				new Intent(HealthMain.this,SportHistroy.class),
				new Intent(HealthMain.this,SportBMI.class)
				};		
		mGridView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startActivity(intents[arg2]);				
			}		
		});
	}
	
	
	/**
	 * Adapter
	 */	
	private class GridAdapter extends BaseAdapter{

		private List<View> list;
		
		public GridAdapter(List<View> mlist){
			this.list = mlist;
		}
		
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			return list.get(arg0);
		}
		
	}
	
}














