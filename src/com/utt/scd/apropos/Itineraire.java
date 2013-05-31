package com.utt.scd.apropos;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.utt.scd.R;
import com.utt.scd.SCD;

public class Itineraire extends SherlockFragmentActivity 
{
	private GoogleMap gMap;

	
	@Override
    protected void onCreate(Bundle arg0) 
    {
    	setTheme(SCD.THEME);
		super.onCreate(arg0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.itineraire);
		
		getSupportActionBar().setHomeButtonEnabled(true);
		gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(new Criteria(), true);

		Location locations = locationManager.getLastKnownLocation(provider);
		List<String>  providerList = locationManager.getAllProviders();
		if(null!=locations && null!=providerList && providerList.size()>0)
		{                 
			double longitude = locations.getLongitude();
			double latitude = locations.getLatitude();
			Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.FRANCE);    
			
			String _location = null;
			
			try 
			{
			    List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
			    
			    if(null!=listAddresses&&listAddresses.size()>0)
			    {
			    	System.out.println(listAddresses.get(0));
			        _location = listAddresses.get(0).getAddressLine(0) + " " + listAddresses.get(0).getAddressLine(1) ;
			    }
			} 
			catch (IOException e) 
			{
			    e.printStackTrace();
			}
	
		
			String destination = "Université de Technologie de Troyes, Troyes";
			
			System.out.println(_location);
			
			if (_location != null)
			{
				//Appel de la méthode asynchrone
		        new ItineraireTask(this, gMap, _location, destination).execute();
				//new ItineraireTask(this, gMap, "Paris", "Marseille").execute();
			}
			else
			{
				Toast.makeText(this, "Votre location est inconnue", Toast.LENGTH_LONG).show();
			}
			
	    }
    }

	
}
