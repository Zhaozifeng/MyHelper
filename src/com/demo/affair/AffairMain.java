package com.demo.affair;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.myhelper.R;

public class AffairMain extends Activity {
	
	private GridView   			mGridView;
	private ArrayList<View> 	mItemList;
	private int 				ItemCount = 7;
	
	//定义图标和文字
	public  int     ItemImageIds[] = {R.drawable.note,R.drawable.diary,R.drawable.account,
			R.drawable.birthday,R.drawable.holiday,R.drawable.sceen_mode,R.drawable.flashligth};
	public  int     ItemsNameIds[] = {R.string.note,R.string.diary,R.string.account,
			R.string.birthday,R.string.festival,R.string.mode,R.string.flahlight};
	
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_affair_main);		
		initialList();
		initialGrid();		
	}
		
	void initialList(){																  //构建每个子项		
				
		mItemList = new ArrayList<View>();
		for(int i=0;i<ItemCount;i++){
			LinearLayout ll   = new LinearLayout(this);
			ll.setGravity(Gravity.CENTER_HORIZONTAL);
			ll.setOrientation(LinearLayout.VERTICAL);
			ImageView    img  = new ImageView(this);
			TextView     tv   = new TextView(this);
			img.setLayoutParams(new LayoutParams(150,150));
			img.setBackgroundResource(ItemImageIds[i]);
			ll.addView(img);
			tv.setText(ItemsNameIds[i]);
			tv.setGravity(Gravity.CENTER_HORIZONTAL);
			ll.addView(tv);
			mItemList.add(ll);
		}
	}
	
	//定义入港额Intent,构建Grid
	void initialGrid(){																 
		mGridView = (GridView)findViewById(R.id.gv_affair_main);
		mGridView.setAdapter(new MyAdapter(mItemList,AffairMain.this));			
		final Intent mIntents [] = {new Intent(AffairMain.this,Affair_Note_List.class),
									new Intent(AffairMain.this,Affair_Diary.class),
									new Intent(AffairMain.this,Affair_Account.class),
									new Intent(AffairMain.this,Affair_Birthday.class),
									new Intent(AffairMain.this,Affair_Festival.class),
									new Intent(AffairMain.this,Affair_Mode.class),
									new Intent(AffairMain.this,Affair_Flashlight.class)	
									}; 
		
		mGridView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				startActivity(mIntents[position]);
			}
		});
	}
	
	
	private class MyAdapter extends BaseAdapter{									 //GridView适配器
		
		private Context		      mContext;
		private ArrayList<View>   mItemList;		
		
		public MyAdapter(ArrayList<View> mItemList,Context mContext){
			this.mContext  = mContext;
			this.mItemList = mItemList;
		}

		@Override
		public int getCount() {
			return mItemList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return (View)mItemList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return (View)mItemList.get(position);
		}		
	}

}
