package com.helper.birthday;

import com.demo.myhelper.R;
import com.demo.tools.Utools;
import com.helper.birthday.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Constellation extends Activity {
	
	public int			constellationId;
	public String		title;
	public TextView		titleTv;
	public ImageView	conImg;
	public TextView		consTv;
	
	//图片资源id
	public int[] 	ImageId = {R.drawable.shuangyu,R.drawable.baiyang,
								R.drawable.jinniu,R.drawable.shuangzi,
								R.drawable.juxie,R.drawable.shizi,
								R.drawable.chunv,R.drawable.tiancheng
								,R.drawable.tianxie,R.drawable.sheshou
								,R.drawable.moxie,R.drawable.shuiping};
	//文字资源
	public	ConsStory	STORY;
	

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);	
		setContentView(R.layout.constellation_layout);
		initUI();
	}
	
	public void initUI(){
		Intent intent 	= getIntent();
		constellationId	=	intent.getIntExtra(BirthAdd.CONSTELLATION, -1);
		title			=	BirthAdd.STARS[constellationId];	
		Utools.customTitle(Constellation.this, title);
		ImageView menu  = (ImageView)findViewById(R.id.custom_title_menu);
		menu.setVisibility(View.INVISIBLE);
		conImg			= (ImageView)findViewById(R.id.constellation_img);
		conImg.setBackgroundResource(ImageId[constellationId]);
		consTv			= (TextView)findViewById(R.id.constellation_tv);
		consTv.setText(STORY.STAR[constellationId]);
	}
	
}




















