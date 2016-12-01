package com.example.findrestaurant;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findrestaurant.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlacesMapActivity extends Activity {
	private static final String GOOGLE_API_KEY = "AIzaSyCCy3bFOeihgVNPwDf8D0t-JYqETj3E0Uw";
	GoogleMap googleMap;
	EditText placeText;
	double latitude = 0;
	double longitude = 0;
	private int PROXIMITY_RADIUS = 500;
	LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		



		// show error dialog if GoolglePlayServices not available
		if (!isGooglePlayServicesAvailable()) {
			finish();
		}
		setContentView(R.layout.activity_google_places);
//		placeText = (EditText) findViewById(R.id.placeText);
		Button btnFind = (Button) findViewById(R.id.btnFind);
		MapFragment fragment = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.googleMap));
		googleMap = fragment.getMap();
		googleMap.setMyLocationEnabled(true);
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String bestProvider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		
		Bundle position = PlacesMapActivity.this.getIntent().getExtras();
		double lat = position.getDouble("Latitude");
		double lng = position.getDouble("Longitude");
		LatLng latLng = new LatLng(lat, lng);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(latLng);
		markerOptions.title("�ڦb�o��");
		markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		googleMap.addMarker(markerOptions);



		btnFind.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				String type = placeText.getText().toString();
				
				Bundle position = PlacesMapActivity.this.getIntent().getExtras();
				double lat = position.getDouble("Latitude");
				double lng = position.getDouble("Longitude");

				String type = "restaurant";
				StringBuilder googlePlacesUrl = new StringBuilder(
						"https://maps.googleapis.com/maps/api/place/search/json?language=zh-tw&");
				googlePlacesUrl
						.append("location=" + lat + "," + lng);
				googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
				googlePlacesUrl.append("&types=" + type);
				googlePlacesUrl.append("&sensor=true");
				googlePlacesUrl.append("&key=" + GOOGLE_API_KEY);

				GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
				Object[] toPass = new Object[2];
				toPass[0] = googleMap;
				toPass[1] = googlePlacesUrl.toString();
				googlePlacesReadTask.execute(toPass);
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(locationListener);

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String best = locationManager.getBestProvider(new Criteria(), true);
		if(best != null){
			locationManager.requestLocationUpdates(best, 5000, 5, locationListener);
		}else{
			Toast.makeText(getBaseContext(), "�L�k���U�w��", 0).show();
		}
	}

	private boolean isGooglePlayServicesAvailable() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (ConnectionResult.SUCCESS == status) {
			return true;
		} else {
			GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
			return false;
		}
	}

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {



			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	};

	public class GooglePlacesReadTask extends
			AsyncTask<Object, Integer, String> {
		String googlePlacesData = null;
		GoogleMap googleMap;

		@Override
		protected String doInBackground(Object... inputObj) {
			try {
				googleMap = (GoogleMap) inputObj[0];
				String googlePlacesUrl = (String) inputObj[1];
				Http http = new Http();
				googlePlacesData = http.read(googlePlacesUrl);
			} catch (Exception e) {
				Log.d("Google Place Read Task", e.toString());
			}
			return googlePlacesData;
		}

		@Override
		protected void onPostExecute(String result) {
			PlacesDisplayTask placesDisplayTask = new PlacesDisplayTask();
			Object[] toPass = new Object[2];
			toPass[0] = googleMap;
			toPass[1] = result;
			placesDisplayTask.execute(toPass);
		}
		
	}
	
}
