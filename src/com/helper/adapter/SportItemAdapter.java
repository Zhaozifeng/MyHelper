package com.helper.adapter;

import java.util.ArrayList;

import com.demo.myhelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SportItemAdapter extends BaseAdapter {

	public Context mcontext;
	public ArrayList<SportItemModel> list;
	public float sumTotal = 0.0f; 
	
	
	
	public SportItemAdapter(Context c, ArrayList<SportItemModel> list){
		this.mcontext = c;
		this.list = list;
	}
	
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(mcontext);
		View view = inflater.inflate(R.layout.sport_list_item, null);
		TextView nameTv = (TextView)view.findViewById(R.id.sport_name_tv);
		TextView energyTv = (TextView)view.findViewById(R.id.sport_energy_tv);
		
		SportItemModel item =  list.get(position);
		nameTv.setText(item.name);
		energyTv.setText("能量 :"+item.energy+"千卡");
		sumTotal+=item.energy;
		view.setTag(item);
		
		return view;
	}
	
	
	
	public static class SportItemModel{
		
		public String name;
		public float  energy;
		public int    minute;
		
		public SportItemModel(String n,float e,int m){
			this.name = n;
			this.energy = e;
			this.minute = m;
		}	
	}
}

















