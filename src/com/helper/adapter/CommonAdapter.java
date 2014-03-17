package com.helper.adapter;

import com.demo.affair.Affair_Note_List;
import com.demo.myhelper.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonAdapter extends BaseAdapter{

	public String[]	list;
	public Context	context;
	
	public CommonAdapter(String[] list,Context context){
		this.list 		= list;
		this.context	= context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout l = new LinearLayout(context);
		l.setGravity(Gravity.CENTER);
		TextView tv = new TextView(context);
		tv.setTextSize(20);
		tv.setTextColor
		(context.getResources().getColor(R.color.white));
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 100));
		tv.setText(list[position]);
		l.addView(tv);
		return l;
	}

}
