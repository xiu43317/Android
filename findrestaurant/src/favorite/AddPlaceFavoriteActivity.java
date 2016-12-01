package favorite;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findrestaurant.MyApp;
import com.example.findrestaurant.R;
import com.example.findrestaurant.utility.MyPlace;
import com.example.findrestaurant.utility.SQLiteFavoriteHelper;

public class AddPlaceFavoriteActivity extends Activity {
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
	private boolean gettingLocation;
	private Integer restKey,idxCatagory,idxCity;
	private MyPlace place;

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
		place = (MyPlace)bundle.getSerializable("place");

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

		prepareCatagory();
		handler.sendEmptyMessage(1);
		prepareCity();
		handler.sendEmptyMessage(2);

		//modify must set data
		handler.sendEmptyMessage(5);

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
		dlat = Double.parseDouble(tvLatitude.getText().toString());
		dlng = Double.parseDouble(tvLongitude.getText().toString());
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
					adapter = new ArrayAdapter(AddPlaceFavoriteActivity.this,
												R.layout.spinner_item,listCatagory);
					spAddCatagory.setAdapter(adapter);
					break;
				case 2:		//setAdapter 4 spinner city
					adapter = new ArrayAdapter(AddPlaceFavoriteActivity.this,
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

	//show original data
	private void showDatas(){
		int idx = -1;
		String Address = place.formatted_address;
		String[] target = new String[]{"台北","台中","台南","台東"};
		String[] replacement = new String[]{"臺北","臺中","臺南","臺東"};
		for(int i=0;i<target.length;i++){
			Log.i("lucas", target[i].toString());
			Address = Address.replace(target[i], replacement[i]);
		}
		tvSave.setText(getString(R.string.Save));

		Log.i("lucas",place.formatted_address);
		idxCity = 0;
		for(String city : listCity){
			idx = Address.indexOf(city);
			if(idx >= 0){
				etAddAddress.setText(Address.substring(idx+city.length()));
				break;
			}
			idxCity++;
		}
		if(idxCity >= listCity.size()) idxCity = 0;
		spAddCity.setSelection(idxCity);

		etAddName.setText(place.name);
		etAddNote.setText(place.Note);
		etAddPhone.setText(place.formatted_phone_number);
		tvLatitude.setText(place.lat+"");
		tvLongitude.setText(place.lng+"");
	}

}
