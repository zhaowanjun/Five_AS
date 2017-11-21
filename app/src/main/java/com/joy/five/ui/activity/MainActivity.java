package com.joy.five.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.joy.five.R;
import com.joy.five.utils.WuziqiUtil;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button rr = (Button) findViewById(R.id.rr);
		Button rj = (Button) findViewById(R.id.rj);
		Button help = (Button) findViewById(R.id.help);
		Button about = (Button) findViewById(R.id.about);
		Button exit = (Button) findViewById(R.id.exit);

		rr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RRMenuActivity.class);
				WuziqiUtil.whiteArray.clear();
				WuziqiUtil.blackArray.clear();
				MainActivity.this.startActivity(intent);
			}
		});

		rj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, RJMenuActivity.class);
				WuziqiUtil.whiteArray.clear();
				WuziqiUtil.blackArray.clear();
				MainActivity.this.startActivity(intent);
			}
		});

		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, HelpActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});

		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, AboutActivity.class);
					MainActivity.this.startActivity(intent);
			}
		});

		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
