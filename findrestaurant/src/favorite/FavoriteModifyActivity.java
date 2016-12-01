package favorite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findrestaurant.GPSTracker;
import com.example.findrestaurant.MyApp;
import com.example.findrestaurant.R;
import com.example.findrestaurant.R.id;
import com.example.findrestaurant.R.layout;
import com.example.findrestaurant.R.string;
import com.example.findrestaurant.utility.GPSservice;
import com.example.findrestaurant.utility.SQLiteFavoriteHelper;

public class FavoriteModifyActivity extends Activity {
	private double latitude,longitude;
	private Spinner spAddCatagory,spAddCity;
	private EditText etAddCatagory,etAddName,etAddPhone,etAddAddress,
					 etAddNote;
	private TextView tvLatitude,tvLongitude,
					 tvSave,tvLocation;
	private MyApp myApp;
	private myHandler handler;
	private SQLiteFavoriteHelper dbHelper;
	private SQLiteDatabase db;
	
	private String strCatagory,strCity,strAddress;
	private String strRestName,strRestPhone,strRestNote;	//4 modify
	private ArrayList<String> listCatagory,listCity;
	private ArrayAdapter adapter;

	private double dlat,dlng;
	private MyReceiver receiver;
	private boolean gettingLocation;
	private Integer restKey,idxCatagory,idxCity;
	
	private Map<String,String> originData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_modify);
		myApp = (MyApp) getApplication();
		handler = new myHandler();

		dbHelper = new SQLiteFavoriteHelper(this, "findrestaurant", null, 1);	//version must bigger than 1
		db= dbHelper.getWritableDatabase();

		Intent it = getIntent();
		Bundle bundle = it.getExtras();
		if(bundle != null){
			restKey = bundle.getInt("key");
			if(restKey != null){
				preparePage(restKey);
			}
		}

		//spinner
		spAddCatagory = (Spinner)findViewById(R.id.spUserAddCatagory);
		spAddCity = (Spinner)findViewById(R.id.spUserAddCity);

		spAddCity.setVerticalFadingEdgeEnabled(true);
		spAddCity.setFadingEdgeLength(20);

		//EditText
		etAddCatagory = (EditText)findViewById(R.id.etUserAddCatagory);
		etAddName = (EditText)findViewById(R.id.etUserAddName);
		etAddPhone = (EditText)findViewById(R.id.etUserAddPhone);
		etAddAddress = (EditText)findViewById(R.id.etUserAddAddress);
		etAddNote = (EditText)findViewById(R.id.etUserAddNote);

		tvLatitude = (TextView)findViewById(R.id.tvUserAddLatitude);
		tvLongitude = (TextView)findViewById(R.id.tvUserAddLongitude);
		tvSave = (TextView)findViewById(R.id.tv_add_save);
		tvLocation = (TextView)findViewById(R.id.tv_add_Location);

		receiver = new MyReceiver();
		gettingLocation = false;

		prepareCatagory();
		handler.sendEmptyMessage(1);
		prepareCity();
		handler.sendEmptyMessage(2);

		//modify must set data
//		preparePage(restKey);
		if(restKey != null){
			handler.sendEmptyMessage(5);
		}

		spAddCatagory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				strCatagory = listCatagory.get(arg2);
				handler.sendEmptyMessage(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		spAddCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				strCity = listCity.get(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	@Override
	protected void onDestroy() {
		if(gettingLocation){
			gettingLocation = false;
			Intent it = new Intent(this,GPSservice.class);
			stopService(it);
			unregisterReceiver(receiver);
		}
		super.onDestroy();
	}

	private void prepareCatagory(){
		Cursor c = db.query(true, "userfavorite", new String[]{"uf_catagory"},
							"", null, "", "", "","");
		listCatagory = new ArrayList<String>();

		listCatagory.add(getString(R.string.NewCatagory));
		while(c.moveToNext()){
			listCatagory.add(c.getString(0));
		}
	}

	private void prepareCity(){
//		listCity = new ArrayList<String>();
		Set<String> setCity = new LinkedHashSet<String>();
		for(Map<String,String> map : myApp.getAreaDatas()){
			setCity.add(map.get("city"));
		}
		
		listCity = new ArrayList<String>(setCity);
	}

	public void saveData(View v){
		long result = 0;
		Log.i("lucas","sava");
		ContentValues values = new ContentValues();
		String restName = etAddName.getText().toString().trim(),
		       restPhone = etAddPhone.getText().toString().trim(),
		       restAddress = etAddAddress.getText().toString().trim(),
		       restNote = etAddNote.getText().toString().trim(),
		       restCatagory = strCatagory;
		//check
		//check catagory
		if(strCatagory.equals(getString(R.string.NewCatagory))){
			if(etAddCatagory.getText().toString().trim().isEmpty()){
				Toast.makeText(this, getString(R.string.CatagoryRequired),
								Toast.LENGTH_SHORT).show();
				return;
			}else{
				restCatagory = etAddCatagory.getText().toString().trim();
			}
		}
		
		//check name
		if(restName.trim().isEmpty()){
			Toast.makeText(this, getString(R.string.NameRequired), Toast.LENGTH_SHORT).show();
			return;
		}

		values.put("uf_catagory", restCatagory);
		values.put("uf_name", restName);
		values.put("uf_phone", restPhone);
		values.put("uf_city", strCity);
		values.put("uf_address", restAddress);
		values.put("uf_note", restNote);
		values.put("uf_latitude", dlat);
		values.put("uf_longitude", dlng);

		if(restKey == null){
			if((result = db.insert("userfavorite", null, values))<0){
				Toast.makeText(this, getString(R.string.AddFailed),
								Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, getString(R.string.AddSuccess),
								Toast.LENGTH_SHORT).show();
			}
		}else{
			if((result = db.update("userfavorite", values, "uf_key = ?", new String[]{restKey+""}))>0){
				Toast.makeText(this, getString(R.string.UpdateSuccess),
								Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, getString(R.string.UpdateFailed),
								Toast.LENGTH_SHORT).show();
			}
		}
		if (result>0) finish();
	}

	@Override
	public void finish() {

		if(gettingLocation){
			gettingLocation = false;
			Intent it = new Intent(this,GPSservice.class);
			stopService(it);
			unregisterReceiver(receiver);
		}
		super.onDestroy();
		super.finish();
	}

	public void getLocation(View v){
		Intent it;
		Log.i("lucas", "getLocation");
		if(gettingLocation){
			gettingLocation = false;
			it = new Intent(this,GPSTracker.class);
			stopService(it);
			Toast.makeText(this, "關閉GPS", Toast.LENGTH_SHORT).show();
			unregisterReceiver(receiver);
			return;
		}else{
			gettingLocation = true;
			it = new Intent(this,GPSTracker.class);
			startService(it);
			IntentFilter itFilter = new IntentFilter(Settings.ACTION_LOCATION_SOURCE_SETTINGS);	//listen broadcast
			registerReceiver(receiver, itFilter);
			Toast.makeText(this, getString(R.string.OpenGPS), Toast.LENGTH_SHORT).show();
		}
	}

	//http://developer.android.com/reference/android/location/Geocoder.html
	//http://syuandroid.blogspot.tw/2012/11/geocoder.html
	private void locationToAddress(){
		StringBuffer sb = new StringBuffer();
		Message msg = new Message();
//		Geocoder gc = new Geocoder(this, Locale.TAIWAN);
		Geocoder gc = new Geocoder(this, Locale.TRADITIONAL_CHINESE);
		try {
			List<Address> listAddress = gc.getFromLocation(dlat, dlng, 1);
			if(!(listAddress.isEmpty() || listAddress == null)){
//				int iCount = listAddress.get(0).getMaxAddressLineIndex();	//in google api, says getMaxAddressLineIndex can get max index, but not work
//				for(int i = 0;i<iCount;i++){
				for(int i = 0;listAddress.get(0).getAddressLine(i)!=null;i++){
					sb.append(i);
					sb.append(listAddress.get(0).getAddressLine(i));
					sb.append("/");
				}
				strAddress = sb.toString();
				handler.sendEmptyMessage(3);
			}else{
				Toast.makeText(this, getString(R.string.LocatingFailed),
								Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	private class myHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch(msg.what){
				case 0:		//visibility 4 TextEdit catagory
					if(strCatagory.equals(getString(R.string.NewCatagory))){
						etAddCatagory.setVisibility(0);
					}else{
						etAddCatagory.setVisibility(8);
					}
					break;
				case 1:		//setAdapter 4 spinner catagory
					adapter = new ArrayAdapter(FavoriteModifyActivity.this,
												R.layout.spinner_item,listCatagory);
					spAddCatagory.setAdapter(adapter);
					break;
				case 2:		//setAdapter 4 spinner city
					adapter = new ArrayAdapter(FavoriteModifyActivity.this,
												R.layout.spinner_item,listCity);
					spAddCity.setAdapter(adapter);
					break;
				case 3:
					etAddAddress.setText(strAddress);
					break;
				case 4:
					tvLatitude.setText(Double.toString(dlat));
					tvLongitude.setText(Double.toString(dlng));
					break;
				case 5:		//for modify default massage
					showDatas();
					break;
			}
		}
	}
	
	private class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			dlat = intent.getDoubleExtra("latitude", 0);
			dlng = intent.getDoubleExtra("longitude", 0);
			Log.i("lucas",dlat + "/" + dlng);
			handler.sendEmptyMessage(4);
			locationToAddress();
		}
		
	}

	// for modify
	//read from data
	private void preparePage(Integer key){
		Log.i("lucas","in preparing data for:"+key);
		Cursor c = db.query("userfavorite",
							new String[]{"uf_catagory","uf_name","uf_phone",
										 "uf_city","uf_address","uf_note"},
							"uf_key = ?", new String[]{key+""}, "", "", "");
		originData = new HashMap<String,String>();
		while(c.moveToNext()){
			originData.put("catagory", c.getString(c.getColumnIndex("uf_catagory")));
			originData.put("name", c.getString(c.getColumnIndex("uf_name")));
			originData.put("city", c.getString(c.getColumnIndex("uf_city")));
			originData.put("phone", c.getString(c.getColumnIndex("uf_phone")));
			originData.put("address", c.getString(c.getColumnIndex("uf_address")));
			originData.put("note", c.getString(c.getColumnIndex("uf_note")));
		}
	}

	//show original data
	private void showDatas(){
		Log.i("lucas","new datas");
		tvSave.setText(getString(R.string.Update));
		String originalCatagory	= originData.get("catagory");
		String originalCity		= originData.get("city");
		etAddName.setText(originData.get("name"));
		etAddAddress.setText(originData.get("address"));
		etAddNote.setText(originData.get("note"));
		etAddPhone.setText(originData.get("phone"));

		idxCatagory = 0;
		for(String catagory : listCatagory){
			if(catagory.equals(originalCatagory)){
				break;
			}
			idxCatagory++;
		}
		if(idxCatagory >= listCatagory.size()) idxCatagory = 0;
		spAddCatagory.setSelection(idxCatagory);

		idxCity = 0;
		for(String city : listCity){
			if(city.equals(originalCity)){
				break;
			}
			idxCity++;
		}
		if(idxCity >= listCity.size()) idxCity = 0;
		spAddCity.setSelection(idxCity);
	}
}
