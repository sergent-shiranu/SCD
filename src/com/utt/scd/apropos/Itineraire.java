package com.utt.scd.apropos;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.utt.scd.R;
import com.utt.scd.SCD;

public class Itineraire extends SherlockFragmentActivity 
{

	List<Overlay> mapOverlays;
    GeoPoint point1, point2;
    LocationManager locManager;
    Drawable drawable;
    Document document;
    GMapV2GetRouteDirection v2GetRouteDirection;
    LatLng fromPosition;
    LatLng toPosition;
    GoogleMap mGoogleMap;
    MarkerOptions markerOptions;
    Location location ;
	
	@Override
    protected void onCreate(Bundle arg0) 
    {
    	setTheme(SCD.THEME);
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.itineraire);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		
		
		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(new Criteria(), true);

		double location_longitude = 0;
		double location_latitude = 0;
		Location locations = locationManager.getLastKnownLocation(provider);
		List<String>  providerList = locationManager.getAllProviders();
		if(null!=locations && null!=providerList && providerList.size()>0)
		{                 
			location_longitude = locations.getLongitude();
			location_latitude = locations.getLatitude();  	
		}
		
		System.out.println(location_longitude + " " + location_latitude);
		
		double destionation_longitude = 4.0669048;
		double destionation_latitude = 48.2690833;
		
		// Enabling MyLocation in Google Map
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
        //mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        /*markerOptions = new MarkerOptions();
        fromPosition = new LatLng(location_latitude, location_longitude);
        toPosition = new LatLng(destionation_latitude, destionation_longitude);
        GetRouteTask getRoute = new GetRouteTask();
        getRoute.execute();*/
		
		new ItineraireTask(this, mGoogleMap, new LatLng(location_latitude, location_longitude), new LatLng(destionation_latitude, destionation_longitude)).execute();
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
			case android.R.id.home:
	
				this.finish();
	
				return true;
	
		};

		return false;
	}

	
	/**
    *
    * @author VIJAYAKUMAR M
    * This class Get Route on the map
    *
    */
   private class GetRouteTask extends AsyncTask<String, Void, String> 
   {
        
         private ProgressDialog Dialog;
         String response = "";
         @Override
         protected void onPreExecute() 
         {
               Dialog = new ProgressDialog(Itineraire.this);
               Dialog.setMessage("Loading route...");
               Dialog.show();
         }

         @Override
         protected String doInBackground(String... urls) 
         {
               //Get All Route values
                     document = v2GetRouteDirection.getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_WALKING);
                     response = "Success";
               return response;

         }

         @Override
         protected void onPostExecute(String result) 
         {
               mGoogleMap.clear();
               System.out.println(response + "xxx");
               if(response.equalsIgnoreCase("Success"))
               {
	               ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
	               PolylineOptions rectLine = new PolylineOptions().width(10).color(
	                           Color.RED);
	
	               for (int i = 0; i < directionPoint.size(); i++) 
	               {
	                     rectLine.add(directionPoint.get(i));
	               }
	               // Adding route on the map
	               mGoogleMap.addPolyline(rectLine);
	               markerOptions.position(toPosition);
	               markerOptions.draggable(true);
	               mGoogleMap.addMarker(markerOptions);

               }
              
               Dialog.dismiss();
         }
   }

	
}
