package com.example.findrestaurant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findrestaurant.utility.MyPlace;
import com.example.findrestaurant.utility.SQLiteFavoriteHelper;

import favorite.AddPlaceFavoriteActivity;
import favorite.FavoriteModifyActivity;


public class SinglePlaceActivitySql extends Activity {
	private TextView tvName,tvPhone,tvAddress,tvNote,tvLocation;
	private Button btnSearch;
	private int restKey;
	private SQLiteFavoriteHelper dbHelper;
	private SQLiteDatabase db;
	private MyHandler handler;

	private String strRestName,strRestPhone,strRestAddress,strRestMemo;
	private Double dLat,dLng;
	
	private MyPlace place;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place);

		Intent it = getIntent();
		Bundle bundle = it.getExtras();
		handler = new MyHandler();
		restKey = bundle.getInt("key");
		Log.i("lucas","key/"+restKey);
		dbHelper = new SQLiteFavoriteHelper(this, "findrestaurant", null, 1);
		db = dbHelper.getReadableDatabase();

		tvName = (TextView)findViewById(R.id.tvName);
		tvPhone = (TextView)findViewById(R.id.tvPhone);
		tvAddress = (TextView)findViewById(R.id.tvAddress);
		tvNote = (TextView)findViewById(R.id.tvNote);
		btnSearch = (Button)findViewById(R.id.search);

		preparePage(restKey);
	}

	private void preparePage(int key){
		Cursor c = db.query("userfavorite",
							new String[]{"uf_name","uf_phone","uf_city","uf_address","uf_note",
										 "uf_latitude","uf_longitude","uf_placeid"},
							"uf_key = ?", new String[]{key+""}, "", "", "");
		place = new MyPlace();
		while(c.moveToNext()){
			Log.i("lucas",c.getString(c.getColumnIndex("uf_name")));
			place.id = c.getString(c.getColumnIndex("uf_placeid"));
			place.name = c.getString(c.getColumnIndex("uf_name"));
			place.formatted_phone_number = c.getString(c.getColumnIndex("uf_phone"));
			place.formatted_address = c.getString(c.getColumnIndex("uf_address"));
			place.Note = c.getString(c.getColumnIndex("uf_note"));
			place.lat = c.getDouble(c.getColumnIndex("uf_latitude"));
			place.lng = c.getDouble(c.getColumnIndex("uf_longitude"));
		}
		handler.sendEmptyMessage(0);
	}

	public void addDatabase(View v){
		Intent it = new Intent(this,FavoriteModifyActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("key", restKey);
		it.putExtras(bundle);
		startActivity(it);
	}

	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case 0:
				try{
				tvName.setText(place.name);
				tvPhone.setText(place.formatted_phone_number);
				tvAddress.setText(place.formatted_address);
				tvNote.setText(place.Note);
				tvLocation.setText(place.lat + "/" +place.lng);
				}catch (Exception e) {
				}
				break;
			default:
				break;
			}
		}
	}
}
