package com.example.findrestaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchOpionsActivity extends Activity {
	private Button tvLocation,tvOption,tvSearch;
	private EditText etOption;
	private MyApp myApp;
	private MyHandler handler;
	private Dialog dialog;
	private StringBuffer selects;
	private ArrayList<Map<String,String>> areaDatas;
	private Geocoder geo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_opions);
		
		myApp = (MyApp)getApplication();
		handler = new MyHandler();
		areaDatas = myApp.getAreaDatas();
		geo = new Geocoder(this, Locale.TAIWAN);
//		tvLocation = (TextView)findViewById(R.id.tvLocation);
//		tvOption = (TextView)findViewById(R.id.tvOpt);
//		tvSearch = (TextView)findViewById(R.id.tvOptSearch);
//		tvLocation = (Button)findViewById(R.id.tvLocation);
		tvOption = (Button)findViewById(R.id.tvOpt);
		tvSearch = (Button)findViewById(R.id.tvOptSearch);
		etOption = (EditText)findViewById(R.id.etOpt);

		Log.i("lucas", "test");
//
//		tvLocation.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});

		tvOption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("lucas","onclick");
				getCity();
			}
		});
//
//		tvSearch.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//	    		try{
//	    			Log.i("lucas", "test1");
//	    			List<Address> list =
//	    					geo.getFromLocationName(etOption.getText().toString(), 1);
//	    			
//	    			if(list == null || list.size()==0){
//	    				Toast.makeText(SearchOpionsActivity.this,
//	    								"沒有查到任何經緯度", Toast.LENGTH_SHORT).show();
//	    			}else{
//	    				Address addr = list.get(0);   				
////	    				String str = String.format("緯度:%f\n經度:%f", 
////	    						addr.getLatitude(),
////	    						addr.getLongitude());
////	    				tv.setText(str);
//	    				Intent intent = new Intent();
//	    	            intent.setClass(SearchOpionsActivity.this, Customize.class);
//
//	    	            Log.i("lucas", "test2");
//	    	            //new一個Bundle物件，並將要傳遞的資料傳入
//	    	            Bundle bundle = new Bundle();
//	    	            bundle.putDouble("Latitude",addr.getLatitude());
//	    	            bundle.putDouble("Longitude",addr.getLongitude());
//
//	    	            //將Bundle物件assign給intent
//	    	            intent.putExtras(bundle);
//
//	    	            Log.i("lucas","test3");
//	    	            //切換Activity
//	    	            startActivity(intent);
//	    			}
//	    		}catch (Exception e) {
//	    			Log.i("lucas","error");
//				}
//			}
//		});
	}

//	public void selOption(View v){
//		Log.i("lucas","onclick");
//		getCity();
//	}
//
//	public void getLocation(View v){
//		
//	}

	public void submitSearch(View v){
		try{
			Log.i("lucas", "test1");
			List<Address> list =
					geo.getFromLocationName(etOption.getText().toString(), 1);
			
			if(list == null || list.size()==0){
				Toast.makeText(SearchOpionsActivity.this,
								"沒有查到任何經緯度", Toast.LENGTH_SHORT).show();
			}else{
				Address addr = list.get(0);   				
//				String str = String.format("緯度:%f\n經度:%f", 
//						addr.getLatitude(),
//						addr.getLongitude());
//				tv.setText(str);
				Intent intent = new Intent();
	            intent.setClass(this, Customize.class);

	            Log.i("lucas", "test2");
	            //new一個Bundle物件，並將要傳遞的資料傳入
	            Bundle bundle = new Bundle();
	            bundle.putDouble("Latitude",addr.getLatitude());
	            bundle.putDouble("Longitude",addr.getLongitude());

	            //將Bundle物件assign給intent
	            intent.putExtras(bundle);

	            Log.i("lucas","test3");
	            //切換Activity
	            startActivity(intent);
			}
		}catch (Exception e) {
			Log.i("lucas",e.toString());
		}
	}

	private void getCity(){
		selects = new StringBuffer();
		HashMap<String,String> datamap;
		LinkedHashSet<HashMap<String,String>> setCities;

		setCities = new LinkedHashSet<HashMap<String,String>>();
		for(Map<String,String> map:areaDatas){
			datamap = new HashMap<String, String>();
			datamap.put("data1", map.get("city"));
			setCities.add(datamap);
		}

		final ArrayList<HashMap<String,String>> listData =
				new ArrayList<HashMap<String,String>>(setCities);

		dialog = new Dialog(this);
		dialog.setTitle(R.string.city);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dlglist);
		dialog.show();
		
		ListView lvList = (ListView)dialog.findViewById(R.id.lvDlg);
		String from[] = new String[]{"data1"};
		int to[] = new int[]{R.id.dlglistitem1};
		SimpleAdapter adapter = new SimpleAdapter(this,
													listData,
													R.layout.dlglistitems,
													from,to);
		lvList.setAdapter(adapter);

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.dismiss();
				String city = listData.get(arg2).get("data1");
				selects.append(city);
				getDist(city);
			}
		});
	}

	public void getDist(final String City){
		Log.i("lucas",City);
		if(City.equals("")) return;

		HashMap<String,String> datamap;
		LinkedHashSet<HashMap<String,String>> setDists;

		setDists = new LinkedHashSet<HashMap<String,String>>();
		datamap = new HashMap<String, String>();
		datamap.put("data1", "");
		setDists.add(datamap);
		for(Map<String,String> map:areaDatas){
			if(map.get("city").equals(City)){
				datamap = new HashMap<String, String>();
				datamap.put("data1", map.get("dist"));
				setDists.add(datamap);
			}
		}

		final ArrayList<HashMap<String,String>> listData =
				new ArrayList<HashMap<String,String>>(setDists);

		dialog = new Dialog(this);
		dialog.setTitle(R.string.dist);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dlglist);
		dialog.show();
		
		ListView lvList = (ListView)dialog.findViewById(R.id.lvDlg);
		String from[] = new String[]{"data1"}; 
		int to[] = new int[]{R.id.dlglistitem1};
		SimpleAdapter adapter = new SimpleAdapter(this,
													listData,
													R.layout.dlglistitems,
													from,to);
		lvList.setAdapter(adapter);

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.dismiss();
				String dist = listData.get(arg2).get("data1");
				selects.append(dist);
				getRoad(City,dist);
			}

		});
	}

	public void getRoad(final String City,final String Dist){
		Log.i("lucas",City);
		if (City.equals("")) return;

		HashMap<String,String> datamap;
		LinkedHashSet<HashMap<String,String>> setRoads;

		setRoads = new LinkedHashSet<HashMap<String,String>>();
		datamap = new HashMap<String, String>();
		datamap.put("data1", "");
		setRoads.add(datamap);

		for(Map<String,String> map:areaDatas){
			if(map.get("city").equals(City) &&
					(Dist.equals("") || map.get("dist").equals(Dist))){
				datamap = new HashMap<String, String>();
				datamap.put("data1", map.get("road"));
				setRoads.add(datamap);
			}
		}

		final ArrayList<HashMap<String,String>> listData =
				new ArrayList<HashMap<String,String>>(setRoads);

		dialog = new Dialog(this);
		dialog.setTitle(R.string.road);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dlglist);
		dialog.show();
		
		ListView lvList = (ListView)dialog.findViewById(R.id.lvDlg);
		String from[] = new String[]{"data1"};
		int to[] = new int[]{R.id.dlglistitem1};
		SimpleAdapter adapter = new SimpleAdapter(this,
													listData,
													R.layout.dlglistitems,
													from,to);
		lvList.setAdapter(adapter);

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.dismiss();
				String road = listData.get(arg2).get("data1");
				selects.append(road);
				handler.sendEmptyMessage(0);
			}

		});
	}

	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				etOption.setText(selects.toString());
				break;

			default:
				break;
			}
		}
	}
}
