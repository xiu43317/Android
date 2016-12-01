package com.example.findrestaurant;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Customize extends Activity {

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;

	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;

	// Places List
	PlacesList nearPlaces;

	// GPS Location
	GPSTracker gps;

	// Button
	Button btnShowOnMap;

	// Progress dialog
	ProgressDialog pDialog;

	// Places Listview
	ListView lv;

	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String, String>>();

	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; // name of the place
	public static String KEY_VICINITY = "vicinity"; // Place area name
	public static String KEY_DISTANCE = "distance";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(Customize.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// creating GPS Class object
		gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude()
					+ ", longitude: " + gps.getLongitude());
		} else {
			// Can't get user's current location
			alert.showAlertDialog(Customize.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// stop executing code by return
			return;
		}

		// Getting listview
		lv = (ListView) findViewById(R.id.list);

		// button show on map
		btnShowOnMap = (Button) findViewById(R.id.btn_show_map);

		// calling background Async task to load Google Places
		// After getting places from Google all the data is shown in listview
		new LoadPlaces().execute();

		/** Button click event for shown on map */
		btnShowOnMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Bundle location = Customize.this.getIntent().getExtras();
				double lat = location.getDouble("Latitude");
				double lng = location.getDouble("Longitude");

				Bundle bundle = new Bundle();
				bundle.putDouble("Latitude", lat);
				bundle.putDouble("Longitude", lng);
				
				Intent i = new Intent(Customize.this,
						PlacesMapActivity2.class);
				// Sending user current geo location
				i.putExtras(bundle);

				startActivity(i);
			}
		});

		/**
		 * ListItem click event On selecting a listitem SinglePlaceActivity is
		 * launched
		 * */
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String reference = ((TextView) view
						.findViewById(R.id.reference)).getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SinglePlaceActivity.class);

				// Sending place refrence id to single place activity
				// place refrence id used to get "Place full details"
				in.putExtra(KEY_REFERENCE, reference);
				startActivity(in);
			}
		});
	}

	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadPlaces extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Customize.this);
			pDialog.setMessage(Html
					.fromHtml("<b>Search</b><br/>Loading Places..."));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			// creating Places class object
			googlePlaces = new GooglePlaces();

			try {
				// Separeate your place types by PIPE symbol "|"
				// If you want all types places make it as null
				// Check list of types supported by google
				//
				String types = "restaurant"; // Listing places only cafes,
												// restaurants

				// Radius in meters - increase this value if you don't find any
				// places
				double radius = 500; // 1000 meters

				// get nearest places
				Bundle location = Customize.this.getIntent().getExtras();
				double lat = location.getDouble("Latitude");
				double lng = location.getDouble("Longitude");
				nearPlaces = googlePlaces.search(lat, lng, radius, types);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog and show
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * from background thread, otherwise you will get error
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
					// Get json response status
					String status = nearPlaces.status;

					// Check for all possible status
					if (status.equals("OK")) {
						// Successfully got places details
						if (nearPlaces.results != null) {
							// loop through each place
							for (Place p : nearPlaces.results) {
								HashMap<String, String> map = new HashMap<String, String>();
								// Place reference won't display in listview -
								// it will be hidden
								// Place reference is used to get
								// "place full details"

								map.put(KEY_REFERENCE, p.reference);
								Bundle location = Customize.this.getIntent().getExtras();
								double sLat = location.getDouble("Latitude");
								double sLng = location.getDouble("Longitude");
								double dLat = p.geometry.location.lat;
								double dLng = p.geometry.location.lng;
								if (sLat != 0 && sLng != 0 && dLat != 0
										&& dLng != 0) {
									float[] result = new float[1];
									Location.distanceBetween(sLat, sLng, dLat,
											dLng, result);
									int a = (int) (result[0]);
									String s = String.valueOf(a);
									map.put(KEY_DISTANCE,
											getString(R.string.Distance)+":" + s + getString(R.string.Meter));
								}

								// Place name
								map.put(KEY_NAME, getString(R.string.Name)+":" + p.name);

								// adding HashMap to ArrayList
								placesListItems.add(map);
							}
							// list adapter
							ListAdapter adapter = new SimpleAdapter(
									Customize.this, placesListItems,
									R.layout.list_item, new String[] {
											KEY_REFERENCE, KEY_NAME,
											KEY_DISTANCE }, new int[] {
											R.id.reference, R.id.name,
											R.id.rating });

							// Adding data into listview
							lv.setAdapter(adapter);
						}
					} else if (status.equals("ZERO_RESULTS")) {
						// Zero results found
						alert.showAlertDialog(
								Customize.this,
								"Near Places",
								"Sorry no places found. Try to change the types of places",
								false);
					} else if (status.equals("UNKNOWN_ERROR")) {
						alert.showAlertDialog(Customize.this, "Places Error",
								"Sorry unknown error occured.", false);
					} else if (status.equals("OVER_QUERY_LIMIT")) {
						alert.showAlertDialog(
								Customize.this,
								"Places Error",
								"Sorry query limit to google places is reached",
								false);
					} else if (status.equals("REQUEST_DENIED")) {
						alert.showAlertDialog(Customize.this, "Places Error",
								"Sorry error occured. Request is denied", false);
					} else if (status.equals("INVALID_REQUEST")) {
						alert.showAlertDialog(Customize.this, "Places Error",
								"Sorry error occured. Invalid Request", false);
					} else {
						alert.showAlertDialog(Customize.this, "Places Error",
								"Sorry error occured.", false);
					}
				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
