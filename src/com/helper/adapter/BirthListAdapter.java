package com.helper.adapter;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BirthListAdapter extends BaseAdapter {

	private Context context;
	
	public BirthListAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view	= inflater.inflate(R.layout.birth_list_item, null);
		return view;
	}

}
