package com.example.findrestaurant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findrestaurant.R;
import com.example.findrestaurant.utility.MyPlace;

import favorite.AddPlaceFavoriteActivity;

public class SinglePlaceActivity extends Activity {
	
	GPSTracker gps;
	
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;
	
	// Place Details
	PlaceDetails placeDetails;
	
	// Progress dialog
	ProgressDialog pDialog;
	
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place);
		
		gps = new GPSTracker(this);
		
		Intent i = getIntent();
		
		// Place referece id
		String reference = i.getStringExtra(KEY_REFERENCE);
		
		// Calling a Async Background thread
		new LoadSinglePlaceDetails().execute(reference);
	}

	public void addDatabase(View v){
		Bundle bundle = new Bundle();
		MyPlace place = new MyPlace();
		place.name = placeDetails.result.name;
		place.formatted_address = placeDetails.result.formatted_address;
		place.formatted_phone_number = placeDetails.result.formatted_phone_number;
		place.id = placeDetails.result.id;
		place.Note = "";
		
		place.lat = gps.latitude;
		place.lng = gps.longitude;
		bundle.putSerializable("place", place);
		Intent it = new Intent(this,AddPlaceFavoriteActivity.class);
		it.putExtras(bundle);
		startActivity(it);
	}
	
	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SinglePlaceActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Profile JSON
		 * */
		protected String doInBackground(String... args) {
			String reference = args[0];
			
			// creating Places class object
			googlePlaces = new GooglePlaces();

			// Check if used is connected to Internet
			try {
				placeDetails = googlePlaces.getPlaceDetails(reference);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					if(placeDetails != null){
						String status = placeDetails.status;
						
						// check place deatils status
						// Check for all possible status
						if(status.equals("OK")){
							if (placeDetails.result != null) {
								String name = placeDetails.result.name;
								String address = placeDetails.result.formatted_address;
								String phone = placeDetails.result.formatted_phone_number;
								String latitude = Double.toString(placeDetails.result.geometry.location.lat);
								String longitude = Double.toString(placeDetails.result.geometry.location.lng);
								
								Log.d("Place ", name + address + phone + latitude + longitude);
								
								// Displaying all the details in the view
								// single_place.xml
								TextView lbl_name = (TextView) findViewById(R.id.tvName);
								TextView lbl_address = (TextView) findViewById(R.id.tvAddress);
								TextView lbl_phone = (TextView) findViewById(R.id.tvPhone);
								TextView lbl_location = (TextView) findViewById(R.id.location);
								Button btn = (Button) findViewById(R.id.search);

								
								// Check for null data from google
								// Sometimes place details might missing
								name = name == null ? "Not present" : name; // if name is null display as "Not present"
								address = address == null ? "Not present" : address;
								phone = phone == null ? "Not present" : phone;
								latitude = latitude == null ? "Not present" : latitude;
								longitude = longitude == null ? "Not present" : longitude;
								
								lbl_name.setText(name);
								lbl_address.setText(address);
								lbl_address.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										Double dLat = placeDetails.result.geometry.location.lat;
										Double dLng = placeDetails.result.geometry.location.lng;
										Double sLat = gps.latitude;
										Double sLng = gps.longitude;
										
										if(sLat != 0 && sLng !=0 && dLat != 0 && dLng != 0  ){
											String uriStr = String.format("http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f",sLat,sLng,dLat,dLng);
											Intent p = new Intent();
											p.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
											p.setAction(android.content.Intent.ACTION_VIEW);
											p.setData(Uri.parse(uriStr));
											startActivity(p);
										}else{
											Toast.makeText(SinglePlaceActivity.this,
															getString(R.string.NavigateFail), 1).show();
										}
									}
								});
								lbl_phone.setText(phone);
								lbl_phone.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										final String a = placeDetails.result.formatted_phone_number;
										Uri uri = Uri.parse("tel:"+a);
										Intent intent = new Intent(Intent.ACTION_DIAL,uri);
										startActivity(intent);
									}
								});
								lbl_location.setText(Html.fromHtml("<b>"+R.string.Latitude+":</b> " + latitude +
																	", <b>"+R.string.Longitude+":</b> " + longitude));
								btn.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										final String b = placeDetails.result.name;
										final String c = placeDetails.result.formatted_phone_number;
										String url="https://www.google.com.tw/#q="+b+" "+c;

										Intent ie = new Intent(Intent.ACTION_VIEW,Uri.parse(url));

										startActivity(ie);
									}
								});
			
							}
						}
						else if(status.equals("ZERO_RESULTS")){
							alert.showAlertDialog(SinglePlaceActivity.this, "Near Places",
									"Sorry no place found.",
									false);
						}
						else if(status.equals("UNKNOWN_ERROR"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry unknown error occured.",
									false);
						}
						else if(status.equals("OVER_QUERY_LIMIT"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry query limit to google places is reached",
									false);
						}
						else if(status.equals("REQUEST_DENIED"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Request is denied",
									false);
						}
						else if(status.equals("INVALID_REQUEST"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Invalid Request",
									false);
						}
						else
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured.",
									false);
						}
					}else{
						alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
								"Sorry error occured.",
								false);
					}
					
					
				}
			});

		}

	}

}

