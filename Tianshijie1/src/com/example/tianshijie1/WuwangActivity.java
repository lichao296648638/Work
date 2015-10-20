package com.example.tianshijie1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class WuwangActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_wuwang);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wuwang, menu);
		return true;
	}

}
