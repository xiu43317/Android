package com.example.findrestaurant.utility;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GPSservice extends Service{
	private LocationManager lmgr;
	private myGPSListener listenter;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		lmgr = (LocationManager)getSystemService(LOCATION_SERVICE);
		listenter = new myGPSListener();
		//request every 1 seconds or 2meters
		lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
									1000, 0, listenter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		lmgr.removeUpdates(listenter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	private class myGPSListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			//broadcast latitude and longitude to other activity
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			Log.i("Log.i",lat+ "*"+lng);
			Intent it = new Intent("gpsinfo");
			it.putExtra("latitude", lat);
			it.putExtra("longitude", lng);
			sendBroadcast(it);
		}

		@Override
		public void onProviderDisabled(String provider) {
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Toast.makeText(getApplicationContext(), "Unable to reach Location right now!",
					       Toast.LENGTH_SHORT).show();
		}
		
	}
}
