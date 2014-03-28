package com.helper.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.demo.affair.Affair_Birthday;
import com.demo.myhelper.GlobalApp;
import com.demo.myhelper.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BirthListAdapter extends BaseAdapter {
	
	//限定倒数的日子
	public static int 		DAY_LEFT = 30;

	private Context context;
	private Cursor  cursor;
	
	public int year;
	public int month;
	public int day;
	public int offset_day;
	public ArrayList <BirthdayPersonModel> list;
	
	
	public BirthListAdapter(Context context,Cursor c){
		this.context = context;
		this.cursor  = c;
		Calendar calendar = Calendar.getInstance();
		year 	= calendar.get(Calendar.YEAR);
		month	= calendar.get(Calendar.MONTH);
		day		= calendar.get(calendar.DAY_OF_MONTH);
		offset_day = calendar.get(Calendar.DAY_OF_YEAR);
		
		if(GlobalApp.getInstance().BirhPeopleList==null){
			makeItem(c);
		}
		else
			list = GlobalApp.getInstance().BirhPeopleList;
		
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view	= inflater.inflate(R.layout.birth_list_item, null);
		
		TextView name = (TextView)view.findViewById(R.id.birth_name_tv);
		TextView date = (TextView)view.findViewById(R.id.birth_time_tv);
		TextView offset = (TextView)view.findViewById(R.id.birth_timeup_tv);
		ImageView	sexid = (ImageView)view.findViewById(R.id.birth_sex_img);	
		ImageView	sexmark = (ImageView)view.findViewById(R.id.birth_mark_img);
		
		BirthdayPersonModel item = list.get(position);				
		name.setText(item.name);
		
		if(item.getOffsetdays()!=0){
			offset.setText("离生日还有"+item.offsetdays+"");			
		}
			
		else{
			int year_old = year - item.year;
			offset.setText("今天"+year_old+"周岁生日啦，快点去帮Ta庆祝吧");
			view.setBackgroundColor(context.getResources().getColor(R.color.tran_red));
		}
			
		
		date.setText(item.year+"-"+(item.month+1)+"-"+item.day);
		
		if(item.sexid==0)
			sexid.setBackgroundResource(R.drawable.male);
		else
			sexid.setBackgroundResource(R.drawable.female);
		
		if(item.markid==0)
			sexmark.setBackgroundResource(R.drawable.male_mark);
		else
			sexmark.setBackgroundResource(R.drawable.female_mark);
		
		view.setTag(item);
		
		return view;
	}
	
	
	//构造对象
	public void makeItem(Cursor c){
		GlobalApp.getInstance().BirhPeopleList = new ArrayList<BirthdayPersonModel>();
		for(int i=0;i<c.getCount();i++){
			int y = c.getInt(5);
			int m = c.getInt(6);
			int d = c.getInt(7);
			String name = c.getString(3);
			BirthdayPersonModel person = new BirthdayPersonModel(y,m,d,name);
			int sex  = c.getInt(4);
			int mark = c.getInt(1);
			int re   = c.getInt(2);
			int con  = c.getInt(8);
			String f    = c.getString(9);
			int sen     = c.getInt(10);
			String wish = c.getString(11);	
			
			//计算当前距离生日还有多少天
			Calendar cal = Calendar.getInstance();
			cal.set(y,m,d);
			int birthOffset = cal.get(Calendar.DAY_OF_YEAR);
			int daysBetween = birthOffset-offset_day;
			
			if(daysBetween<0)
				daysBetween+=365;
									
			person.setDetail(sex, mark, daysBetween);
			person.setDetail2(re, con, f, sen, wish);
			
			GlobalApp.getInstance().BirhPeopleList.add(person);
			c.moveToNext();
		}
		list = GlobalApp.getInstance().BirhPeopleList;
	}
	
	
	
	
	public int isComingSoon(Cursor c){
		int y = c.getInt(5);
		int m = c.getInt(6);
		int d = c.getInt(7);
		Calendar cal = Calendar.getInstance();
		cal.set(y,m,d);
		int birthOffset = cal.get(Calendar.DAY_OF_YEAR);
		int daysBetween = birthOffset-offset_day;
		return daysBetween;
			
	}
	
	
	//人类对象	
	public static class BirthdayPersonModel{
		
		public String 	name;
		public int	   	sexid;
		public int    	markid;
		public int		year;
		public int		month;
		public int 		day;
		public int		offsetdays;
		public int		relation;
		public int		constellation;
		public String  favourite;
		public int		sending;
		public String	wishtext;
				
		public BirthdayPersonModel(int y,int m,int d,String name){
			this.year  	= y;
			this.month	= m;
			this.day	= d;
			this.name	= name;			
		}
		
		public void setDetail(int sexid,int markid,int offsetdays){
			this.sexid  	= sexid;
			this.markid 	= markid;
			this.offsetdays = offsetdays;
		}
		
		public void setDetail2(int r,int c,String f,int s,String w){
			this.relation = r;
			this.constellation = c;
			this.favourite = f;
			this.sending = s;
			this.wishtext = w;
		}
		
		//计算还剩几天生日
		public int getOffsetdays(){
			Calendar c = Calendar.getInstance();
			int todayoff = c.get(Calendar.DAY_OF_YEAR);
			c.set(Calendar.MONTH, month);
			c.set(Calendar.DAY_OF_MONTH, day);
			int birthoff = c.get(Calendar.DAY_OF_YEAR);
			if(todayoff<=birthoff)
				return birthoff-todayoff;
			else
				return (365-(todayoff-birthoff));
		}
		
	}

}



















