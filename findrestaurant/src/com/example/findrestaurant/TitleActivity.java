package com.example.findrestaurant;

import com.example.findrestaurant.utility.CsvProcess;
import com.example.findrestaurant.utility.SQLiteFavoriteHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.RelativeLayout;

public class TitleActivity extends Activity {
	private MyApp myApp;
	private CsvProcess csvProcess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);

		myApp = (MyApp)getApplication();
		new Thread(new Runnable() {
			public void run() {
				myApp.insertAreaDatas(new CsvProcess(TitleActivity.this).csvToArrayList());
				Intent it = new Intent(TitleActivity.this, MainActivity.class);
				startActivity(it);
				finish();
			}
		}).start();
	}
}
